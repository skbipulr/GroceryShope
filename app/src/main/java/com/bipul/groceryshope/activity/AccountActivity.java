package com.bipul.groceryshope.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bipul.groceryshope.R;
import com.bipul.groceryshope.Utils.Common;
import com.bipul.groceryshope.interfaces.ApiInterface;
import com.bipul.groceryshope.modelForOTP.OTPResponse;
import com.bipul.groceryshope.modelForProfile.ClientData;
import com.bipul.groceryshope.modelForProfile.ProfileResponse;
import com.bipul.groceryshope.webApi.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private TextView userNameTV,userPhoneNo;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        colorChangeStatusBar();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        getUserInfo();
    }

    private void getUserInfo() {
        Call<ProfileResponse> call = apiInterface.getProfileInfo(
                                                                Common.app_key,
                                                                Common.assess_token, Common.client_id);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.code()==200){
                   ClientData clientData =  response.body().getData().getClientData();

                   userNameTV.setText(clientData.getName());
                   userPhoneNo.setText(clientData.getMobile());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }


    private void init() {
        userNameTV = findViewById(R.id.userNameTV);
        userPhoneNo = findViewById(R.id.userPhoneNo);

        apiInterface = RetrofitClient.getRetrofitWithoutHome().create(ApiInterface.class);

    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sign_out) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void backPress(View view) {
        onBackPressed();
        finish();
    }

    public void go(View view) {
        startActivity(new Intent(this, PhoneLoginActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void clickOnChangePassword(View view) {
        startActivity(new Intent(this,ChangePasswordActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void colorChangeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }

    public void goToEdit(View view) {
        startActivity(new Intent(this,ConfirmActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void goToOrderList(View view) {
        Intent intent = new Intent(this,OrderListActivity.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(intent);
    }

    public void backBtn(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    public void goTo(View view) {
        Intent intent = new Intent(this,EditPersonalInfoActivity.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(intent);

    }
}
