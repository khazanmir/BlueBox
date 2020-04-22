package com.example.bluebox;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
public class ApproveFragment extends Fragment {

    RecyclerView approveRecyler;
    AdapterApprove adapterApprove;
    RecyclerView.LayoutManager approveLayout;
    BlueBoxAPI approveAPI;
    ArrayList<Order> listOrder;
    ArrayList<OrderLink> listOfApprove = new ArrayList<>();
    ArrayList<OrderLink> listRecycler = new ArrayList<>();

    public ApproveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approve, container, false);
        approveRecyler = view.findViewById(R.id.rvOrderApprove);
        approveRecyler.setHasFixedSize(true);

        getAllOrders();

        return view;
    }

    private void getAllOrders(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://blueb0x.herokuapp.com/api/io/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        approveAPI = retrofit.create(BlueBoxAPI.class);
        Call<GetAllOrders> call = approveAPI.getOrder(Common.token);
        call.enqueue(new Callback<GetAllOrders>() {
            @Override
            public void onResponse(Call<GetAllOrders> call, Response<GetAllOrders> response) {
                if (!response.isSuccessful()) {
                    Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.code());
                    Toast.makeText(getContext(), "No Connection Available", Toast.LENGTH_SHORT).show();

                    return;
                }
                Common.approveCounter = -1;
                GetAllOrders getAllOrders = response.body();
                Common.allOrders = getAllOrders;
                listOrder = Common.allOrders.orders;
                Log.d("Order All",  " ----- item -- "+ getAllOrders.success);

                Log.d("Order All",  " ----- Pruning ------ ");

                for (int i = 0; i < listOrder.size(); i++){
                    int linkSize = listOrder.get(i).order_links.size();
                    for (int j = 0; j < linkSize; j++) {
                        int status = Integer.parseInt(listOrder.get(i).order_links.get(j).link_status);
                        if (status < 2 && status > 0){
                            listOfApprove.add(listOrder.get(i).order_links.get(j));
                            Log.d("List Of Pending Order ", " ---- " + j);
                        }
                    }

                }
                recycler();

            }

            @Override
            public void onFailure(Call<GetAllOrders> call, Throwable t) {
                Log.d("Order All", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                Toast.makeText(getContext(), "Lost Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void recycler(){
        if (listOfApprove.size() > 0) {
            listRecycler.addAll(listOfApprove);
            approveLayout = new LinearLayoutManager(getContext());
            adapterApprove = new AdapterApprove(listRecycler);
            approveRecyler.setLayoutManager(approveLayout);
            approveRecyler.setAdapter(adapterApprove);
            Common.approveCounter = 1;
            adapterApprove.onItemClickListner(new AdapterApprove.OnItemClickListner() {
                @Override
                public void onAcceptClick(int pos) {
                    acceptLink(pos);
                    DialoguePayment dialoguePayment = new DialoguePayment();
                    dialoguePayment.show(getFragmentManager(), "payment");
                }

                @Override
                public void onRemoveClick(int pos) {
                    deleteLink(pos);
                }
            });

        } else {
            Toast.makeText(getContext(), "No Pending Order", Toast.LENGTH_SHORT).show();
        }
    }

    private void acceptLink(final int pos) {
        final int index = pos;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://blueb0x.herokuapp.com/api/io/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        approveAPI = retrofit.create(BlueBoxAPI.class);
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("status_code", 2);
        Map<String, String> params2 = new HashMap<String, String>();
        params2.put("link_id", listOfApprove.get(pos).get_id());
        Call<LoginResponse> call = approveAPI.postAccept(Common.token, params, params2);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.body().msg);
                    Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }
                notifyAdapter(index);
//                Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
//                adapterApprove.notifyItemChanged(index);
//                listOfApprove.remove(index);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Lost Connection " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteLink(int pos) {
        final int index = pos;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://blueb0x.herokuapp.com/api/io/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        approveAPI = retrofit.create(BlueBoxAPI.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("link_id", listOfApprove.get(pos).get_id());

        Call<LoginResponse> call = approveAPI.postRemove(Common.token, params);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d("Order", " --------- UNSUCCESSFUL -------- " + response.body().msg);
                    Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }
                notifyAdapter(index);
//                Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
//                adapterApprove.notifyItemChanged(index);
//                listOfApprove.remove(index);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Order", " --------- FAILED -------- " + t.getMessage() + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Lost Connection " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void notifyAdapter(int index){
        listOfApprove.remove(index);
        listRecycler.clear();
        adapterApprove.notifyDataSetChanged();
        listRecycler.addAll(listOfApprove);
        adapterApprove.notifyItemRangeInserted(0, listRecycler.size());
    }

}
