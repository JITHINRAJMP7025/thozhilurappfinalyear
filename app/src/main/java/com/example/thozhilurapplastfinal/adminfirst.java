package com.example.thozhilurapplastfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminfirst extends AppCompatActivity {
    Button admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminfirst);
        admin =(Button)findViewById(R.id.button5);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(adminfirst.this,adminsetup.class);
                startActivity(intent1);
                finish();

            }
        });
    }
}