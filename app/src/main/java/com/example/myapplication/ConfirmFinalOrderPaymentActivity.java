package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmFinalOrderPaymentActivity extends AppCompatActivity {

    private String totalPrice="";
    private Button lipanampesa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order_payment);


        totalPrice=getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total price=Ksh"+totalPrice, Toast.LENGTH_SHORT).show();

        lipanampesa=findViewById(R.id.lipanampesa);
        lipanampesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmFinalOrderPaymentActivity.this, "still in development", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
