package com.bipul.groceryshope.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bipul.groceryshope.R;

public class EditPersonalInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        colorChangeStatusBar();
    }

    public void backBtn(View view) {
        onBackPressed();
        finish();
    }


    public void colorChangeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }

    public void goBackAccount(View view) {
        startActivity(new Intent(this,AccountActivity.class));
    }

}
