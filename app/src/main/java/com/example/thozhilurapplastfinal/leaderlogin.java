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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
Spinner pid;
EditText userid,password;
TextView forgotpassword;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef4;
    String user_id,password_id;
    public String panchayatidname;
    List<String> panchyat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderlogin);

        userid=(EditText)findViewById(R.id.userid);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        forgotpassword=(TextView)findViewById(R.id.forgotpassword);
        pid=(Spinner)findViewById(R.id.panchayatid);
        ArrayAdapter arrayAdapter = new ArrayAdapter(leaderlogin.this, android.R.layout.simple_spinner_item, panchyat);

          DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"Panchayat");

          fb_to_read.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  List<String> list = new ArrayList<String>();
                  for (DataSnapshot dsp : snapshot.getChildren()) {
                      list.add(String.valueOf(dsp.getKey()));
                      // Toast.makeText(leaderlogin.this,"hi", Toast.LENGTH_SHORT).show();
                  }

                  for(final String data:list) {
                      Toast.makeText(leaderlogin.this, data, Toast.LENGTH_SHORT).show();
                      Toast.makeText(leaderlogin.this,"hai", Toast.LENGTH_SHORT).show();
                      panchyat.add(data);
                  }
                  pid.setAdapter(arrayAdapter);

              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });

        login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Intent login=new Intent(leaderlogin.this,leaderfirstpage.class);
            //startActivity(login);
            panchayatidname= pid.getSelectedItem().toString();

            user_id = userid.getText().toString();

            DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users" + "/" + "Panchayat" + "/" +panchayatidname+"/"+ "leaderlogin");
            fb_to_read.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> list = new ArrayList<String>();
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        list.add(String.valueOf(dsp.getKey()));

                    }
                    for (final String data : list) {
                        Toast.makeText(leaderlogin.this,data , Toast.LENGTH_SHORT).show();
                        if (data.equals(user_id.toString())) {

                            password_id = password.getText().toString();
                            Toast.makeText(leaderlogin.this, password_id, Toast.LENGTH_SHORT).show();
                            DatabaseReference fb_read = FirebaseDatabase.getInstance().getReference("users" + "/" + "Panchayat"+"/" + panchayatidname+"/" + "leaderlogin" + "/" + user_id + "/" + "password");
                            fb_read.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   String pass = snapshot.getValue(String.class);

                                    if (pass.equals(password_id.toString())) {
                                        Toast.makeText(leaderlogin.this, pass, Toast.LENGTH_SHORT).show();
                                       Intent login = new Intent(leaderlogin.this, leaderfirstpage.class);

                                        login.putExtra("userid_id", user_id);//change code here
                                        Toast.makeText(leaderlogin.this, panchayatidname, Toast.LENGTH_SHORT).show();

                                       // bundle.putString("panchayatname_id",panchayatidname);
                                        //.putExtras(bundle);
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
