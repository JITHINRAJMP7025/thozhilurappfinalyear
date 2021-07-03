package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class workdetails extends AppCompatActivity {
 String passed_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workdetails);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview);
        bottomNavigationView.setSelectedItemId(R.id.workdetails);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("Mobile_id");

           // Toast.makeText(workdetails.this, passed_id, Toast.LENGTH_SHORT).show();
        }
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Profileworkers:
                        Intent intent1 = new Intent(workdetails.this, userprofile.class);
                        intent1.putExtra("Mobile_id", passed_id);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return;
                    case R.id.workdetails:
                        return;
                    case R.id.Applyworker:
                        Intent intent = new Intent(workdetails.this, applyworker.class);
                        intent.putExtra("Mobile_id", passed_id);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return;
                }
            }
        });


    }



}