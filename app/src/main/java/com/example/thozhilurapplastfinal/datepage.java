package com.example.thozhilurapplastfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class datepage extends AppCompatActivity implements View.OnClickListener {
TextView name;
Button apply;
int num=0;
Button add,submit;
LinearLayout layoutlist;
String name1,date_id1,user_id,passed_id,number,status,pic;
String date1,user_mobid;
FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
DatabaseReference getRef1;
int i=0;
List<String> dutystatus = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepage);


        Bundle login = getIntent().getExtras();
        if (login != null) {
            date_id1 = login.getString("date_id");
            passed_id = login.getString("user_id");

            //Intent intent=getIntent();

            Toast.makeText(datepage.this, date_id1, Toast.LENGTH_SHORT).show();
        }

        getRef1 = FirebaseDatabase.getInstance().getReference("users" + "/" + "leader" + "/" + passed_id + "/" + "workdetails" + "/" + date_id1);

        layoutlist = findViewById(R.id.layout_list);
        add = (Button) findViewById(R.id.addbutton);
        submit=(Button)findViewById(R.id.submitbutton);
        add.setOnClickListener(this);
        dutystatus.add("status");
        dutystatus.add("Fullday");
        dutystatus.add("Halfday");

    }

    @Override
    public void onClick(View v) {
        addView();
    }




    private void addView() {
        if(i==0) {
            final View sectionview = getLayoutInflater().inflate(R.layout.row_add_section, null, false);
            EditText editText = (EditText) sectionview.findViewById(R.id.sectiontext);
            // Button button=(Button)sectionview.findViewById(R.id.applybutton);
            AppCompatSpinner spinnerduty = (AppCompatSpinner) sectionview.findViewById(R.id.dutyspinner);
            ImageView imageview = (ImageView) sectionview.findViewById(R.id.imageclose);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dutystatus);
            spinnerduty.setAdapter(arrayAdapter);
            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeView(sectionview);
                }
            });
            layoutlist.addView(sectionview);
        }
        else {

            final View sectionview = getLayoutInflater().inflate(R.layout.row_add_section, null, false);
            EditText editText = (EditText) sectionview.findViewById(R.id.sectiontext);
            // Button button=(Button)sectionview.findViewById(R.id.applybutton);
            AppCompatSpinner spinnerduty = (AppCompatSpinner) sectionview.findViewById(R.id.dutyspinner);
            ImageView imageview = (ImageView) sectionview.findViewById(R.id.imageclose);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dutystatus);
            spinnerduty.setAdapter(arrayAdapter);
            number = editText.getText().toString();
            status = spinnerduty.getTag().toString();
            user_mobid = "users" + "/" + "leader" + "/" + passed_id + "/" + "workdetails" + "/" + date_id1;
            String details = user_mobid + num;
            String cardno = details + "/" + "cardno";
            String dutystatus = details + "/" + "dutystatus";
            DatabaseReference workercardno = mDatabase.getReference(cardno);
            DatabaseReference workstatus = mDatabase.getReference(dutystatus);
            num = num + 1;
            workercardno.setValue(number);
            workstatus.setValue(status);
            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeView(sectionview);
                }
            });
            layoutlist.addView(sectionview);
        }
      /*  submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });*/
       /* imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(sectionview);
            }
        });*/
        //layoutlist.addView(sectionview);
    }


    private void removeView(View view) {
        layoutlist.removeView(view);
    }


}