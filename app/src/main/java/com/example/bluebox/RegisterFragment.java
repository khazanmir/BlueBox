package com.example.bluebox;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.LinkedHashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    Button btn_regNext;
    EditText [] editTexts = new EditText[8];
    TextView txt_dob;
    String [] regInfo = new String[7];
    DatePickerDialog.OnDateSetListener mDateSetListener;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Toolbar toolBarReg = getActivity().findViewById(R.id.toolBarMain);
        toolBarReg.setTitle("Register");

        // Set Id
        editTexts[0] = view.findViewById(R.id.edtAddress);
        editTexts[1] = view.findViewById(R.id.edtEmail);
        editTexts[2] = view.findViewById(R.id.edtPw);
        editTexts[3] = view.findViewById(R.id.edtConfirmPw);
        editTexts[4] = view.findViewById(R.id.edtPhone);
        editTexts[5] = view.findViewById(R.id.edtAlterPhone);
        editTexts[6] = view.findViewById(R.id.edtFirstName);
        editTexts[7] = view.findViewById(R.id.edtLastName);
        txt_dob = view.findViewById(R.id.edtDate);
        btn_regNext = view.findViewById(R.id.btnRegNext);

        txt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                txt_dob.setText(date);
            }
        };


        btn_regNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Register2Fragment register2Fragment = new Register2Fragment();
//                        Bundle args = new Bundle();
//                        args.putStringArray("regInfo", regInfo);
//                        register2Fragment.setArguments(args);
//                        getFragmentManager().beginTransaction().replace(R.id.frameFrag, register2Fragment, "reg2").commit();
                Log.d("REGISTER", "======= " + editTexts[2] + " =======");
                if (editTexts[2].getText().toString().equals(editTexts[3].getText().toString())) {
                    for (int i = 0; i < 6; i++){
                        regInfo[i] = editTexts[i].getText().toString();
                    }
                    regInfo[4] = "234".concat(regInfo[4]);
                    regInfo[5] = "234".concat(regInfo[5]);
                    regInfo[6] = txt_dob.getText().toString();
                    if (editTexts[6].getText().toString().equals("") || editTexts[7].getText().toString().equals("") || regInfo[0].equals("") || regInfo[1].equals("") || regInfo[2].equals("") || regInfo[4].equals("") || regInfo[6].equals("")){
                        Toast.makeText(getActivity(), "Fill the required fields", Toast.LENGTH_SHORT).show();
                    } else {
                        Register2Fragment register2Fragment = new Register2Fragment();
                        Bundle args = new Bundle();
                        args.putStringArray("regInfo", regInfo);
                        register2Fragment.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.frameFrag, register2Fragment, "reg2").commit();
                    }
                } else {
                    Toast.makeText(getActivity(), "Password Do not Match", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

}
