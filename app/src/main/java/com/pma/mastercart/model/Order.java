package com.pma.mastercart.model;

import java.util.Date;

import com.pma.mastercart.model.enums.OrderStatus;
import com.pma.mastercart.model.enums.OrderType;

public class Order {
    private Long id;
    private Date time;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private double price;
    private Product product;
    private int quantity;
    private User buyer;

    public Order() {
    }

    public Order(Long id, Date time, OrderStatus orderStatus, OrderType orderType, double price, Product product, int quantity, User buyer) {
        this.id = id;
        this.time = time;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
        this.price = price;
        this.product = product;
        this.quantity = quantity;
        this.buyer = buyer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
}
