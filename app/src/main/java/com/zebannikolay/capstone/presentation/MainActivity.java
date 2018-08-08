package com.zebannikolay.capstone.presentation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.zebannikolay.capstone.R;
import com.zebannikolay.capstone.core.adapters.ScreenSlidePagerAdapter;
import com.zebannikolay.capstone.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(R.string.app_name);

        initBottomNavigation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void initBottomNavigation() {
        ArrayList<Fragment> fragments = new ArrayList<>(3);
        fragments.add(new GamesListFragment());
        fragments.add(new RecentGamesListFragment());
        fragments.add(new FavoriteGamesListFragment());
        binding.viewPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager(), fragments));

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_search:
                binding.viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_recent:
                binding.viewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_favorite:
                binding.viewPager.setCurrentItem(2);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (binding.viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() - 1);
        }
    }
}
