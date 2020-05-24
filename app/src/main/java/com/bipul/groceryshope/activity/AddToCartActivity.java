package com.bipul.groceryshope.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bipul.groceryshope.R;

public class AddToCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        colorChangeStatusBar();
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

    public void CheckOut(View view) {
        Intent intent = new Intent(this,PhoneLoginActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }
}
