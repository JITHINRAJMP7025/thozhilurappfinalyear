package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class changepassword extends AppCompatActivity {
    EditText password,newpassword1,newpassword2;
    String pswrd,passed_id,newpswrd1,newpswrd2;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1;
    DatabaseReference dbRef;
    Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        password=(EditText)findViewById(R.id.password);
        newpassword1=(EditText)findViewById(R.id.newpassword1);
        newpassword2=(EditText)findViewById(R.id.newpassword2);
        reset=(Button)findViewById(R.id.reset);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("userid_id");

            Toast.makeText(changepassword.this, passed_id, Toast.LENGTH_SHORT).show();

        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          pswrd=password.getText().toString();

        // getRef1=FirebaseDatabase.getInstance().getReference("users" + "/" + "panchayat" + "/" + "leaderlogin");
          DatabaseReference fb_read = FirebaseDatabase.getInstance().getReference( "users" + "/" + "panchayat" + "/" + "leaderlogin"+ "/" +passed_id+"/"+ "password");
         fb_read.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String pass = snapshot.getValue(String.class);
                Toast.makeText(changepassword.this, pass, Toast.LENGTH_SHORT).show();
                if (pass.equals(pswrd.toString())) {

                    newpswrd1 = newpassword1.getText().toString();
                    Toast.makeText(changepassword.this, "hai", Toast.LENGTH_SHORT).show();
                    newpswrd2 = newpassword2.getText().toString();
                    if (newpswrd1.equals(newpswrd2)) {
                       DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users" + "/" + "panchayat" + "/" + "leaderlogin" + "/" + passed_id );
                       // dbRef.child("users").child("panchayat").child("leaderlogin").child(passed_id).child("password").setValue(newpswrd1);

                        dbRef.child("password").setValue(newpswrd1);
                        Toast.makeText(changepassword.this, "password changed", Toast.LENGTH_SHORT).show();
                       //  Intent intent=new Intent(changepassword.this,sectionpage.class);
                        // startActivity(intent);
                    }
                    else{
                        Toast.makeText(changepassword.this, "new password not matching", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(changepassword.this, "invalid password", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            }
        });






    }
}