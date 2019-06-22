package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Product  implements Parcelable {

    private Long id;
    private String name;
    private byte[] imageResource; //mozda path u storage?
    private double price;
    private String description;
    private int onStock;
    private String size;
    private int discount;
    private boolean active;
    private double rating;
    private int numberOfRatings;
    private List<Comment> comments;
    private Category category;
    private List<Order> orders;
    private Shop shop;

    public Product() {
    }

    public Product(Long id, String name, byte[] imageResource, double price, String description, int onStock, String size, int discount, boolean active, double rating, int numberOfRatings, List<Comment> comments, Category category, List<Order> orders, Shop shop) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.price = price;
        this.description = description;
        this.onStock = onStock;
        this.size = size;
        this.discount = discount;
        this.active = active;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.comments = comments;
        this.category = category;
        this.orders = orders;
        this.shop = shop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImageResource() {
        return imageResource;
    }

    public void setImageResource(byte[] imageResource) {
        this.imageResource = imageResource;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOnStock() {
        return onStock;
    }

    public void setOnStock(int onStock) {
        this.onStock = onStock;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }




    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeByteArray(this.imageResource);
        dest.writeDouble(this.price);
        dest.writeString(this.description);
        dest.writeInt(this.onStock);
        dest.writeString(this.size);
        dest.writeInt(this.discount);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.rating);
        dest.writeInt(this.numberOfRatings);
        dest.writeTypedList(this.comments);
        dest.writeParcelable(this.category, flags);
        dest.writeTypedList(this.orders);
        dest.writeParcelable(this.shop, flags);
    }

    protected Product(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.imageResource = in.createByteArray();
        this.price = in.readDouble();
        this.description = in.readString();
        this.onStock = in.readInt();
        this.size = in.readString();
        this.discount = in.readInt();
        this.active = in.readByte() != 0;
        this.rating = in.readDouble();
        this.numberOfRatings = in.readInt();
        this.comments = in.createTypedArrayList(Comment.CREATOR);
        this.category = in.readParcelable(Category.class.getClassLoader());
        this.orders = in.createTypedArrayList(Order.CREATOR);
        this.shop = in.readParcelable(Shop.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
