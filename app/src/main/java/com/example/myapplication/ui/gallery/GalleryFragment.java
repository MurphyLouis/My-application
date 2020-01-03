package com.example.myapplication.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ConfirmFinalOrderPaymentActivity;
import com.example.myapplication.Model.Cart;
import com.example.myapplication.Prevalent.Prevalent;
import com.example.myapplication.R;
import com.example.myapplication.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextBtn;
    private TextView txtTotalAmount;

    private int overTotalPrice=0;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

          recyclerView=root.findViewById(R.id.cardrecyclerview);
          recyclerView.setHasFixedSize(true);
          layoutManager=new LinearLayoutManager(getContext());
          recyclerView.setLayoutManager(layoutManager);

        NextBtn=root.findViewById(R.id.btncardnext);
        txtTotalAmount=root.findViewById(R.id.txtcardtotalamount);

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                txtTotalAmount.setText("Total Price= "+"ksh"+""+String.valueOf(overTotalPrice));
                Intent intent=new Intent(getActivity(), ConfirmFinalOrderPaymentActivity.class);
                intent.putExtra("Total Price",String.valueOf(overTotalPrice));
                startActivity(intent);

            }
        });
        return root;
    }

    @Override
    public void onStart()

    {


        super.onStart();



       final DatabaseReference cardListRef= FirebaseDatabase.getInstance().getReference().child("Card List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cardListRef.child("User View")
                .child(Prevalent.currentOnlineUser.getStudentNo()).child("Events"),Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                =new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model)
            {
              holder.txteventname.setText(model.getEvname());
              holder.txteventpricedescription.setText(model.getEvpricedescription());
              holder.txteventlocation.setText(model.getEvlocation());
              holder.txteventdate.setText(model.getEvdate());
              holder.txteventtime.setText(model.getEvtime());



                int eventprice=((Integer.valueOf(model.getEvpricedescription())))*1;
                overTotalPrice=overTotalPrice+eventprice;



              holder.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v)
                  {
                      CharSequence options[]=new CharSequence[]
                              {
                                 "Remove from Card"
                              };
                      AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                      builder.setTitle("Option:");


                      builder.setItems(options, new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which)
                          {
                              if(which==0)
                              {
                                  cardListRef.child("User View")
                                          .child(Prevalent.currentOnlineUser.getStudentNo())
                                          .child("Events")
                                          .child(model.getEvid())
                                          .removeValue()
                                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                                              @Override
                                              public void onComplete(@NonNull Task<Void> task)
                                              {
                                                   if (task.isSuccessful())
                                                   {
                                                       Toast.makeText(getActivity(), "Event has been removed from the card", Toast.LENGTH_SHORT).show();

                                                   }
                                              }
                                          });
                              }
                          }
                      });

                      builder.show();

                  }
              });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_items_layout, parent, false);
                CartViewHolder holder= new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}