package com.example.hr_app.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hr_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class StorageActivity extends BaseHRActivity {
    //Declaration of variables
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button choose;
    private Button upload;
    private TextView show;
    private EditText fileName;
    private ImageView image;
    private ProgressBar bar;
    private Uri imageUri;
    private StorageReference storageRef;
    private DatabaseReference dbref;
    private StorageTask uploadTask;

    /**
     * onCreate
     * Create the activity
     *
     * @param savedInstanceState - the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_storage);
        getLayoutInflater().inflate(R.layout.activity_storage, frameLayout);
        navigationView.setCheckedItem(position);


        choose = findViewById(R.id.button_choose_image);
        upload = findViewById(R.id.upload_button);
        show = findViewById(R.id.textview_upload);
        fileName = findViewById(R.id.edit_text_file_name);
        image = findViewById(R.id.image_upload);
        bar = findViewById(R.id.progress_bar);

        /**
         * Get the references of our database and storage
         */
        storageRef = FirebaseStorage.getInstance().getReference("uploads");
        dbref = FirebaseDatabase.getInstance().getReference("uploads");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This doesn't allow the spam of the upload button
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(StorageActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadMethod();
                }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImages();
            }
        });
    }

    /**
     * Open the file app to choose an image
     */
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * When the activity is launched
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(image);
        }
    }

    /**
     * Get the file extension of the image
     *
     * @param uri link of the image
     * @return .png, .jpg, etc.
     */
    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    /**
     * Upload the image in the Firebase store
     */
    private void uploadMethod() {
        if (imageUri != null) {
            StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            uploadTask = fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //the bar will be set to 0 after 0.5 second
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bar.setProgress(0);
                        }
                    }, 500);

                    Toast.makeText(StorageActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    UploadClass uploadClass = new UploadClass(fileName.getText().toString().trim(),downloadUrl.toString());
                    String uploadID = dbref.push().getKey();
                    dbref.child(uploadID).setValue(uploadClass);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(StorageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                //animation of the bar
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    bar.setProgress((int) progress);
                }
            });
        } else {
            Toast.makeText(this, "no file detected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Creation of the activity where the user can see all images
     */
    private void openImages(){
        Intent intent = new Intent(this, ListPicActivity.class);
        startActivity(intent);

    }
}
