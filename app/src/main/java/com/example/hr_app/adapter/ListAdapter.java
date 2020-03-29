package com.example.hr_app.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hr_app.R;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.database.entity.Collaborator;
import com.example.hr_app.util.RecyclerViewItemClickListener;
import java.util.List;
import java.util.Objects;

public class ListAdapter<T> extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    /**
     * Declaration of the variables
     */
    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    /**
     * The view holder which will contain the view according its content
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        ViewHolder(TextView textView){
            super(textView);
            tv = textView;
        }
    }

    /**
     * Constructor to call it
     * @param listener onItemClick / onLongItemClick
     */
    public ListAdapter(RecyclerViewItemClickListener listener){
        mListener = listener;
    }


    /**
     * Method which will get all the layout and listener of our view
     * @param parent
     * @param viewType
     * @return the view holder "completed"
     */
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    /**
     * Method which will set the data inside our text view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        T item =mData.get(position);
        if(item.getClass().equals(Collaborator.class)){
            holder.tv.setText(((Collaborator) item).getName());
        } else {
            holder.tv.setText(((Absences) item).getStartAbsence() + " - " + ((Absences) item).getEndAbsence());
        }

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    /**
     * Method which will convert the live data to the list of data
     * @param data
     */
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
                    } else if(mData instanceof Absences){
                        return ((Absences) mData.get(oldItemPosition)).getIdAbsence() == ((Absences) mData.get(newItemPosition)).getIdAbsence();
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
                    } else if(mData instanceof Absences){
                        Absences newAbs = (Absences) data.get(newItemPosition);
                        Absences oldAbs = (Absences) data.get(oldItemPosition);
                        return newAbs.getIdAbsence() == oldAbs.getIdAbsence()
                                && Objects.equals(newAbs.getEmail(), oldAbs.getEmail())
                                && Objects.equals(newAbs.getReason(), oldAbs.getReason())
                                && Objects.equals(newAbs.getStartAbsence(), oldAbs.getStartAbsence())
                                && Objects.equals(newAbs.getEndAbsence(),oldAbs.getEndAbsence())
                                && Objects.equals(newAbs.isValidate(), oldAbs.isValidate());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }



}
