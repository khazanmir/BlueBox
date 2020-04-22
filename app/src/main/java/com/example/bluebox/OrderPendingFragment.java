package com.example.bluebox;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPendingFragment extends Fragment {

    RecyclerView orderRecycler;
    OrderPendingAdapter orderPendingAdapter;
    RecyclerView.LayoutManager orderPendingLayout;
    ArrayList<Order> listOfOrder = new ArrayList<>();
    ArrayList<Order> listOfPendingOrder = new ArrayList<>();

    ArrayList<OrderLink> pendingOrder = new ArrayList<>();

    BlueBoxAPI orderAllAPI;


    public OrderPendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_pending, container, false);

        Toolbar toolBarReg = getActivity().findViewById(R.id.toolbarOrder);
        toolBarReg.setTitle("Pending Orders");

        orderRecycler = view.findViewById(R.id.rvOrderPending);
        getAllOrders();
        
        return view;
    }

    private void getAllOrders(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://blueb0x.herokuapp.com/api/io/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        orderAllAPI = retrofit.create(BlueBoxAPI.class);
        Call<GetAllOrders> call = orderAllAPI.getOrder(Common.token);
        call.enqueue(new Callback<GetAllOrders>() {
            @Override
            public void onResponse(Call<GetAllOrders> call, Response<GetAllOrders> response) {
                if (!response.isSuccessful()) {
                    Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                    Toast.makeText(getContext(), "No Connection Available", Toast.LENGTH_SHORT).show();

                    return;
                }

                GetAllOrders getAllOrders = response.body();
                Common.allOrders = getAllOrders;
                listOfOrder = Common.allOrders.orders;
                Log.d("Order All",  " ----- item -- "+ getAllOrders.success);
                prune();
            }

            @Override
            public void onFailure(Call<GetAllOrders> call, Throwable t) {
                Log.d("Order All", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                Toast.makeText(getContext(), "Lost Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prune(){

        Log.d("Order All",  " ----- Pruning ------ ");
        for (int i = 0; i < listOfOrder.size(); i++){
            int linkSize = listOfOrder.get(i).order_links.size();
            for (int k = 0; k < linkSize; k++) {
                int status = Integer.parseInt(listOfOrder.get(i).order_links.get(k).link_status);
                if (status > 1 && status < 7) {
                    pendingOrder.add(listOfOrder.get(i).order_links.get(k));
                }
            }

        }

        if (pendingOrder.size() > 0) {
            orderRecycler.setHasFixedSize(true);
            orderPendingLayout = new LinearLayoutManager(getContext());
            orderPendingAdapter = new OrderPendingAdapter(pendingOrder);
            orderRecycler.setLayoutManager(orderPendingLayout);
            orderRecycler.setAdapter(orderPendingAdapter);
        } else {
            Toast.makeText(getContext(), "No Pending Order", Toast.LENGTH_SHORT).show();
        }
    }
}
