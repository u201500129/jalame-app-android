package pe.tp1.hdpeta.jalame.Fragment;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

import pe.tp1.hdpeta.jalame.R;
import pe.tp1.hdpeta.jalame.Singleton.PersonSingleton;

import static android.support.constraint.Constraints.TAG;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastLocation;
    private LatLng latLngUser = new LatLng(-12.05165, -77.03461);
    // Plaza San Martin:  -12.05165/-77.03461

    private String mLastUpdateTime;
    private boolean mLocationPermissionGranted;
    private Boolean mRequestingLocationUpdates;
    private static final int TAG_CODE_PERMISSION_LOCATION = 1 ;
    private static final int DEFAULT_ZOOM = 15;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 20000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.gmaps);
        mapFragment.getMapAsync(this);

        getLocationPermission();

        if (mLocationPermissionGranted )  {
            //Construct a FusedLocationProviderClient.
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
            //initLocationCallback();
            getDriverLocation2();
            System.out.println("mLocationPermissionGranted TRUE,   initLocationCallback()");
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
            if (mMap != null & mLastLocation != null) {

                latLngUser = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngUser, DEFAULT_ZOOM));

                //Personalizacion Inicial
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.getUiSettings().setMapToolbarEnabled(false);
                mMap.getUiSettings().setCompassEnabled(false);
                //mMap.setLocationSource();

            } else {
                Toast.makeText(this.getActivity(),"El mapa aun no se ha cargado, googleMap is NULL", Toast.LENGTH_SHORT).show();
            }
    }



    public void centerCamera() {
        // Add user marker

        if (mLastLocation != null){
            latLngUser = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        }else{
            return;
        }

        MarkerOptions marker = new MarkerOptions().position(latLngUser);
        marker.title("My Name");
        marker.snippet("mi Correo");

        try{
            // Changing marker icon
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            if (mMap != null) {
                // adding marker
                mMap.addMarker(marker);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLngUser).zoom(DEFAULT_ZOOM).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngUser, DEFAULT_ZOOM));

                listaVehiculos();
            } else {
                Toast.makeText(this.getActivity(),"El mapa aun no se ha cargado, googleMap is NULL", Toast.LENGTH_SHORT).show();
            }
        }catch (SecurityException e){
            System.out.println("Error: " + e.getMessage());
        }
        //googleMap.addMarker(new MarkerOptions().position(latLngUser).title("USUARIO"));
        //googleMap.moveCamera( CameraUpdateFactory.newLatLng(latLngUser));
    }




    private boolean getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            //}else if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
        }else{
            ActivityCompat.requestPermissions(this.getActivity(), new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION }, TAG_CODE_PERMISSION_LOCATION);
            Toast.makeText(this.getActivity(),"Permiso denegado, Imposible acceder a la ubicacion", Toast.LENGTH_SHORT).show();
            mLocationPermissionGranted = false;
        }
        return mLocationPermissionGranted;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case TAG_CODE_PERMISSION_LOCATION: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    //Toast.makeText(this.getActivity(), "Permiso denegado, Sin Ubicacion", Toast.LENGTH_LONG).show();
                }

            }
        }
        //updateLocationUI();
    }


    //Conocer el movimiento de las unidades
    private void getDriverLocation1() {
        try {
            if (mLocationPermissionGranted) {
                // FORMA 1
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this.getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            mLastLocation =(Location) task.getResult();
                            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                            Log.e("addOnCompleteListener:", mLastLocation.toString() +"  Time:" + mLastUpdateTime);
                        } else {
                            Log.e(TAG, "LOCATION ERROR: %s", task.getException());
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("LOCATION ERROR EX: %s", e.getMessage());
        }
    }

    private void getDriverLocation2() {
        try {
            if (mLocationPermissionGranted) {
                // FORMA 2
                mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    mLastLocation = location;
                                    mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                                    centerCamera();
                                    Log.e("OnSuccessListener:", mLastLocation.toString() +"  Time:" + mLastUpdateTime);
                                }
                            }
                        });

            }
        } catch(SecurityException e)  {
            Log.e("LOCATION ERROR EX: %s", e.getMessage());
        }
    }


    // LOOPER
    @SuppressLint("MissingPermission")
    private void initLocationCallback() {
        //mSettingsClient = LocationServices.getSettingsClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mLastLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                Log.e("initLocationCallback:", mLastLocation.toString() +"  Time:" + mLastUpdateTime);
                //updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        //noinspection MissingPermission
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                //mLastKnownLocation = null;
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }






    private  void  listaVehiculos(){
        String idUser = "1" ;
        String restURL = "http://services.tarrillobarba.com.pe:6789/jalame/vehiculo/list/" + idUser +
                         "/" + latLngUser.latitude + "/" + latLngUser.longitude;

        String resultado = "";

        try {
            URL restServiceURL = new URL(restURL);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");

            httpConnection.setRequestProperty("Accept", "application/json");

            if (httpConnection.getResponseCode() == 200) {
                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
                resultado = responseBuffer.readLine();
            }else {
                Log.w("RESTfull", "ERROR: " + "HTTP GET: " + httpConnection.getResponseCode());
            }

            //Log.w("RESTfull", "DATO: " + resultado);
            httpConnection.disconnect();
        } catch (MalformedURLException e) {
            Log.w("RESTfull", "ERROR: " + "HTTP URL: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.w("RESTfull", "ERROR: " + "HTTP IOE: " + e.getMessage());
            e.printStackTrace();
        }


        if (resultado != null) {
            if (resultado.length() > 10) {
                try {
                    //Creando Objeto JSON
                    JSONObject joVehiculoAll = new JSONObject(resultado);
                    JSONArray joVehiculoArray = (JSONArray) joVehiculoAll.get("vehiculo");
                    int item = 0;

                    while (item < joVehiculoArray.length()){
                        JSONObject joVehiculo = joVehiculoArray.getJSONObject(item);
                        // = joVehiculo.getInt("codVehiculo")
                        // = joVehiculo.getInt("codPersona")
                        // = joVehiculo.getString("polizaSoat")

                        createMarker(joVehiculo.getString("latitud"),
                                joVehiculo.getString("longitud"),
                                joVehiculo.getString("matricula"),
                                joVehiculo.getString("marca") + " "+
                                joVehiculo.getString("modelo") + " "+
                                joVehiculo.getString("aFabrica"));


                        // = joVehiculo.getString("color")
                        // = joVehiculo.getInt("asientosTotal")
                        // = joVehiculo.getInt("asientosDisp")

                        // = joVehiculo.getString("visible")
                        // = joVehiculo.getInt("calificacion")
                        // = joVehiculo.getString("estadoR")
                        // = joVehiculo.getDate("tsupdate")
                        // = joVehiculo.getInt("distancia")

                        item ++;
                        Log.w("JALAME", "INFO: " + joVehiculo.toString());
                    }


                } catch (JSONException e) {
                    Log.w("JALAME", "ERROR: JSON " + e.getMessage());
                }
            }
        }

    }


    private void createMarker(String lat, String lng, String titulo, String descripcion){
        // Add a markers
        try{

            MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
            marker.title(titulo);
            marker.snippet(descripcion);

            // Changing marker icon
            //marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.jcar));
            // adding marker
            if (mMap != null) {
                mMap.addMarker(marker);
            }
        }catch (SecurityException e){
            System.out.println("Marker Error: " + e.getMessage());
        }
    }



}
