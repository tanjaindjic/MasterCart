package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import com.pma.mastercart.model.enums.OrderStatus;
import com.pma.mastercart.model.enums.OrderType;

public class Order implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeLong(this.time != null ? this.time.getTime() : -1);
        dest.writeInt(this.orderStatus == null ? -1 : this.orderStatus.ordinal());
        dest.writeInt(this.orderType == null ? -1 : this.orderType.ordinal());
        dest.writeDouble(this.price);
        dest.writeParcelable(this.product, flags);
        dest.writeInt(this.quantity);
        dest.writeParcelable(this.buyer, flags);
    }

    protected Order(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        long tmpTime = in.readLong();
        this.time = tmpTime == -1 ? null : new Date(tmpTime);
        int tmpOrderStatus = in.readInt();
        this.orderStatus = tmpOrderStatus == -1 ? null : OrderStatus.values()[tmpOrderStatus];
        int tmpOrderType = in.readInt();
        this.orderType = tmpOrderType == -1 ? null : OrderType.values()[tmpOrderType];
        this.price = in.readDouble();
        this.product = in.readParcelable(Product.class.getClassLoader());
        this.quantity = in.readInt();
        this.buyer = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
