package com.example.thozhilurapplastfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editpassword extends AppCompatActivity {
    String user,password,confirmpassword;
    EditText password1,password2;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpassword);
        Bundle forgetPassword=getIntent().getExtras();
        if(forgetPassword!=null){
            user=forgetPassword.getString("user_id");
            //  Toast.makeText(MainActivity.this,user, Toast.LENGTH_SHORT).show();
        }
        password1=findViewById(R.id.passwordEditText);
        password2=findViewById(R.id.confirmpasswordEditText);
        save=findViewById(R.id.saveButton);
        // DatabaseReference fb_to_read = FirebaseDatabase.getInstance().getReference("users/"+user+"/userDetails");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password=password1.getText().toString();
                confirmpassword=password2.getText().toString();

                if(password.equals(confirmpassword)) {
                    FirebaseDatabase regDatabase = FirebaseDatabase.getInstance();
                    String path = "users"+"/"+"workers"+"/" + user + "profile"+"/"+password1;
                    DatabaseReference passwordReference = regDatabase.getReference(path );
                    passwordReference.setValue(password);
                    Intent toLoginPage=new Intent(editpassword.this,userlogin.class);
                    startActivity(toLoginPage);
                    finish();
                }
                else
                {
                    Toast.makeText(editpassword.this, "NOT EQUAL", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

