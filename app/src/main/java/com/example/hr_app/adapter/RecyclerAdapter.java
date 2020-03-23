package com.example.hr_app.adapter;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hr_app.R;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;


public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        /*TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;*/
        return null; //à supprimer
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(Absences.class))
            holder.mTextView.setText((item).toString());
        if (item.getClass().equals(Collaborator.class))
            holder.mTextView.setText(((Collaborator) item).getName());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mData == null) {
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
                    if (mData instanceof Absences) {
                        return ((Absences) mData.get(oldItemPosition)).getIdAbsence() == ((Absences) data.get(newItemPosition)).getIdAbsence();
                    }
                    if (mData instanceof Collaborator) {
                        return ((Collaborator) mData.get(oldItemPosition)).getEmail() ==
                                ((Collaborator) data.get(newItemPosition)).getEmail();
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof Absences) {
                        Absences newAccount = (Absences) data.get(newItemPosition);
                        Absences oldAccount = (Absences) mData.get(newItemPosition);
                        return Objects.equals(newAccount.getIdAbsence(), oldAccount.getIdAbsence())
                                && Objects.equals(newAccount.getStartAbsence(), oldAccount.getStartAbsence())
                                && Objects.equals(newAccount.getEndAbsence(), oldAccount.getEndAbsence())
                                && newAccount.getReason().equals(oldAccount.getReason())
                                && Objects.equals(newAccount.getEmail(), oldAccount.getEmail());
                    }
                    if (mData instanceof Collaborator) {
                        Collaborator newClient = (Collaborator) data.get(newItemPosition);
                        Collaborator oldClient = (Collaborator) mData.get(newItemPosition);
                        return Objects.equals(newClient.getEmail(), oldClient.getEmail())
                                && Objects.equals(newClient.getName(), oldClient.getName())
                                && Objects.equals(newClient.getService(), oldClient.getService())
                                && newClient.getEmail().equals(oldClient.getEmail())
                                && newClient.getPassword().equals(oldClient.getPassword());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
