package com.example.bluebox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements LinkDialogue.ExampleDialogListener, ShipDialogue.ShipDialogListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.getMenu().getItem(1).setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().add(R.id.frameHome, new HomeFragment(), "home").commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.order:
                            selectedFragment = new OrderFragment();
                            break;
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    Bundle args = new Bundle();
                    args.putInt("frag", 1);
                    selectedFragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameHome,
                            selectedFragment).commit();

                    return true;
                }
    };

    @Override
    public void applyTexts(String link) {
        Common.listOfLink.add(new PostLink(link));
        OrderFragment orderFragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt("frag", 1);
        orderFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, orderFragment, "order").commit();
    }

    @Override
    public void applyTexts(String link, String shipNo) {
        Common.listOfShipLink.add(new PostShip(link));
        OrderFragment orderFragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt("frag", 2);
        orderFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, orderFragment, "order").commit();
    }
}
