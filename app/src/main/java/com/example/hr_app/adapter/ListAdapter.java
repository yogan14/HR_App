package com.example.hr_app.adapter;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hr_app.R;

import java.util.List;



public class ListAdapter<T> extends ArrayAdapter<T> {

    private int mResource;
    private List<T> mData;

    public ListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> data) {
        super(context, resource, data);
        mResource = resource;
        mData = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getCustomView(position, convertView, parent);
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(mResource, parent, false);

            viewHolder = new ViewHolder();
            //viewHolder.itemView = convertView.findViewById(R.id.tvClientView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        T item = getItem(position);
        if (item != null) {
            viewHolder.itemView.setText(item.toString());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView itemView;
    }

    public void updateData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
