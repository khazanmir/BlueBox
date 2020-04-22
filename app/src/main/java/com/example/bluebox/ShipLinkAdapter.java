package com.example.bluebox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.*;

public class ShipLinkAdapter extends RecyclerView.Adapter<ShipLinkAdapter.ShipViewHolder> {
    ArrayList<PostShip> mlist;

    public ShipLinkAdapter(ArrayList<PostShip> list){
        mlist = list;
    }
    @NonNull
    @Override
    public ShipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_link_ship, parent, false);
        ShipViewHolder holder = new ShipViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShipViewHolder holder, int position) {
        PostShip currShip = mlist.get(position);
        holder.txt_shipLink.setText(currShip.getLink());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class ShipViewHolder extends  RecyclerView.ViewHolder{
        TextView txt_shipLink;
        public ShipViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_shipLink = itemView.findViewById(R.id.cvTxtShipLink);
        }
    }
}
