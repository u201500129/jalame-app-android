package pe.tp1.hdpeta.jalame;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double pLatitud = getIntent().getExtras().getDouble("pLatitud");
        double pLongitud = getIntent().getExtras().getDouble("pLongitud");

        // Add a marker in Sydney and move the camera
        LatLng lima = new LatLng(pLatitud, pLongitud);
        mMap.addMarker(new MarkerOptions().position(lima).title("Mi Ubicacion actual."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lima));
        //para hacer el zoom in
        LatLng latLng = new LatLng(pLatitud, pLongitud);
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
    }
}
