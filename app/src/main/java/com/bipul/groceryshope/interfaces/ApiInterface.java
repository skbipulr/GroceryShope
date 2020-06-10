package com.bipul.groceryshope.interfaces;


import retrofit2.Call;
import retrofit2.http.GET;

import com.bipul.groceryshope.modelFodSlider.SliderResponse;

import retrofit2.http.Header;

public interface ApiInterface {

    @GET("home/slider_products")
    Call<SliderResponse> getSliderResponse(@Header("APP-KEY")String appKey);





}
