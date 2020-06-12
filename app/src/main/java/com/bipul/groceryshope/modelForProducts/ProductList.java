
package com.bipul.groceryshope.modelForProducts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("unit_name")
    @Expose
    private String unitName;
    @SerializedName("upazila_name")
    @Expose
    private String upazilaName;
    @SerializedName("union_name")
    @Expose
    private String unionName;
    @SerializedName("total_credit")
    @Expose
    private Integer totalCredit;
    @SerializedName("total_debit")
    @Expose
    private Integer totalDebit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUpazilaName() {
        return upazilaName;
    }

    public void setUpazilaName(String upazilaName) {
        this.upazilaName = upazilaName;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }

    public Integer getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(Integer totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Integer getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(Integer totalDebit) {
        this.totalDebit = totalDebit;
    }

}
