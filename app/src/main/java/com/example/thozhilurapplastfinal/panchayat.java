package com.example.thozhilurapplastfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class panchayat extends AppCompatActivity {
Button addleader,admin;
TextView logout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panchayat);

            admin =(Button)findViewById(R.id.button5);
            logout1=(TextView)findViewById(R.id.logout);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(panchayat.this,adminsetup.class);
                startActivity(intent1);
                finish();

            }
        });
       /* logout1.setOnClickListener(new View.zOnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(panchayat.this,selectsection.class);
                startActivity(intent2);
                finish();
            }
        });*/
    }
}