package com.temple.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mayur Jain
 *
 */
@XmlRootElement(name="DevoteeSms")
public class DevoteeSms {
	private String devoteeId;
	private String smsId;
	private boolean deliveryStatus;

	public String getDevoteeId() {
		return devoteeId;
	}

	public void setDevoteeId(String devoteeId) {
		this.devoteeId = devoteeId;
	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public boolean isDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

}
