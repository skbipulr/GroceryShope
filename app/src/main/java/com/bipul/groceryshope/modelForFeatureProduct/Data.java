
package com.bipul.groceryshope.modelForFeatureProduct;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("feature_product")
    @Expose
    private List<FeatureProduct> featureProduct = null;

    public List<FeatureProduct> getFeatureProduct() {
        return featureProduct;
    }

    public void setFeatureProduct(List<FeatureProduct> featureProduct) {
        this.featureProduct = featureProduct;
    }

}
