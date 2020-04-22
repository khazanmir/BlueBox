package com.example.bluebox;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ReceiptFragment extends Fragment {

    BlueBoxAPI orderAllAPI;
    ArrayList<Order> allOrder = new ArrayList<>();
    ArrayList<OrderLink> receiptOrder = new ArrayList<>();
    RecyclerView receiptRecycler;
    AdapterReceipt adapterReceipt;
    RecyclerView.LayoutManager receiptLayoutManager;
    String descp;

    public ReceiptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);
        receiptRecycler = view.findViewById(R.id.rvReceipt);
        receiptRecycler.setHasFixedSize(true);
        getOrders();

        return view;
    }

    private void getOrders(){
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
                allOrder = getAllOrders.getOrders();
                prune();
//                Common.allOrders = getAllOrders;
//                listOfOrder = Common.allOrders.orders;
                Log.d("Order All",  " ----- item -- "+ getAllOrders.success);

            }

            @Override
            public void onFailure(Call<GetAllOrders> call, Throwable t) {
                Log.d("Order All", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                Toast.makeText(getContext(), "Lost Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prune() {

        Log.d("Order All", " ----- Pruning ------ ");
        for (int i = 0; i < allOrder.size(); i++) {
            int linkSize = allOrder.get(i).order_links.size();
            if (i < linkSize) {
                int status = Integer.parseInt(allOrder.get(i).order_links.get(i).link_status);
                if (status > 2) {
//                    listOfPendingOrder.add(listOfOrder.get(i));
//                    Log.d("List Of Pending Order ", " ---- " + i);
                    for (int j = 0; j < allOrder.get(i).order_links.size(); j++) {
                        int linkStatus = Integer.parseInt(allOrder.get(i).order_links.get(j).getLink_status());

                        if (linkStatus > 2)
                            receiptOrder.add(allOrder.get(i).order_links.get(j));
                    }
                }
            }
        }

        receiptLayoutManager = new LinearLayoutManager(getContext());
        adapterReceipt = new AdapterReceipt(receiptOrder);
        receiptRecycler.setLayoutManager(receiptLayoutManager);
        receiptRecycler.setAdapter(adapterReceipt);

        adapterReceipt.setReceiptClick(new AdapterReceipt.ReceiptClick() {
            @Override
            public void onReceiptClick(int pos) {
                descp = receiptOrder.get(pos).getLink_description().get(0);
                for (int i = 1; i < receiptOrder.get(pos).getLink_description().size(); i++) {
                    descp = descp.concat("\n" + receiptOrder.get(pos).getLink_description().get(i));
                }
                Common.desc = descp;
                Common.total = receiptOrder.get(pos).getLink_price();
                DialogueReceipt dialogueReceipt = new DialogueReceipt();
                dialogueReceipt.show(getFragmentManager(), "Receipt");
            }
        });
    }

}
