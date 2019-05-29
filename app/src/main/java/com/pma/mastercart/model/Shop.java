package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Shop  implements Parcelable {

    private Long id;
    private String name;
    private byte[] imageResource;
    private String location;
    public double lat;
    public double lng;
    private String phone;
    private String email;
    private boolean active;
    private double rating;
    private int numberOfRatings;
    private List<Product> products;
    private List<User> seller;
    private List<Comment> comments;

    public Shop() {
    }

    public Shop(Long id, String name, byte[] imageResource, String location, double lat, double lng, String phone, String email, boolean active, double rating, int numberOfRatings, List<Product> products, List<User> seller, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.email = email;
        this.active = active;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.products = products;
        this.seller = seller;
        this.comments = comments;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<User> getSeller() {
        return seller;
    }

    public void setSeller(List<User> seller) {
        this.seller = seller;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
        dest.writeString(this.location);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.rating);
        dest.writeInt(this.numberOfRatings);
        dest.writeTypedList(this.products);
        dest.writeTypedList(this.seller);
        dest.writeTypedList(this.comments);
    }

    protected Shop(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.imageResource = in.createByteArray();
        this.location = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.phone = in.readString();
        this.email = in.readString();
        this.active = in.readByte() != 0;
        this.rating = in.readDouble();
        this.numberOfRatings = in.readInt();
        this.products = in.createTypedArrayList(Product.CREATOR);
        this.seller = in.createTypedArrayList(User.CREATOR);
        this.comments = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel source) {
            return new Shop(source);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

}
