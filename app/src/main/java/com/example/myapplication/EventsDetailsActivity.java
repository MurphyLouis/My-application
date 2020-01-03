package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Events;
import com.example.myapplication.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class EventsDetailsActivity extends AppCompatActivity {

    private ImageView eventdetailsimage;
    private TextView eventdate, eventlocation, eventtime, eventpricedescription,eventname;
    private Button btnbooknow;
    private String eventid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);

        eventid=getIntent().getStringExtra("evid");

        eventname=findViewById(R.id.event_name_details);
        eventdetailsimage=findViewById(R.id.event_image_details);
        eventdate=findViewById(R.id.event_date_details);
        eventtime=findViewById(R.id.event_time_details);
        eventlocation=findViewById(R.id.event_location_details);
        eventpricedescription=findViewById(R.id.event_pricedescription_details);
        btnbooknow=findViewById(R.id.book_now);


        getEventDetails(eventid);


        btnbooknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                 addingToCardList();



            }
        });
    }


    private void addingToCardList()
    {
        String saveCurrentDate, saveCurrentTime;

        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate=currentDate.format(calender.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calender.getTime());

        final DatabaseReference cardListRef=FirebaseDatabase.getInstance().getReference().child("Card List");



        final HashMap<String,Object> cartMap=new HashMap<>();


        cartMap.put("evid", eventid);
        cartMap.put("evname",eventname.getText().toString());
        cartMap.put("evdate", eventdate.getText().toString());
        cartMap.put("evtime", eventtime.getText().toString());
        cartMap.put("evlocation", eventlocation.getText().toString());
        cartMap.put("evpricedescription", eventpricedescription.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);


        cardListRef.child("User View").child(Prevalent.currentOnlineUser.getStudentNo())
                .child("Events").child(eventid)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            cardListRef.child("Admin View").child(Prevalent.currentOnlineUser.getStudentNo())
                                    .child("Events").child(eventid)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(EventsDetailsActivity.this, "Booked Successfull open card to proceed to final payment", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    private void getEventDetails(String eventid)
    {
        DatabaseReference eventsref= FirebaseDatabase.getInstance().getReference().child("Events");

        eventsref.child(eventid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Events events= dataSnapshot.getValue(Events.class);

                   eventname.setText(events.getName());
                   eventdate.setText(events.getDateevent());
                   eventtime.setText(events.getTimeevent());
                   eventlocation.setText(events.getLocation());
                   eventpricedescription.setText(events.getDescription());
                    Picasso.get().load(events.getImage()).into(eventdetailsimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
