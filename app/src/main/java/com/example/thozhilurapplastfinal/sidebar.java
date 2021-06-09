package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class sidebar extends AppCompatActivity {
    EditText name,userid,password;
    Button set;
    private StorageReference m1storageref;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1, getRef2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageuri;
    String name1,user_id,password1,user_mobid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addleader);
        name=(EditText)findViewById(R.id.editTextTextPersonName2);
        userid=(EditText)findViewById(R.id.editTextTextPersonName3);
        password=(EditText)findViewById(R.id.editTextTextPersonName5);
        set=(Button)findViewById(R.id.button6);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRef2 = FirebaseDatabase.getInstance().getReference("users" + "/" + "Admin");
                name1 = name.getText().toString();
                user_id = userid.getText().toString();
                password1 = password.getText().toString();

                user_mobid = "users" + "/" + "Admin"+"/"+user_id;
                String details = user_mobid ;

                String panchayatname = details + "/" + "name";
                String panchayatid = details + "/" + "panchayatid";
                String panchayatpswrd = details + "/" + "password";

                DatabaseReference panchayatname1 = mDatabase.getReference(panchayatname);
                DatabaseReference panchayatid1 = mDatabase.getReference(panchayatid);
                DatabaseReference panchayatpswrd1 = mDatabase.getReference(panchayatpswrd);

                panchayatname1.setValue(name1);
                panchayatid1.setValue(user_id);
                panchayatpswrd1.setValue(password1);
            }
        });

    }
}
