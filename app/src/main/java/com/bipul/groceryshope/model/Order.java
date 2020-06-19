package com.bipul.groceryshope.model;

public class Order {
    private int Id;
    private int ProductId;
    private String ProductName;
    private String ProductImage;
    private String ProductUnit;
    private String ProductPrice;
    private int ProductQuantity;

    public Order() {
    }

    public Order(int id, int productId, String productName, String productImage, String productUnit, String productPrice, int productQuantity) {
        Id = id;
        ProductId = productId;
        ProductName = productName;
        ProductImage = productImage;
        ProductUnit = productUnit;
        ProductPrice = productPrice;
        ProductQuantity = productQuantity;
    }

    public Order(int productId, String productName, String productImage, String productunit, String productPrice, int productQuantity) {
        ProductId = productId;
        ProductName = productName;
        ProductImage = productImage;
        ProductUnit = productunit;
        ProductPrice = productPrice;
        ProductQuantity = productQuantity;
    }



    public Order(String productName, String productImage, String productunit, String productPrice, int  productQuantity) {
        ProductName = productName;
        ProductImage = productImage;
        ProductUnit = productunit;
        ProductPrice = productPrice;
        ProductQuantity = productQuantity;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductunit() {
        return ProductUnit;
    }

    public void setProductunit(String productunit) {
        ProductUnit = productunit;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public int getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        ProductQuantity = productQuantity;
    }
}
