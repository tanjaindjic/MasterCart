package com.pma.mastercart.model.DTO;

public class WalletDTO {

    private String balance;
    private String userEmail;

    public WalletDTO(){}

    public WalletDTO(String balance, String userEmail) {
        this.balance = balance;
        this.userEmail = userEmail;
    }

    public String getBalance() {
        return balance;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
