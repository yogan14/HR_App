package com.example.hr_app.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hr_app.R;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerDD<T> extends RecyclerView.Adapter<RecyclerDD.ViewHolder> {
    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        ViewHolder(TextView textView){
            super(textView);
            tv = textView;
        }
    }

    public RecyclerDD(RecyclerViewItemClickListener listener){
        mListener = listener;
    }


    @Override
    public RecyclerDD.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> { mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerDD.ViewHolder holder, int position) {
        T item =mData.get(position);
        holder.tv.setText(((Collaborator) item).getName());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data){
        if(mData == null){
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if(mData instanceof Collaborator){
                        return ((Collaborator) mData.get(oldItemPosition)).getEmail().equals(((Collaborator) data.get(newItemPosition)).getEmail());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if(mData instanceof Collaborator){
                        Collaborator newCollabo =(Collaborator) data.get(newItemPosition);
                        Collaborator oldCollabo = (Collaborator) mData.get(oldItemPosition);
                        return newCollabo.getEmail().equals(oldCollabo.getEmail())
                                && Objects.equals(newCollabo.getName(), oldCollabo.getName())
                                && Objects.equals(newCollabo.getService(), oldCollabo.getService())
                                && Objects.equals(newCollabo.getPassword(), oldCollabo.getPassword());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }



}
