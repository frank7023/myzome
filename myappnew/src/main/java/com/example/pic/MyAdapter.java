package com.example.pic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappnew.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 7023 on 2016/4/1.
 */
public class MyAdapter extends BaseAdapter {
    private List<PisEntity> list;
    private Context context;
    private LayoutInflater inflater;

    public MyAdapter() {
        super();

    }

    public MyAdapter(List<PisEntity> list, Context context) {
        super();
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gridview_item, null);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.iv);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PisEntity psEntity = list.get(position);
        holder.title.setText(psEntity.getTitle());
        Picasso.with(context).load(psEntity.getImg()).into(holder.img);
        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView title;

    }

}
