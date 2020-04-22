package com.example.bluebox;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView [] textViews = new TextView[7];
    BlueBoxAPI profileAPI;
    Button btn_signout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolBarReg = getActivity().findViewById(R.id.toolBar);
        toolBarReg.setTitle("Profile");
        btn_signout = view.findViewById(R.id.btnSignout);
        textViews[0] = view.findViewById(R.id.txtProfName);
        textViews[1] = view.findViewById(R.id.txtProfEmail);
        textViews[2] = view.findViewById(R.id.txtProfDob);
        textViews[3] = view.findViewById(R.id.txtProfAddress);
        textViews[4] = view.findViewById(R.id.txtProfPhone);
        textViews[5] = view.findViewById(R.id.txtProfID);
        textViews[6] = view.findViewById(R.id.txtProfSex);

        textViews[0].setText(Common.name);
        textViews[1].setText(Common.email);
        textViews[2].setText(Common.dob);
        textViews[3].setText(Common.address);
        textViews[4].setText(Common.phone);
        textViews[5].setText(Common.idNumber);
        textViews[6].setText(Common.sex);

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.token = null;
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

}
