package com.brodwy.MyEcommerce.utils;

public enum OrderStatus {
	
	    IN_PROGRESS("In Progress"),
	    ORDER_RECEIVED("Order Received"),
	    PRODUCT_PACKED("Product Packed"),
	    OUT_FOR_DELIVERY("Out for Delivery"),
	    DELIVERED("Delivered"),
	    CANCELLED("Cancelled");

	    private final String name;

	    OrderStatus(String name) {
	        this.name = name;
	    }

	    public String getName() {
	        return name;
	    }
	

}
