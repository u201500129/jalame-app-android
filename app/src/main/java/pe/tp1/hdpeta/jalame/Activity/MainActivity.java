package pe.tp1.hdpeta.jalame.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.DataBase.DBHelper;
import pe.tp1.hdpeta.jalame.Fragment.MapFragment;
import pe.tp1.hdpeta.jalame.Fragment.ProfileFragment;
import pe.tp1.hdpeta.jalame.Fragment.QualifyServiceFragment;
import pe.tp1.hdpeta.jalame.Fragment.ServiciosFragment;
import pe.tp1.hdpeta.jalame.Fragment.NearDriversFragment;
import pe.tp1.hdpeta.jalame.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txtUserName;
    private TextView txtUserEmail;

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerLayout = navigationView.getHeaderView(0);

        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        txtUserName = (TextView) headerLayout.findViewById(R.id.txtUserName);
        txtUserEmail = (TextView) headerLayout.findViewById(R.id.txtUserEmail);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager.beginTransaction().replace(R.id.container, new MapFragment()).commit();

        DBHelper db = new DBHelper(this);

        PersonBean personBean = db.personBean();

        if (personBean != null){
            txtUserName.setText(personBean.getNombre());
            txtUserEmail.setText(personBean.getCorreo());
        }


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
    public boolean onOptionsItemSelected(MenuItem item) {
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

        Fragment fragment = null;
        boolean needCheck = true;


        if (id == R.id.nav_ubicacion) {
            fragment = MapFragment.newInstance();
        } else if (id == R.id.nav_conductor) {
            fragment = new NearDriversFragment();
        } else if (id == R.id.nav_servicios) {
            fragment = new ServiciosFragment();
        } else if (id == R.id.nav_pagos) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            needCheck = false;
        } else if (id == R.id.nav_calificar) {
            fragment = new QualifyServiceFragment();
        } else if (id == R.id.nav_logout) {
            needCheck = false;
            logoutUser();
        }

        if (fragment != null) {
            // Parameters to Fragment
            //SampleFragment fragment = new SampleFragment();
            //Bundle args = new Bundle();
            //args.putInt(SampleFragment.ARG_POSITION, position);
            //fragment.setArguments(args);

            //Paso 1: Obtener la instancia del administrador de fragmentos
            //FragmentManager fragmentManager = getSupportFragmentManager();

            //Paso 2: Crear una nueva transacción
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            //Paso 3: Añadir/Remplazar el nuevo fragmento creado al Activity
            transaction.replace(R.id.container, fragment);
            //transaction.add(R.id.content_main, fragment);

            transaction.addToBackStack(null);

            //Paso 4: Confirmar el cambio
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (needCheck) item.setChecked(true);

        getSupportActionBar().setTitle(item.getTitle());

        return true;
    }


    private void logoutUser() {
        this.deleteDatabase("Jalame.bd");
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }


}
