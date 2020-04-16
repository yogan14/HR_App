package com.example.hr_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hr_app.R;
import com.example.hr_app.adapter.ImageAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListPicActivity extends BaseHRActivity implements ImageAdapter.OnItemClickListener {
    //Declaration of variables
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private ProgressBar progress;
    private FirebaseStorage storage;
    private DatabaseReference ref;
    private ValueEventListener DBListener;
    private List<UploadClass> list;


    /**
     * onCreate
     * Create the activity
     * @param savedInstanceState - the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
        getLayoutInflater().inflate(R.layout.activity_test, frameLayout);
        navigationView.setCheckedItem(position);


        recyclerView = findViewById(R.id.recycler_view_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progress = findViewById(R.id.progress_bar_images_list);


        list = new ArrayList<>();

        adapter = new ImageAdapter(ListPicActivity.this,list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(ListPicActivity.this);
        ref = FirebaseDatabase.getInstance().getReference("uploads");
        storage = FirebaseStorage.getInstance();

        DBListener = ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot before : dataSnapshot.getChildren()){
                    UploadClass upload = before.getValue(UploadClass.class);
                    upload.setKey(before.getKey());
                    list.add(upload);
                }

                adapter.notifyDataSetChanged();

                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListPicActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, StorageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        UploadClass select = list.get(position);
        String key = select.getKey();

        StorageReference imageRef = storage.getReferenceFromUrl(select.getUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ref.child(key).removeValue();
                Toast.makeText(ListPicActivity.this, "Image deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ref.removeEventListener(DBListener);
    }
}
