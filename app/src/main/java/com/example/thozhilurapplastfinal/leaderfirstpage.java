package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class leaderfirstpage extends AppCompatActivity {
Button apply,workdetails;
String passed_id;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderfirstpage);
        mDrawerlayout=(DrawerLayout)findViewById(R.id.drawer);
        mtoggle=new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        navigationView=(NavigationView)findViewById(R.id.navigation_menu);
        apply=(Button) findViewById(R.id.apply);
        workdetails=(Button) findViewById(R.id.workdetails);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("userid_id");

            Toast.makeText(leaderfirstpage.this, passed_id, Toast.LENGTH_SHORT).show();

        }
        getRef1=FirebaseDatabase.getInstance().getReference("users" + "/" + "leader" + "/" + passed_id);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(leaderfirstpage.this,applywork.class);

                intent.putExtra("userid_id", passed_id);
                startActivity(intent);
            }
        });
        workdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(leaderfirstpage.this,sectionpage.class);
                intent.putExtra("userid_id", passed_id);
                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        Intent intent = new Intent(leaderfirstpage.this, selectsection.class);
                        startActivity(intent);
                        break;

                    case R.id.changepassword:
                        Intent intent1 = new Intent(leaderfirstpage.this, changepassword.class);
                        intent1.putExtra("userid_id", passed_id);
                        startActivity(intent1);
                        break;
                }
                return false;

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}