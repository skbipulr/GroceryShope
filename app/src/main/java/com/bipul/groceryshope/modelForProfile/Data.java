
package com.bipul.groceryshope.modelForProfile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("client_data")
    @Expose
    private ClientData clientData;
    @SerializedName("upazila")
    @Expose
    private List<Upazila> upazila = null;
    @SerializedName("union")
    @Expose
    private List<Object> union = null;

    public ClientData getClientData() {
        return clientData;
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }

    public List<Upazila> getUpazila() {
        return upazila;
    }

    public void setUpazila(List<Upazila> upazila) {
        this.upazila = upazila;
    }

    public List<Object> getUnion() {
        return union;
    }

    public void setUnion(List<Object> union) {
        this.union = union;
    }

}
