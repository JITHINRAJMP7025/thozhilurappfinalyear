package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class userlogin extends AppCompatActivity {
    EditText mob, password;
    Button login;
    TextView forgotpswrd, signup, signupsecond;
    String mobile_id, password_id;
    String check_ID, check_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        signup = (TextView) findViewById(R.id.signuptext);
        signupsecond = (TextView) findViewById(R.id.signuptextsecond);





        login=(Button)findViewById(R.id.login);
        forgotpswrd=(TextView)findViewById(R.id.forgotpassword);
        mob=(EditText)findViewById(R.id.number);
        password=(EditText)findViewById(R.id.password);


       /* SharedPreferences lDetails =  getSharedPreferences("logdetails", MODE_PRIVATE);
        check_ID = lDetails.getString("mobile","0");
        check_Password = lDetails.getString("password1","0");
       // Toast.makeText(userlogin.this, check_ID, Toast.LENGTH_SHORT).show();*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobile_id=mob.getText().toString();
                DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"workers");
                fb_to_read.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        List<String> list=new ArrayList<String>();
                        for (DataSnapshot dsp : snapshot.getChildren()){
                            list.add(String.valueOf(dsp.getKey()));
                        }
                        for(final String data:list) {

                            if (data.equals(mobile_id.toString())) {

                                password_id = password.getText().toString();
                                DatabaseReference fb_read = FirebaseDatabase.getInstance().getReference("users" + "/" + "workers" + "/" + mobile_id + "/" + "profile" + "/" + "password1");
                                fb_read.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String pass = snapshot.getValue(String.class);

                                        if (pass.equals(password_id.toString())) {

                                           SharedPreferences loginDetails = getSharedPreferences("logdetails", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = loginDetails.edit();
                                            editor.putString("mobile", mobile_id);
                                            editor.putString("password1", password_id);
                                            editor.commit();

                                            Intent login = new Intent(userlogin.this, userprofile.class);
                                            login.putExtra("Mobile_id", mobile_id);//change code here
                                            startActivity(login);
                                            finish();

                                        } else {
                                            Toast.makeText(userlogin.this, "WRONG USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(userlogin.this, signup.class);
                startActivity(reg);
                finish();
            }
        });

        forgotpswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotIntent = new Intent(userlogin.this, forgotpswrd.class);
                forgotIntent.putExtra("Mobile_id", mobile_id);
                startActivity(forgotIntent);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(userlogin.this, signup.class);

                startActivity(signupIntent);
            }
        });
        signupsecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent1 = new Intent(userlogin.this, signup.class);

                startActivity(signupIntent1);
            }
        });
    }
}