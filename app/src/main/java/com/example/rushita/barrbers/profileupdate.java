package com.example.rushita.barrbers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.rushita.barrbers.Fragment.Profile_Account;

public class profileupdate extends AppCompatActivity {

    Button btn_update;
    ImageView back;
    String PRENAME_KEY = "mypreference";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String fulladdress="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileupdate);
        btn_update=(Button)findViewById(R.id.btn_update);
        back=(ImageView)findViewById(R.id.back);
        preferences = profileupdate.this.getSharedPreferences(PRENAME_KEY, MODE_PRIVATE);
        editor = preferences.edit();
        fulladdress=""+preferences.getString("fulladdress","no");
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        findViewById(R.id.profileupdate_root).setOnClickListener(new View.OnClickListener(){
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
