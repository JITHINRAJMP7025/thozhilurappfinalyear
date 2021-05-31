package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.provider.FontsContractCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class workersdetails extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST=1;
    private Uri imageuri;
    Button upload;
    TextView chooseimage;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef2;
    ImageView imageView;
 //   EditText mEditTextFilename;
    String passed_id;
    private StorageReference mstorageref;
    //private DatabaseReference mdatabaseref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workersdetails);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnavigationview);

        bottomNavigationView.setSelectedItemId(R.id.workdetails);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("usermob_id");

            Toast.makeText(workersdetails.this, passed_id, Toast.LENGTH_SHORT).show();

        }



     /*  mstorageref= FirebaseStorage.getInstance().getReference("users"+"/"+"workers"+"/"+passed_id+"/"+"Applydetails");
        getRef2= FirebaseDatabase.getInstance().getReference("users"+"/"+"workers"+"/"+passed_id+"/"+"Applydetails");

        chooseimage=(TextView) findViewById(R.id.choose);
        imageView=(ImageView)findViewById(R.id.imageview);
        upload=(Button)findViewById(R.id.upload);
       // mEditTextFilename=(EditText)findViewById(R.id.edittext);
        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

       upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadfile();
            }
        });*/
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Profileworkers:
                        startActivity(new Intent(getApplicationContext(), userprofile.class));
                        overridePendingTransition(0, 0);
                        return;
                    case R.id.workdetails:
                        return;
                    case R.id.Applyworker:
                        startActivity(new Intent(getApplicationContext(), applyworker.class));
                        overridePendingTransition(0, 0);
                        return;
                }
            }
        });
    }
   /* private  void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() !=null) {
            imageuri =data.getData();
            Picasso.with(this).load(imageuri).into(imageView);

        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));//cR
    }
private void uploadfile()
{
    if(imageuri != null) {
        StorageReference fileReference = mstorageref.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));

                 fileReference.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //mEditTextFilename.getText().toString().trim(),
                        upload upload1 =new upload(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                        String uploadid = getRef2.push().getKey();
                        getRef2.child(uploadid).setValue(upload1);
                        Toast.makeText(workersdetails.this, "upload Successful", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(workersdetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    else
    {
        Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
    }
}*/
}