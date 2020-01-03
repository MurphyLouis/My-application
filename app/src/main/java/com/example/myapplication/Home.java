package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.Prevalent.Prevalent;
import com.google.android.material.navigation.NavigationView;

import io.paperdb.Paper;

public class Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Paper.init(this);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       // FloatingActionButton fab = findViewById(R.id.fab);
    //    fab.setOnClickListener(new View.OnClickListener() {
      //      @Override
       //     public void onClick(View view) {
       //         Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
       //                 .setAction("Action", null).show();
      //      }
      //  });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

       // nav header retrieving student_no and password

        View headerView=navigationView.getHeaderView(0);
        TextView userNameTextView=headerView.findViewById(R.id.UserNamenavheader);
        TextView studentNoTextView=headerView.findViewById(R.id.studentnonavheader);


       userNameTextView.setText(Prevalent.currentOnlineUser.getName());
        studentNoTextView.setText(Prevalent.currentOnlineUser.getStudentNo());


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments)
            {
             if (destination.getId()==R.id.nav_home)
             {

             }

                if (destination.getId()==R.id.nav_gallery)
                {

                }
                if (destination.getId()==R.id.nav_tools)
                {

                }
                if (destination.getId()==R.id.nav_share)
                {

                }
                if (destination.getId()==R.id.nav_home)
                {

                }
                if (destination.getId()==R.id.nav_send)
                {
                    Intent intent= new Intent(Home.this, Login.class);
                    Paper.book().destroy();
                    startActivity(intent);
                    finish();
                }
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
