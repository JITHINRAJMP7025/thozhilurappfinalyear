package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminlogin extends AppCompatActivity {
    EditText mob, password;
    Button login;
    TextView forgotpswrd, signup, signupsecond;
    String user_id, password_id,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        login=(Button)findViewById(R.id.login);

        mob=(EditText)findViewById(R.id.userid);
        password=(EditText)findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=mob.getText().toString();

                DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users");
                fb_to_read.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String username = snapshot.child("username").getValue(String.class);

                        if (username.equals(id.toString())){
                           // Toast.makeText(adminlogin.this, username, Toast.LENGTH_SHORT).show();
                           password_id = password.getText().toString();
                          // Toast.makeText(adminlogin.this, password_id, Toast.LENGTH_SHORT).show();
                            DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users");
                            fb_to_read.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                    String pswrd = snapshot.child("password").getValue(String.class);
                                   // password.setText(pswrd);
                                  //  Toast.makeText(adminlogin.this, pswrd, Toast.LENGTH_SHORT).show();

                                   // Toast.makeText(adminlogin.this, password_id, Toast.LENGTH_SHORT).show();
                                    if (pswrd.equals(password_id.toString())) {
                                      //  Toast.makeText(adminlogin.this, "pswrd", Toast.LENGTH_SHORT).show();
                                        Intent login = new Intent(adminlogin.this, adminfirst.class);
                                       // login.putExtra("Mobile_id", mobile_id);//change code here
                                        startActivity(login);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });

            }
        });

    }
}