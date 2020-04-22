package com.example.bluebox;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register2Fragment extends Fragment {

    Button btn_selectId, btn_reg;
    private int STORAGE_PERMISSION_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView img_id, img_id2;
    private Uri mImageUri;
    EditText [] edtIdInfo = new EditText[4];
    TextView [] textViews = new TextView[2];
    String [] idInfo = new String [7];
    String [] prevInfo = new String[6];
    File file;
    int type = 0;
    RequestBody fbody;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    FileInputStream fileInputStream;

    public Register2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register2, container, false);
        //Get Previous Data
        prevInfo = getArguments().getStringArray("regInfo");
        // Set Ids
        edtIdInfo[0] = view.findViewById(R.id.edtIdName);
        edtIdInfo[1] = view.findViewById(R.id.edtIdNumber);
        edtIdInfo[2] = view.findViewById(R.id.edtIdAddress);
        edtIdInfo[3] = view.findViewById(R.id.edtIdSex);

        textViews[0] = view.findViewById(R.id.edtIdIssue);
        textViews[1] = view.findViewById(R.id.edtIdExpire);

        btn_selectId = view.findViewById(R.id.btnSelectId);
        btn_reg = view.findViewById(R.id.btnRegister);
        img_id = view.findViewById(R.id.imgId);
        img_id2 = view.findViewById(R.id.imgId2);

        // Listeners
        img_id2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    openFileChooser();
                } else {
                    requestStoragePermission();
                }

            }
        });

        img_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    openFileChooser();
                } else {
                    requestStoragePermission();
                }

            }
        });

        textViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(0);
            }
        });
        textViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(1);
            }
        });

        btn_selectId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), btn_selectId);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.vCard:
                                btn_selectId.setText("Voters card");
                                return true;
                            case R.id.nId:
                                btn_selectId.setText("National Id");
                                return true;
                            case R.id.dLicense:
                                btn_selectId.setText("Driving license");
                                return true;
                            case R.id.passport:
                                btn_selectId.setText("International Passport");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.inflate(R.menu.id_types);
                popup.show();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postImage();
                btn_reg.setEnabled(false);
                if (type == 1) {
                    postImage();
                } else {
                    if (btn_selectId.getText().toString().equals("Choose ID type")) {
                        Toast.makeText(getActivity(), "Please Select An ID Type", Toast.LENGTH_SHORT).show();
                        btn_reg.setEnabled(true);
                    }
                    if (textViews[0].getText().toString().equals("") || textViews[1].getText().toString().equals("") || edtIdInfo[0].getText().toString().equals("") || edtIdInfo[1].getText().toString().equals("") || edtIdInfo[2].getText().toString().equals("") || edtIdInfo[3].getText().toString().equals("")){
                        Toast.makeText(getActivity(), "Fill the required fields", Toast.LENGTH_SHORT).show();
                        btn_reg.setEnabled(true);
                    } 
                    if (isImgValid()){
                        for (int i = 0; i < 4; i++){
                            idInfo[i] = edtIdInfo[i].getText().toString();
                        }
                        idInfo[4] = btn_selectId.getText().toString();
                        idInfo[5] = textViews[0].getText().toString();
                        idInfo[6] = textViews[1].getText().toString();
                        postRegInfo();
                    } else {
                        Toast.makeText(getActivity(), "Invalid Image Input", Toast.LENGTH_SHORT).show();
                        btn_reg.setEnabled(true);
                    }
                }

            }
        });

        return view;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(img_id);
            img_id2.setVisibility(View.INVISIBLE);
            file = new File(FileUtil.getPath(mImageUri, getContext()));
            fbody = RequestBody.create(MediaType.parse("image/*"), file);
//            fbody = RequestBody.create(MediaType.parse("image/png"), file);
//            fbody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        }
    }

    public boolean isImgValid(){
        boolean flag = false;
        if (!(file == null) && !(file.equals("")) && file.length() < 1048577){
            flag = true;
        } 
        return flag;
    }

    public void openDatePicker(final int i){
        final int index = i;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                textViews[index].setText(date);
            }
        };
    }

    public void postRegInfo(){
        // Retrofit
        Retrofit regRetrofit = new Retrofit.Builder().baseUrl("https://blueb0x.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        BlueBoxAPI regAPI = regRetrofit.create(BlueBoxAPI.class);

        Map<String,String> params = new HashMap<String, String>();
        params.put("dob", prevInfo[6]);
        params.put("address", prevInfo[0]);
        params.put("username", prevInfo[1]);
        params.put("password", prevInfo[2]);
        params.put("phone", prevInfo[4]);
        params.put("alt_phone", prevInfo[5]);
        params.put("id_type", idInfo[4]);
        params.put("legal_name", idInfo[0]);
        params.put("id_number", idInfo[1]);
        params.put("id_address", idInfo[2]);
        params.put("sex", idInfo[3]);
        params.put("issue_date_id", idInfo[5]);
        params.put("expiry_date_id", idInfo[6]);
        Call<LoginResponse> call = regAPI.postRegiterMap(params);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d("REGISTER", " --------- UNSUCCESSFUL -------- " + response.code());
                    btn_reg.setEnabled(true);
                } else if (response.body().getSuccess().equals("true")){
//                    btn_reg.setEnabled(true);
                    type = 1;
                    postImage();
                } else {
                    btn_reg.setEnabled(true);
                    Toast.makeText(getActivity(), response.body().getMsg()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("REGISTER", " --------- Failed -------- " + t.getMessage());
                btn_reg.setEnabled(true);
                Toast.makeText(getActivity(), "Connection Lost", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postImage(){
        // Retrofit
        Retrofit regRetrofit = new Retrofit.Builder().baseUrl("https://blueb0x.herokuapp.com/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        BlueBoxAPI regAPI = regRetrofit.create(BlueBoxAPI.class);

        LinkedHashMap<String,String> params = new LinkedHashMap<String,String>();
        params.put("username", prevInfo[1]);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("id_image", "id_image", fbody);
//                                        RequestBody.create(MediaType.parse("image/*"), file));
        Log.d("USERNAME: ", "----- --- "+ prevInfo[1]);
        RequestBody username = RequestBody.create(MediaType.parse("multipart/form-data"),prevInfo[1]);
        Call<LoginResponse> call = regAPI.postImage(username, filePart);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d("REGISTER", " --------- UNSUCCESSFUL -------- " + response.code());
                    btn_reg.setEnabled(true);
                    return;
                }

                btn_reg.setEnabled(true);
                Toast.makeText(getActivity(), "Request Sent", Toast.LENGTH_SHORT).show();
                Log.d("Register ----- ", response.body().getMsg()+"");
                getFragmentManager().beginTransaction().replace(R.id.frameFrag, new LoginFragment(), "login").addToBackStack(null).commit();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("REGISTER", " --------- Failed -------- " + t.getMessage());
                btn_reg.setEnabled(true);
                Toast.makeText(getActivity(), "Connection Lost", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Storage permission
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to upload ID photo.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(" Permissiom", "-- Permission Granted --");
            } else {
                Log.d(" Permissiom", "-- Permission Denied --");
            }
        }
    }
}
