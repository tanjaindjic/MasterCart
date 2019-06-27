package com.pma.mastercart.model.DTO;

public class MessageDTO {

    private String initiator;
    private String conversationId;
    private String content;

    public MessageDTO(){}

    public MessageDTO(String initiator, String conversationId, String content) {
        this.initiator = initiator;
        this.conversationId = conversationId;
        this.content = content;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
