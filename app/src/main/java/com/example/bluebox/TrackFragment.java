package com.example.bluebox;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrackFragment extends Fragment {

    EditText edt_ship_no;
    Button btn_ship_track;
    ImageView [] imageViews = new ImageView[9];
    TextView [] textViews = new TextView[5];
    BlueBoxAPI trackAPI;
    String status3 = "Arrived Distribution Centre";

    public TrackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        edt_ship_no = view.findViewById(R.id.edtShipNo);
        btn_ship_track = view.findViewById(R.id.btnShipTrack);

        imageViews[0] = view.findViewById(R.id.imgTrack1);
        imageViews[1] = view.findViewById(R.id.imgArrow1);
        imageViews[2] = view.findViewById(R.id.imgTrack2);
        imageViews[3] = view.findViewById(R.id.imgArrow2);
        imageViews[4] = view.findViewById(R.id.imgTrack3);
        imageViews[5] = view.findViewById(R.id.imgArrow3);
        imageViews[6] = view.findViewById(R.id.imgTrack4);
        imageViews[7] = view.findViewById(R.id.imgArrow4);
        imageViews[8] = view.findViewById(R.id.imgTrack5);
        textViews[0] = view.findViewById(R.id.txtStatus1);
        textViews[1] = view.findViewById(R.id.txtStatus2);
        textViews[2] = view.findViewById(R.id.txtStatus3);
        textViews[3] = view.findViewById(R.id.txtStatus4);
        textViews[4] = view.findViewById(R.id.txtStatus5);

//        textViews[2].setText(status3);

        for (int i = 0; i < 9; i++){
            imageViews[i].setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < 5; i++) {
            textViews[i].setVisibility(View.INVISIBLE);
        }

        btn_ship_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_ship_no.getText().toString().length() == 0){
                    Toast.makeText(getActivity(), "Enter Shipment Number", Toast.LENGTH_SHORT).show();
                } else {
                    getTrackStatus(edt_ship_no.getText().toString());
                }
            }
        });

        return view;
    }

    public void getTrackStatus(String trackNo) {
        Retrofit retrofit = new Retrofit.Builder().
                            baseUrl("https://blueb0x.herokuapp.com/api/get/").
                            addConverterFactory(GsonConverterFactory.create()).
                            build();
        trackAPI = retrofit.create(BlueBoxAPI.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("shipment_number", trackNo);
        Call<LoginResponse> call = trackAPI.getTrackStatus(Common.token, params);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d("Track Shipment", " --------- UNSUCCESSFUL -------- " + response.code());
                    return;
                }

                Log.d(" CONTENT", " -------------- " + response.body().getContent() + " --------------");
                if (response.body().getContent() == null || response.body().getContent().equals("")){
                    Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    int content = Integer.parseInt(response.body().getContent());
                    if (content == 3){
                        Toast.makeText(getActivity(), "Order In Process", Toast.LENGTH_SHORT).show();
                    } else if (content == 4) {
                        showView();
                        textViews[0].setBackgroundColor(Color.parseColor("#00FF99"));
                    } else if (content == 5) {
                        showView();
                        textViews[1].setBackgroundColor(Color.parseColor("#00FF99"));
                    } else if (content == 6) {
                        showView();
                        textViews[3].setBackgroundColor(Color.parseColor("#00FF99"));
                    } else if (content == 7) {
                        showView();
                        textViews[4].setBackgroundColor(Color.parseColor("#00FF99"));
                    } else if (content == -1){
                        Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Lost Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showView(){
        for (int i = 0; i < 9; i++){
            imageViews[i].setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < 5; i++) {
            textViews[i].setVisibility(View.VISIBLE);
        }
    }

}
