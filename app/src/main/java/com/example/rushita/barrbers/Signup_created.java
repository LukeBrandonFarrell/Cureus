package com.example.rushita.barrbers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class Signup_created extends AppCompatActivity {

Button btn_continue;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_created);
        btn_continue=(Button)findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Signup_created.this,MainActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.signup4_root).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
            }
        });
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
