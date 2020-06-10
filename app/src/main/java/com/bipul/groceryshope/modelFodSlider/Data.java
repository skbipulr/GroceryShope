
package com.bipul.groceryshope.modelFodSlider;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("slider_product")
    @Expose
    private List<SliderProduct> sliderProduct = null;

    public List<SliderProduct> getSliderProduct() {
        return sliderProduct;
    }

    public void setSliderProduct(List<SliderProduct> sliderProduct) {
        this.sliderProduct = sliderProduct;
    }

}
