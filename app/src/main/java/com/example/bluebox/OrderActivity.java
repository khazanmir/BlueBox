package com.example.bluebox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toolbar;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    Toolbar toolBarOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        androidx.appcompat.widget.Toolbar toolBarOrder = findViewById(R.id.toolbarOrder);
        setSupportActionBar(toolBarOrder);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        String fragName = getIntent().getStringExtra("Btn");

        if (fragName.equals("pending")) {
            getSupportFragmentManager().beginTransaction().add(R.id.frameOrder, new OrderPendingFragment(), "pendingOrder").commit();
            toolBarOrder.setTitle("Pending Order");
        } else if (fragName.equals("payment")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameOrder, new PaymentFragment(), "payment").commit();
            toolBarOrder.setTitle("Payment");
        } else if (fragName.equals("track")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameOrder, new TrackFragment(), "track").commit();
            toolBarOrder.setTitle("Track Order");
        } else if (fragName.equals("approve")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameOrder, new ApproveFragment(), "approve").commit();
            toolBarOrder.setTitle("Approve Order");
        } else if (fragName.equals("contact")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameOrder, new ContactFragment(), "contact").commit();
            toolBarOrder.setTitle("Contact Us");
        } else if(fragName.equals("receipt")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameOrder, new ReceiptFragment(), "receipt").commit();
            toolBarOrder.setTitle("Customer's Shipment Official Receipt");
        }
    }
}
