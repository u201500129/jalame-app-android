package pe.tp1.hdpeta.jalame.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
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
import java.util.ArrayList;
import java.util.Date;

import pe.tp1.hdpeta.jalame.Activity.Dialog.ConfirmRequestDialogActivity;
import pe.tp1.hdpeta.jalame.Bean.PersonBean;
import pe.tp1.hdpeta.jalame.Bean.VehiculoBean;
import pe.tp1.hdpeta.jalame.DataBase.DBHelper;
import pe.tp1.hdpeta.jalame.Interface.NearDriverList;
import pe.tp1.hdpeta.jalame.Interface.RestClient;
import pe.tp1.hdpeta.jalame.Network.HttpUrlHandler;
import pe.tp1.hdpeta.jalame.Network.RetrofitInstance;
import pe.tp1.hdpeta.jalame.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    private ArrayList<VehiculoBean> drivers;
    private  String idUser;
    private  String idCar;
    private String mLastUpdateTime;
    private boolean mLocationPermissionGranted;
    private boolean mRequestingLocationUpdates;
    private boolean bEnableSolicitud;
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
        idUser = String.valueOf(personBean.getCodPersona());

        Spinner spSedes = view.findViewById(R.id.spMapSedes);
        Switch swDriverVisible = view.findViewById(R.id.swMapDriver);

        if (personBean.getPerfil().toUpperCase().trim().equals("U")){
            ArrayList<String> listSedes = new ArrayList<String>();
            listSedes.add("UPC MONTERRICO");
            listSedes.add("UPC VILLA");
            listSedes.add("UPC SAN ISIDRO");
            listSedes.add("UPC SAN MIGULE");
            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listSedes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSedes.setAdapter(adapter);

            swDriverVisible.setVisibility(View.GONE);

        }else {
            getMyCarId();

            spSedes.setVisibility(View.INVISIBLE);
            swDriverVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if ( isChecked ){
                        updateVisible(idCar,"S");
                        Toast.makeText(getContext(),"Su vehiculo es VISIBLE a usuarios.", Toast.LENGTH_SHORT).show();
                    }else {
                        updateVisible(idCar,"N");
                        Toast.makeText(getContext(),"Su vehiculo está OCULTO a usuarios.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }




        getLocationPermission();

        if (mLocationPermissionGranted )  {

            //Construct a FusedLocationProviderClient.
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

            //Looper
            initLocationCallback();

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
                Toast.makeText(getContext(),"Necesita permisos para acceder al MAPA.", Toast.LENGTH_SHORT).show();
            }

        } catch (InterruptedException e) {
            System.out.println("THREAD ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        String position = marker.getTag().toString();
        solicitarServicioDialog(Integer.valueOf(Integer.valueOf(position)));
        return false;
    }




    public void centerCamera(double lat, double lng) {
        LatLng punto = new LatLng(lat, lng);
        try{

            if (mMap != null) {
                CameraPosition cameraPosition = new CameraPosition.Builder().target(punto).zoom(DEFAULT_ZOOM).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngUser, DEFAULT_ZOOM));
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
            Toast.makeText(getContext(),"Permiso denegado, Imposible acceder a la ubicacion", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Permiso denegado, Sin Ubicacion", Toast.LENGTH_LONG).show();
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


    private void updateMarkers(){
        if (mLastLocation != null){
            mMap.clear();

            latLngUser = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());

            centerCamera(mLastLocation.getLatitude(),mLastLocation.getLongitude());

            if(personBean.getPerfil().trim().toUpperCase().equals("U")){
                createMarker("U", personBean.getCodPersona(),
                        String.valueOf(latLngUser.latitude),
                        String.valueOf(latLngUser.longitude),
                        personBean.getNombre(), personBean.getApellido(),9, 100);

                //Lista vehiculos cercanos
                listaVehiculos();

            }else{
                createMarker("V", personBean.getCodPersona(),
                        String.valueOf(latLngUser.latitude),
                        String.valueOf(latLngUser.longitude),
                        personBean.getNombre(), personBean.getApellido(),9, 200);

                //Lista las solicitudes de Usuario
                listaUsuarios();

                //Actualiza su ubicacion en BD
                updateDrivePosition(idCar, latLngUser.latitude, latLngUser.longitude);
            }
        }
    }


    private  void listaUsuarios(){


        String path =  "servicio/list/driver/" + idUser ;

        HttpUrlHandler httpRest = new HttpUrlHandler("GET", path);

        String jsonString ;


        if (httpRest.readREST()){
            jsonString = httpRest.getJsonString();

            if (jsonString.length() > 10) {
                try {
                    //Creando Objeto JSON
                    JSONObject joServiceAll = new JSONObject(jsonString);
                    JSONArray joServiceArray = (JSONArray) joServiceAll.get("servicio");
                    int item = 0;

                    while (item < joServiceArray.length()){
                        JSONObject joService = joServiceArray.getJSONObject(item);

                        // Solo Pintamos las Solicitudes Pendientes
                        if (joService.getString("estadoServ").trim().toUpperCase().equals("S")){
                            createMarker( "U", joService.getInt("codServicio"),
                                    joService.getString("origenLat"),
                                    joService.getString("origenLon"),
                                    joService.getString("usuario"), "Destino: "+
                                    joService.getString("destinoDes") + "   " +
                                    joService.getString("formaPago") + " :  S/ " +
                                            String.valueOf(joService.getDouble("importe")), 0, item);
                        }

                        item ++;
                    }
                } catch (JSONException e) {
                    Log.w("JALAME: ", "ERROR: JSON " + e.getMessage());
                }
            }
        }

    }




    private  void listaVehiculos(){

        /*
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
                                        joVehiculo.getString("aFabrica"),0);
                        item ++;
                    }
                } catch (JSONException e) {
                    Log.w("JALAME", "ERROR: JSON " + e.getMessage());
                }
            }
        }*/

        RestClient restClient = RetrofitInstance.getRetrofitInstance().create(RestClient.class);
        Call<NearDriverList> call = restClient.nearDrivers(Integer.parseInt(idUser),
                String.valueOf(latLngUser.latitude),
                String.valueOf(latLngUser.longitude));

        call.enqueue(new Callback<NearDriverList>() {
            @Override
            public void onResponse(Call<NearDriverList> call, Response<NearDriverList> response) {
                drivers = response.body().getVehiculoArrayList();
                prepareDriverMarkers(drivers);
            }

            @Override
            public void onFailure(Call<NearDriverList> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void createMarker(String tipo, int id, String lat, String lng, String titulo, String descripcion, float index, int position){
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
            }else if (tipo.equals("P")){
                mOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.iclocation));
            }else {
                mOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }

            mOptions.zIndex(index);

            // adding marker
            if (mMap != null) {
                nMarker = mMap.addMarker(mOptions);
                nMarker.setTag(String.valueOf(position));
            }

        }catch (SecurityException e){
            System.out.println("Marker Error: " + e.getMessage());
        }
    }

    private void prepareDriverMarkers(ArrayList<VehiculoBean> drivers){
        int position = 0;
        for (VehiculoBean driver:drivers) {
            createMarker("V",
                    driver.getCodVehiculo(),
                    driver.getLatitud(),
                    driver.getLongitud(),
                    driver.getCalificacion() + "E | " + driver.getMatricula(),
                    driver.getMarca() + " " + driver.getModelo() + " " + driver.getaFabrica(),
                    0,
                    position);
            position ++;
            /*
            Marker marker;
            try{
                LatLng driverLatLng = new LatLng(Double.parseDouble(driver.getLatitud()), Double.parseDouble(driver.getLongitud()));
                MarkerOptions markerOptions = new MarkerOptions().
                        position(driverLatLng);
                markerOptions.title(driver.getMarca() + "" + driver.getModelo());
                markerOptions.snippet("Calificación: " + String.valueOf(driver.getCalificacion()) + "estrellas");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.iccarb));
                markerOptions.zIndex(0);
                Log.d("Marca", driver.getMarca());
                if (mMap != null){
                    marker = mMap.addMarker(markerOptions);
                    marker.setTag(driver.getCodVehiculo());
                }

            } catch (SecurityException e){
                Log.d("Marker Error: ", e.getMessage());
            }

            */
        }
    }

    private  void getMyCarId(){

        String path =  "vehiculo/mylist/" + idUser  ;

        HttpUrlHandler httpRest = new HttpUrlHandler("GET", path);

        String jsonString ;

        if (httpRest.readREST()){
            jsonString = httpRest.getJsonString();

            if (jsonString.length() > 10) {
                try {
                    //Creando Objeto JSON
                    JSONObject joVehiculoAll = new JSONObject(jsonString);
                    JSONArray joVehiculoArray = (JSONArray) joVehiculoAll.get("vehiculo");
                    if (joVehiculoArray.length() > -1 ){
                        JSONObject joVehiculo = joVehiculoArray.getJSONObject(0);
                        idCar = String.valueOf(joVehiculo.getInt("codVehiculo"));
                    }
                } catch (JSONException e) {
                    Log.w("JALAME", "JSON ERROR: getMyCarId " + e.getMessage());
                }
            }
        }
    }


    private void updateDrivePosition(String idDriver, double lat, double lng){

        String path =  "vehiculo/location/" + String.valueOf(idDriver).trim() +"/" +  String.valueOf(lat).trim() + "/" + String.valueOf(lng).trim();

        HttpUrlHandler httpRest = new HttpUrlHandler("PUT", path);

        String jsonString ;

        if (httpRest.readREST()){
            jsonString = httpRest.getJsonString();
            System.out.println("REST READ json: " + jsonString);
        }

    }


    private void updateVisible(String idDriver, String visible){

        String path =  "vehiculo/visible/" + idDriver +"/" + visible;

        HttpUrlHandler httpRest = new HttpUrlHandler("PUT", path);

        String jsonString ;

        if (httpRest.readREST()){
            jsonString = httpRest.getJsonString();
            System.out.println("REST READ json: " + jsonString);
        }

    }


    private void solicitarServicioDialog(int markerPosition) {

        /*
        FragmentManager fm =   this.getActivity().getSupportFragmentManager();
        SolicitaServicioFragment solicitServiceFragment = SolicitaServicioFragment.newInstance("Solicitar Servicio");
        solicitServiceFragment.setTargetFragment(MapFragment.this,300);
        solicitServiceFragment.show(fm, "fragment_solicit_service");
        */
        Log.d("Marker position: ", String.valueOf(markerPosition));

        if (markerPosition < 100) {
            VehiculoBean driver = drivers.get(markerPosition);
            Intent solicitudActivity = new Intent(getContext(), ConfirmRequestDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("codVehiculo", driver.getCodVehiculo());
            bundle.putInt("codPersona", driver.getCodPersona());
            bundle.putString("marca",driver.getMarca());
            bundle.putString("modelo",driver.getModelo());
            bundle.putString("matricula",driver.getMatricula());
            bundle.putInt("asientosDisponibles",driver.getAsientosDisp());
            bundle.putInt("distancia",driver.getDistancia());
            bundle.putString("aFabrica",driver.getaFabrica());
            solicitudActivity.putExtras(bundle);
            getActivity().startActivity(solicitudActivity);
        }

    }


    @Override
    public void onFinishDialog(String respueta) {
        Toast.makeText(getContext(), "Resputas:" + respueta, Toast.LENGTH_SHORT).show();
    }



}
