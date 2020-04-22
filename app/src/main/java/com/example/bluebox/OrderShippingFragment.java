package com.example.bluebox;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderShippingFragment extends Fragment {

    RecyclerView rcShipLink;
    ShipLinkAdapter shipLinkAdapter;
    RecyclerView.LayoutManager rcShipLayout;
    public OrderShippingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_shipping, container, false);
        rcShipLink = view.findViewById(R.id.recyclerShipLink);
        rcShipLink.setHasFixedSize(true);
        rcShipLayout = new LinearLayoutManager(getContext());
        ShipLinkAdapter shipLinkAdapter = new ShipLinkAdapter(Common.listOfShipLink);
        rcShipLink.setLayoutManager(rcShipLayout);
        rcShipLink.setAdapter(shipLinkAdapter);

        return view;
    }

}
