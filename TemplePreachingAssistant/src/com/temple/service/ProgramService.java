package com.temple.service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.temple.repository.IRepositoryEntityTypes;
import com.temple.repository.IRepositroyCommunication;
import com.temple.repository.RepositoryCommunicationFactory;
import com.temple.util.TempleUtility;

/**
 * @author Mayur Jain
 *
 */

@Path("/programService")
public class ProgramService {

	@GET
	@Path("/addNewProgramType")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNewProgramType(
			@QueryParam("programType") String programType,
			@QueryParam("programDesc") String programDesc) {
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "Insert into " + IRepositoryEntityTypes.PROGRAM_TYPE
					+ "(PTProgramType,PTProgramDesc)" + " values(?,?)";
			statement = repository.getPreparedStatement(SQL);
			statement.setString(1, programType);
			statement.setString(2, programDesc);
			if (statement.executeUpdate() > 0) {
				return Response
						.ok()
						.entity("sucessfully saved the "
								+ "program type to repository").build();
			} else {
				return Response
						.serverError()
						.entity("Failed to save the "
								+ "program type to repository").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()
							+ "\nDetails:"+e.getMessage()).build();
		} finally {
			if (repository != null)
				try {
					repository.close();
				} catch (Exception e) {
					e.printStackTrace();
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "server side\n.Details:"+e.getMessage()).build();
				}
		}

	}

	@GET
	@Path("/getProgramTypes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProgramType() {
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "SELECT PTProgramType from " + IRepositoryEntityTypes.PROGRAM_TYPE;
			statement = repository.getPreparedStatement(SQL);
			ResultSet result=statement.executeQuery();
			
			JSONArray array = new JSONArray();
			while(result.next()){
				JSONObject response=new JSONObject();
				response.put("name", result.getString("PTProgramType"));
				response.put("id", result.getString("PTProgramType"));
				array.put(response);
			}
			return Response.ok().entity(array.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()
							+ "\nDetails:"+e.getMessage()).build();
		} finally {
			if (repository != null)
				try {
					repository.close();
				} catch (Exception e) {
					e.printStackTrace();
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "server side\n.Details:"+e.getMessage()).build();
				}
		}

	}
	
	@GET
	@Path("/getProgramNames")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProgramNames() {
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "SELECT PMProgramID,PMProgramName from " + IRepositoryEntityTypes.PROGRAM_MASTER;
			statement = repository.getPreparedStatement(SQL);
			ResultSet result=statement.executeQuery();
			
			JSONArray array = new JSONArray();
			while(result.next()){
				JSONObject response=new JSONObject();
				response.put("name", result.getString("PMProgramName"));
				response.put("id", result.getString("PMProgramID"));
				array.put(response);
			}
			return Response.ok().entity(array.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()
							+ "\nDetails:"+e.getMessage()).build();
		} finally {
			if (repository != null)
				try {
					repository.close();
				} catch (Exception e) {
					e.printStackTrace();
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "server side\n.Details:"+e.getMessage()).build();
				}
		}

	}
	
	@GET
	@Path("/getMentors")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMentors() {
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "SELECT MIMentorID,MIMentorInitiatedName from " + IRepositoryEntityTypes.MENTOR_INFO;
			statement = repository.getPreparedStatement(SQL);
			ResultSet result=statement.executeQuery();
			
			JSONArray array = new JSONArray();
			while(result.next()){
				JSONObject response=new JSONObject();
				response.put("name", result.getString("MIMentorInitiatedName"));
				response.put("id", result.getString("MIMentorID"));
				array.put(response);
			}
			return Response.ok().entity(array.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()
							+ "\nDetails:"+e.getMessage()).build();
		} finally {
			if (repository != null)
				try {
					repository.close();
				} catch (Exception e) {
					e.printStackTrace();
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "server side\n.Details:"+e.getMessage()).build();
				}
		}

	}
	
	@GET
	@Path("/programMaster")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNewProgramMaster(
			@QueryParam("programType") String programType,
			@QueryParam("programDesc") String programDesc,
			@QueryParam("programInterval") String programInterval,
			@QueryParam("mentorId") int mentorId,
			@QueryParam("assmentorId") int assmentorId,
			@QueryParam("programName") String programName) {
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "Insert into "
					+ IRepositoryEntityTypes.PROGRAM_MASTER
					+ "(PMProgramType,PMProgramDescription"
					+ ",PMProgramInterval,PMMentorID,PMAsstMentorID,PMProgramName)"
					+ " values(?,?,?,?,?,?)";

			statement = repository.getPreparedStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, programType);
			statement.setString(2, programDesc);
			statement.setString(3, programInterval);
			statement.setInt(4, mentorId);
			statement.setInt(5, assmentorId);
			statement.setString(6, programName);
			if (statement.executeUpdate() > 0) {
				ResultSet tableKeys = statement.getGeneratedKeys();
				tableKeys.next();
				int autoGeneratedID = tableKeys.getInt(1);
				return Response
						.ok()
						.entity("Sucessfully saved the "
								+ "program master with ID= " + autoGeneratedID
								+ " to repository").build();
			} else {
				return Response
						.serverError()
						.entity("Failed to save the "
								+ "program master to repository").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()
							+ "\nDetails:"+e.getMessage()).build();
		} finally {
			if (repository != null)
				try {
					statement.close();
					repository.close();
				} catch (Exception e) {
					e.printStackTrace();
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "server side\n.Details:"+e.getMessage()).build();
				}
		}

	}

	@GET
	@Path("/programParticipation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response programParticipation(
			@QueryParam("programId") int programId,
			@QueryParam("enrolementDate") String enrolementDate,
			@QueryParam("devoteeId") int devoteeId,
			@QueryParam("devoteeName") String devoteeName) {
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		Date enrolement = null;
		try {
			
			repository = RepositoryCommunicationFactory.newInstance();

			String SQL = "Insert into "
					+ IRepositoryEntityTypes.PROGRAM_PARTICIPATION
					+ " (PPDevoteeID,PPProgramID,PPEnrolementDate,PPDevoteeName)"
					+ " values(?,?,?,?)";
			try {
				enrolement = TempleUtility.getSQLDateFromString(enrolementDate);
			} catch (ParseException e) {
				return Response
						.serverError()
						.entity("Invalid date format provided for devotee enrolment date.")
						.build();
			}
			statement = repository.getPreparedStatement(SQL);
			statement.setInt(1, devoteeId);
			statement.setInt(2, programId);
			statement.setDate(3, enrolement);
			statement.setString(4, devoteeName);
			if (statement.executeUpdate() > 0) {
				return Response
						.ok()
						.entity("sucessfully saved the "
								+ "program participation to repository")
						.build();
			} else {
				return Response
						.serverError()
						.entity("Failed to save the "
								+ "program participation to repository")
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()).build();
		} finally {
			if (repository != null)
				try {
					statement.close();
					repository.close();
				} catch (Exception e) {
					e.printStackTrace();
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "server side\n.Details:"+e.getMessage()).build();
				}
		}

	}
	@GET
	@Path("/getProgramParticipants")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProgramParticipants(@QueryParam("programID") int programID) {
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "select PROGRAM_PARTICIPATION.PPDevoteeID , DEVOTEE_INFO.DILegalName, DEVOTEE_INFO.DIInitiatedName , DEVOTEE_INFO.DISmsPhone,DEVOTEE_INFO.DIArea from " +IRepositoryEntityTypes.DEVOTEE_INFO+"," +IRepositoryEntityTypes.PROGRAM_PARTICIPATION +" where PROGRAM_PARTICIPATION.PPDevoteeID=DEVOTEE_INFO.DIDevoteeID and PROGRAM_PARTICIPATION.PPProgramID=?" ;
			statement = repository.getPreparedStatement(SQL);
			statement.setInt(1, programID);
			ResultSet result=statement.executeQuery();
			
			JSONArray array = new JSONArray();
			while(result.next()){
				JSONObject response=new JSONObject();
				response.put("id", result.getString("PPDevoteeID"));
				response.put("lname", result.getString("DILegalName"));
				response.put("iname", result.getString("DIInitiatedName"));
				response.put("mobile", result.getString("DISmsPhone"));
				response.put("area", result.getString("DIArea"));
				array.put(response);
			}
			return Response.ok().entity(array.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
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
					e.printStackTrace();
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "server side\n.Details:"+e.getMessage()).build();
				}
		}

	}
	private int getDevoteeId(String mobileNo)throws Exception{
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		int devoteeId=0;
		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "select DIDevoteeID from  "
					+ IRepositoryEntityTypes.DEVOTEE_INFO+ " where DISmsPhone=?" ;
			statement = repository.getPreparedStatement(SQL);
			statement.setString(1, mobileNo);
			ResultSet result=statement.executeQuery();
			while(result.next()){
				devoteeId=result.getInt("DIDevoteeID");	
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (repository != null)
				try {
					statement.close();
					repository.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return devoteeId;
	}
	
	@GET
	@Path("/programAttendance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response programAttendance(
			@QueryParam("programId") int programId,
			@QueryParam("devoteeIds") String devoteeIds,
			@QueryParam("programdate") String programdate) {
		IRepositroyCommunication repository = null;
		PreparedStatement statement = null;
		Date date = null;

		try {
			repository = RepositoryCommunicationFactory.newInstance();
			String SQL = "Insert into "
					+ IRepositoryEntityTypes.PROGRAM_ATTENDANCE
					+ "(PAProgramID,PADevoteeID,PAProgramDate)"
					+ " values(?,?,?)";
			try {
				date = TempleUtility.getSQLDateFromString(programdate);
			} catch (ParseException e) {
				return Response
						.serverError()
						.entity("Invalid date format provided for program date.")
						.build();
			}
			String[]devoteesId= devoteeIds.split(",");
			
			statement = repository.getPreparedStatement(SQL);
			for(String devoteeId:devoteesId){
			statement.setInt(1,programId);
			statement.setInt(2, Integer.parseInt(devoteeId));
			statement.setDate(3, date);
			statement.addBatch();
			}
			if (statement.executeBatch().length ==devoteesId.length) {
				return Response
						.ok()
						.entity("sucessfully saved the "
								+ "program attendance to repository").build();
			} else {
				return Response
						.serverError()
						.entity("Failed to save the "
								+ "program attendance to repository").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response
					.serverError()
					.entity("Problem Occured at "
							+ "server side\n.Reason:"
							+ e.getMessage()).build();
		} finally {
			if (repository != null)
				try {
					statement.close();
					repository.close();
				} catch (Exception e) {
					e.printStackTrace();
					return Response
							.serverError()
							.entity("Problem Occured at "
									+ "server side\n.Details:"+e.getMessage()).build();
				}
		}

	}
}
