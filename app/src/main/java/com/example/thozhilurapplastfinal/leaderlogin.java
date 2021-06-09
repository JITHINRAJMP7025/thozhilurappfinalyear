package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class leaderlogin extends AppCompatActivity {
Button login;
EditText userid,password;
TextView forgotpassword;
String user_id,password_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderlogin);

        userid=(EditText)findViewById(R.id.userid);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        forgotpassword=(TextView)findViewById(R.id.forgotpassword);
        login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Intent login=new Intent(leaderlogin.this,leaderfirstpage.class);
            //startActivity(login);
            user_id = userid.getText().toString();
            DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users" + "/" + "panchayat" + "/" + "leaderlogin");
            fb_to_read.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> list = new ArrayList<String>();
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        list.add(String.valueOf(dsp.getKey()));
                    }
                    for (final String data : list) {

                        if (data.equals(user_id.toString())) {

                            password_id = password.getText().toString();
                            DatabaseReference fb_read = FirebaseDatabase.getInstance().getReference("users" + "/" + "panchayat" + "/" + "leaderlogin" + "/" + user_id + "/" + "password");
                            fb_read.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String pass = snapshot.getValue(String.class);

                                    if (pass.equals(password_id.toString())) {
                                        Intent login = new Intent(leaderlogin.this, leaderfirstpage.class);
                                        login.putExtra("userid_id", user_id);//change code here
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

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        });
     forgotpassword.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             user_id = userid.getText().toString();
             Intent intent=new Intent(leaderlogin.this,leaderforgotpswrd.class);
             intent.putExtra("userid_id", user_id);//change code here

             startActivity(intent);
         }
     });

    }

}
