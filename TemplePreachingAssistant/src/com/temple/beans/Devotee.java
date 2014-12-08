package com.temple.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mayur Jain
 *
 */
@XmlRootElement(name = "devotee")
public class Devotee {

	private String devoteeId;
	private String legalName;
	private String initiatedName;
	private String dateOfBirth;
	private String smsPhone;
	private String altPhone;
	private String introDate;
	private String email;
	private String shikshaLevel;
	private String updateDate;
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	private String pincode;

	public String getDevoteeId() {
		return devoteeId;
	}

	public void setDevoteeId(String devoteeId) {
		this.devoteeId = devoteeId;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getInitiatedName() {
		return initiatedName;
	}

	public void setInitiatedName(String initiatedName) {
		this.initiatedName = initiatedName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSmsPhone() {
		return smsPhone;
	}

	public void setSmsPhone(String smsPhone) {
		this.smsPhone = smsPhone;
	}

	public String getAltPhone() {
		return altPhone;
	}

	public void setAltPhone(String altPhone) {
		this.altPhone = altPhone;
	}

	public String getIntroDate() {
		return introDate;
	}

	public void setIntroDate(String introDate) {
		this.introDate = introDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShikshaLevel() {
		return shikshaLevel;
	}

	public void setShikshaLevel(String shikshaLevel) {
		this.shikshaLevel = shikshaLevel;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
