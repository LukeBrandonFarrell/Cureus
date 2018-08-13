package com.example.rushita.barrbers.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rushita.barrbers.Adapter.ApointmentAdapter;
import com.example.rushita.barrbers.R;

public class Apointment extends Fragment {
    ListView listView;
    ApointmentAdapter apointmentAdapter;

    public static String[] time1 = new String[]{"4pm || 23rd of July,2018 ", "4pm || 23rd of July,2018 "};
    public static String[] subtitle1 = new String[]{"with Belgard ","with Belgard "};
    public static String[] txt_title1 = new String[]{"Haircut & Wash","Haircut & Wash"};
    public static String[] cost1 = new String[]{"£25","£25"};
    public static String[] mtext1 = new String[]{"Secondary line text Lorem ipsum doller sit amet, Concectetor adipiscing elit.Nam massa Quam.","Secondary line text Lorem ipsum doller sit amet, Concectetor adipiscing elit.Nam massa Quam."};
    public static String[] minut1 = new String[]{"30 minutes","30 minutes"};
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.apointment, container, false);
        listView=(ListView)view.findViewById(R.id.list_apointment);
        apointmentAdapter = new ApointmentAdapter(getActivity(),time1, subtitle1,txt_title1,cost1,mtext1,minut1);
        listView.setAdapter(apointmentAdapter);

        return view;
    }
}
