package com.pizzashop.model;

public class PizzaOrder {

	private int orderID;
	private String orderDetails;
	private String orderType;
	private String orderDate;
	private int customerID;
	private int orderQuantity;
	
	public PizzaOrder(int orderID,String orderDetails ,String orderType , String orderDate , int customerID , int orderQuantity)
	{
		this.orderID = orderID;
		this.orderDetails = orderDetails ;
		this.orderType = orderType ;
		this.orderDate = orderDate;
		this.customerID = customerID;
		this.orderQuantity = orderQuantity;
		
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	@Override
	public String toString() {
		return "\n\t" + orderID + "\t" + orderDetails + "\t" + orderType
				+ "\t\t" + orderDate + "\t" + customerID + "\t\t" + orderQuantity ;
	}
	
	

	


}
