package com.basic.retailer_meatablez.models;

public class OrderModel {
    public String orderId, orderDateTime, productName;

    public OrderModel(String orderId, String orderDateTime, String productName) {
        this.orderId = orderId;
        this.orderDateTime = orderDateTime;
        this.productName = productName;
    }
}
