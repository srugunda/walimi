package com.example.navigationdrawermateriallibrary;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdrawermateriallibrary.Prevalent.PrevalentItem;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        }

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(PrevalentItem.currentOnlineUser.getName());
    }

    /**
     * Handles the Back button: closes the nav drawer.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     * Inflates the options menu.
     * @param menu  Menu to inflate
     * @return      Returns true if menu is inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handles a click on the Settings item in the options menu.
     * @param item  Item in options menu that was clicked.
     * @return      Returns true if the item was Settings.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        /*
        else if(id == R.id.action_logout){
            Paper.book().destroy();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Logged out of account", Toast.LENGTH_SHORT).show();
            finish();
        }

         */

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles a navigation drawer item click. It detects which item was
     * clicked and displays a toast message showing which item.
     * @param item  Item in the navigation drawer
     * @return      Returns true after closing the nav drawer
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_cart:
                // Handle the camera import action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_profile));
                return true;
            case R.id.nav_orders:
                // Handle the gallery action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_favourites));
                return true;
            case R.id.nav_categories:
                // Handle the slideshow action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_previous_orders));
                return true;
            case R.id.nav_settings:
                // Handle the tools action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_report_problem));
                return true;
            case R.id.nav_logout:
                Paper.book().destroy();
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            default:
                return false;
        }
    }

    /**
     * Displays a toast message.
     * @param message   Message to display in toast
     */
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showOrganicFoodDetails(View view) {
        Intent intent = new Intent(MainActivity.this, DisplayItemsActivity.class);
        startActivity(intent);
    }
}
