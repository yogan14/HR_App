package com.example.hr_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hr_app.R;
import com.example.hr_app.entity.Collaborator;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.CollaboratorHolder> {

    private List<Collaborator> collaborators = new ArrayList<>();

    class CollaboratorHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewService;

        public CollaboratorHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewService = itemView.findViewById(R.id.text_view_service);
        }
    }



    @NonNull
    @Override
    public CollaboratorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collaborator_item, parent, false);

        return new CollaboratorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CollaboratorHolder holder, int position) {

        Collaborator currentCollaborator = collaborators.get(position);
        holder.textViewName.setText(currentCollaborator.getName());
        holder.textViewService.setText(currentCollaborator.getService());

    }

    @Override
    public int getItemCount() {
        return collaborators.size();
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
        notifyDataSetChanged();
    }

}
