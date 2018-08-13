package com.example.rushita.barrbers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.tapadoo.alerter.Alerter;

public class Signup_detail extends AppCompatActivity {

   EditText edt_name;
   Button btn_continue;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_detail);
        edt_name=(EditText)findViewById(R.id.edt_name);
        btn_continue=(Button)findViewById(R.id.btn_continue);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_name.getText().toString().equals(""))
                {
                    Alerter.create(Signup_detail.this)
                            .setText("Please enter amount")
                            .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                            .enableSwipeToDismiss()
                            .setDuration(3000)
                            .show();
                }
                else
                {
                    Intent i=new Intent(Signup_detail.this,Signup_created.class);
                    startActivity(i);
                }
            }
        });

        findViewById(R.id.signup3_root).setOnClickListener(new View.OnClickListener(){
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
