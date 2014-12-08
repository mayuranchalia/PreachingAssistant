package com.temple.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mayur Jain
 *
 */

@XmlRootElement(name="SmsLog")
public class SmsLog {

	private String programId;
	private String text;
	private int sentCount;
	private String timeStamp;
	private String smsId;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSentCount() {
		return sentCount;
	}

	public void setSentCount(int sentCount) {
		this.sentCount = sentCount;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

}
