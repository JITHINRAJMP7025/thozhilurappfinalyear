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

import java.util.ArrayList;
import java.util.List;

public class signup extends AppCompatActivity {
    EditText Name,Mobile,Password,Confirmpassword;
    Button register;
    String Nameworker,mobworker,pswdworker,cnfrmpswrdworker;
    TextView signup,login;
    List<String> list=new ArrayList<String>();
    int f=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login=(TextView)findViewById(R.id.logintext);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(signup.this, userlogin.class);
                startActivity(d);
            }
        });
        register=(Button)findViewById(R.id.register);
        DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"workers");
        fb_to_read.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()){
                    list.add(String.valueOf(dsp.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                f = 0;
                Name = (EditText) findViewById(R.id.name);
                Mobile = (EditText) findViewById(R.id.mobile);
                Password = (EditText) findViewById(R.id.password);
                Confirmpassword = (EditText) findViewById(R.id.confirmpassword);

                Nameworker = Name.getText().toString();
                mobworker = Mobile.getText().toString();
                pswdworker = Password.getText().toString();
                cnfrmpswrdworker = Confirmpassword.getText().toString();

                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                String user_id = "users"+"/"+"workers";


                for (final String data : list) {

                    if (data.equals(mobworker.toString())) {
                        f = 1;
                    }
                }
                if (f == 0) {
                    if (mobworker.length() == 10) {
                        if (pswdworker.length() >= 5) {
                            if (pswdworker.equals(cnfrmpswrdworker)) {
                                String details = user_id + "/" + mobworker + "/" + "profile";
                                String workername = details + "/" + "name";
                                String num = details + "/" + "mobile";
                                String passwordpath = details + "/" + "password1";


                                DatabaseReference Workername = mDatabase.getReference(workername);
                                DatabaseReference mno = mDatabase.getReference(num);
                                DatabaseReference Passwordpath = mDatabase.getReference(passwordpath);
                                DatabaseReference chittyCount = mDatabase.getReference(user_id + "/" + mobworker + "/workerscount");


                                Workername.setValue(Nameworker);
                                mno.setValue(mobworker);
                                Passwordpath.setValue(pswdworker);
                                chittyCount.setValue(0);

                                Intent d = new Intent(signup.this, userlogin.class);
                                startActivity(d);
                                finish();
                            } else {
                                Toast.makeText(signup.this, "PASSWORDS DOESN'T MATCH", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(signup.this, "Not Strong 5-10 digit", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(signup.this, "NOT A VALID NUMBER", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(signup.this, "MOBILENUMBER ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
