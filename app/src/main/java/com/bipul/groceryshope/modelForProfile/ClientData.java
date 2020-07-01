
package com.bipul.groceryshope.modelForProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientData {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("photo")
    @Expose
    private Object photo;
    @SerializedName("porosova")
    @Expose
    private Object porosova;
    @SerializedName("ward")
    @Expose
    private Object ward;
    @SerializedName("corona_zone")
    @Expose
    private Object coronaZone;
    @SerializedName("upazila_id")
    @Expose
    private Object upazilaId;
    @SerializedName("upazila_name")
    @Expose
    private Object upazilaName;
    @SerializedName("union_id")
    @Expose
    private Object unionId;
    @SerializedName("union_name")
    @Expose
    private Object unionName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public Object getPorosova() {
        return porosova;
    }

    public void setPorosova(Object porosova) {
        this.porosova = porosova;
    }

    public Object getWard() {
        return ward;
    }

    public void setWard(Object ward) {
        this.ward = ward;
    }

    public Object getCoronaZone() {
        return coronaZone;
    }

    public void setCoronaZone(Object coronaZone) {
        this.coronaZone = coronaZone;
    }

    public Object getUpazilaId() {
        return upazilaId;
    }

    public void setUpazilaId(Object upazilaId) {
        this.upazilaId = upazilaId;
    }

    public Object getUpazilaName() {
        return upazilaName;
    }

    public void setUpazilaName(Object upazilaName) {
        this.upazilaName = upazilaName;
    }

    public Object getUnionId() {
        return unionId;
    }

    public void setUnionId(Object unionId) {
        this.unionId = unionId;
    }

    public Object getUnionName() {
        return unionName;
    }

    public void setUnionName(Object unionName) {
        this.unionName = unionName;
    }

}
