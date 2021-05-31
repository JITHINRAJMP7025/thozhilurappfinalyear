package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class datepage extends AppCompatActivity implements View.OnClickListener {
TextView name;
Button apply;
int num=0;
Button add,submit;
LinearLayout layoutlist;
String name1,date_id1,user_id,passed_id,pic;
String date1,user_mobid;
FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
DatabaseReference getRef1;
int i=0;
public String number,status;
ImageView myimage,camera;
List<String> dutystatus = new ArrayList<>();
private StorageReference mstorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepage);
       myimage=(ImageView)findViewById(R.id.myimage);
         camera=(ImageView)findViewById(R.id.camera);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("user_id");
            date_id1 = login.getString("date_id");

            Toast.makeText(datepage.this, passed_id, Toast.LENGTH_SHORT).show();
        }


        mstorage= FirebaseStorage.getInstance().getReference("users" + "/" + "leader" + "/" +passed_id+"/"+ "workdetails"+"/"+date_id1+"/"+"photo");

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



            final View sectionview = getLayoutInflater().inflate(R.layout.row_add_section, null, false);
            EditText editText = (EditText) sectionview.findViewById(R.id.sectiontext);

            AppCompatSpinner spinnerduty = (AppCompatSpinner) sectionview.findViewById(R.id.dutyspinner);
            ImageView imageview = (ImageView) sectionview.findViewById(R.id.imageclose);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dutystatus);
            spinnerduty.setAdapter(arrayAdapter);


       // user_mobid = "users" + "/" + "leader" + "/" + passed_id + "/" + "workdetails" + "/" + date_id1;
           getcount();
            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // removeView(sectionview);
                   // savedata
                    getRef1=FirebaseDatabase.getInstance().getReference( "users" + "/" + "leader" + "/" +passed_id);
                    user_mobid="users" + "/" + "leader" + "/" +passed_id+"/"+"workdetails"+"/"+date_id1;
                    number = editText.getText().toString();
                    status= spinnerduty.getSelectedItem().toString();

                    String details = user_mobid +"/"+ number;
                    String cardno = details + "/" + "cardno";
                    String dutystatus = details + "/" + "dutystatus";
                   // Toast.makeText(datepage.this, number, Toast.LENGTH_SHORT).show();
                    DatabaseReference workercardno = mDatabase.getReference(cardno);
                    DatabaseReference workstatus = mDatabase.getReference(dutystatus);
                    DatabaseReference applycount = mDatabase.getReference("users" + "/" + "leader" + "/" +passed_id +"/"+ "workdetails"+"/"+date_id1+"/"+"applycount");
                    num=num+1;
                    workercardno.setValue(number);
                    workstatus.setValue(status);
                    applycount.setValue(num);
                }
            });
            layoutlist.addView(sectionview);


     /*  imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(sectionview);
            }
        });*/
        //layoutlist.addView(sectionview);
    }
  //  private void removeView(View view) {
       // layoutlist.removeView(view);
  private void getcount()
  {
      DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users" + "/" + "leader" + "/" +passed_id+"/"+ "workdetails"+"/"+date_id1 );
      fb_to_read.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {

              num= (int) snapshot.getChildrenCount();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

  }
    //}


  public void uploadimage(View v){
    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(intent,101);
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 101){
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail=(Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG,90,bytes);
        byte bb[]=bytes.toByteArray();
        myimage.setImageBitmap(thumbnail);
        uploadtofirebase(bb);

    }

    private void uploadtofirebase(byte[] bb) {
       // mstorage= FirebaseStorage.getInstance().getReference("users" + "/" + "leader" + "/" +passed_id+"/"+ "workdetails"+"/"+date_id1);
       // StorageReference sr=mstorage.child("myimages/a.jpg");
        mstorage.putBytes(bb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(datepage.this, "Succesfully uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(datepage.this, "failed to upload", Toast.LENGTH_SHORT).show();
            }
        });
    }


}