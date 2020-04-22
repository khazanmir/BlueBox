package com.example.bluebox;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterApprove extends RecyclerView.Adapter<AdapterApprove.ApproveViewHolder> {

    ArrayList<OrderLink> approveList;
    String approveDesc;
    private OnItemClickListner mlistner;

    public interface OnItemClickListner{
        void onAcceptClick(int pos);
        void onRemoveClick(int pos);
    }

    public void onItemClickListner(OnItemClickListner listner){
        mlistner = listner;
    }

    public AdapterApprove(ArrayList<OrderLink> list) {
        approveList = list;
    }

    @NonNull
    @Override
    public ApproveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_approve, parent, false);
        ApproveViewHolder holder = new ApproveViewHolder(view, mlistner);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ApproveViewHolder holder, int position) {

        OrderLink shipment = approveList.get(position);
        holder.txt_approveShipNo.setText(shipment.getLink_shipping_id());
        if (shipment.link_description.size() > 0){
            ArrayList<String> arrayDesc = shipment.getLink_description();
            Log.d("Order", "----- " + arrayDesc.get(0));
            approveDesc = shipment.getLink_description().get(0);
            if (shipment.getLink_img_url_1() != null && !(shipment.getLink_img_url_1().equals("")))
                Picasso.get().load(shipment.getLink_img_url_1()).fit().centerInside().into(holder.approveImg[0]);
            if (shipment.link_img_url_2 != null && !(shipment.link_img_url_2.equals("")) )
                Picasso.get().load(shipment.link_img_url_2).fit().centerInside().into(holder.approveImg[1]);
            if (shipment.link_img_url_3 != null && !(shipment.link_img_url_3.equals("")))
                Picasso.get().load(shipment.link_img_url_3).fit().centerInside().into(holder.approveImg[2]);
            Log.d("Picture --- ", ""+ shipment.getLink_img_url_1() + "\n" + shipment.link_img_url_2 + "\n" + shipment.link_img_url_3);
            for (int i = 1; i < arrayDesc.size(); i++) {
                approveDesc = approveDesc.concat("\n" + shipment.getLink_description().get(i));
            }
            holder.txt_approveDetail.setText(approveDesc);
            holder.txt_appTotal.setText(shipment.link_price);
        }

    }

    @Override
    public int getItemCount() {
        return approveList.size();
    }

    public static class ApproveViewHolder extends RecyclerView.ViewHolder{
        ImageView [] approveImg = new ImageView[3];
        TextView txt_approveShipNo, txt_approveDetail, txt_appTotal, txt_approve, txt_remove;
        public ApproveViewHolder(@NonNull View itemView, final OnItemClickListner listner) {
            super(itemView);
            approveImg[0] = itemView.findViewById(R.id.cvAppImg1);
            approveImg[1] = itemView.findViewById(R.id.cvAppImg2);
            approveImg[2] = itemView.findViewById(R.id.cvAppImg3);
            txt_approveShipNo = itemView.findViewById(R.id.cvAppShipNo);
            txt_approveDetail = itemView.findViewById(R.id.cvAppDetail);
            txt_appTotal = itemView.findViewById(R.id.cvAppTotal);
            txt_approve = itemView.findViewById(R.id.cvTxtApprove);
            txt_remove = itemView.findViewById(R.id.cvTxtReject);

            txt_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listner.onAcceptClick(position);
                        }
                    }
                }
            });

            txt_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listner.onRemoveClick(position);
                        }
                    }
                }
            });
        }
    }
}
