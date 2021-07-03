package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class sectionpage extends AppCompatActivity {

    CalendarView calenderview;

    Button next;
    TextView mydate,datetext;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1;
    String date1,user_mobid,passed_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectionpage);

         next = (Button) findViewById(R.id.next);
         calenderview=(CalendarView)findViewById(R.id.calendar);
         mydate=(TextView)findViewById(R.id.mydate);
        datetext=(TextView)findViewById(R.id.datetext);

        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("userid_id");

        }
        getRef1 = FirebaseDatabase.getInstance().getReference("users" + "/" + "leader" + "/" + passed_id);
        calenderview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date=(month + 1)+"/"+dayOfMonth+"/"+year;
                datetext.setText(date);

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date1 = datetext.getText().toString();
               // Toast.makeText(sectionpage.this, date1, Toast.LENGTH_SHORT).show();

                user_mobid = "users" + "/" + "leader"+ "/" + passed_id+"/"+"workdetails";
                String details = user_mobid ;
                String dateapply = details + "/" + date1;
               // DatabaseReference leadername = mDatabase.getReference(dateapply);
               // leadername.setValue(date1);
                Intent intent=new Intent(sectionpage.this,datepage.class);
                Bundle bundle=new Bundle();
                bundle.putString("date_id", date1);
                bundle.putString("user_id",passed_id);
                intent.putExtras(bundle);
              //  Toast.makeText(sectionpage.this, passed_id, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


    }
}