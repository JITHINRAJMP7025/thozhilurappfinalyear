package com.example.thozhilurapplastfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class sectionpage extends AppCompatActivity {
  //  LinearLayout layoutlist;
 // public class sectionpage extends AppCompatActivity implements View.OnClickListener {
      //  LinearLayout layoutlist;
    EditText date;
    Button buttonadd;
    int i = 1;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1;
    //List<String> sectionlist = new ArrayList<>();
    String date1,user_mobid,passed_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectionpage);
        //    layoutlist = (LinearLayout) findViewById(R.id.sectionlist);
        buttonadd = (Button) findViewById(R.id.buttonadd);
        date=(EditText)findViewById(R.id.editdate);
        //  buttonadd.setOnClickListener(this);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("userid_id");

        }
        getRef1 = FirebaseDatabase.getInstance().getReference("users" + "/" + "leader" + "/" + passed_id+"/"+ "workdetails");

        date1 = date.getText().toString();
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date1 = date.getText().toString();
                user_mobid = "users" + "/" + "leader"+ "/" + passed_id+"/"+ "workdetails";
                String details = user_mobid ;
                String dateapply = details + "/" + date1;
                DatabaseReference leadername = mDatabase.getReference(dateapply);
                leadername.setValue(date1);
                Intent intent=new Intent(sectionpage.this,datepage.class);
                Bundle bundle=new Bundle();
                bundle.putString("date_id", date1);
                bundle.putString("user_id",user_mobid);
                intent.putExtras(bundle);
                // add();
                //  Intent intent=new Intent(getApplicationContext(),)
                startActivity(intent);
            }
        });
/*    @Override
    public void onClick(View v) {
        addView();
    }


    private void addView() {
        final View sectionview = getLayoutInflater().inflate(R.layout.row_add_section, null, false);
        EditText editText = (EditText) sectionview.findViewById(R.id.sectiontext);
        ImageView imageClose = (ImageView) sectionview.findViewById(R.id.imageclose);
        Button button=(Button)sectionview.findViewById(R.id.applybutton);

        date1 = editText.getText().toString();
        button.setText(date1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date1 = editText.getText().toString();
                user_mobid = "users" + "/" + "leader"+ "/" + "workdetails";
                String details = user_mobid ;
                String dateapply = details + "/" + date1;
                DatabaseReference leadername = mDatabase.getReference(dateapply);
                leadername.setValue(date1);
                Intent intent=new Intent(sectionpage.this,datepage.class);
                intent.putExtra("date_id", date1);
               // add();
                //  Intent intent=new Intent(getApplicationContext(),)
                startActivity(intent);
            }
        });
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(sectionview);
            }
        });
        layoutlist.addView(sectionview);
    }


    private void removeView(View view) {
        layoutlist.removeView(view);
    }*/

    }
}