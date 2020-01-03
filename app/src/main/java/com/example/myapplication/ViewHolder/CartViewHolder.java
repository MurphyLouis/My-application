package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.interfaceclick.ItemClickListner;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txteventlocation,txteventname,txteventtime,txteventdate,txteventpricedescription;
    private ItemClickListner itemClickListner;

    public CartViewHolder(  View itemView)
    {
        super(itemView);

        txteventname=itemView.findViewById(R.id.cardeventname);
        txteventpricedescription=itemView.findViewById(R.id.cardeventpricedescription);
        txteventlocation=itemView.findViewById(R.id.cardeventlocation);
        txteventdate=itemView.findViewById(R.id.cardeventdate);
        txteventtime=itemView.findViewById(R.id.cardeventtime);
    }

    @Override
    public void onClick(View v)
    {
       itemClickListner.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}
