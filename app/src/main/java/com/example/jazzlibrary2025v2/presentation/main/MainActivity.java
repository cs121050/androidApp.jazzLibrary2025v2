package com.example.jazzlibrary2025v2.presentation.main;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.jazzlibrary2025v2.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jazzlibrary2025v2.databinding.ActivityMainBinding;

//import dagger.hilt.android.HiltAndroidApp;
//@HiltAndroidApp
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    DrawerLayout drawer;
    NavigationView navigationView ;

    AppBarLayout appBarLayout;
    Toolbar toolbar;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflates the layout using View Binding.
        //Sets the layout as the activity’s UI.
        //Configures the toolbar as the ActionBar.
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        FindViewByIdComponents();


        //floatin button
        //initFab();

        initRightNavView(drawer,navigationView);






        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination,
                                             @Nullable Bundle arguments) {
                updateToolbarVisibility(destination.getId());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    private void FindViewByIdComponents() {
        Log.d(TAG, "initViews: Started");

        drawer = binding.drawerLayout;
        navigationView  = binding.navView;

        appBarLayout = binding.appBarMain.appBarLayout;
        toolbar = binding.appBarMain.toolbar;

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    }



    public void initRightNavView(DrawerLayout drawer, NavigationView rightNavView){
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void initFab(){
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
    }

    private void updateToolbarVisibility(int destinationId) {
        if (destinationId == R.id.nav_gallery) {
            binding.appBarMain.toolbar.setVisibility(View.GONE);
            getSupportActionBar().hide();
        } else {
            binding.appBarMain.toolbar.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
        }
    }

}