package com.example.thozhilurapplastfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class addleader extends AppCompatActivity {
EditText name,number,password;
Button set;
    private StorageReference m1storageref;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1, getRef2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageuri;
    String name1,number1,password1,user_mobid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addleader);
        name=(EditText)findViewById(R.id.editTextTextPersonName2);
        number=(EditText)findViewById(R.id.editTextTextPersonName3);
        password=(EditText)findViewById(R.id.editTextTextPersonName5);
        set=(Button)findViewById(R.id.button6);
set.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               getRef2 = FirebaseDatabase.getInstance().getReference("users" + "/" + "panchayat" + "/" + "leaderlogin");
                               name1 = name.getText().toString();
                               number1 = number.getText().toString();
                               password1 = password.getText().toString();

                               user_mobid = "users" + "/" + "panchayat"+"/"+"leaderlogin"+"/"+number1;
                               String details = user_mobid ;

                               String leadername = details + "/" + "name";
                               String leaderno = details + "/" + "mobileno";
                               String mobileno = details + "/" + "password";

                               DatabaseReference leadername1 = mDatabase.getReference(leadername);
                               DatabaseReference leaderno1 = mDatabase.getReference(leaderno);
                               DatabaseReference leaderpassword1 = mDatabase.getReference(mobileno);

                               leadername1.setValue(name1);
                               leaderno1.setValue(number1);
                               leaderpassword1.setValue(password1);
                           }
                       });

    }
}
