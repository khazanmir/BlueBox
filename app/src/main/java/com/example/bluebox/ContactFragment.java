package com.example.bluebox;


import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    Button btn_whatsapp, btn_insta, btn_mail, btn_fb, btn_services, btn_car;
    TextView txt_whatsapp, txt_insta, txt_mail, txt_fb, txt_terms;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        btn_whatsapp = view.findViewById(R.id.btnWhatsApp);
        btn_insta = view.findViewById(R.id.btnInsta);
        btn_mail = view.findViewById(R.id.btnMail);
        btn_fb = view.findViewById(R.id.btnFb);
        txt_fb = view.findViewById(R.id.txtFb);
        txt_mail = view.findViewById(R.id.txtMail);
        txt_insta = view.findViewById(R.id.txtInsta);
        txt_whatsapp = view.findViewById(R.id.txtWhatsapp);
        txt_terms = view.findViewById(R.id.txtTerm);
        btn_services = view.findViewById(R.id.btnServices);
        btn_car = view.findViewById(R.id.btnCarServices);

        btn_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frameOrder, new CarServiceFragment()).commit();
            }
        });

        btn_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frameOrder, new ServiceFragment()).commit();
            }
        });

        txt_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frameOrder, new TermFragment()).commit();
            }
        });

        btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp("2349059427238");
            }
        });

        btn_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInsta();
            }
        });

        btn_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMail();
            }
        });

        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFb();
            }
        });

        txt_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFb();
            }
        });

        txt_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMail();
            }
        });

        txt_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInsta();
            }
        });

        txt_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp("2349059427238");
            }
        });


        return view;
    }

    private void openWhatsApp(String number) {
        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
            startActivity(sendIntent);

        } catch(Exception e) {
            Log.e("Contact", "ERROR_OPEN_MESSANGER"+e.toString());
        }
    }

    private void openInsta(){
        Uri uri = Uri.parse("http://instagram.com/_u/Ofenetworks");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/xxx")));
        }
    }

    private void openMail(){
        try{
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "contactofenetworks@gmail.com"));
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Log.d("Contact", e+" -- ---");
        }
    }

    private void openFb(){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Ofenetworks/?__tn__=%2Cd%2CP-R&eid=ARDvOLjj0wEOONDHJVOUO_OceE4Mz0o9Wic_9QVF_v849-4AOoGlsGh2FXZ4gG4k6MBnOkh2Ss2LDugm"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/appetizerandroid")));
        }
    }
}
