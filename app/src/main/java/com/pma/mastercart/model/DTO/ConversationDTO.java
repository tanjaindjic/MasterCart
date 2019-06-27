package com.pma.mastercart.model.DTO;

public class ConversationDTO {

    private String shopId;
    private String message;
    private String userEmail;

    public ConversationDTO() {
    }

    public ConversationDTO(String shopId, String message, String userEmail) {
        this.shopId = shopId;
        this.message = message;
        this.userEmail = userEmail;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
