package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class leadernotification extends AppCompatActivity {
    String passed_id,panchayat,status,cardno;
    LinearLayout layoutlist;
    DatabaseReference getRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadernotification);
        layoutlist = findViewById(R.id.layout_list);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("userid_id");
            panchayat = login.getString("panchayatname");
            
        }
        DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"Panchayat"+"/"+panchayat+"/"+"workstatus"+"/"+passed_id);
        fb_to_read.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> list=new ArrayList<String>();
                for (DataSnapshot dsp : snapshot.getChildren()){
                    list.add(String.valueOf(dsp.getKey()));
                }
                for(final String data:list) {
                    Toast.makeText(leadernotification.this, data, Toast.LENGTH_SHORT).show();
                    getRef1 = FirebaseDatabase.getInstance().getReference("users"+"/"+"Panchayat"+"/"+panchayat+"/"+"workstatus"+"/"+passed_id+"/"+data);
                    getRef1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                             status=snapshot.child("status").getValue(String.class);
                             cardno=data;
                            addView(status,cardno);
                        }

                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {

                        }
                    });
                }

                }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
    private void addView(String status,String cardno ) {


        final View sectionview = getLayoutInflater().inflate(R.layout.leadernotification, null, false);
        TextView textview = (TextView) sectionview.findViewById(R.id.sectiontext);

       // ImageView imageview = (ImageView) sectionview.findViewById(R.id.imageclose);
        textview.setText(cardno+"-"+"work"+" "+status);

        layoutlist.addView(sectionview);
    }
}