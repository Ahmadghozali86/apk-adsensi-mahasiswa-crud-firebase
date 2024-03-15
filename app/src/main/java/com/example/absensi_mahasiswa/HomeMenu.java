package com.example.absensi_mahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeMenu extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    FragmentAkun fragmentAkun;
    FragmentMenu fragmentMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        fragmentAkun = new FragmentAkun();
        fragmentMenu = new FragmentMenu();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_menu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,fragmentMenu).commit();

                } else if(item.getItemId()==R.id.menu_akun){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,fragmentAkun).commit();

                } else {
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_menu);
    }

}