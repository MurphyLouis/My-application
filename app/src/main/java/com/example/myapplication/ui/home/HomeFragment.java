package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.EventsDetailsActivity;
import com.example.myapplication.Model.Events;
import com.example.myapplication.R;
import com.example.myapplication.ViewHolder.EventViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private DatabaseReference EventsRef;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=root.findViewById(R.id.recyclervieweventall);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        EventsRef= FirebaseDatabase.getInstance().getReference().child("Events");


        return root;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Events>()
                .setQuery(EventsRef, Events.class)
                .build();
        FirebaseRecyclerAdapter<Events, EventViewHolder> adapter
                =new FirebaseRecyclerAdapter<Events, EventViewHolder>(options)
       {
            @Override
            protected void onBindViewHolder(@NonNull final EventViewHolder eventViewHolder, int i, @NonNull final Events events)
            {
                eventViewHolder.txtdate.setText(events.getDateevent());
                eventViewHolder.txttime.setText(events.getTimeevent());
                eventViewHolder.txtlocation.setText(events.getLocation());
                eventViewHolder.txtdescription.setText(events.getDescription()+""+"Ksh");
                Picasso.get().load(events.getImage()).into(eventViewHolder.imageView);

                eventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(getActivity(), EventsDetailsActivity.class);
                        intent.putExtra("evid", events.getEvid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout,parent,false);
                EventViewHolder holder=new EventViewHolder(view);
                return holder;
            }
        };

       recyclerView.setAdapter(adapter);
       adapter.startListening();
    }



}