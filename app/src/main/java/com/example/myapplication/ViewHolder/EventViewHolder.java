package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.interfaceclick.ItemClickListner;

public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtdate,txttime,txtlocation,txtdescription;
    public ImageView imageView;
    public ItemClickListner listner;

    public EventViewHolder(@NonNull View itemView)
    {
        super(itemView);

        txtdate=itemView.findViewById(R.id.dateeventlayout);
        txttime=itemView.findViewById(R.id.timeeventlayout);
        txtlocation=itemView.findViewById(R.id.locationeventlayout);
        txtdescription=itemView.findViewById(R.id.Descriptioneventlayout);
        imageView=itemView.findViewById(R.id.imageeventlayout);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner=listner;
    }

    @Override
    public void onClick(View v)


    {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
