package com.example.rushita.barrbers.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rushita.barrbers.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MaplistAdapter extends BaseAdapter {

    public String titl[],mll[],wll[];
    public Context context;

    LayoutInflater inflater = null;

    public MaplistAdapter(Context context, String[] title, String[] mill, String[] will)
    {

        this.context = context;
               this.titl = title;
        Log.e("titlesize",""+title.length);
        this.mll=mill;
        this.wll=will;


    }

    public int getCount() {

        return titl.length;
    }


    public Object getItem(int position) {

        return titl[position];
    }


    public long getItemId(int position) {

        return position;
    }

    public class ViewHolder {
        CircleImageView image;
        TextView txt_title, txt_mil,txt_will;

    }
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        holder = new ViewHolder();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list1map, parent, false);
        holder.image = (CircleImageView) convertView.findViewById(R.id.image);
        holder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
        holder.txt_mil = (TextView) convertView.findViewById(R.id.txt_mil);
        holder.txt_will = (TextView) convertView.findViewById(R.id.txt_will);
//        holder.txt_time = (TextView) convertView.findViewById(R.id.txt_time);

        convertView.setTag(holder);
        Log.e("mil",""+position);
        holder.txt_title.setText(titl[position]);
        holder.txt_mil.setText(mll[position]);
        holder.txt_will.setText(wll[position]);
//        holder.txt_time.setText(time1[position]);
        holder.image.setImageResource(R.mipmap.listimage);
        return convertView;
    }
    public interface ClickCallBack{
        public void listclick(int position);
    }




}

