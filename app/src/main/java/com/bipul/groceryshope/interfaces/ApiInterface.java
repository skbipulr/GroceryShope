package com.bipul.groceryshope.interfaces;


import retrofit2.Call;
import retrofit2.http.GET;

import com.bipul.groceryshope.modelFodSlider.SliderResponse;
import com.bipul.groceryshope.modelForFeatureProduct.FeatureProductResponse;

import retrofit2.http.Header;

public interface ApiInterface {

    @GET("slider_products")
    Call<SliderResponse> getSliderResponse(@Header("APP-KEY")String appKey);

    @GET("featured_products")
    Call<FeatureProductResponse> getFeatureProduct(@Header("APP-KEY")String appKey);




}
