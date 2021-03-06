package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class applyworker extends AppCompatActivity {
    String user_mobid,cardno;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1, getRef2, getRef3,getRef4;
    EditText name, address, adharno, bankno;
    TextView applverify, choosepic, chooseadhar, uploadpic, adharpic;
    Button pic, applyuser;
    Spinner panchayat;
    String name1;
    String address1;
    String adharno1;
    String bankno1;
    String user_id;
    String mob;
    EditText mobile;
    ImageView mImageView;
    public String panchayatidname;
    private StorageReference m1storageref;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageuri;
    List<String> panchyatid = new ArrayList<>();

//ValueEventListener listeneer;
//ArrayAdapter<String> adapter;
//ArrayList<String> spinnerDatalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyworker);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            user_mobid = login.getString("Mobile_id");
             cardno=login.getString("cardno");
           // Toast.makeText(applyworker.this, user_mobid, Toast.LENGTH_SHORT).show();
        }

        name = (EditText) findViewById(R.id.edit_name);
        address = (EditText) findViewById(R.id.edit_address);
        adharno = (EditText) findViewById(R.id.edit_adhar);
        bankno = (EditText) findViewById(R.id.edit_bank);

        mobile = (EditText)findViewById(R.id.edit_mobile);
        chooseadhar = (TextView) findViewById(R.id.adharpictextview);
        applyuser = (Button) findViewById(R.id.apply);
        panchayat = (Spinner) findViewById(R.id.panchayatname);
       // spinnerDatalist =new ArrayList<>();
      //  adapter = new ArrayAdapter<String>(applyworker.this, android.R.layout.simple_spinner_dropdown_item,spinnerDatalist);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, panchyatid);




        getRef3 = FirebaseDatabase.getInstance().getReference("users" + "/" + "workers" + "/" + user_mobid + "/" + "Applydetails" + "/" + "adharphoto");
        getRef1 = FirebaseDatabase.getInstance().getReference("users" + "/" + "workers" + "/" + user_mobid + "/" + "Applydetails");
        m1storageref = FirebaseStorage.getInstance().getReference("users" + "/" + "workers" + "/" + user_mobid + "/" + "Applydetails" + "/" + "adharphoto");
        getRef4 = FirebaseDatabase.getInstance().getReference("users"+"/"+"Admin");

        DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"Admin");
        fb_to_read.addValueEventListener(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                 List<String> list=new ArrayList<String>();
                                                 for (DataSnapshot dsp : snapshot.getChildren()){
                                                     list.add(String.valueOf(dsp.getKey()));
                                                 }
                                                 for(final String data:list) {
                                                    // Toast.makeText(applyworker.this, data, Toast.LENGTH_SHORT).show();
                                                     panchyatid.add(data);
                                                 }
                                                // adapter = new ArrayAdapter<String>(applyworker.this, android.R.layout.simple_spinner_dropdown_item,spinnerDatalist);
                                                 panchayat.setAdapter(arrayAdapter);

                                             }

                                             @Override
                                             public void onCancelled(@NonNull DatabaseError error) {

                                             }
                                         });


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
                mob=mobile.getText().toString();
                panchayatidname= panchayat.getSelectedItem().toString();
                user_id = "users" + "/" + "workers" + "/" + user_mobid;
              //  Toast.makeText(applyworker.this, user_id, Toast.LENGTH_SHORT).show();

                String details = user_id + "/" + "Applydetails";
                String workername = details + "/" + "name";
                String workeraddress = details + "/" + "address";
                String adharno = details + "/" + "adharno";
                String bankno = details + "/" + "bankno";
                String panchaytname=details +"/"+"panchayatname";
                String mobile1 = details +"/"+"mobile";


                DatabaseReference Workername1 = mDatabase.getReference(workername);
                DatabaseReference workeraddress1 = mDatabase.getReference(workeraddress);
                DatabaseReference workeradharno1 = mDatabase.getReference(adharno);
                DatabaseReference workerbankno1 = mDatabase.getReference(bankno);
                DatabaseReference workerpanchayat = mDatabase.getReference(panchaytname);
                DatabaseReference workermobile = mDatabase.getReference(mobile1);

                Workername1.setValue(name1);
                workeraddress1.setValue(address1);
                workeradharno1.setValue(adharno1);
                workerbankno1.setValue(bankno1);
                workerpanchayat.setValue(panchayatidname);
                workermobile.setValue(mob);
                uploadfile();
               // uploadfile1();

                applyuser.setEnabled(false);
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
                        intent1.putExtra("Mobile_id", user_mobid);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return;
                    case R.id.workdetails:
                        Intent intent = new Intent(applyworker.this, workersdetails.class);
                        intent.putExtra("Mobile_id", user_mobid);
                        intent.putExtra("cardno", cardno);
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
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    upload up=new upload(uri.toString());
                                    String uploadId = getRef3.push().getKey();
                                    getRef3.child(uploadId).setValue(up);

                                }
                            });
                          /*  upload upload1 = new upload(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadid = getRef3.push().getKey();
                            getRef3.child(uploadid).setValue(upload1);
                            Toast.makeText(applyworker.this, "upload Successful", Toast.LENGTH_SHORT).show();*/

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

