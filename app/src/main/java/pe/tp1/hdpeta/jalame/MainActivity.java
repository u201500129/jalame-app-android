package pe.tp1.hdpeta.jalame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pe.tp1.hdpeta.jalame.utils.GeoLocation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GeoLocation geoLocation;

    private String posLatitud;
    private String posLongitud;
    private EditText txtLatitud;
    private EditText txtLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //US02
        geoLocation = new GeoLocation(this);
        txtLatitud =(EditText) findViewById(R.id.txtLatitud);
        txtLatitud.setVisibility(View.GONE);
        txtLongitud = (EditText) findViewById(R.id.txtLongitud) ;
        txtLongitud.setVisibility(View.GONE);

        geoLocation.getTextCoordinates(txtLongitud,txtLatitud,null,null );
        //--US02

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //aca
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_location) {
            // Accciones location:
            MostrarUbicacion();
        } else if (id == R.id.nav_conductor) {

        } else if (id == R.id.nav_servicios) {

        } else if (id == R.id.nav_pagos) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_calificar) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //US02
    private void MostrarUbicacion() {
        double pLongitud = Double.parseDouble(txtLongitud.getText().toString());
        double pLatitud = Double.parseDouble(txtLatitud.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putDouble("pLongitud",pLongitud);
        bundle.putDouble("pLatitud",pLatitud);
        Intent mapaActivity = new Intent(this, MyLocation.class);
        mapaActivity.putExtras(bundle);
        startActivity(mapaActivity);
//        Intent mapaActivity = new Intent(this, MyLocation.class);
//        startActivity(mapaActivity);
        String msg = "demo";
        Toast.makeText(this,
                "logitud es: "+txtLongitud.getText().toString()
                + "\n latitud es:" + txtLatitud.getText().toString()
                ,Toast.LENGTH_SHORT).show();
        //txtLatitud.setText(msg.toString());
    }
    @Override
    protected void onStop() {
        super.onStop();
        //ALF:para quitar el uso del gps y guardar bateria
        geoLocation.stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        geoLocation.stopLocationUpdates();
    }
    //--US02

    @Override
    protected void onPause() {
        super.onPause();
        geoLocation.stopLocationUpdates();
    }
}
