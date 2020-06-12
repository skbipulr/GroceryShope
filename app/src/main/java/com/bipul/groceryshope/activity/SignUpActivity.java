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
import com.bipul.groceryshope.registrationModel.RegistrationResponse;
import com.bipul.groceryshope.registrationModel.UserInfo;
import com.bipul.groceryshope.webApi.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtFullName, edtMobile, edtPassword, edtConfirmPassword;
    private String name, mobileNo, password, confirmPassword;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        colorChangeStatusBar();

        init();
        createUser();
    }

    private void createUser() {
        name = edtFullName.getText().toString().trim();
        mobileNo = edtMobile.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (name.isEmpty()) {
            edtFullName.setError("Please Enter Your Full Name.");
            edtFullName.requestFocus();
            return;
        } else if (mobileNo.isEmpty()) {
            edtMobile.setError("Please Enter Your Mobile No.");
            edtMobile.requestFocus();
        } else if (password.isEmpty()) {
            edtPassword.setError("Please Enter Your Password");
        } else if (confirmPassword.isEmpty()) {
            edtConfirmPassword.setError("Please Enter Your Confirm Password");
        } else {
            final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();

            Call<RegistrationResponse> responseCall = apiInterface.setUserInfo(name, mobileNo, password, confirmPassword, "A1b1C2d32564kjhkjadu");
            responseCall.enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    if (response.code() == 200) {
                        RegistrationResponse meg = response.body();

                        Common.currentUser = meg.getData().getUserInfo().getName();
                        Common.currentMobileNo = meg.getData().getUserInfo().getMobile();

                        Toast.makeText(SignUpActivity.this, "Congratulations!! Your registration successful.", Toast.LENGTH_SHORT).show();

                        mDialog.dismiss();

                        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent);
                        finish();

                    } else if (response.code() == 404) {
                        Toast.makeText(SignUpActivity.this, "404" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {

                    Toast.makeText(SignUpActivity.this, "failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    private void init() {
        edtFullName = findViewById(R.id.edtFullName);
        edtMobile = findViewById(R.id.edtMobileNo);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

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

  /*  public void goToMainAct(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }*/


    public void btnSignUp(View view) {
        createUser();
        /*Intent intent = new Intent(this, MainActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);*/
    }



}
