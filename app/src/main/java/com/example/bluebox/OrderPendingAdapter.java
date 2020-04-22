package com.example.bluebox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OrderPendingAdapter extends RecyclerView.Adapter<OrderPendingAdapter.OrderPendingViewHolder> {

    ArrayList<OrderLink> shipments;

    OrderPendingAdapter (ArrayList<OrderLink> temp) {
        shipments = temp;
    }
    String desc;
    int status;

    @NonNull
    @Override
    public OrderPendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pending, parent, false);
        OrderPendingViewHolder orderHolder = new OrderPendingViewHolder(view);
        return orderHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderPendingViewHolder holder, int position) {
        OrderLink shipment = shipments.get(position);
        Log.d("IF Check ", "------ 1 ------");
//        if (shipment.size() > 0) {

            Log.d("IF Check ", "------ 2 ------");
            if (shipment.link_description.size() > 0){
                ArrayList<String> arrayDesc = shipment.getLink_description();
                Log.d("Order", "----- " + arrayDesc.get(0));
                desc = shipment.getLink_description().get(0);
                for (int i = 1; i < arrayDesc.size(); i++) {
                    desc = desc.concat("\n" + shipment.getLink_description().get(i));
                }
                holder.txt_details.setText(desc);
            }
        if (shipment.getLink_img_url_1() != null && !(shipment.getLink_img_url_1().equals("")))
            Picasso.get().load(shipment.getLink_img_url_1()).fit().centerInside().into(holder.cv_imgPending[0]);
        if (shipment.link_img_url_2 != null && !(shipment.link_img_url_2.equals("")) )
            Picasso.get().load(shipment.link_img_url_2).fit().centerInside().into(holder.cv_imgPending[1]);
        if (shipment.link_img_url_3 != null && !(shipment.link_img_url_3.equals("")))
            Picasso.get().load(shipment.link_img_url_3).fit().centerInside().into(holder.cv_imgPending[2]);

//        Picasso.get().load(shipments.get(position).getLink_img_url_1()).fit().centerInside().into(holder.cv_imgPending[0]);
//        Picasso.get().load(shipments.get(position).link_img_url_2).fit().centerInside().into(holder.cv_imgPending[1]);
//        Picasso.get().load(shipments.get(position).link_img_url_3).fit().centerInside().into(holder.cv_imgPending[2]);
        Log.d("Picture --- ", ""+shipments.get(position).link_img_url_1);
            holder.txt_shipId.setText(shipment.link_shipping_id);
            status = Integer.parseInt(shipment.getLink_status());
            if (status > 2) {
                holder.txt_status.setText("Verified");
                holder.txt_status.setBackgroundColor(Color.parseColor("#00FF99"));
            } else {
                holder.txt_status.setText("Unverified");
                holder.txt_status.setBackgroundColor(Color.parseColor("#e8e26b"));
            }
//        }


    }

    @Override
    public int getItemCount() {
        return shipments.size();
    }

    public static class OrderPendingViewHolder extends RecyclerView.ViewHolder{

        ImageView [] cv_imgPending = new ImageView[3];
        TextView txt_shipId,txt_status, txt_details;
        public OrderPendingViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_imgPending[0] = itemView.findViewById(R.id.cvPendImg1);
            cv_imgPending[1] = itemView.findViewById(R.id.cvPendImg2);
            cv_imgPending[2] = itemView.findViewById(R.id.cvPendImg3);
            txt_status = itemView.findViewById(R.id.cvTxtPayStatus);
            txt_shipId = itemView.findViewById(R.id.cvPendShipNo);
            txt_details = itemView.findViewById(R.id.cvPendDetail);
        }
    }
}
