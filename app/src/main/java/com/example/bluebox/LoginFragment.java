package com.example.bluebox;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    Button btn_login;
    TextView txt_regNow;
    EditText edt_username, edt_pw;
    BlueBoxAPI loginBlueBox;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btn_login = view.findViewById(R.id.btnLogin);
        txt_regNow = view.findViewById(R.id.txtRegNow);
        edt_username = view.findViewById(R.id.edtUsername);
        edt_pw = view.findViewById(R.id.edtPw);



        txt_regNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frameFrag, new RegisterFragment(), "reg1").commit();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLinks(edt_username.getText().toString(), edt_pw.getText().toString());
                btn_login.setEnabled(false);
//                Intent intent = new Intent(getActivity(), HomeActivity.class);
//                startActivity(intent);
//                getActivity().finish();
            }
        });

        return view;
    }

    public void getLinks(String user, String pw) {
        final String username = user;
        final String password = pw;

//        LinkedHashMap<String,String> params = new LinkedHashMap<String,String>();
        final Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        Retrofit regRetrofit = new Retrofit.Builder().baseUrl("https://blueb0x.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        loginBlueBox = regRetrofit.create(BlueBoxAPI.class);

        Call<LoginResponse> call = loginBlueBox.postLogin(params);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d("TEST", " --------- UNSUCCESSFUL -------- " + response.code());
                    return;
                }
                if (response.body().getSuccess().equals("true")) {
                    Log.d("TEST", " --------- Response -------- " + response.body().getMsg());
                    Log.d("Login -- ", " ==== ++++++ _____ ___------"+params);
                    Common.token = "Bearer ".concat(response.body().token);
                    getProfile();
                } else {
                    Toast.makeText(getActivity(),  "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    Log.d("Login -- ", " ==== ++++++ _____ ___------"+params);
                    btn_login.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("TEST", " --------- FAILED -------- "+t.getMessage());
                Toast.makeText(getActivity(), "Connection Lost", Toast.LENGTH_SHORT).show();
                btn_login.setEnabled(true);
            }
        });
    }

    private void getProfile(){
        Retrofit profileRetrofit = new Retrofit.Builder().baseUrl("https://blueb0x.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        loginBlueBox = profileRetrofit.create(BlueBoxAPI.class);

        Call<UserProfile> call = loginBlueBox.getProfile(Common.token);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (!response.isSuccessful()) {
                    Log.d("TEST", " --------- UNSUCCESSFUL -------- " + response.code());
                    return;
                }
                UserProfile profile = response.body();
                Common.name = profile.profile_content.getName();
                Common.dob = String.valueOf(profile.profile_content.getDob());
                Common.email = profile.profile_content.getEmail();
                Common.address = profile.profile_content.getAddress();
                Common.phone = profile.profile_content.getPhone();
                Common.idNumber = profile.profile_content.getIdentity();
                Common.sex = profile.profile_content.getSex();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                getActivity().finish();

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.d("TEST", " --------- FAILED -------- "+t.getMessage());
                Toast.makeText(getActivity(), "Connection Lost", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
