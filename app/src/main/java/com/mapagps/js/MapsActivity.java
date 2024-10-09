package com.mapagps.js;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import android.location.Location;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final int LOCATION_REQUEST_CODE = 101;
    private Marker currentLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); // Asegúrate de que el layout se llame activity_maps.xml

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtener el SupportMapFragment y ser notificado cuando el mapa esté listo.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Verificar permisos de ubicación antes de acceder a la ubicación
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; // Si no hay permisos, salir del método
        }

        // Habilitar la capa de ubicación
        mMap.setMyLocationEnabled(true);

        // Llamar a getCurrentLocation para obtener la ubicación actual
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; // Si no hay permisos, salir del método
        }

        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(this, new OnSuccessListener<Location>() {


            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                super. onRequestPermissionsResult(requestCode, permissions, grantResults);
                if (requestCode == LOCATION_REQUEST_CODE) {
                    if (grantResults. length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getCurrentLocation();
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    // Eliminar el marcador anterior si existe
                    if (currentLocationMarker != null) {
                        currentLocationMarker.remove();
                    }

                    // Añadir un nuevo marcador en la ubicación actual
                    currentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Estás aquí"));

                    // Mover la cámara a la ubicación actual
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                }
            }
        });
    }
}