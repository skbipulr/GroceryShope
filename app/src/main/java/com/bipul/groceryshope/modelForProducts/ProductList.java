
package com.bipul.groceryshope.modelForProducts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductList implements Parcelable {

    private int countForCart=0;

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

    public ProductList(int countForCart, Integer id,  Integer rate, String productName, String picture, Integer categoryId, String unitName, String upazilaName, String unionName) {
        this.countForCart = countForCart;
        this.id = id;
        this.rate = rate;
        this.productName = productName;
        this.picture = picture;
        this.categoryId = categoryId;
        this.unitName = unitName;
        this.upazilaName = upazilaName;
        this.unionName = unionName;
    }

    public ProductList(int countForCart, Integer id, Integer productId, Integer rate, String productName, String picture, Integer categoryId, String unitName, String upazilaName, String unionName) {
        this.countForCart = countForCart;
        this.id = id;
        this.productId = productId;
        this.rate = rate;
        this.productName = productName;
        this.picture = picture;
        this.categoryId = categoryId;
        this.unitName = unitName;
        this.upazilaName = upazilaName;
        this.unionName = unionName;
    }

    public int getCountForCart() {
        return countForCart;
    }

    public void setCountForCart(int countForCart) {
        this.countForCart = countForCart;
    }

    protected ProductList(Parcel in) {
        countForCart = in.readInt();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            productId = null;
        } else {
            productId = in.readInt();
        }
        if (in.readByte() == 0) {
            rate = null;
        } else {
            rate = in.readInt();
        }
        productName = in.readString();
        picture = in.readString();
        if (in.readByte() == 0) {
            categoryId = null;
        } else {
            categoryId = in.readInt();
        }
        unitName = in.readString();
        upazilaName = in.readString();
        unionName = in.readString();
        if (in.readByte() == 0) {
            totalCredit = null;
        } else {
            totalCredit = in.readInt();
        }
        if (in.readByte() == 0) {
            totalDebit = null;
        } else {
            totalDebit = in.readInt();
        }
    }

    public static final Creator<ProductList> CREATOR = new Creator<ProductList>() {
        @Override
        public ProductList createFromParcel(Parcel in) {
            return new ProductList(in);
        }

        @Override
        public ProductList[] newArray(int size) {
            return new ProductList[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(countForCart);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (productId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(productId);
        }
        if (rate == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(rate);
        }
        parcel.writeString(productName);
        parcel.writeString(picture);
        if (categoryId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(categoryId);
        }
        parcel.writeString(unitName);
        parcel.writeString(upazilaName);
        parcel.writeString(unionName);
        if (totalCredit == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalCredit);
        }
        if (totalDebit == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalDebit);
        }
    }
}
