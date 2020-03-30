package com.example.hr_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hr_app.R;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.database.entity.Collaborator;

import java.util.List;
import java.util.Objects;

public class ValidateAbsencesAdapter<T> extends RecyclerView.Adapter<ValidateAbsencesAdapter.ViewHolder> {

    /**
     * Declaration of the variables
     */
    private List<T> mData;

    private List<Collaborator> collaboList;
    /**
     * The view holder which will contain the view according its content
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView date;
        private TextView cause;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_view_collaborator_name);
            date = itemView.findViewById(R.id.text_view_date);
            cause = itemView.findViewById(R.id.cause);
        }
    }

    /**
     * Empty constructor for the adapter since we don't
     * ask a listener
     */
    public ValidateAbsencesAdapter(List<Collaborator> collaboList){
        this.collaboList = collaboList;
    }

    /**
     * when the list is found in the database, set the list with the values
     * @param collaboList - list of collaborators
     */
    public void setDataChanged(List<Collaborator> collaboList) {
        this.collaboList = collaboList;
        notifyItemRangeInserted(0, collaboList.size());
    }

    /**
     * Method which will get all the layout and listener of our view
     * @param parent - the parents
     * @param viewType - the view
     * @return the view holder "completed"
     */
    @NonNull
    @Override
    public ValidateAbsencesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.validateabs_item,parent,false);
        return new ViewHolder(cv);
    }

    /**
     * Method which will set the data inside our different fields
     * @param holder - the viewHolder
     * @param position - the position
     */
    @Override
    public void onBindViewHolder(@NonNull ValidateAbsencesAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);

        for (Collaborator c : collaboList) {
            if(c.getEmail().equals(((Absences) item).getEmail())) {
                holder.name.setText(c.getName());
            }
        }

        holder.date.setText(((Absences) item).getStartAbsence() + " to " + ((Absences) item).getEndAbsence());
        holder.cause.setText(((Absences) item).getReason());
    }

    /**
     * method who count the number of item for the display
     */
    @Override
    public int getItemCount() {
        if(mData==null){
            return 0;
        } else {
            return mData.size();
        }

    }

    /**
     * Method which will convert the live data to the list of data
     * @param data - the list of datas
     */
    public void setData(final List<T> data){
        if(mData==null){
            mData = data;
            notifyItemRangeInserted(0,data.size());
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
                    if(mData instanceof Absences){
                        return ((Absences) mData.get(oldItemPosition)).getIdAbsence() == ((Absences) mData.get(newItemPosition)).getIdAbsence();
                    } else{
                        return false;
                    }

                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if(mData instanceof Absences){
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

    public T getAbsenceAt(int position){
        return mData.get(position);
    }
}
