package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Users;
import com.example.myapplication.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    //initialisation

    TextView joinnow, admlink,notadmlink,forgetpassword;
    EditText studentnolog,passwordlog;
    Button login;
    private ProgressDialog loadingBar;
    private String parentDbName="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Paper.init(this);
         joinnow=findViewById(R.id.textjoinnow);
         admlink=findViewById(R.id.textadmin);
         notadmlink=findViewById(R.id.textnotadmin);
         login=findViewById(R.id.buttonlogin);
         studentnolog=findViewById(R.id.studentNoedittextlog);
         passwordlog=findViewById(R.id.passwordedittextlog);
         loadingBar=new ProgressDialog(Login.this);
         forgetpassword=findViewById(R.id.textforgotpassword);

         joinnow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent Register= new Intent(Login.this, Register.class);
                 startActivity(Register);
             }
         });

         forgetpassword.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(Login.this, "the password is being proceed", Toast.LENGTH_SHORT).show();
             }
         });

         // remember me codeline using Paper library

         String StudentNokey= Paper.book().read(Prevalent.StudentNokey);
        String PasswordKey= Paper.book().read(Prevalent.Passwordkey);

         if(StudentNokey != "" && PasswordKey!="")
         {
             if(!TextUtils.isEmpty(StudentNokey) && !TextUtils.isEmpty(PasswordKey))
             {
                 AllowAccess(StudentNokey, PasswordKey);

                 loadingBar.setTitle("Already logged in");
                 loadingBar.setMessage("Please Wait");
                 loadingBar.setCanceledOnTouchOutside(false);
                 loadingBar.show();
             }
         }




         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v)
             {

             loginUser();

             }
         });

         admlink.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v)
             {
             login.setText("LOGIN ADMIN");
             studentnolog.setHint("Enter Identification");
             admlink.setVisibility(View.INVISIBLE);
             notadmlink.setVisibility(View.VISIBLE);
                 parentDbName="Admins";
             }
         });

         notadmlink.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v)
             {
                 login.setText("LOGIN");
                 studentnolog.setHint("StudentNo");
                 admlink.setVisibility(View.VISIBLE);
                 notadmlink.setVisibility(View.INVISIBLE);
                 parentDbName="Users";
             }
         });

    }

    private void AllowAccess(final String studentno, final String password)
    {

     //database access

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(parentDbName).child(studentno).exists())
                {
                    Users usersData=dataSnapshot.child(parentDbName).child(studentno).getValue(Users.class);

                    if(usersData.getStudentNo().equals(studentno))
                    {
                        if(usersData.getPassword().equals(password))
                        {
                            if(parentDbName.equals("Admins"))
                            {
                                Toast.makeText(Login.this, "welcome to AdminPanel", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent= new Intent(Login.this, Adminpanel.class);
                                startActivity(intent);
                                finish();
                            }
                            else if(parentDbName.equals("Users"))
                            {
                                Toast.makeText(Login.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent= new Intent(Login.this, Home.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(Login.this, "Account "+ studentno +"doesnot exist ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loginUser()
    {
        String studentno=studentnolog.getText().toString();
        String password=passwordlog.getText().toString();

        if (TextUtils.isEmpty(studentno))
        {
            Toast.makeText(this, "Please enter StudentNo", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account ");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(studentno, password);
        }
    }

    private void AllowAccessToAccount(final String studentno, final String password)
    {


            //Paper.book().write(Prevalent.StudentNokey, studentno);
           // Paper.book().write(Prevalent.Passwordkey, password);

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(parentDbName).child(studentno).exists())
                {
                    Users usersData=dataSnapshot.child(parentDbName).child(studentno).getValue(Users.class);

                    if(usersData.getStudentNo().equals(studentno))
                    {
                        if(usersData.getPassword().equals(password))
                        {
                           if(parentDbName.equals("Admins"))
                           {
                               Toast.makeText(Login.this, "welcome to AdminPanel", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent= new Intent(Login.this, Adminpanel.class);
                               startActivity(intent);
                               finish();
                           }
                           else if(parentDbName.equals("Users"))
                            {
                                Toast.makeText(Login.this, "Logged Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();


                                Intent intent= new Intent(Login.this, Home.class);
                                Prevalent.currentOnlineUser=usersData;// retriving data for currentusers studentno and name
                                startActivity(intent);
                                finish();
                             }
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(Login.this, "Account "+ studentno +"doesnot exist ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
