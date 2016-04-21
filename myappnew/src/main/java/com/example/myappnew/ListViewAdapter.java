package com.example.myappnew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class ListViewAdapter extends BaseAdapter {
    private List<FoodEntity> list;
    private Context context;
    private LayoutInflater inflater;

    public ListViewAdapter(List<FoodEntity> list, Context context) {
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
            convertView = inflater.inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.iv = ((ImageView) convertView.findViewById(R.id.iv));
            holder.description = ((TextView) convertView.findViewById(R.id.description));
            holder.keywords = ((TextView) convertView.findViewById(R.id.keywords));
            convertView.setTag(holder);
        }else{
            holder = ((ViewHolder) convertView.getTag());
        }
        FoodEntity foodEntity = list.get(position);
        holder.keywords.setText(foodEntity.getKeywords());
        holder.description.setText(foodEntity.getDescription());
        Picasso.with(context).load(foodEntity.getImg()).into(holder.iv);
            return convertView;
    }


    class ViewHolder{
        ImageView iv;
        TextView description,keywords;
    }
}
