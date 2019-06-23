package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Comment  implements Parcelable {
    private Long id;
    private Shop forShop;
    private Product forProduct;
    private String user;
    private String comment;
    private Date time;
    private double shopRating;
    private double productRating;
    private Long orderId;

    public Comment() {
    }

    public Comment(Shop forShop, Product forProduct, String user, String comment, Date time, double shopRating, double productRating, Long orderId) {
        this.forShop = forShop;
        this.forProduct = forProduct;
        this.user = user;
        this.comment = comment;
        this.time = time;
        this.shopRating = shopRating;
        this.productRating = productRating;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shop getForShop() {
        return forShop;
    }

    public void setForShop(Shop forShop) {
        this.forShop = forShop;
    }

    public Product getForProduct() {
        return forProduct;
    }

    public void setForProduct(Product forProduct) {
        this.forProduct = forProduct;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getShopRating() {
        return shopRating;
    }

    public void setShopRating(double shopRating) {
        this.shopRating = shopRating;
    }




    public double getProductRating() {
        return productRating;
    }

    public void setProductRating(double productRating) {
        this.productRating = productRating;
    }



    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.forShop, flags);
        dest.writeParcelable(this.forProduct, flags);
        dest.writeString(this.user);
        dest.writeString(this.comment);
        dest.writeLong(this.time != null ? this.time.getTime() : -1);
        dest.writeDouble(this.shopRating);
        dest.writeDouble(this.productRating);
        dest.writeValue(this.orderId);
    }

    protected Comment(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.forShop = in.readParcelable(Shop.class.getClassLoader());
        this.forProduct = in.readParcelable(Product.class.getClassLoader());
        this.user = in.readString();
        this.comment = in.readString();
        long tmpTime = in.readLong();
        this.time = tmpTime == -1 ? null : new Date(tmpTime);
        this.shopRating = in.readDouble();
        this.productRating = in.readDouble();
        this.orderId = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
