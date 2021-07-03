package com.example.thozhilurapplastfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class adminsetup extends AppCompatActivity {
    EditText name,userid,password,mailid;
    Button set;
   // private StorageReference m1storageref;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference  getRef2;
    //private static final int PICK_IMAGE_REQUEST = 1;
    //private Uri imageuri;
    String name1,user_id,password1,user_mobid,mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsetup);


            name=(EditText)findViewById(R.id.editTextTextPersonName2);
            userid=(EditText)findViewById(R.id.editTextTextPersonName3);
            password=(EditText)findViewById(R.id.editTextTextPersonName5);
             mailid=(EditText)findViewById(R.id.mail);
            set=(Button)findViewById(R.id.button6);
            set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getRef2 = FirebaseDatabase.getInstance().getReference("users" + "/" + "Admin");
                    name1 = name.getText().toString();
                    user_id = userid.getText().toString();
                    password1 = password.getText().toString();
                     mail=mailid.getText().toString();
                    user_mobid = "users" + "/" + "Admin"+"/"+user_id;


                    String panchayatname = user_mobid + "/" + "name";
                      String panchayatmail = user_mobid + "/" + "mailid";
                    String panchayatid = user_mobid + "/" + "panchayatid";
                    String panchayatpswrd = user_mobid+ "/" + "password";

                    DatabaseReference panchayatname1 = mDatabase.getReference(panchayatname);
                    DatabaseReference panchayatmid = mDatabase.getReference(panchayatmail);
                    DatabaseReference panchayatid1 = mDatabase.getReference(panchayatid);
                    DatabaseReference panchayatpswrd1 = mDatabase.getReference(panchayatpswrd);


                    panchayatname1.setValue(name1);
                    panchayatmid.setValue(mail);
                    panchayatid1.setValue(user_id);
                    panchayatpswrd1.setValue(password1);
                    Intent intent1=new Intent(adminsetup.this,selectsection.class);
                    startActivity(intent1);
                    finish();



                }
            });

        }
    }

