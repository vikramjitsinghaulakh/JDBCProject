package com.pizzashop.model;

public class PizzaUser {
	
	private String firstName;
	private String lastName;
	private String address;
	private String areaCode;
	private String emailId;
	private String password;
	
	public PizzaUser() {
		// TODO Auto-generated constructor stub
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "\n\t " + firstName + "\t" + lastName + "\t" + address + "\t"
				+ areaCode + "\t" + emailId + "\t" + password ;
	}
	
	
	
}
