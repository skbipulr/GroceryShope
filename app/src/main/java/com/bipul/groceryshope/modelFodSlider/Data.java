
package com.bipul.groceryshope.modelFodSlider;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("slider_products")
    @Expose
    private List<SliderProduct> sliderProducts = null;

    public List<SliderProduct> getSliderProducts() {
        return sliderProducts;
    }

    public void setSliderProducts(List<SliderProduct> sliderProducts) {
        this.sliderProducts = sliderProducts;
    }

}
