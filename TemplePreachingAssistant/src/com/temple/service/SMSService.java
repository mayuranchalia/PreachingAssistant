package com.temple.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.temple.common.IConfiguration;
import com.temple.repository.IRepositoryEntityTypes;
import com.temple.repository.IRepositroyCommunication;
import com.temple.repository.RepositoryCommunicationFactory;

/**
 * @author Mayur Jain
 *
 */
@Path("/smsService")
public class SMSService {
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	final static Logger logger = Logger.getLogger(SMSService.class);
	
	@GET
	@Path("/sendSMS")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendSMS(@QueryParam("mobileNos")String mobileNos , @QueryParam("smsText") String smsText,
			@QueryParam("programID") int ProgramID) {
		logger.debug("calling sendSMS");
		int smsCount=0;
		if(mobileNos!=null && !mobileNos.isEmpty()){
			smsCount=mobileNos.split(",").length;
		}else{
			return Response.serverError()
					.entity("Mobile no's can't be empty.").build();
		}
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(classloader
					.getResourceAsStream("sms_files/sms_send.xml"));
			
			//NodeList numbers=doc.getElementsByTagName("to");
			Node sendSMS = doc.getElementsByTagName("sendSMS").item(0);
			NodeList list = sendSMS.getChildNodes(); 
			for (int i = 0; i < list.getLength(); i++) {
	                   Node node = list.item(i);
			   if ("to".equals(node.getNodeName())) {
				node.setTextContent(mobileNos);
			   }
			   if ("msgid".equals(node.getNodeName())) {
					node.setTextContent(mobileNos);
				   }
			   if ("text".equals(node.getNodeName())) {
					node.setTextContent(smsText);
				   }
			}
			
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(domSource, result);
			logger.debug("GateWay post xml: "+writer.toString());
			int responseCode=sendPost(writer.toString(), ProgramID, smsText, smsCount);
			if(responseCode==200){
				return Response.ok().entity("Successfully submitted sms.").build();
			}else if(responseCode==500){
				return Response.ok().entity("Successfully submitted sms but there was error while saving in DB.").build();
			}else {
			return Response.serverError().entity("Some error occured while submitting the sms.").build();
			}
		} catch (Exception e) {
			logger.error("Problem occured during xml retrival", e);
			return Response.serverError()
					.entity("Problem occured during xml retrival" + e).build();
		}
	}
	
	@GET
	@Path("/getSMSDeliveryStatus")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSMSDeliveryStatus(@QueryParam("smsID") int smsID) {
		logger.debug("Calling getSMSDeliveryStatus");
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		Response response=null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "Select  SLText,SMS_RESPONSE,SLTimestamp from " + IRepositoryEntityTypes.SMS_LOG
					+ " where SLSMSID=?";
			logger.debug(SQL);
			statement = repository.getPreparedStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, smsID);
			ResultSet result=statement.executeQuery();
			Blob smsResponse=null;
			while(result.next()){
				 smsResponse=result.getBlob("SMS_RESPONSE");
			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(smsResponse.getBinaryStream());
			
			//NodeList numbers=doc.getElementsByTagName("to");
			Node sendSMS = doc.getElementsByTagName("response").item(0);
			NodeList list = sendSMS.getChildNodes(); 
			int devoteeID=0;
			String deliveryStatus=null;
			for (int i = 0; i < list.getLength(); i++) {
	                   Node node = list.item(i);
	                   if ("to".equals(node.getNodeName())) {
	                	   logger.debug("Mobile No#: "+node.getTextContent());
	                	   response =DevoteeService.getProgramParticipants(node.getTextContent());
	                	   if(response.getStatus()==200){
	                		JSONArray data = new JSONArray(response.getEntity().toString());
	                			JSONObject object = new JSONObject(data.get(0).toString());
	                			devoteeID = Integer.parseInt(object.get("id").toString());
	                			logger.debug("Devotee ID#: "+devoteeID);	                			
	                	   }else{
	                		   logger.error("Problem Occured at server side\n.Reason: Not able to retrieve DevoteeID from"
	               							+ "mobile no# "+node.getTextContent());
	                		   return Response
	               					.serverError()
	               					.entity("Problem Occured at server side\n.Reason: Not able to retrieve DevoteeID from"
	               							+ "mobile no# "+node.getTextContent()).build();
	                	   }
	       			   }
	                   if ("msgid".equals(node.getNodeName())) {
	                	   logger.debug("MSG ID#: "+node.getTextContent());	                	   
	                	   deliveryStatus = getDilevryStatus(node.getTextContent());
	                	   deliveryStatus = deliveryStatus.substring(deliveryStatus.lastIndexOf(" ")+1);
	                	   logger.debug("Delivery Status#: "+deliveryStatus);
	                	   response=addDevoteeSMSStatus(devoteeID, smsID, deliveryStatus);
	       			   }
			}
			if(response.getStatus()==200){
				return Response.ok().entity("Successfully generated SMS Status for SMS ID: "+smsID).build();
			}else{
				logger.error("Error occured while generating SMS Status for SMS ID"+smsID);
			return Response.serverError().entity("Error occured while generating SMS Status for SMS ID"+smsID).build();
			}
		} catch (Exception e) {
			logger.error("Problem Occured at server side\n.Reason:", e);
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()).build();
		} finally {
			if (repository != null)
				try {
					// statement.close();
					repository.close();
				} catch (Exception e) {
					logger.error("Problem Occured at "
									+ "DB Connection Close.",e);
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "DB Connection Close \n.Details:"+e.getMessage()).build();
				}
		}

	}
	
	
	private Response addSmsLog(int programID,String smsText,int smsCount,InputStream xmlResponse) {
		logger.debug("Calling addSmsLog");
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "Insert into " + IRepositoryEntityTypes.SMS_LOG
					+ "(SLProgramID,SLText" + ",SLTimestamp,SLSMSSentCount,SMS_RESPONSE)"
					+ " values(?,?,?,?,?)";
			statement = repository.getPreparedStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);
			logger.debug(SQL);
			statement.setInt(1, programID);
			statement.setString(2, smsText);
			statement.setTimestamp(3,
					new Timestamp(new java.util.Date().getTime()));
			statement.setInt(4, smsCount);
			statement.setBlob(5, xmlResponse);
			if (statement.executeUpdate() > 0) {
				ResultSet tableKeys = statement.getGeneratedKeys();
				tableKeys.next();
				int autoGeneratedID = tableKeys.getInt(1);
				logger.debug("Sucessfully saved the " + "SMS LOG ,SMS ID= "
						+ autoGeneratedID + " to repository.");
				return Response
						.ok()
						.entity("Sucessfully saved the " + "SMS LOG ,SMS ID= "
								+ autoGeneratedID + " to repository.").build();
			} else {
				logger.error("Failed to save the "
								+ "SMS LOG to repository.");
				return Response
						.serverError()
						.entity("Failed to save the "
								+ "SMS LOG to repository.").build();
			}
		} catch (Exception e) {
			logger.error("Problem Occured at server side",e);
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()).build();
		} finally {
			if (repository != null)
				try {
					// statement.close();
					repository.close();
				} catch (Exception e) {
					logger.error("Problem Occured at DB Connection Close",e);
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "DB Connection Close \n.Details:"
									+ e.getMessage()).build();
				}
		}

	}

	
	
	protected String getString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }

        return null;
    }
		// HTTP GET request
		private String getDilevryStatus(String msgID) throws Exception {
			logger.debug("Calling getDilevryStatus");
			String url = "http://alerts.icisms.in/api/status.php?workingkey="+IConfiguration.SMS_GATEWAY_KEY+"&messageid="+msgID;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
	 
			//int responseCode = con.getResponseCode();
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
	 
		}
		
		private int sendPost(String payload, int ProgramID,String smsText,int count) throws Exception {
			logger.debug("Calling sendPost");
			String url = "http://alerts.icisms.in/api/xmlapi.php";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
			String urlParameters = "data="+payload;
	 
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	 
			int responseCode = con.getResponseCode();
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			logger.debug(response.toString());
			InputStream stream = new ByteArrayInputStream(response.toString().getBytes(StandardCharsets.UTF_8));
			if(responseCode==200){
			Response smsResponse=addSmsLog(ProgramID, smsText, count, stream);
			if(smsResponse.getStatus()==200){
				responseCode=200;
				}else{
					responseCode=500;
				}
			}
			return responseCode;
	 
		}
		
		private Response addDevoteeSMSStatus(int devoteeID,int smsID,String deliveryStatus){
			logger.debug("Calling addDevoteeSMSStatus");
			IRepositroyCommunication repository = null;
			PreparedStatement statement = null;
			try {
				repository = RepositoryCommunicationFactory.newInstance();
				String SQL = "Insert into " + IRepositoryEntityTypes.DEVOTEE_SMS
						+ " (DSDevoteeID,DSSMSID,DSDeliveryStatus)"
						+ " values(?,?,?)";
				statement = repository.getPreparedStatement(SQL);
				logger.debug(SQL);
				statement.setInt(1, devoteeID);
				statement.setInt(2, smsID);
				statement.setString(3, deliveryStatus);
				if (statement.executeUpdate() > 0) {
					logger.debug("Sucessfully saved the " + "Devotee SMS to repository.");
					return Response
							.ok()
							.entity("Sucessfully saved the " + "Devotee SMS to repository.").build();
				} else {
					logger.error("Failed to save the "
									+ "Devotee SMS to repository.");
					return Response
							.serverError()
							.entity("Failed to save the "
									+ "Devotee SMS to repository.").build();
				}
			} catch (Exception e) {
				logger.error("Problem Occured at server side",e);
				return Response
						.serverError()
						.entity("Problem Occured at "
								+ "server side\n.Reason:"
								+ e.getMessage()).build();
			} finally {
				if (repository != null)
					try {
						// statement.close();
						repository.close();
					} catch (Exception e) {
						logger.error("Problem Occured at DB Connection Close",e);
						return Response
								.serverError()
								.entity("Problem Occured at "
										+ "DB Connection Close \n.Details:"
										+ e.getMessage()).build();
					}
			}
		} 
		
		public static void main(String[] args) {
			SMSService service = new SMSService();
			try {
				service.getDilevryStatus("972406510-1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
}
