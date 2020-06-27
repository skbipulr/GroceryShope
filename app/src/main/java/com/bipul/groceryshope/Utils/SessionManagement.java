package com.bipul.groceryshope.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String SESSION_NAME = "session_name";
    String SESSION_MOBILE = "session_mobile";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(int clientId,String name, String mobile){
        //save session of user whenever user is logged in


        editor.putInt(SESSION_KEY,clientId).commit();
        editor.putString(SESSION_NAME,name).commit();
        editor.putString(SESSION_MOBILE,mobile).commit();
    }

    public int getSession(){
        //return user id whose session is saved
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public String getName(){
        return sharedPreferences.getString(SESSION_NAME,"n");
    }

    public String getMobile(){
        return sharedPreferences.getString(SESSION_MOBILE,"M");
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
        editor.putString(SESSION_NAME,"n").commit();
        editor.putString(SESSION_MOBILE,"M").commit();
        editor.clear();
    }
}