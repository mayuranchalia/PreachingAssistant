package com.temple.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mayur Jain
 *
 */

@XmlRootElement(name="programType")
public class ProgramType {

	private String type;
	private String programDesc;

	public String getType() {
		return type;
	}

	public void setType(String programType) {
		this.type = programType;
	}

	public String getProgramDesc() {
		return programDesc;
	}

	public void setProgramDesc(String programDesc) {
		this.programDesc = programDesc;
	}

}
