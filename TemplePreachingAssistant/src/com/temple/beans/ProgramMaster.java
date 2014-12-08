package com.temple.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mayur Jain
 *
 */
@XmlRootElement(name = "programMaster")
public class ProgramMaster {

	private String programId;
	private String programType;
	private String extraDesc;
	private String mentor;
	private String asmentor;
	private String programInterval;

	public String getProgramInterval() {
		return programInterval;
	}

	public void setProgramInterval(String programInterval) {
		this.programInterval = programInterval;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public String getExtraDesc() {
		return extraDesc;
	}

	public void setExtraDesc(String extraDesc) {
		this.extraDesc = extraDesc;
	}

	public String getMentor() {
		return mentor;
	}

	public void setMentor(String mentor) {
		this.mentor = mentor;
	}

	public String getAsmentor() {
		return asmentor;
	}

	public void setAsmentor(String asmentor) {
		this.asmentor = asmentor;
	}

}
