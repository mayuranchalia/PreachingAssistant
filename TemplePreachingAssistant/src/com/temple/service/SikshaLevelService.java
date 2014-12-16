package com.temple.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.temple.repository.IRepositoryEntityTypes;
import com.temple.repository.IRepositroyCommunication;
import com.temple.repository.RepositoryCommunicationFactory;

/**
 * @author Mayur Jain
 *
 */
@Path("/sikshaService")
public class SikshaLevelService {
	final static Logger logger = Logger.getLogger(SikshaLevelService.class);
	@GET
	@Path("/addNewSiksha")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addNewSiksha(@QueryParam("sikshaName") String sikshaName,
			@QueryParam("sikshaDesc") String sikshaDesc) {
		logger.debug("Calling addNewSiksha");
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "Insert into " + IRepositoryEntityTypes.SIKSHA_LEVEL
					+ "(SLName,SLDescription)" + " values(?,?)";
			logger.debug(SQL);
			statement = repository.getPreparedStatement(SQL);
			statement.setString(1, sikshaName);
			statement.setString(2, sikshaDesc);
			if (statement.executeUpdate() > 0) {
				return Response
						.ok()
						.entity("sucessfully saved the "
								+ "new siksha to repository").build();
			} else {
				logger.error("Failed to save the " + "siksha to repository");
				return Response.serverError()
						.entity("Failed to save the " + "siksha to repository")
						.build();
			}
		} catch (Exception e) {
			logger.error("Problem Occured at server side", e);
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()).build();
		} finally {
			if (repository != null)
				try {
					repository.close();
				} catch (Exception e) {
					logger.error("Problem Occured at server side", e);
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "server side\n.Details:"+e.getMessage()).build();
				}
		}

	}
	
	@GET
	@Path("/getSikshaLevels")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSikshaLevels() {
		logger.debug("Calling getSikshaLevels");
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "Select SLNAME from " + IRepositoryEntityTypes.SIKSHA_LEVEL;
			logger.debug(SQL);
			statement = repository.getPreparedStatement(SQL);
			ResultSet result=statement.executeQuery();
			
			JSONArray array = new JSONArray();
			while(result.next()){
				JSONObject response=new JSONObject();
				response.put("name", result.getString("SLNAME"));
				response.put("id", result.getString("SLNAME"));
				array.put(response);
			}
			return Response.ok().entity(array.toString()).build();
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
					repository.close();
				} catch (Exception e) {
					logger.error("Problem Occured at server side", e);
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "server side\n.Details:"+e.getMessage()).build();
				}
		}

	}

}
