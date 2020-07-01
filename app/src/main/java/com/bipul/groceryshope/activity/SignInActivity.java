package com.bipul.groceryshope.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bipul.groceryshope.R;
import com.bipul.groceryshope.Utils.Common;
import com.bipul.groceryshope.Utils.SessionManagement;
import com.bipul.groceryshope.interfaces.ApiInterface;
import com.bipul.groceryshope.modelForLogin.LoginResponse;
import com.bipul.groceryshope.registrationModel.RegistrationResponse;
import com.bipul.groceryshope.webApi.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText edtPhone, edtPassword;
    private String phone, password;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String ASSESS_TOKEN = "assessToken";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String USER_ID = "USER_ID";

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        colorChangeStatusBar();

        init();

    }

    private void init() {
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        apiInterface = RetrofitClient.getRetrofitWithoutHome().create(ApiInterface.class);
    }

    private void createLogin() {
        phone = edtPhone.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if (phone.isEmpty()) {
            edtPhone.setError("Please Enter Your Mobile No.");
            edtPhone.requestFocus();
            return;
        } else if (password.isEmpty()) {
            edtPassword.setError("Please Enter Your Password.");
            edtPassword.requestFocus();
        } else {
            final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();

            Call<LoginResponse> call = apiInterface.setUserInfoForLogin(phone, password, "A1b1C2d32564kjhkjadu");

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if (response.code() == 200) {
                        LoginResponse meg = response.body();
                        String assessToken = meg.getToken();
                        String name = meg.getData().getUserInfo().getName();
                        String mobile =  meg.getData().getUserInfo().getMobile();
                        int clientId = meg.getData().getUserInfo().getRecordId();
                        int userId = meg.getData().getUserInfo().getId();




                        //Common.client_id = meg.getData().getUserInfo().getId();
                        // Common.assess_token = meg.getToken();

                        // Toast.makeText(SignInActivity.this, "client id"+meg.getData().getUserInfo().getId(), Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(ASSESS_TOKEN, assessToken);
                        editor.putString(Name, name);
                        editor.putString(Phone, mobile);
                        editor.putString(CLIENT_ID, String.valueOf(clientId));
                        editor.putString(USER_ID, String.valueOf(userId));
                        editor.commit();

                        sharedpreferences = getSharedPreferences(MyPREFERENCES,
                                Context.MODE_PRIVATE);
                        String userAssessToken =  sharedpreferences.getString(ASSESS_TOKEN, "");
                        String n =  sharedpreferences.getString(Name, "");
                        String m =  sharedpreferences.getString(Phone,"");
                        String token =  sharedpreferences.getString(ASSESS_TOKEN,"");
                        String client_ID = sharedpreferences.getString(CLIENT_ID,"");
                        String user_ID = sharedpreferences.getString(USER_ID,"");
                        Common.name =  n;
                        Common.mobile = m;
                        Common.assess_token = token;
                        Common.client_id = client_ID;
                        Common.user_id = user_ID;


                        // Toast.makeText(SignInActivity.this, ""+userAssessToken, Toast.LENGTH_LONG).show();
                        Toast.makeText(SignInActivity.this, "Congratulations!! "+meg.getMessage(), Toast.LENGTH_LONG).show();
                        mDialog.dismiss();

                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent);
                        finish();

                    } else if (response.code() == 401) {
                        Toast.makeText(SignInActivity.this, "Sorry You are" + response.message(), Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    Toast.makeText(SignInActivity.this, "Failed "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            });
        }
    }



    public void colorChangeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }

    public void backBtn(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    public void goSignUpActivity(View view) {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }


    public void OnLogin(View view) {
        createLogin();
    }
}