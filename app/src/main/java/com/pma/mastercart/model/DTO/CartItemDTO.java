package com.pma.mastercart.model.DTO;

public class CartItemDTO  {
    private Long id;
    private int quantity;
    private double total;
    private Long productId;
    public CartItemDTO(Long id, int quantity, double total, Long productId) {
        super();
        this.id = id;
        this.quantity = quantity;
        this.total = total;
        this.productId = productId;
    }
    public CartItemDTO() {
        super();
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
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

}
