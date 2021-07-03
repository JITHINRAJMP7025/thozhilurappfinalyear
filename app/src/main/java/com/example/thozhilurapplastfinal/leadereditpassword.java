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

public class leadereditpassword extends AppCompatActivity {
    String user,password,confirmpassword;
    EditText password1,password2;
    Button save;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadereditpassword);
        Bundle forgetPassword=getIntent().getExtras();
        if(forgetPassword!=null){
            user=forgetPassword.getString("user_id");
           // Toast.makeText(leadereditpassword.this,user, Toast.LENGTH_SHORT).show();
        }
        password1=findViewById(R.id.passwordEditText);
        password2=findViewById(R.id.confirmpasswordEditText);
        save=findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password=password1.getText().toString();
                confirmpassword=password2.getText().toString();

                if(password.equals(confirmpassword)) {
                    DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"Panchayat");
                    fb_to_read.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                            List<String> list = new ArrayList<String>();
                            for (DataSnapshot dsp : snapshot.getChildren()) {
                                list.add(String.valueOf(dsp.getKey()));
                            }
                            for (final String data : list) {

                                DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"Panchayat"+"/"+data+"/"+"leaderlogin");
                                fb_to_read.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                        List<String> list = new ArrayList<String>();
                                        for (DataSnapshot dsp : snapshot.getChildren()) {
                                            list.add(String.valueOf(dsp.getKey()));
                                        }
                                        for (final String data1 : list) {
                                            if(data1.equals(user)){
                                                FirebaseDatabase regDatabase = FirebaseDatabase.getInstance();
                                                String path = "users"+"/"+"Panchayat"+"/"+data+"/"+"leaderlogin"+"/"+data1+"/"+"password";
                                                DatabaseReference passwordReference = regDatabase.getReference(path );
                                                passwordReference.setValue(password);
                                                Intent toLoginPage=new Intent(leadereditpassword.this,leaderlogin.class);
                                                startActivity(toLoginPage);
                                                finish();
                                            }
                                        }

                                        }

                                    @Override
                                    public void onCancelled(@NonNull  DatabaseError error) {

                                    }
                                });

                            }

                        }



                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(leadereditpassword.this, "NOT EQUAL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}