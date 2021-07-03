package com.example.thozhilurapplastfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
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

import org.w3c.dom.Text;

public class applywork extends AppCompatActivity {
    Button apply, chooseimage;
    EditText cardno, name, mobile;
    TextView applyverify;
    String cardno1, name1, mobile1, chooseimage1, user_mobid,userid;
    String passed_id=null;
    private StorageReference m1storageref;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1, getRef2,getRef3;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageuri;
    int num=0;
    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applywork);
        apply = (Button) findViewById(R.id.applybutton);
        chooseimage = (Button) findViewById(R.id.picbutton);
        cardno = (EditText) findViewById(R.id.editcard);
        name = (EditText) findViewById(R.id.editname);
        mobile = (EditText) findViewById(R.id.editmobile);
        Bundle login = getIntent().getExtras();
        if (login != null) {
            passed_id = login.getString("userid_id");

        }


        getRef1 = FirebaseDatabase.getInstance().getReference("users" + "/" + "leader" + "/" + passed_id+"/"+"applywork");
      //  getRef2 = FirebaseDatabase.getInstance().getReference("users" + "/" + "leader"+ "/"  +"applycount");
        getRef3 = FirebaseDatabase.getInstance().getReference("users"+"/"+"leader"+"/"+ passed_id+"/" + "applywork"+"/"+"count");
      

        m1storageref = FirebaseStorage.getInstance().getReference("users" + "/" + "leader" + "/" +passed_id+"/"+ "applywork"+"/"+"photo");

       chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
       });

        // getcount();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name1 = name.getText().toString();
                cardno1 = cardno.getText().toString();
                mobile1 = mobile.getText().toString();
                userid ="users"+"/"+"leader"+"/"+ passed_id+"/" + "applywork" ;
                String details=userid+"/"+cardno1;

                if(mobile1.length()==10) {


                    String leadername = details + "/" + "name";
                    String leaderno = details + "/" + "cardno";
                    String mobileno = details + "/" + "mobileno";
                   // String count= details+"/"+"count";
                    DatabaseReference leadername1 = mDatabase.getReference(leadername);
                    DatabaseReference leaderno1 = mDatabase.getReference(leaderno);
                    DatabaseReference leadermobileno1 = mDatabase.getReference(mobileno);
                   // DatabaseReference leadercount1 = mDatabase.getReference("users"+"/"+"leader"+"/"+ passed_id+"/" + "applywork"+"/"+cardno1+"/"+"count");
                    //DatabaseReference applycount = mDatabase.getReference("users" + "/" + "leader" + "/" +passed_id +"/"+"count"+"/"+"applycount");

                   // num=num+1;
                    //cnt=cnt+1;
                    leadername1.setValue(name1);
                    leaderno1.setValue(cardno1);
                    leadermobileno1.setValue(mobile1);
                   // leadercount1.setValue(num);
                  //  applycount.setValue(num);
                    Toast.makeText(applywork.this, "applied successfully", Toast.LENGTH_SHORT).show();

                    // applyverify.setText("Applied")
                    // finish();
                    uploadfile();
                }
                else{
                    Toast.makeText(applywork.this, "invalid mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

  /*  private void getcount()
    {
        DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users" + "/" + "leader" + "/" +passed_id +"/"+"count");
        fb_to_read.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                num= (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/





    private void openFileChooser () {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageuri = data.getData();
            }
        }

        private String getFileExtension (Uri uri){
            ContentResolver cr = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cr.getType(uri));//cR
        }

    private void uploadfile() {
       getRef2=FirebaseDatabase.getInstance().getReference("users" + "/" + "leader" + "/" +passed_id+"/"+"applywork"+"/"+cardno1+"/"+"photo");

        m1storageref = FirebaseStorage.getInstance().getReference("users" + "/" + "leader"+"/" +passed_id+ "/" +  "applywork"+"/"+cardno1+"/"+"photo" );

        if ( imageuri != null) {

            StorageReference fileReference = m1storageref.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));

                   fileReference.putFile(imageuri)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          /* upload upload1 = new upload(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                           String uploadid = getRef1.push().getKey();
                            getRef1.child(uploadid).setValue(upload1);
                           Toast.makeText(applywork.this, "upload Successful", Toast.LENGTH_SHORT).show();*/
                           fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   upload up=new upload(uri.toString());
                                   String uploadId = getRef2.push().getKey();
                                   getRef2.child(uploadId).setValue(up);

                               }
                           });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           Toast.makeText(applywork.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


}
