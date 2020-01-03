package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Dateandtimepicker.Datepicker;
import com.example.myapplication.Dateandtimepicker.Timepicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Adminpanel extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
     String dateevent,timeevent,location,description,name,saveCurrentDate,saveCurrentTime, eventRandomkey, downloadImageUrl;
    ImageView inputeventimage;
    EditText inputeventlocation,inputeventdescription,inputeventname;
    Button addneweventbutton,datepicker,timepicker;
    TextView inputeventdate,inputeventtime;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private StorageReference EventImageRef;
    private DatabaseReference EventsRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        EventImageRef= FirebaseStorage.getInstance().getReference().child("Event Images");
        EventsRef= FirebaseDatabase.getInstance().getReference().child("Events");


        inputeventimage=findViewById(R.id.adminpaneladdevent);
        inputeventdate=findViewById(R.id.eventdate);
        inputeventtime=findViewById(R.id.eventtime);
        inputeventdescription=findViewById(R.id.eventDescription);
        inputeventlocation=findViewById(R.id.eventlocation);
        inputeventname=findViewById(R.id.eventname);
        addneweventbutton=findViewById(R.id.btnaddevent);
        loadingBar=new ProgressDialog(Adminpanel.this);
        datepicker=findViewById(R.id.datepicker);
        timepicker=findViewById(R.id.timepicker);





        inputeventimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                OpenGallery();
            }
        });

        addneweventbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ValidateEventInformation();
            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFragment datepicker= new Datepicker();
                datepicker.show(getSupportFragmentManager(),"date picker");

            }
        });

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker=new Timepicker();
                timepicker.show(getSupportFragmentManager(),"time picker");
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString= DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        inputeventdate.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
     inputeventtime.setText(hourOfDay+":"+minute+"hrs");
    }

    private void OpenGallery()
    {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK  &&  data!=null)
        {
         ImageUri=data.getData();
         inputeventimage.setImageURI(ImageUri);
        }
    }

     private void ValidateEventInformation()
     {
         dateevent=inputeventdate.getText().toString();
         timeevent=inputeventtime.getText().toString();
         location=inputeventlocation.getText().toString();
         description=inputeventdescription.getText().toString();
         name=inputeventname.getText().toString();


         if(ImageUri==null)
         {
             Toast.makeText(this, "Please Add Event Image", Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(dateevent))
         {
             Toast.makeText(this, "Please Add Event Date", Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(timeevent))
         {
             Toast.makeText(this, "Please Add Event Time", Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(location))
         {
             Toast.makeText(this, "Please Add Event Location", Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(name))
         {
             Toast.makeText(this, "Please Add Event Name", Toast.LENGTH_SHORT).show();
         }
         else if(TextUtils.isEmpty(description))
         {
             Toast.makeText(this, "Please Add Event Description", Toast.LENGTH_SHORT).show();
         }
         else
         {
             StoreEventInformation();
         }
     }

    private void StoreEventInformation()
    {
        loadingBar.setTitle("Adding New Event  ");
        loadingBar.setMessage("Please Wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate=currentDate.format(calender.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calender.getTime());

         eventRandomkey=saveCurrentDate + saveCurrentTime;

       final StorageReference filePath= EventImageRef.child(ImageUri.getLastPathSegment() + eventRandomkey +".jpg");

        final UploadTask uploadTask=filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
            String message= e.toString();
                Toast.makeText(Adminpanel.this, "Error:"+ message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(Adminpanel.this, "Image upload Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urltask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                       if(!task.isSuccessful())
                       {
                           throw task.getException();

                       }
                        downloadImageUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {

                            downloadImageUrl=task.getResult().toString();

                            Toast.makeText(Adminpanel.this, "Image Url Successful", Toast.LENGTH_SHORT).show();


                            saveEventInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void saveEventInfoToDatabase()
    {
        HashMap<String, Object> eventMap=new HashMap<>();
        eventMap.put("evid",eventRandomkey);
        eventMap.put("date",saveCurrentDate);
        eventMap.put("time",saveCurrentTime);
        eventMap.put("image",downloadImageUrl);
        eventMap.put("location",location);
        eventMap.put("dateevent",dateevent);
        eventMap.put("timeevent",timeevent);
        eventMap.put("description",description);
        eventMap.put("name",name);


        EventsRef.child(eventRandomkey).updateChildren(eventMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                          if(task.isSuccessful())
                          {


                              loadingBar.dismiss();
                              Toast.makeText(Adminpanel.this, "Event has been added ", Toast.LENGTH_SHORT).show();
                          }
                          else
                          {
                              loadingBar.dismiss();
                              String message=task.getException().toString();
                              Toast.makeText(Adminpanel.this, "Error"+message, Toast.LENGTH_SHORT).show();

                          }
                    }
                });
    }
}
