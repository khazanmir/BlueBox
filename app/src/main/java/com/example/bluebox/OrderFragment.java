package com.example.bluebox;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    //    Button btn_new, btn_pending;
    Switch switch_order;
    TextView txt_switch;
    EditText edt_coupon;
    FloatingActionButton btn_addLink;
    Button btn_confirm;
    BlueBoxAPI linkAPI, shipAPI;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        int arg = getArguments().getInt("frag");

        edt_coupon = view.findViewById(R.id.edtCouponCode);
        btn_addLink = view.findViewById(R.id.btnAddLink);
        txt_switch = view.findViewById(R.id.txtSwitch);
        Toolbar toolBarReg = getActivity().findViewById(R.id.toolBar);
        toolBarReg.setTitle("Order Management");
        btn_confirm = view.findViewById(R.id.btnConfirm);
        switch_order = view.findViewById(R.id.switchOrder);
//        switch_order.setChecked(true);

        if (arg == 1) {
            switch_order.setChecked(true);
            getFragmentManager().beginTransaction().add(R.id.frameOrder, new OrderNewFragment(), "New Order").commit();
            if (Common.listOfLink.size() == 0) {
                btn_confirm.setVisibility(View.INVISIBLE);
                edt_coupon.setVisibility(View.INVISIBLE);
            } else {
                btn_confirm.setVisibility(View.VISIBLE);
                edt_coupon.setVisibility(View.VISIBLE);
            }
        } else if(arg == 2){
            switch_order.setChecked(false);
            getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderShippingFragment(), "Pending Order").commit();
            if (Common.listOfShipLink.size() == 0) {
                btn_confirm.setVisibility(View.INVISIBLE);
                edt_coupon.setVisibility(View.INVISIBLE);
            } else {
                btn_confirm.setVisibility(View.VISIBLE);
                edt_coupon.setVisibility(View.VISIBLE);
            }
        }

//            Toast.makeText(getActivity(), "0", Toast.LENGTH_SHORT).show();
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switch_order.isChecked()) {
                    String temp = edt_coupon.getText().toString();
                    if (temp.length() > 0) {
                        postLink(temp);
                    } else {
                        postLink(" ");
                    }

                } else {
                    String temp = edt_coupon.getText().toString();
                    if (temp.length() > 0) {
                        postShipLink(temp);
                    } else {
                        postShipLink(" ");
                    }
                }
            }
        });

        switch_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "New Order").commit();
                    if (Common.listOfLink.size() == 0) {
                        btn_confirm.setVisibility(View.INVISIBLE);
                    } else {
                        btn_confirm.setVisibility(View.VISIBLE);
                        edt_coupon.setVisibility(View.VISIBLE);
                    }
                    txt_switch.setText("Switch to Ship4Me");
                } else {
                    DialogueWarehouse dialogueWarehouse = new DialogueWarehouse();
                    dialogueWarehouse.show(getFragmentManager(), "warehouse dialogue");
                    getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderShippingFragment(), "Pending Order").commit();
                    if (Common.listOfShipLink.size() == 0) {
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                    } else {
                        btn_confirm.setVisibility(View.VISIBLE);
                        edt_coupon.setVisibility(View.VISIBLE);
                    }
                    txt_switch.setText("Switch to Buy4Me");
                }
            }
        });

        btn_addLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_order.isChecked()) {
                    LinkDialogue linkDialogue = new LinkDialogue();
                    linkDialogue.show(getFragmentManager(), "link dialogue");
                } else {
                    ShipDialogue shipDialogue = new ShipDialogue();
                    shipDialogue.show(getFragmentManager(), "ship dialogue");
                }
            }
        });

        return view;
    }

    public void postShipLink(String coupon) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://blueb0x.herokuapp.com/api/io/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        linkAPI = retrofit.create(BlueBoxAPI.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("order_type", "s4m");
        Map<String, String> couponParam = new HashMap<String, String>();
        params.put("coupon_code", coupon);
        if (Common.listOfShipLink.size() < 21) {
            if (Common.listOfShipLink.size() == 1) {

                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.body().msg);
                            Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
//                            return;
                        }
                        Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                        Toast.makeText(getActivity(), "Lost Connection " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (Common.listOfShipLink.size() == 2) {
                Map<String, String> params1 = new HashMap<String, String>();
                Map<String, String> params2 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 3) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 4) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 5) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 6) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5, params6, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 7) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 8) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 9) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 10) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 11) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfShipLink.get(10).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 12) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfShipLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfShipLink.get(11).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params12, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 13) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfShipLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfShipLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfShipLink.get(12).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 14) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfShipLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfShipLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfShipLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfShipLink.get(13).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 15) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfShipLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfShipLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfShipLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfShipLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfShipLink.get(14).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 16) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfShipLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfShipLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfShipLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfShipLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfShipLink.get(14).getLink());
                Map<String, String> params16 = new HashMap<String, String>();
                params16.put("links", Common.listOfShipLink.get(15).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15,
                        params16, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 17) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfShipLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfShipLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfShipLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfShipLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfShipLink.get(14).getLink());
                Map<String, String> params16 = new HashMap<String, String>();
                params16.put("links", Common.listOfShipLink.get(15).getLink());
                Map<String, String> params17 = new HashMap<String, String>();
                params17.put("links", Common.listOfShipLink.get(16).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15,
                        params16, params17, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 18) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfShipLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfShipLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfShipLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfShipLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfShipLink.get(14).getLink());
                Map<String, String> params16 = new HashMap<String, String>();
                params16.put("links", Common.listOfShipLink.get(15).getLink());
                Map<String, String> params17 = new HashMap<String, String>();
                params17.put("links", Common.listOfShipLink.get(16).getLink());
                Map<String, String> params18 = new HashMap<String, String>();
                params18.put("links", Common.listOfShipLink.get(17).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15,
                        params16, params17, params18, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 19) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfShipLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfShipLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfShipLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfShipLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfShipLink.get(14).getLink());
                Map<String, String> params16 = new HashMap<String, String>();
                params16.put("links", Common.listOfShipLink.get(15).getLink());
                Map<String, String> params17 = new HashMap<String, String>();
                params17.put("links", Common.listOfShipLink.get(16).getLink());
                Map<String, String> params18 = new HashMap<String, String>();
                params18.put("links", Common.listOfShipLink.get(17).getLink());
                Map<String, String> params19 = new HashMap<String, String>();
                params19.put("links", Common.listOfShipLink.get(18).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15,
                        params16, params17, params18, params19, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfShipLink.size() == 20) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfShipLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfShipLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfShipLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfShipLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfShipLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfShipLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfShipLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfShipLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfShipLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfShipLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfShipLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfShipLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfShipLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfShipLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfShipLink.get(14).getLink());
                Map<String, String> params16 = new HashMap<String, String>();
                params16.put("links", Common.listOfShipLink.get(15).getLink());
                Map<String, String> params17 = new HashMap<String, String>();
                params17.put("links", Common.listOfShipLink.get(16).getLink());
                Map<String, String> params18 = new HashMap<String, String>();
                params18.put("links", Common.listOfShipLink.get(17).getLink());
                Map<String, String> params19 = new HashMap<String, String>();
                params19.put("links", Common.listOfShipLink.get(18).getLink());
                Map<String, String> params20 = new HashMap<String, String>();
                params20.put("links", Common.listOfShipLink.get(19).getLink());

                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15,
                        params16, params17, params18, params19, params20, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfShipLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });
            }

        } else {
            Toast.makeText(getActivity(), "Limit Reached", Toast.LENGTH_SHORT).show();
        }
    }

    public void postLink(String coupon) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://blueb0x.herokuapp.com/api/io/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        linkAPI = retrofit.create(BlueBoxAPI.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("order_type", "b4m");
        Map<String, String> couponParam = new HashMap<String, String>();
        params.put("coupon_code", coupon);
        if (Common.listOfLink.size() < 21) {
            if (Common.listOfLink.size() == 1) {

                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.body().msg);
                            Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
//                            return;
                        }
                        Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                        Toast.makeText(getActivity(), "Lost Connection " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (Common.listOfLink.size() == 2) {
                Map<String, String> params1 = new HashMap<String, String>();
                Map<String, String> params2 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                params2.put("links", Common.listOfLink.get(1).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 3) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 4) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 5) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 6) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5, params6, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 7) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 8) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 9) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 10) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 11) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfLink.get(10).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 12) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfLink.get(11).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params12, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 13) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfLink.get(12).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 14) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfLink.get(13).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 15) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfLink.get(14).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 16) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfLink.get(14).getLink());
                Map<String, String> params16 = new HashMap<String, String>();
                params16.put("links", Common.listOfLink.get(15).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15,
                        params16, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 17) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfLink.get(14).getLink());
                Map<String, String> params16 = new HashMap<String, String>();
                params16.put("links", Common.listOfLink.get(15).getLink());
                Map<String, String> params17 = new HashMap<String, String>();
                params17.put("links", Common.listOfLink.get(16).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15,
                        params16, params17, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 18) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfLink.get(14).getLink());
                Map<String, String> params16 = new HashMap<String, String>();
                params16.put("links", Common.listOfLink.get(15).getLink());
                Map<String, String> params17 = new HashMap<String, String>();
                params17.put("links", Common.listOfLink.get(16).getLink());
                Map<String, String> params18 = new HashMap<String, String>();
                params18.put("links", Common.listOfLink.get(17).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15,
                        params16, params17, params18, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 19) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfLink.get(14).getLink());
                Map<String, String> params16 = new HashMap<String, String>();
                params16.put("links", Common.listOfLink.get(15).getLink());
                Map<String, String> params17 = new HashMap<String, String>();
                params17.put("links", Common.listOfLink.get(16).getLink());
                Map<String, String> params18 = new HashMap<String, String>();
                params18.put("links", Common.listOfLink.get(17).getLink());
                Map<String, String> params19 = new HashMap<String, String>();
                params19.put("links", Common.listOfLink.get(18).getLink());
                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15,
                        params16, params17, params18, params19, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });

            } else if (Common.listOfLink.size() == 20) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("links", Common.listOfLink.get(0).getLink());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("links", Common.listOfLink.get(1).getLink());
                Map<String, String> params3 = new HashMap<String, String>();
                params3.put("links", Common.listOfLink.get(2).getLink());
                Map<String, String> params4 = new HashMap<String, String>();
                params4.put("links", Common.listOfLink.get(3).getLink());
                Map<String, String> params5 = new HashMap<String, String>();
                params5.put("links", Common.listOfLink.get(4).getLink());
                Map<String, String> params6 = new HashMap<String, String>();
                params6.put("links", Common.listOfLink.get(5).getLink());
                Map<String, String> params7 = new HashMap<String, String>();
                params7.put("links", Common.listOfLink.get(6).getLink());
                Map<String, String> params8 = new HashMap<String, String>();
                params8.put("links", Common.listOfLink.get(7).getLink());
                Map<String, String> params9 = new HashMap<String, String>();
                params9.put("links", Common.listOfLink.get(8).getLink());
                Map<String, String> params10 = new HashMap<String, String>();
                params10.put("links", Common.listOfLink.get(9).getLink());
                Map<String, String> params11 = new HashMap<String, String>();
                params11.put("links", Common.listOfLink.get(10).getLink());
                Map<String, String> params12 = new HashMap<String, String>();
                params12.put("links", Common.listOfLink.get(11).getLink());
                Map<String, String> params13 = new HashMap<String, String>();
                params13.put("links", Common.listOfLink.get(12).getLink());
                Map<String, String> params14 = new HashMap<String, String>();
                params14.put("links", Common.listOfLink.get(13).getLink());
                Map<String, String> params15 = new HashMap<String, String>();
                params15.put("links", Common.listOfLink.get(14).getLink());
                Map<String, String> params16 = new HashMap<String, String>();
                params16.put("links", Common.listOfLink.get(15).getLink());
                Map<String, String> params17 = new HashMap<String, String>();
                params17.put("links", Common.listOfLink.get(16).getLink());
                Map<String, String> params18 = new HashMap<String, String>();
                params18.put("links", Common.listOfLink.get(17).getLink());
                Map<String, String> params19 = new HashMap<String, String>();
                params19.put("links", Common.listOfLink.get(18).getLink());
                Map<String, String> params20 = new HashMap<String, String>();
                params20.put("links", Common.listOfLink.get(19).getLink());

                Call<LoginResponse> call = linkAPI.postLink(Common.token, params1, params2, params3, params4, params5,
                        params6, params7, params8, params9, params10,
                        params11, params12, params13, params14, params15,
                        params16, params17, params18, params19, params20, params, couponParam);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                            return;
                        }
                        Toast.makeText(getContext(), "Request Generated", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.INVISIBLE);
                        edt_coupon.setVisibility(View.INVISIBLE);
                        Common.listOfLink.clear();
                        getFragmentManager().beginTransaction().replace(R.id.frameOrder, new OrderNewFragment(), "order new").commit();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                    }
                });
            }

        } else {
            Toast.makeText(getActivity(), "Limit Reached", Toast.LENGTH_SHORT).show();
        }
    }




}
