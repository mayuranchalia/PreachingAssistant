package com.temple.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mayur Jain
 *
 */
@XmlRootElement(name="programAttendance")
public class ProgramAttendance {

	private String prgramDate;
	private String programId;
	private String devoteeId;
	private boolean attended;

	public String getPrgramDate() {
		return prgramDate;
	}

	public void setPrgramDate(String prgramDate) {
		this.prgramDate = prgramDate;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getDevoteeId() {
		return devoteeId;
	}

	public void setDevoteeId(String devoteeId) {
		this.devoteeId = devoteeId;
	}

	public boolean isAttended() {
		return attended;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}

}
