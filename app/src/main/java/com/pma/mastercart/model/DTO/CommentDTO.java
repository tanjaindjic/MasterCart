package com.pma.mastercart.model.DTO;

public class CommentDTO {
    private Long id;
    private Long StoreId;
    private Long productId;
    private String text;
    private double reviev;
    private String author;

    public CommentDTO() {
    }

    public CommentDTO(Long id, Long storeId, Long productId, String text, double reviev, String author) {
        this.id = id;
        StoreId = storeId;
        this.productId = productId;
        this.text = text;
        this.reviev = reviev;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return StoreId;
    }

    public void setStoreId(Long storeId) {
        StoreId = storeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getReviev() {
        return reviev;
    }

    public void setReviev(double reviev) {
        this.reviev = reviev;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
