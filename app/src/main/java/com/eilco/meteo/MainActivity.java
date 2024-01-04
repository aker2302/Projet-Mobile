package com.eilco.meteo;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private String MyCity = "Calais";

    TodayFragment todayFragment = new TodayFragment();
    WeekFragment weekFragment = new WeekFragment();

    public void updateCity(String city){
        MyCity = city;
    }

    public String getCity(){
        return MyCity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.BNVNavBar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, todayFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.todayFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,todayFragment).commit();
                        return true;
                    case R.id.weekFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,weekFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}