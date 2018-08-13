package com.example.rushita.barrbers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tapadoo.alerter.Alerter;

public class Signup2_verify extends AppCompatActivity {

 EditText edit1,edit2,edit3,edit4;
 Button btn_continue,resend;
 ImageView back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2_verify);
        edit1=(EditText)findViewById(R.id.edit1);
        edit2=(EditText)findViewById(R.id.edit2);
        edit3=(EditText)findViewById(R.id.edit3);
        edit4=(EditText)findViewById(R.id.edit4);
        back=(ImageView)findViewById(R.id.back);
        resend=(Button)findViewById(R.id.resend);
        btn_continue=(Button)findViewById(R.id.btn_continue);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Signup2_verify.this,Signup1.class);
                startActivity(i);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                edit1.clearFocus();
                edit2.requestFocus();
                edit2.setCursorVisible(true);

            }
        });
        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                edit2.clearFocus();
                edit3.requestFocus();
                edit3.setCursorVisible(true);

            }
        });
        edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                edit3.clearFocus();
                edit4.requestFocus();
                edit4.setCursorVisible(true);

            }
        });
        edit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                edit4.clearFocus();

            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit1.getText().toString().equals("")||edit2.getText().toString().equals("")||edit3.getText().toString().equals("")||edit4.getText().toString().equals("")){
                    Alerter.create(Signup2_verify.this)
                            .setText("Please enter code")
                            .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                            .enableSwipeToDismiss()
                            .setDuration(3000)
                            .show();
                }
                else
                {
                    Intent i=new Intent(Signup2_verify.this,Signup_detail.class);
                    startActivity(i);
                }

            }
        });

        findViewById(R.id.signup2_root).setOnClickListener(new View.OnClickListener(){
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
