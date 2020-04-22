package com.example.bluebox;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogueReceipt extends AppCompatDialogFragment {
    TextView [] textViews = new TextView [5];
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogue_receipt, null);
        textViews[0] = view.findViewById(R.id.txtReceiptName);
        textViews[1] = view.findViewById(R.id.txtReceiptPhone);
        textViews[2] = view.findViewById(R.id.txtReceiptEmail);
        textViews[3] = view.findViewById(R.id.txtReceiptPDetail);
        textViews[4] = view.findViewById(R.id.txtReceiptTotal);

        textViews[0].setText(Common.name);
        textViews[1].setText(Common.phone);
        textViews[2].setText(Common.email);
        textViews[3].setText(Common.desc);
        textViews[4].setText(Common.total);

        builder.setView(view)
                .setTitle("Receipt")
                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
