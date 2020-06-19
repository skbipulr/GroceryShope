package com.bipul.groceryshope.Utils;

import android.content.SharedPreferences;

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
    public static int client_id=0;
    public static List<Order> orderList = new ArrayList<>();

    public static int orderId=0;
    public static int po=0;
    public static int getCount=0;


    public static int quantity=0;
    public static String name;
    public static String mobile;





    public static List<Order> carts = new ArrayList<>();

    static public int  calculatePrice(){
        //Calculate total price
        int total = 0;
        for (Order order : carts)
            total += (Integer.parseInt(order.getProductPrice()));

        return total;
        //Toast.makeText(AddToCartActivity.this, ""+carts.get(0).getProductQuantity(), Toast.LENGTH_SHORT).show();
    }
}
