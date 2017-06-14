package cz.folprechtova.hides.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cz.folprechtova.hides.R;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private MapView mapView;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

         mapView = (MapView) findViewById(R.id.mapView);

         mapView.onCreate(savedInstanceState);
         mapView.getMapAsync(new OnMapReadyCallback() {
             @Override
             public void onMapReady(GoogleMap googleMap) {
                 MapsActivity.this.mMap = googleMap;
             }
         });
     }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }
}
