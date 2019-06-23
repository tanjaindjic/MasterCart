package com.pma.mastercart.model.DTO;

import java.util.Date;

public class ReviewDTO {
    Long shopId;
    Long productId;
    String user;
    String comment;
    Date date;
    double shopRating;
    double productRating;
    Long orderId;

    public ReviewDTO(Long shopId, Long productId, String user, String comment, Date date, double shopRating, double productRating, Long orderId) {
        this.shopId = shopId;
        this.productId = productId;
        this.user = user;
        this.comment = comment;
        this.date = date;
        this.shopRating = shopRating;
        this.productRating = productRating;
        this.orderId = orderId;
    }

    public ReviewDTO() {
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
