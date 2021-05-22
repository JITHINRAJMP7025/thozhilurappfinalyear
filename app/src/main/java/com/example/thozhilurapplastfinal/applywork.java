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
    String cardno1, name1, mobile1, chooseimage1, user_mobid;
    String passed_id=null;
    private StorageReference m1storageref;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference getRef1, getRef2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageuri;
    int num=1;

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

        m1storageref = FirebaseStorage.getInstance().getReference("users" + "/" + "leader" + "/" +passed_id+"/"+ "applywork");

       chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

      //  add();
       
       // uploadfile();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1 = name.getText().toString();
                cardno1 = cardno.getText().toString();
                mobile1 = mobile.getText().toString();
                if(mobile1.length()==10) {
                    //getRef1=FirebaseDatabase.getInstance().getReference("users" + "/" + "leader"+"/" +passed_id+ "/" + "applywork"+"/"+num);
                   // user_mobid = "users" + "/" + "leader";
                    String details ="users"+"/"+"leader"+"/"+ passed_id+"/" + "applywork" + "/" + cardno1;

                    String leadername = details + "/" + "name";
                    String leaderno = details + "/" + "cardno";
                    String mobileno = details + "/" + "mobileno";

                    DatabaseReference leadername1 = mDatabase.getReference(leadername);
                    DatabaseReference leaderno1 = mDatabase.getReference(leaderno);
                    DatabaseReference leadermobileno1 = mDatabase.getReference(mobileno);
                    DatabaseReference applycount = mDatabase.getReference("users" + "/" + "leader" + "/" +passed_id +"/"+ "applywork"+"/"+cardno1);
                    num=num+1;
                    leadername1.setValue(name1);
                    leaderno1.setValue(cardno1);
                    leadermobileno1.setValue(mobile1);
                    applycount.setValue(num);
                    applyverify.setText("Applied");
                    // finish();
                    uploadfile();
                }
                else{
                    Toast.makeText(applywork.this, "invalid mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





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
        getRef1=FirebaseDatabase.getInstance().getReference("users" + "/" + "leader" + "/" +passed_id+"/"+"applywork"+"/"+cardno1);
        //String details = user_mobid + "/" + "applywork" + "/" + num;
        //String image = details + "/" + "taxpic";
       // getRef2 = FirebaseDatabase.getInstance().getReference("users" + "/" + "leader"+"/"+"applywork"+"/"+num);
        m1storageref = FirebaseStorage.getInstance().getReference("users" + "/" + "leader"+"/" +passed_id+ "/" +  "applywork"+"/"+cardno1 );

        if ( imageuri != null) {

            StorageReference fileReference = m1storageref.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));

                   fileReference.putFile(imageuri)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           upload upload1 = new upload(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                           String uploadid = getRef1.push().getKey();
                            getRef1.child(uploadid).setValue(upload1);
                           Toast.makeText(applywork.this, "upload Successful", Toast.LENGTH_SHORT).show();

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
