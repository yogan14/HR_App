package com.example.hr_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hr_app.R;
import com.example.hr_app.database.entity.AbsencesEntity;
import com.example.hr_app.database.entity.CollaboratorEntity;
import com.example.hr_app.database.entity.MessageEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatAdapter<T> extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{

    private List<T> mData;

    /**
     * ViewHolder to be the item of the list
     */
    static final class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView message;

        ChatViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_username);
            message = (TextView) view.findViewById(R.id.item_message);
        }
    }

    private List<MessageEntity> mContent = new ArrayList<>();

    public void clearData() {
        mContent.clear();
    }

    public void addData(MessageEntity data) {
        mContent.add(data);
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recycler, parent, false);
        return new ChatViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        MessageEntity data = mContent.get(position);

        holder.message.setText(data.getMessage());
        holder.name.setText(data.getSenderName());
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
                    if(mData instanceof CollaboratorEntity){
                        return ((CollaboratorEntity) mData.get(oldItemPosition)).getEmail().equals(((CollaboratorEntity) data.get(newItemPosition)).getEmail());
                    } else if(mData instanceof AbsencesEntity){
                        return ((AbsencesEntity) mData.get(oldItemPosition)).getIdAbsence() == ((AbsencesEntity) mData.get(newItemPosition)).getIdAbsence();
                    } else if(mData instanceof MessageEntity){
                        return ((MessageEntity) mData.get(oldItemPosition)).getMessageID() == ((MessageEntity) mData.get(newItemPosition)).getMessageID();
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if(mData instanceof CollaboratorEntity){
                        CollaboratorEntity newCollabo =(CollaboratorEntity) data.get(newItemPosition);
                        CollaboratorEntity oldCollabo = (CollaboratorEntity) mData.get(oldItemPosition);
                        return newCollabo.getEmail().equals(oldCollabo.getEmail())
                                && Objects.equals(newCollabo.getName(), oldCollabo.getName())
                                && Objects.equals(newCollabo.getService(), oldCollabo.getService())
                                && Objects.equals(newCollabo.getPassword(), oldCollabo.getPassword());
                    } else if(mData instanceof AbsencesEntity){
                        AbsencesEntity newAbs = (AbsencesEntity) data.get(newItemPosition);
                        AbsencesEntity oldAbs = (AbsencesEntity) data.get(oldItemPosition);
                        return newAbs.getIdAbsence() == oldAbs.getIdAbsence()
                                && Objects.equals(newAbs.getEmail(), oldAbs.getEmail())
                                && Objects.equals(newAbs.getReason(), oldAbs.getReason())
                                && Objects.equals(newAbs.getStartAbsence(), oldAbs.getStartAbsence())
                                && Objects.equals(newAbs.getEndAbsence(),oldAbs.getEndAbsence());
                    } else if(mData instanceof MessageEntity) {
                        MessageEntity newMess = (MessageEntity) data.get(newItemPosition);
                        MessageEntity oldMess = (MessageEntity) data.get(oldItemPosition);
                        return newMess.getMessageID() == oldMess.getMessageID()
                                && Objects.equals(newMess.getMessage(),oldMess.getMessage())
                                && Objects.equals(newMess.getSenderName(),oldMess.getSenderName());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }

}
