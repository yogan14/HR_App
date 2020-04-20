package com.example.hr_app.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hr_app.R;
import com.example.hr_app.ui.storage.UploadClass;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * ImageAdapter
 * Adapter for the images
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private List<UploadClass> uploads;
    private OnItemClickListener listener;

    /**
     * Constructor of the adapter
     * @param context application context
     * @param uploads list of images
     */
    public ImageAdapter(Context context,List<UploadClass> uploads){
        this.context=context;
        this.uploads=uploads;
    }

    /**
     * Will get the layout from the image_item to the recycler
     */
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);

        return new ImageViewHolder(v);
    }

    /**
     * Binding the differents view elements according to the data
     */
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        UploadClass current = uploads.get(position);
        holder.imageName.setText(current.getName());
        Picasso.get().load(current.getUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.image);
    }

    /**
     * Number of items
     */
    @Override
    public int getItemCount() {
        return uploads.size();
    }

    /**
     * Our view holder
     */
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView imageName;
        private ImageView image;
        private ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageName = itemView.findViewById(R.id.text_name);
            image = itemView.findViewById(R.id.images_upload_view);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        /**
         * create the menu when long click
         * @param menu - the menu
         * @param v - the view
         * @param menuInfo - the box
         */
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select action");
            MenuItem delete = menu.add(Menu.NONE,1,1,"Delete");
            delete.setOnMenuItemClickListener(this);
        }

        /**
         * the behaviour of the menu
         * @param item - the selected option
         */
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(listener!=null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            listener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
