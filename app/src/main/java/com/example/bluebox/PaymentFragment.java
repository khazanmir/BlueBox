package com.example.bluebox;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    Button btn_payment;
    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

//        Toolbar toolBarReg = getActivity().findViewById(R.id.toolBar);
//        toolBarReg.setTitle("Payment");

        btn_payment = view.findViewById(R.id.btnPayment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Awaiting payment confirmation. Kindly make payment", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
