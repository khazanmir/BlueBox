package com.example.bluebox;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class LinkDialogue extends AppCompatDialogFragment {

    private EditText edt_link;
    private ExampleDialogListener listener;
//    ClipboardManager cbm;
//    private ClipData clipData

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogue_link, null);

//        cbm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

        edt_link = view.findViewById(R.id.edtLink);

//        btn_paste = view.findViewById(R.id.btnPaste);
//
//        btn_paste.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        builder.setView(view)
                .setTitle("Add Link")
                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyTexts(edt_link.getText().toString());
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String link);
    }

}
