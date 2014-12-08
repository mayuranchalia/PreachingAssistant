package com.temple.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mayur Jain
 *
 */
@XmlRootElement(name="devoteeHistory")
public class DevoteeHistory {

	private String devoteeId;
	private String historyDate;
	private String updatedById;
	private String info;

	public String getDevoteeId() {
		return devoteeId;
	}

	public void setDevoteeId(String devoteeId) {
		this.devoteeId = devoteeId;
	}

	public String getHistoryDate() {
		return historyDate;
	}

	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}

	public String getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
