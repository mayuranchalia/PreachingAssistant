package com.temple.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.temple.repository.IRepositoryEntityTypes;
import com.temple.repository.IRepositroyCommunication;
import com.temple.repository.RepositoryCommunicationFactory;

@Path("/smsService")
public class SMSService {
	private final String USER_AGENT = "Mozilla/5.0";
	@GET
	@Path("/addSmsLog")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addSmsLog(@QueryParam("programID") int programID,
			@QueryParam("smsText") String smsText,
			@QueryParam("smsCount") int smsCount) {
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "Insert into " + IRepositoryEntityTypes.SMS_LOG
					+ "(SLProgramID,SLText" + ",SLTimestamp,SLSMSSentCount)"
					+ " values(?,?,?,?)";
			statement = repository.getPreparedStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, programID);
			statement.setString(2, smsText);
			statement.setTimestamp(3,
					new Timestamp(new java.util.Date().getTime()));
			statement.setInt(4, smsCount);
			if (statement.executeUpdate() > 0) {
				ResultSet tableKeys = statement.getGeneratedKeys();
				tableKeys.next();
				int autoGeneratedID = tableKeys.getInt(1);
				return Response
						.ok()
						.entity("Sucessfully saved the " + "SMS LOG ,SMS ID= "
								+ autoGeneratedID + " to repository.").build();
			} else {
				return Response
						.serverError()
						.entity("Failed to save the "
								+ "SMS LOG to repository.").build();
			}
		} catch (Exception e) {
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()
							+ "\nDetails:"
							+ Arrays.toString(e.getStackTrace()).replace(",",
									"\n")).build();
		} finally {
			if (repository != null)
				try {
					// statement.close();
					repository.close();
				} catch (Exception e) {
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "DB Connection Close \n.Details:"
									+ Arrays.toString(e.getStackTrace())
											.replace(",", "\n")).build();
				}
		}

	}

	@GET
	@Path("/getSmsXml")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSmsXml(@Context HttpServletRequest request) {
		/*String contextPath = request.getSession().getServletContext()
				.getRealPath("/");*/
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
				node.setTextContent("9986182719");
			   }
			}
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			System.out.println(getString("to",doc.getDocumentElement()));
			System.out.println("XML IN String format is: \n"
					+ writer.toString());
			sendGet();
			return Response.ok().entity(writer.toString()).build();
		} catch (Exception e) {
			return Response.serverError()
					.entity("Problem occured during xml retrival" + e).build();
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
		private void sendGet() throws Exception {
	 
			String url = "http://www.google.com/search?q=mkyong";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
	 
		}
		
		private void sendPost() throws Exception {
			 
			String url = "https://selfsolve.apple.com/wcResults.do";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	 
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
			String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
	 
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			System.out.println(response.toString());
	 
		}
	 
}