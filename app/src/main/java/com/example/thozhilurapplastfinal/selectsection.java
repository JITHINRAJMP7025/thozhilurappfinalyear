package com.example.thozhilurapplastfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class selectsection extends AppCompatActivity {
Button worker,leader,panchayat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectsection);
        leader=(Button) findViewById(R.id.button);
        worker=(Button) findViewById(R.id.button2);
        panchayat=(Button)findViewById(R.id.button3);
        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(selectsection.this,userlogin.class);
                startActivity(intent);
            }
        });
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(selectsection.this,leaderlogin.class);
                startActivity(intent1);
            }
        });
        panchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(selectsection.this,panchayat.class);
                startActivity(intent2);
            }
        });
        
    }

}