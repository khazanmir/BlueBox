package com.example.bluebox;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarServiceFragment extends Fragment {


    public CarServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_service, container, false);

        Toolbar toolBarReg = getActivity().findViewById(R.id.toolbarOrder);
        toolBarReg.setTitle("Car Shipping");

        return view;
    }

}
