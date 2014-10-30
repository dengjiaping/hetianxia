package com.htx.model;

import java.io.Serializable;

public class ContactInfo implements Serializable {
	
	public String contactName;
	public String userNumber;
	public String che;
	public String getChe() {
		return che;
	}

	public void setChe(String che) {
		this.che = che;
	}

	public Boolean isChecked;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
}