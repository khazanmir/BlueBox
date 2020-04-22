package com.example.bluebox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterReceipt extends RecyclerView.Adapter<AdapterReceipt.ReceiptVH> {

    ReceiptClick receiptClick;
    ArrayList<OrderLink> receiptList;

    public interface ReceiptClick{
        void onReceiptClick(int pos);
    }

    public void setReceiptClick(ReceiptClick receiptClick1) {
        this.receiptClick = receiptClick1;
    }

    public AdapterReceipt(ArrayList<OrderLink> temp){
        receiptList = temp;
    }
    @NonNull
    @Override
    public ReceiptVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_receipt, parent, false);
        ReceiptVH holder = new ReceiptVH(view, receiptClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptVH holder, int position) {
        OrderLink orderLink = receiptList.get(position);
        holder.txt_receipt.setText(orderLink.getLink_url());
    }

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    public static class ReceiptVH extends RecyclerView.ViewHolder  {
        TextView txt_receipt;
        public ReceiptVH(@NonNull View itemView, final ReceiptClick receiptClick) {
            super(itemView);
            txt_receipt = itemView.findViewById(R.id.txtReceipt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (receiptClick != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            receiptClick.onReceiptClick(position);
                        }
                    }
                }
            });
        }
    }

}
