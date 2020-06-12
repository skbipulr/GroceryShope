package com.bipul.groceryshope.interfaces;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import com.bipul.groceryshope.modelFodSlider.SliderResponse;
import com.bipul.groceryshope.modelForFeatureProduct.FeatureProductResponse;
import com.bipul.groceryshope.modelForLogin.LoginResponse;
import com.bipul.groceryshope.modelForProducts.ProductsResponse;
import com.bipul.groceryshope.registrationModel.RegistrationResponse;


import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("slider_products")
    Call<SliderResponse> getSliderResponse(@Header("APP-KEY")String appKey);

    @GET("featured_products")
    Call<FeatureProductResponse> getFeatureProduct(@Header("APP-KEY")String appKey);

    @GET("products")
    Call<ProductsResponse> getProducts(@Header("APP-KEY")String appKey);


    @FormUrlEncoded
    @POST("client_registration")
    Call<RegistrationResponse> setUserInfo(@Field("name") String name,
                                           @Field("mobile") String mobile,
                                           @Field("password") String password,
                                           @Field("confirm_password") String confirm_password,
                                           @Header("APP-KEY")String appKey);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> setUserInfoForLogin(@Field("username") String mobile,
                                            @Field("password") String password,
                                            @Header("APP-KEY")String appKey);


}
