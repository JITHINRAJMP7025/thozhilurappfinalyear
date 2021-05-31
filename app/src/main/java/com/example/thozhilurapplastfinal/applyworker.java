package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class applyworker extends AppCompatActivity {
    String user_mobid;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1, getRef2, getRef3;
    EditText name, address, adharno, bankno;
    TextView applverify, choosepic, chooseadhar, uploadpic, adharpic;
    Button pic, applyuser;
    Spinner panchayat;
    String name1, address1, adharno1, bankno1, user_id;
    ImageView mImageView;
    private StorageReference m1storageref;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyworker);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            user_mobid = login.getString("usermob_id");

            Toast.makeText(applyworker.this, user_mobid, Toast.LENGTH_SHORT).show();
        }


        name = (EditText) findViewById(R.id.edit_name);
        address = (EditText) findViewById(R.id.edit_address);
        adharno = (EditText) findViewById(R.id.edit_adhar);
        bankno = (EditText) findViewById(R.id.edit_bank);
        applverify = (TextView) findViewById(R.id.applystatus);

        chooseadhar = (TextView) findViewById(R.id.adharpictextview);
        applyuser = (Button) findViewById(R.id.apply);
        panchayat = (Spinner) findViewById(R.id.editheading);



        getRef3 = FirebaseDatabase.getInstance().getReference("users" + "/" + "workers" + "/" + user_mobid + "/" + "Applydetails" + "/" + "adharphoto");
        getRef1 = FirebaseDatabase.getInstance().getReference("users" + "/" + "workers" + "/" + user_mobid + "/" + "Applydetails");
        m1storageref = FirebaseStorage.getInstance().getReference("users" + "/" + "workers" + "/" + user_mobid + "/" + "Applydetails" + "/" + "adharphoto");



        chooseadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        applyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name1 = name.getText().toString();
                address1 = address.getText().toString();
                adharno1 = adharno.getText().toString();
                bankno1 = bankno.getText().toString();
                user_id = "users" + "/" + "workers" + "/" + user_mobid;
                Toast.makeText(applyworker.this, user_id, Toast.LENGTH_SHORT).show();

                String details = user_id + "/" + "Applydetails";
                String workername = details + "/" + "name";
                String workeraddress = details + "/" + "address";
                String adharno = details + "/" + "adharno";
                String bankno = details + "/" + "bankno";


                DatabaseReference Workername1 = mDatabase.getReference(workername);
                DatabaseReference workeraddress1 = mDatabase.getReference(workeraddress);
                DatabaseReference workeradharno1 = mDatabase.getReference(adharno);
                DatabaseReference workerbankno1 = mDatabase.getReference(bankno);

                Workername1.setValue(name1);
                workeraddress1.setValue(address1);
                workeradharno1.setValue(adharno1);
                workerbankno1.setValue(bankno1);
                uploadfile();
               // uploadfile1();


            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview);
        bottomNavigationView.setSelectedItemId(R.id.Applyworker);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Profileworkers:
                        Intent intent1 = new Intent(applyworker.this, userprofile.class);
                        intent1.putExtra("usermob_id", user_mobid);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return;
                    case R.id.workdetails:

                        Intent intent = new Intent(applyworker.this, workersdetails.class);
                        intent.putExtra("usermob_id", user_mobid);
                        startActivity(intent);
                        // startActivity(new Intent(getApplicationContext(), workersdetails.class));
                        overridePendingTransition(0, 0);
                        return;
                    case R.id.Applyworker:

                        return;
                }
            }
        });


    }


    private void openFileChooser() {
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
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));//cR
    }

    private void uploadfile() {
        if ( imageuri != null) {

            StorageReference fileReference = m1storageref.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));

            fileReference.putFile(imageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            upload upload1 = new upload(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadid = getRef3.push().getKey();
                            getRef3.child(uploadid).setValue(upload1);
                            Toast.makeText(applyworker.this, "upload Successful", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(applyworker.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }


    }

