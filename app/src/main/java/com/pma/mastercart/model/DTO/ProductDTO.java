package com.pma.mastercart.model.DTO;

public class ProductDTO {

    private String id;
    private String name;
    private String imageResource; //mozda path u storage?
    private String price;
    private String description;
    private String onStock;
    private String size;
    private String discount;
    private String idShop;
    private String idCategory;

    public ProductDTO() {}

    public ProductDTO(String id, String name, String imageResource, String price, String description,
                      String onStock, String size, String discount, String idShop,String idCategory) {
        super();
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.price = price;
        this.description = description;
        this.onStock = onStock;
        this.size = size;
        this.discount = discount;
        this.idShop = idShop;
        this.idCategory = idCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOnStock() {
        return onStock;
    }

    public void setOnStock(String onStock) {
        this.onStock = onStock;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
        this.idShop = idShop;
    }
}
