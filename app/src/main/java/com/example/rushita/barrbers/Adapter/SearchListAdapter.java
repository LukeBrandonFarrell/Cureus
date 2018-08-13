package com.example.rushita.barrbers.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rushita.barrbers.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchListAdapter extends BaseAdapter {

    public String title[],mill[],will[],time1[];
    public Context context;
    ClickCallBack clickCallBack;
    LayoutInflater inflater = null;

    public SearchListAdapter(Context context, String[] title, String[] mill, String[] will, String[] time1,ClickCallBack clickCallBack)
    {

        this.context = context;
        this.clickCallBack=clickCallBack;
        this.title = title;
        Log.e("titlesize",""+title.length);
        this.mill=mill;
        this.will=will;
        this.time1=time1;

    }

    public int getCount() {

        return title.length;
    }


    public Object getItem(int position) {

        return title[position];
    }


    public long getItemId(int position) {

        return position;
    }

    public class ViewHolder {
        CircleImageView image;
        TextView txt_title, txt_mil, txt_will, txt_time;
LinearLayout listclick;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        holder = new ViewHolder();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item, parent, false);
        holder.image = (CircleImageView) convertView.findViewById(R.id.image);
        holder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
        holder.txt_mil = (TextView) convertView.findViewById(R.id.txt_mil);
        holder.txt_will = (TextView) convertView.findViewById(R.id.txt_will);
        holder.txt_time = (TextView) convertView.findViewById(R.id.txt_time);
        holder.listclick=(LinearLayout)convertView.findViewById(R.id.listclick);
        holder.listclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallBack.listclick(position);

            }
        });
        convertView.setTag(holder);
        Log.e("mill",""+position);
        holder.txt_title.setText(title[position]);
        holder.txt_mil.setText(mill[position]);
        holder.txt_will.setText(will[position]);
        holder.txt_time.setText(time1[position]);
        holder.image.setImageResource(R.mipmap.listimage);
        return convertView;
    }
    public interface ClickCallBack{
        public void listclick(int position);
    }




}

