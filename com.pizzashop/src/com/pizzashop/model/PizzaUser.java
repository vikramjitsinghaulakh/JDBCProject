package com.pizzashop.model;

public class PizzaUser {

	private int customerID;
	private String firstName;
	private String lastName;
	private String address;
	private String contactNumber;
	private String areaCode;
	private String emailId;
	private int stauts;
	//private String password;


	public PizzaUser(int customerID, String firstName, String lastName, String address, String contactNumber,
			String areaCode, String emailId, int stauts) {
		super();
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.contactNumber = contactNumber;
		this.areaCode = areaCode;
		this.emailId = emailId;
		this.stauts = stauts;
	}


	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public int getStauts() {
		return stauts;
	}
	public void setStauts(int stauts) {
		this.stauts = stauts;
	}

	@Override
	public String toString() {
		return "\n\t\t " + customerID + "\t| " + firstName + "\t " + lastName
				+ "| \t" + address + "|\t " + contactNumber + "|\t " + areaCode + "| \t"
				+ emailId + "| \t" + stauts;
	}




}
