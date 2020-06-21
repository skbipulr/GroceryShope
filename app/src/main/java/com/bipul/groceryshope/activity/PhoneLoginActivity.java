package com.bipul.groceryshope.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bipul.groceryshope.R;
import com.bipul.groceryshope.Utils.Common;
import com.bipul.groceryshope.interfaces.ApiInterface;
import com.bipul.groceryshope.modelForLogin.LoginResponse;
import com.bipul.groceryshope.modelForOTP.OTPResponse;
import com.bipul.groceryshope.webApi.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneLoginActivity extends AppCompatActivity {

    private EditText editPhoneNumber;
    private String phone;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        colorChangeStatusBar();

        init();

        createOTP();
    }

    private void createOTP() {
        phone = editPhoneNumber.getText().toString().trim();


        if (phone.isEmpty()) {
           /* editPhoneNumber.setError("Please Enter Your Mobile No.");
            editPhoneNumber.requestFocus();*/
            editPhoneNumber.setText(Common.mobile);
        } else if (Common.assess_token != null) {

            final ProgressDialog mDialog = new ProgressDialog(PhoneLoginActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();
            Call<OTPResponse> call = apiInterface.setOTPForLogin(phone, "A1b1C2d32564kjhkjadu",
                    Common.assess_token,
                    Common.client_id);

            call.enqueue(new Callback<OTPResponse>() {
                @Override
                public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                    if (response.code() == 200) {
                        OTPResponse response1 = response.body();
                        Toast.makeText(PhoneLoginActivity.this, "" + response1.getMessage(), Toast.LENGTH_SHORT).show();

                        mDialog.dismiss();
                        Intent intent = new Intent(PhoneLoginActivity.this, VerifyActivity.class);
                        intent.putExtra("code",String.valueOf(response1.getOtpCode()));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<OTPResponse> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
        }


    }

    private void init() {
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        apiInterface = RetrofitClient.getRetrofitWithoutHome().create(ApiInterface.class);
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

    public void nextButton(View view) {
        createOTP();
    }
}
