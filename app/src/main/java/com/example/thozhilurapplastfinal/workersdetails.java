package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.provider.FontsContractCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class workersdetails extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageuri;
    Button refresh;
    TextView chooseimage;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef2;
    ImageView imageView;
    //   EditText mEditTextFilename;
    LinearLayout layoutlist;
    String passed_id,cardno,date;
    private StorageReference mstorageref;

    //private DatabaseReference mdatabaseref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workersdetails);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview);

        bottomNavigationView.setSelectedItemId(R.id.workdetails);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("Mobile_id");
            cardno=login.getString("cardno");

          //  Toast.makeText(workersdetails.this, cardno, Toast.LENGTH_SHORT).show();

        }
        layoutlist = findViewById(R.id.layout_list);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Profileworkers:
                        Intent intent1 = new Intent(workersdetails.this, userprofile.class);
                        intent1.putExtra("Mobile_id", passed_id);
                        intent1.putExtra("cardno", cardno);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return;
                    case R.id.workdetails:
                        return;
                    case R.id.Applyworker:
                        Intent intent = new Intent(workersdetails.this, applyworker.class);
                        intent.putExtra("Mobile_id", passed_id);
                        intent.putExtra("cardno", cardno);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return;
                }
            }
        });
        /*refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"leader");

            public void onClick(View v) {
                addView();
            }
        });*/

        DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"leader");
        fb_to_read.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                List<String> list=new ArrayList<String>();
                for (DataSnapshot dsp : snapshot.getChildren()){
                    list.add(String.valueOf(dsp.getKey()));
                }
                for(final String data:list) {

                    DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"leader"+"/"+data+"/"+"workdetails");
                    fb_to_read.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> list=new ArrayList<String>();
                            for (DataSnapshot dsp : snapshot.getChildren()){
                                list.add(String.valueOf(dsp.getKey()));
                            }
                            for(final String data1:list) {

                                DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"leader"+"/"+data+"/"+"workdetails"+"/"+data1);
                                fb_to_read.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        List<String> list=new ArrayList<String>();
                                        for (DataSnapshot dsp : snapshot.getChildren()){
                                            list.add(String.valueOf(dsp.getKey()));
                                        }
                                        for(final String data2:list) {
                                            DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"leader"+"/"+data+"/"+"workdetails"+"/"+data1+"/"+data2);
                                            fb_to_read.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    List<String> list=new ArrayList<String>();
                                                    for (DataSnapshot dsp : snapshot.getChildren()){
                                                        list.add(String.valueOf(dsp.getKey()));
                                                    }
                                                    for(final String data3:list) {
                                                        DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users"+"/"+"leader"+"/"+data+"/"+"workdetails"+"/"+data1+"/"+data2+"/"+data3);
                                                        fb_to_read.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                List<String> list=new ArrayList<String>();
                                                                for (DataSnapshot dsp : snapshot.getChildren()){
                                                                    list.add(String.valueOf(dsp.getKey()));
                                                                }
                                                                for(final String data4:list) {
                                                                    {
                                                                         date=data1+"/"+data2+"/"+data3;
                                                                       // Toast.makeText(workersdetails.this, date, Toast.LENGTH_SHORT).show();

                                                                        if(data4.equals(cardno))
                                                                        {

                                                                           // Toast.makeText(workersdetails.this, date, Toast.LENGTH_SHORT).show();
                                                                            addView(date);
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull  DatabaseError error) {

                                                            }
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                        }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addView(String date) {


        final View sectionview = getLayoutInflater().inflate(R.layout.workadd, null, false);
        TextView textview = (TextView) sectionview.findViewById(R.id.sectiontext);

       // ImageView imageview = (ImageView) sectionview.findViewById(R.id.imageclose);
         textview.setText(date);
       // Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        layoutlist.addView(sectionview);
    }
}