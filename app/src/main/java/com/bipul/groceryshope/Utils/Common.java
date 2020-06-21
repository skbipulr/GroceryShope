package com.bipul.groceryshope.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bipul.groceryshope.model.Order;
import com.bipul.groceryshope.registrationModel.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import static android.content.Context.MODE_PRIVATE;

public class Common {

    //share pref
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";

    SharedPreferences sharedpreferences;

    public static String currentUser=null;
    public static String currentMobileNo=null;
    public static String assess_token=null;
    public static String app_key="A1b1C2d32564kjhkjadu";
    public static String client_id=null;
    public static List<Order> orderList = new ArrayList<>();

    public static int orderId=0;
    public static int po=0;
    public static int getCount=0;


    public static int quantity=0;
    public static String name;
    public static String mobile;

    //--------------for-------stat------------------
    public static boolean isConnectToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info!=null){
                for(int i=0;i<info.length;i++){
                    if (info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }

                }
            }
        }
        return false;
    }
   //--------------for-------end------------------



}
