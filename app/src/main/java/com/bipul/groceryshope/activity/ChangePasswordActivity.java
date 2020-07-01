package com.bipul.groceryshope.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bipul.groceryshope.R;
import com.bipul.groceryshope.Utils.Common;
import com.bipul.groceryshope.interfaces.ApiInterface;
import com.bipul.groceryshope.modelForLogin.LoginResponse;
import com.bipul.groceryshope.modelForPasswordChange.PasswordChangeResponse;
import com.bipul.groceryshope.webApi.RetrofitClient;

import in.anshul.libray.PasswordEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private PasswordEditText oldPasswordET, passwordET, confirmPasswordET;
    private String user_id, old_password, password, confirm_password;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        colorChangeStatusBar();

        init();


    }

    private void changePassword() {

        old_password = oldPasswordET.getText().toString().trim();
        password = passwordET.getText().toString().trim();
        confirm_password = confirmPasswordET.getText().toString().trim();

        if (old_password.isEmpty()) {
            oldPasswordET.setError("Please Enter Your Old Password.");
            oldPasswordET.requestFocus();
            return;
        } else if (password.isEmpty()) {
            passwordET.setError("Please Enter Your New Password.");
            passwordET.requestFocus();
        } else if (confirm_password.isEmpty()) {
            confirmPasswordET.setError("Please Enter Your New Password.");
            confirmPasswordET.requestFocus();
        } else {
            final ProgressDialog mDialog = new ProgressDialog(ChangePasswordActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();
            Call<PasswordChangeResponse> call = apiInterface.setPasswordChange(Integer.parseInt(Common.user_id), old_password,
                    password, confirm_password, Common.app_key, Common.assess_token, Common.client_id);
            call.enqueue(new Callback<PasswordChangeResponse>() {
                @Override
                public void onResponse(Call<PasswordChangeResponse> call, Response<PasswordChangeResponse> response) {
                    if (response.code() == 200) {
                        String meg = response.body().getMessage();
                        Toast.makeText(ChangePasswordActivity.this, meg, Toast.LENGTH_SHORT).show();
                        // Toast.makeText(SignInActivity.this, ""+userAssessToken, Toast.LENGTH_LONG).show();
                        mDialog.dismiss();

                        Intent intent = new Intent(ChangePasswordActivity.this, AccountActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<PasswordChangeResponse> call, Throwable t) {

                    Toast.makeText(ChangePasswordActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    private void init() {
        oldPasswordET = findViewById(R.id.oldPasswordET);
        passwordET = findViewById(R.id.new_password);
        confirmPasswordET = findViewById(R.id.confirmPassword);
        apiInterface = RetrofitClient.getRetrofitWithoutHome().create(ApiInterface.class);

    }

    public void backBtn(View view) {
        onBackPressed();
    }

    public void colorChangeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }

    public void changePassword(View view) {
        changePassword();
    }
}
