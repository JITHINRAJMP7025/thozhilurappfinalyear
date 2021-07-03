package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private static int SPLASH_SCREEN = 5000;
    Animation topanim, bottomanim;
    ImageView thozhilpic, logo;
    TextView appname;
    String mobile_id,password_id,check_ID,check_Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topanim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        thozhilpic = findViewById(R.id.imageView);
        logo = findViewById(R.id.imageView2);
        appname = findViewById(R.id.textView);
        thozhilpic.setAnimation(topanim);
        logo.setAnimation(bottomanim);
       // SharedPreferences lDetails =  getSharedPreferences("logdetails", MODE_PRIVATE);
     /*   Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                    Intent next=new Intent(getApplicationContext(),userlogin.class);
                    startActivity(next);
                    finish();
                    super.run();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };*/
      //  timer.start()
        //
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
      @Override
            public void run() {


                /*  SharedPreferences lDetails =  getSharedPreferences("logdetails", MODE_PRIVATE);
                  check_ID = lDetails.getString("mobile","0");
                  check_Password = lDetails.getString("password1","0");

                  if(check_ID != "0") {

                      mobile_id = check_ID;
                      Toast.makeText(MainActivity.this, mobile_id, Toast.LENGTH_SHORT).show();
                      DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"workers");
                      fb_to_read.addValueEventListener(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot) {

                              List<String> list = new ArrayList<String>();
                              for (DataSnapshot dsp : snapshot.getChildren()) {
                                  list.add(String.valueOf(dsp.getKey()));
                              }
                              for (final String data : list) {

                                  if (data.equals(mobile_id.toString())) {

                                      password_id = check_Password;
                                      DatabaseReference fb_read = FirebaseDatabase.getInstance().getReference("users" + "/" + "workers" + "/" + mobile_id + "/" + "profile" + "/" + "password1");
                                      fb_read.addValueEventListener(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                                              String pass = snapshot.getValue(String.class);


                                              if (pass.equals(password_id.toString())) {
                                                  SharedPreferences loginDetails = getSharedPreferences("logDetails", MODE_PRIVATE);
                                                  SharedPreferences.Editor editor = loginDetails.edit();
                                                  editor.putString("mobile", mobile_id);
                                                  editor.putString("password1", password_id);
                                                  editor.apply();
                                                  Intent login = new Intent(MainActivity.this, userprofile.class);
                                                  login.putExtra("Mobile_id", mobile_id);//change code here
                                                  startActivity(login);
                                                  finish();

                                              } else {
                                                  Toast.makeText(MainActivity.this, "WRONG USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
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


                  }*/
                 // else
                  //{
                      Intent intent=new Intent(MainActivity.this,selectsection.class);
                      startActivity(intent);
                      finish();
                  //}

      }
  },5000);



    }
}