package com.example.rushita.barrbers.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rushita.barrbers.R;
import com.example.rushita.barrbers.Signup1;
import com.example.rushita.barrbers.SplashActivity;
import com.example.rushita.barrbers.profileupdate;

import static android.content.Context.MODE_PRIVATE;

public class Profile_Account extends Fragment {
    TextView edit;
    Fragment fragment=null;
    String PRENAME_KEY = "mypreference";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String fulladdress="";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profileacount, container, false);
        edit=(TextView)view.findViewById(R.id.edit);
        preferences = getActivity().getSharedPreferences(PRENAME_KEY, MODE_PRIVATE);
        editor = preferences.edit();
        edit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i=new Intent(getActivity(),profileupdate.class);
                startActivity(i);
//
            }
        });
        return view;
    }
}
