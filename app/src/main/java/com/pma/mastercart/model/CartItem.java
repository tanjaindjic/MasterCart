package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.pma.mastercart.model.enums.StatusCartItem;

public class CartItem implements Parcelable {
    private Long id;
    private int quantity;
    private double total;
    private StatusCartItem statusCartItem;
    private Product item;

    public CartItem() {

    }

    public CartItem(Long id, int quantity, double total, StatusCartItem statusCartItem, Product item) {
        this.id = id;
        this.quantity = quantity;
        this.total = total;
        this.statusCartItem = statusCartItem;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public StatusCartItem getStatusCartItem() {
        return statusCartItem;
    }

    public void setStatusCartItem(StatusCartItem statusCartItem) {
        this.statusCartItem = statusCartItem;
    }

    public Product getItem() {
        return item;
    }

    public void setItem(Product item) {
        this.item = item;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeInt(this.quantity);
        dest.writeDouble(this.total);
        dest.writeInt(this.statusCartItem == null ? -1 : this.statusCartItem.ordinal());
        dest.writeParcelable(this.item, flags);
    }

    protected CartItem(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.quantity = in.readInt();
        this.total = in.readDouble();
        int tmpStatusCartItem = in.readInt();
        this.statusCartItem = tmpStatusCartItem == -1 ? null : StatusCartItem.values()[tmpStatusCartItem];
        this.item = in.readParcelable(Product.class.getClassLoader());
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel source) {
            return new CartItem(source);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };
}
