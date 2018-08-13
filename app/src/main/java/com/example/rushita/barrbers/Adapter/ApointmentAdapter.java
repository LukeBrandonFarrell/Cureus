package com.example.rushita.barrbers.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rushita.barrbers.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApointmentAdapter extends BaseAdapter {
    public String time1[], subtitle1[], txt_title1[], cost1[], mtext1[], minut1[];
    public Context context;
    LayoutInflater inflater = null;
    public ApointmentAdapter(Context context, String[] time1,String[] subtitle1,String[] txt_title1,String[] cost1,String[] mtext1,String[] minut1)
    {
        this.context = context;
        this.time1=time1;
        this.subtitle1=subtitle1;
        this.txt_title1=txt_title1;
        this.cost1=cost1;
        this.mtext1=mtext1;
        this.minut1=minut1;

    }
        public int getCount() {
            return time1.length;
    }

    @Override
    public Object getItem(int position) {
        return time1[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        CircleImageView listimage;
        TextView txt_time, subtitle, txt_title, cost, mtext, minut;
    }

        @Override
    public View getView(int position, View view1, ViewGroup viewGroup) {
            ViewHolder holder;
            holder = new ViewHolder();
            // LayoutInflater inflater = getLayoutInflater();
            // if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view1 = inflater.inflate(R.layout.list_apointment,viewGroup,false);
            holder.listimage = (CircleImageView) view1.findViewById(R.id.image);
            holder.txt_time =(TextView) view1.findViewById(R.id.txt_time);
            holder.subtitle=(TextView) view1.findViewById(R.id.subtitle);
            holder.txt_title=(TextView) view1.findViewById(R.id.txt_title);
            holder.cost=(TextView) view1.findViewById(R.id.cost);
            holder.mtext=(TextView) view1.findViewById(R.id.mtext);
            holder.minut=(TextView) view1.findViewById(R.id.minut);
            viewGroup.setTag(holder);
            Log.e("time11",""+time1[position]);
            holder.txt_time.setText(time1[position]);
            holder.subtitle.setText(subtitle1[position]);
            holder.txt_title.setText(txt_title1[position]);
            holder.cost.setText(cost1[position]);
            holder.mtext.setText(mtext1[position]);
            holder.minut.setText(minut1[position]);
            holder.listimage.setImageResource(R.mipmap.listimage);
        return view1;

    }


}
