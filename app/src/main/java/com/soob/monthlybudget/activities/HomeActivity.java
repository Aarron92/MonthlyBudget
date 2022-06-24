package com.soob.monthlybudget.activities;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.soob.monthlybudget.R;
import com.soob.monthlybudget.databinding.ActivityHomeBinding;

/**
 * Home screen activity with a navigation bar at the bottom which will go to the various other
 * sub-screens that are different fragments
 */
public class HomeActivity extends AppCompatActivity
{
    /**
     * Binding to the XML file
     */
    private ActivityHomeBinding binding;

    /**
     * On creation, setup up the navigation bar at the bottom of the screen
     *
     * @param savedInstanceState state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // inflate the XML and the Activity
        this.binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        // the navigation panel at the bottom of the view
        BottomNavigationView navigationView = findViewById(R.id.navigation_view);

        // passing each menu ID as a set of IDs because each menu should be considered as top level destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_accounts, R.id.navigation_outgoings,
                R.id.navigation_transactions, R.id.navigation_monthly_savings).build();

        // create the navigation bar
        NavController navigationController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navigationController, appBarConfiguration);
        NavigationUI.setupWithNavController(this.binding.navigationView, navigationController);
    }

}