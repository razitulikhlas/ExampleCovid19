package com.example.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.covid19.fragment.AccountFragment;
import com.example.covid19.fragment.BookmarkFragment;
import com.example.covid19.fragment.HomeFragment;
import com.example.covid19.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends BaseActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, HomeFragment.newInstance()).commitNow();
        }

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bvHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, HomeFragment.newInstance()).commitNow();
                        break;
                    case R.id.bvSearch:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, SearchFragment.newInstance()).commitNow();
                        break;
                    case R.id.bvBookmark:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, BookmarkFragment.newInstance()).commitNow();
                        break;
                    case R.id.bvAccount:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, AccountFragment.newInstance()).commitNow();
                        break;
                }

                return true;
            }
        });

    }
}