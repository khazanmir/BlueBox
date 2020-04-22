package com.example.bluebox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {

    ArrayList<PostLink> arrayLink;

    public LinkAdapter(ArrayList<PostLink> list) {
        arrayLink = list;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_link, parent, false);
        LinkViewHolder holder = new LinkViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        PostLink currLink = arrayLink.get(position);
        holder.cv_txtLink.setText(currLink.getLink());

    }

    @Override
    public int getItemCount() {
        return arrayLink.size();
    }

    public static class LinkViewHolder extends RecyclerView.ViewHolder {

        public TextView cv_txtLink, cv_count;

        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);

            cv_txtLink = itemView.findViewById(R.id.cvTxtLink);
        }

    }
}
