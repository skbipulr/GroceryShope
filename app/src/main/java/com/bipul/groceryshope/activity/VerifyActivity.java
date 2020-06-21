package com.bipul.groceryshope.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bipul.groceryshope.R;
import com.bipul.groceryshope.modelForProducts.ProductList;

import java.util.ArrayList;
import java.util.List;

public class VerifyActivity extends AppCompatActivity {

    private EditText verificationET;
    String verifyCode, userCode;

    private List<ProductList> cartProductLists;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        colorChangeStatusBar();

        init();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = verificationET.getText().toString();
                if (code.isEmpty() || code.length() < 6) {
                    verificationET.setError("Please Enter valid Code.");
                    verificationET.requestFocus();
                    return;
                } else {
                    codeVerify(code);
                }
            }
        });


        Toast.makeText(this, "" + verifyCode, Toast.LENGTH_SHORT).show();

    }

    private void codeVerify(String userCode) {

        if (userCode.equals(verifyCode)) {
            cartProductLists = new ArrayList<>();
            sharedPreferences = getSharedPreferences("CartPref", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.clear().commit();
            Intent intent = new Intent(this, MainActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Verification Successful", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Thank for Order", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Verification is miss match", Toast.LENGTH_SHORT).show();
        }

    }




    private void init() {
        verifyCode = getIntent().getStringExtra("code");
        verificationET = findViewById(R.id.verificationET);
        userCode = verificationET.getText().toString().trim();
        loginBtn = findViewById(R.id.loginBtn);


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

}
