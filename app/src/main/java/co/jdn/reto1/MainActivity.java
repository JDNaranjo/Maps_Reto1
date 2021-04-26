package co.jdn.reto1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MapsFragment mapsFragment;
    private NewPlaceFragment newPlaceFragment;
    private SearchFragment searchFragment;

    private BottomNavigationView navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPlaceFragment = NewPlaceFragment.newInstance();
        mapsFragment = MapsFragment.newInstance();
        searchFragment = SearchFragment.newInstance();

        navigationBar = findViewById(R.id.navigationBar);

        showFragment(newPlaceFragment);

        navigationBar.setOnNavigationItemSelectedListener(
                (menuItem) -> {
                    switch (menuItem.getItemId()){
                        case R.id.newPlace:
                            showFragment(newPlaceFragment);
                            break;
                        case R.id.myMap:
                            showFragment(mapsFragment);
                            break;
                        case R.id.searchPlace:
                            showFragment(searchFragment);
                            break;
                    }
                    return true;
                }
        );
    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}