package pe.tp1.hdpeta.jalame.Fragment;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.DataBase.DBHelper;
import pe.tp1.hdpeta.jalame.Network.HttpUrlHandler;
import pe.tp1.hdpeta.jalame.R;


public class MapFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
                    SolicitaServicioFragment.SolicitaServicioListener {

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

    PersonBean personBean;


    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.gmaps);
        mapFragment.getMapAsync(this);

        DBHelper db = new DBHelper(getContext());
        personBean = db.personBean();

        getLocationPermission();

        if (mLocationPermissionGranted )  {
            //Construct a FusedLocationProviderClient.
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

            initLocationCallback();
            //getDriverLocation2();


            System.out.println("mLocationPermissionGranted TRUE,   initLocationCallback()");
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        int intentos = 0;

        try {
            //Esperamos que el mapa este listo
            while (mMap == null && intentos < 30 ){
                Thread.sleep(1000);
                intentos ++;
            }

            if (mMap != null) {

                mMap.setOnMarkerClickListener(this);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngUser, 10));

                //Personalizacion Inicial
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.getUiSettings().setMapToolbarEnabled(false);
                mMap.getUiSettings().setCompassEnabled(false);

                //mMap.setLocationSource();

            } else {
                Toast.makeText(this.getActivity(),"Necesita permisos para acceder al MAPA.", Toast.LENGTH_SHORT).show();
            }

        } catch (InterruptedException e) {
            System.out.println("THREAD ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        solicitarServicio();
        return false;
    }




    public void centerCamera(double lat, double lng) {
        LatLng punto = new LatLng(lat, lng);
        try{

            if (mMap != null) {
                CameraPosition cameraPosition = new CameraPosition.Builder().target(punto).zoom(DEFAULT_ZOOM).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngUser, DEFAULT_ZOOM));
                //listaVehiculos();
            }
        }catch (SecurityException e){
            System.out.println("Error: " + e.getMessage());
        }
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
                    Toast.makeText(this.getActivity(), "Permiso denegado, Sin Ubicacion", Toast.LENGTH_LONG).show();
                }
            }
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
                Log.e("LocationCallback", " time:(" + mLastUpdateTime +")   Location:" + mLastLocation.toString());

                updateMarkers();
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





    private void createMarker(String tipo, int id, String lat, String lng, String titulo, String descripcion){
        // Add a markers
        Marker nMarker;
        try{

            MarkerOptions mOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
            mOptions.title(titulo);
            mOptions.snippet(descripcion);

            // Changing marker icon
            if (tipo.equals("V")){
                mOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.iccarb));
            }else if (tipo.equals("U")){
                mOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icflag));
                mOptions.zIndex(9);
            }else if (tipo.equals("P")){
                mOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.iclocation));
            }else {
                mOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }

            // adding marker
            if (mMap != null) {
                nMarker = mMap.addMarker(mOptions);
                nMarker.setTag(tipo + String.valueOf(id));
            }
        }catch (SecurityException e){
            System.out.println("Marker Error: " + e.getMessage());
        }
    }




    private void updateMarkers(){
        if (mLastLocation != null){
            mMap.clear();

            latLngUser = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());

            createMarker("U", personBean.getCodPersona(),
                    String.valueOf(latLngUser.latitude),
                    String.valueOf(latLngUser.longitude),
                    personBean.getNombre(), personBean.getApellido());

            centerCamera(mLastLocation.getLatitude(),mLastLocation.getLongitude());

            listaVehiculos();
        }
    }




    private  void listaVehiculos(){

        //temp
        String idUser = String.valueOf(personBean.getCodPersona());
        //personBean.getPerfil();

        String path =  "vehiculo/list/" + idUser +"/" +  latLngUser.latitude + "/" + latLngUser.longitude;

        HttpUrlHandler httpRest = new HttpUrlHandler("GET", path);

        String jsonString ;

        if (httpRest.readREST()){
            jsonString = httpRest.getJsonString();

            if (jsonString.length() > 10) {
                try {
                    //Creando Objeto JSON
                    JSONObject joVehiculoAll = new JSONObject(jsonString);
                    JSONArray joVehiculoArray = (JSONArray) joVehiculoAll.get("vehiculo");
                    int item = 0;

                    while (item < joVehiculoArray.length()){
                        JSONObject joVehiculo = joVehiculoArray.getJSONObject(item);

                        createMarker( "V", joVehiculo.getInt("codVehiculo"),
                                     joVehiculo.getString("latitud"),
                                     joVehiculo.getString("longitud"),
                                joVehiculo.getInt("calificacion") + "E | " +
                                        joVehiculo.getString("matricula"),
                                joVehiculo.getString("marca") + " " +
                                        joVehiculo.getString("modelo") + " " +
                                        joVehiculo.getString("aFabrica"));

                        item ++;
                        //Log.w("JALAME", "INFO: " + joVehiculo.toString());
                    }

                } catch (JSONException e) {
                    Log.w("JALAME", "ERROR: JSON " + e.getMessage());
                }
            }
        }

    }




    private void solicitarServicio() {
        FragmentManager fm =   this.getActivity().getSupportFragmentManager();
        SolicitaServicioFragment solicitServiceFragment = SolicitaServicioFragment.newInstance("Solicitar Servicio");

        solicitServiceFragment.setTargetFragment(MapFragment.this,300);

        solicitServiceFragment.show(fm, "fragment_solicit_service");
    }


    @Override
    public void onFinishDialog(String respueta) {
        Toast.makeText(getContext(), "Resputas:" + respueta, Toast.LENGTH_SHORT).show();
    }



}
