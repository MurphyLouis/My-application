package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    private Button join;
    private EditText studentnoReg,passwordReg,nameReg,phonenumberReg;
     private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        join=findViewById(R.id.buttonjoin);
        studentnoReg=findViewById(R.id.studentNoedittextReg);
        passwordReg=findViewById(R.id.passworedittextReg);
        nameReg=findViewById(R.id.nameedittextReg);
        phonenumberReg=findViewById(R.id.phonenumberedittextReg);
        loadingBar=new ProgressDialog(this);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               CreateAccount();
            }
        });
    }

    private void CreateAccount()
    {
        String studentno=studentnoReg.getText().toString();
        String password=passwordReg.getText().toString();
        String name=nameReg.getText().toString();
        String phone=phonenumberReg.getText().toString();

        if (TextUtils.isEmpty(studentno))
        {
            Toast.makeText(this, "Please enter StudentNo", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please enter Name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please enter PhoneNumber", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

             ValidatestudentNumber(studentno, password, name, phone);
        }

    }

    private void ValidatestudentNumber(final String studentno, final String password, final String name, final String phone)
    {
      final DatabaseReference RootRef;
      RootRef= FirebaseDatabase.getInstance().getReference();

      RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot)
          {
              if(!(dataSnapshot.child("Users").child(studentno).exists()))
              {
                  HashMap<String, Object> userdataMap=new HashMap<>();
                  userdataMap.put("studentNo", studentno);
                  userdataMap.put("password", password);
                  userdataMap.put("name", name);
                  userdataMap.put("phone", phone);


                  RootRef.child("Users").child(studentno).updateChildren(userdataMap)
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task)
                              {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Register.this, "Congratulations, welcome to Event app", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(Register.this,Login.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    loadingBar.dismiss();
                                    Toast.makeText(Register.this, "Network Error:try Again", Toast.LENGTH_SHORT).show();
                                }
                              }
                          });
              }
              else
              {
                  Toast.makeText(Register.this, "This"+ studentno +"already exists", Toast.LENGTH_SHORT).show();
                  loadingBar.dismiss();
                  Toast.makeText(Register.this, "Please Login", Toast.LENGTH_SHORT).show();

                  Intent intent = new Intent(Register.this,Login.class);
                  startActivity(intent);


              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError)
          {

          }
      });
    }
}
