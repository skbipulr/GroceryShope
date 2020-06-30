
package com.bipul.groceryshope.modelForLatestProduct;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

    private int countForCart=0;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("upazila_name")
    @Expose
    private String upazilaName;
    @SerializedName("union_name")
    @Expose
    private String unionName;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("total_stock")
    @Expose
    private Integer totalStock;

    protected Datum(Parcel in) {
        countForCart = in.readInt();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            categoryId = null;
        } else {
            categoryId = in.readInt();
        }
        upazilaName = in.readString();
        unionName = in.readString();
        productName = in.readString();
        picture = in.readString();
        if (in.readByte() == 0) {
            rate = null;
        } else {
            rate = in.readInt();
        }
        if (in.readByte() == 0) {
            totalStock = null;
        } else {
            totalStock = in.readInt();
        }
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };

    public int getCountForCart() {
        return countForCart;
    }

    public void setCountForCart(int countForCart) {
        this.countForCart = countForCart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Integer totalStock) {
        this.totalStock = totalStock;
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
        if (categoryId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(categoryId);
        }
        parcel.writeString(upazilaName);
        parcel.writeString(unionName);
        parcel.writeString(productName);
        parcel.writeString(picture);
        if (rate == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(rate);
        }
        if (totalStock == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalStock);
        }
    }
}
