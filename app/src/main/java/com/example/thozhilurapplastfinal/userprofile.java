 package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.squareup.picasso.Picasso.setSingletonInstance;

 public class userprofile extends AppCompatActivity {
    String passed_id,num,id,test;
    Button ret;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1,getRef2,getRef4,image,getRef5;
    private StorageReference mstorageref;
     ImageView imageView;
     private Uri imageuri;
     private static final int PICK_IMAGE_REQUEST = 1;
     TextView displayname,displaynumber,logout,upload,choose,displaycard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnavigationview);

        bottomNavigationView.setSelectedItemId(R.id.Profileworkers);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("Mobile_id");

           // Toast.makeText(userprofile.this, passed_id, Toast.LENGTH_SHORT).show();

        }

       // choose=(TextView)findViewById(R.id.chooseimage);
       // upload=(TextView)findViewById(R.id.upload);
        imageView=(ImageView)findViewById(R.id.roundedimage);
        displayname=(TextView)findViewById(R.id.displayname);
        displaynumber=(TextView)findViewById(R.id.displaymob);
        displaycard=(TextView)findViewById(R.id.displaycard);
        logout=(TextView)findViewById(R.id.logouttext);
        mstorageref = FirebaseStorage.getInstance().getReference("users" + "/" + "workers" + "/" + passed_id + "/" + "Applydetails" + "/" + "photo");
        getRef1 = FirebaseDatabase.getInstance().getReference("users" + "/" + "workers" + "/" + passed_id + "/" + "profile" );
        getRef2 = FirebaseDatabase.getInstance().getReference("users" + "/" + "workers" + "/" +passed_id + "/" + "Applydetails" + "/" + "photo" );
        getRef4 = FirebaseDatabase.getInstance().getReference("users" + "/" + "workers" + "/" + passed_id + "/" + "Applydetails" + "/" + "photo" );

        getRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name1=snapshot.child("name").getValue(String.class);
                displayname.setText(name1);
                 num=snapshot.child("mobile").getValue(String.class);
                displaynumber.setText(num);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"Panchayat");
        fb_to_read.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> list=new ArrayList<String>();
                for (DataSnapshot dsp : snapshot.getChildren()){
                    list.add(String.valueOf(dsp.getKey()));
                }
                for(final String data:list) {

                    DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"Panchayat"+"/"+data+"/"+"cardno");
                    fb_to_read.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> list=new ArrayList<String>();
                            for (DataSnapshot dsp : snapshot.getChildren()){
                                list.add(String.valueOf(dsp.getKey()));
                            }
                            for(final String data1:list) {


                                getRef5 = FirebaseDatabase.getInstance().getReference("users"+"/"+"Panchayat"+"/"+data+"/"+"cardno"+"/"+data1);
                                getRef5.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                      //  Toast.makeText(userprofile.this, "num"+num, Toast.LENGTH_SHORT).show();
                                        if(data1.equals(num))
                                        {
                                        //    Toast.makeText(userprofile.this, "data1"+data1, Toast.LENGTH_SHORT).show();
                                             test=snapshot.child("cardno").getValue(String.class);
                                            displaycard.setText(test);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences loginDetails = getSharedPreferences("logdetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginDetails.edit();
                editor.putString("mobile","0" );
                editor.putString("password1","0" );
                editor.commit();

                Intent login=new Intent(userprofile.this,selectsection.class);
                finish();
                startActivity(login);
                System.exit(0);
            }
        });

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Profileworkers:
                         return;
                    case R.id.workdetails:
                        Intent intent1 = new Intent(userprofile.this, workersdetails.class);
                        intent1.putExtra("Mobile_id", passed_id);
                        intent1.putExtra("cardno",test);
                        startActivity(intent1);//
                        overridePendingTransition(0, 0);
                         return;
                    case R.id.Applyworker:
                        startActivity(new Intent(getApplicationContext(), applyworker.class));
                        Intent intent2 = new Intent(userprofile.this, applyworker.class);
                        intent2.putExtra("Mobile_id", passed_id);
                        intent2.putExtra("cardno", test);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                         return;
                }
            }
        });
      /*  choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });*/
     /*   upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadfile();
            }
        });*/
       /* ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                mstorageref= FirebaseStorage.getInstance().getReference("users" + "/" + "workers" + "/" + passed_id + "/" + "Applydetails" ).child("photo");

                try {

                    File localfile= File.createTempFile("tempfile",".jpeg");
                    mstorageref.getFile(localfile);
                    Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                    Toast.makeText(userprofile.this, "hai", Toast.LENGTH_SHORT).show();
                   // Picasso.with(this).load(imageuri).into(imageView);
                      .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                          @Override
                          public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                          }
                      }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/


    }
    /* private void openFileChooser() {
         Intent intent = new Intent();
         intent.setType("image/*");
         intent.setAction(Intent.ACTION_GET_CONTENT);
         startActivityForResult(intent, PICK_IMAGE_REQUEST);
     }
     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
             imageuri = data.getData();
            Picasso.with(this).load(imageuri).into(imageView);
         }

     }

     private String getFileExtension(Uri uri) {
         ContentResolver cr = getContentResolver();
         MimeTypeMap mime = MimeTypeMap.getSingleton();
         return mime.getExtensionFromMimeType(cr.getType(uri));//cR

     }
     private void uploadfile() {
         if (imageuri != null) {

             StorageReference fileReference = mstorageref.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));

             fileReference.putFile(imageuri)
                     .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             upload upload1 = new upload(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                             String uploadid = getRef2.push().getKey();
                             getRef2.child(uploadid).setValue(upload1);
                             Toast.makeText(userprofile.this, "upload Successful", Toast.LENGTH_SHORT).show();

                         }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(userprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     });

         }

     }
/*private void show(){
    mstorageref = FirebaseStorage.getInstance().getReference("users" + "/" + "workers" + "/" + passed_id + "/" + "Applydetails" + "/" + "photo");
    Glide.with(this).load(mstorageref).into(imageView);
    }*/
 }
