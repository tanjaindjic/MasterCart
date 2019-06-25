package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.pma.mastercart.model.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class User  implements Parcelable {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private Role role;
    private byte[] imageResource;
    private ArrayList<Product> favorites;
    private Wallet wallet;
    private ArrayList<CartItem> cartItems;
    private ArrayList<Order> orders;
    private List<Conversation> conversations;

    public User() {
    }

    public User(Long id, String email, String password, String firstName, String lastName, String address, String phone, Role role, byte[] imageResource, ArrayList<Product> favorites, Wallet wallet, ArrayList<CartItem> cartItems, ArrayList<Order> orders, List<Conversation> conversations) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.imageResource = imageResource;
        this.favorites = favorites;
        this.wallet = wallet;
        this.cartItems = cartItems;
        this.conversations = conversations;
    }

    public Long getId() {  return id;  }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {  return password;  }

    public void setPassword(String password) {  this.password = password;   }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public byte[] getImageResource() {
        return imageResource;
    }

    public void setImageResource(byte[] imageResource) {
        this.imageResource = imageResource;
    }

    public ArrayList<Product> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Product> favorites) {
        this.favorites = favorites;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }


    @Override
    public String toString() {
        return  email ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeInt(this.role == null ? -1 : this.role.ordinal());
        dest.writeByteArray(this.imageResource);
        dest.writeTypedList(this.favorites);
        dest.writeParcelable(this.wallet, flags);
        dest.writeTypedList(this.cartItems);
        dest.writeTypedList(this.orders);
        dest.writeTypedList(this.conversations);
    }

    protected User(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.email = in.readString();
        this.password = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        int tmpRole = in.readInt();
        this.role = tmpRole == -1 ? null : Role.values()[tmpRole];
        this.imageResource = in.createByteArray();
        this.favorites = in.createTypedArrayList(Product.CREATOR);
        this.wallet = in.readParcelable(Wallet.class.getClassLoader());
        this.cartItems = in.createTypedArrayList(CartItem.CREATOR);
        this.orders = in.createTypedArrayList(Order.CREATOR);
        this.conversations = in.createTypedArrayList(Conversation.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
