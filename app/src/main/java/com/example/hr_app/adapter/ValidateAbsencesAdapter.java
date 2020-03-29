package com.example.hr_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hr_app.R;
import com.example.hr_app.database.entity.Absences;
import com.example.hr_app.util.RecyclerViewItemClickListener;
import java.util.List;
import java.util.Objects;

public class ValidateAbsencesAdapter<T> extends RecyclerView.Adapter<ValidateAbsencesAdapter.ViewHolder> {

    /**
     * Declaration of the variables
     */
    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    /**
     * The view holder which will contain the view according its content
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView date;
        private TextView cause;
        private Button accept;

        public ViewHolder(@NonNull View itemView) {
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
    public ValidateAbsencesAdapter(){

    }

    /**
     * Method which will get all the layout and listener of our view
     * @param parent
     * @param viewType
     * @return the view holder "completed"
     */
    @NonNull
    @Override
    public ValidateAbsencesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.validateabs_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(cv);
        return viewHolder;
    }

    /**
     * Method which will set the data inside our different fields
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ValidateAbsencesAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        holder.name.setText(((Absences) item).getEmail());
        holder.date.setText(((Absences) item).getStartAbsence() + " to " + ((Absences) item).getEndAbsence());
        holder.cause.setText(((Absences) item).getReason());
    }

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
     * @param data
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
