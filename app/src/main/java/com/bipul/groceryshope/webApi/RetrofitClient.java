package com.bipul.groceryshope.webApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    private static final  String BASE_URL_WITH_HOME ="http://narsingdi.gobazaar.com.bd/api/home/";
    private static final  String BASE_URL_WITHOUT_HOME ="http://narsingdi.gobazaar.com.bd/api/";
    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_WITH_HOME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }



    public static Retrofit getRetrofitWithoutHome(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_WITHOUT_HOME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }



    }






