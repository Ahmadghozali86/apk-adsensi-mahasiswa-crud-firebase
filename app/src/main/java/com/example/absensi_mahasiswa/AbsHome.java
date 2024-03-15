package com.example.absensi_mahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AbsHome extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentAbsen fragmentAbsen;
    FragmentView fragmentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation1);

        fragmentAbsen = new FragmentAbsen();
        fragmentView = new FragmentView();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_absensi){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout1,fragmentAbsen).commit();

                } else if(item.getItemId()==R.id.menu_view){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout1,fragmentView).commit();

                } else {
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_absensi);
    }

}