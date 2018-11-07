package com.example.dawidsopata.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.example.dawidsopata.myapplication.dto.ClientDto;
import com.example.dawidsopata.myapplication.dto.LocationDto;
import com.example.dawidsopata.myapplication.dto.ServicePointDto;
import com.example.dawidsopata.myapplication.dto.TargetLocationsDto;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    static final String REST_SERVICE_URL = "http://10.0.2.2:8080/";
     //static final String REST_SERVICE_URL = "http://192.168.8.100:8080/";

    //Local address for virtual android machine: http://10.0.2.2:8080/
    static final int DEFAULT_ZOOM = 30;

    GoogleMap mMap;
    Retrofit.Builder builder;
    Retrofit retrofit;
    Clientx client;
    Button clr;
    Button getClosestDirections;

    List<Marker> markers;
    List<Polyline> polylines = new ArrayList<>();

    Location deviceLocation;
    FusedLocationProviderClient mFusedLocationProviderClient;
    Boolean mLocationPermissionGranted = true;
    Timer timerObj = new Timer();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            checkPermission();
        }
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //tracker = new LocationTracker(this);
        clr = (Button) findViewById(R.id.button_reload_markers);
        getClosestDirections = (Button) findViewById(R.id.button_get_closest_directions);
        //button refresh
        clr.setOnClickListener(buttonClearOnClickListener);
        getClosestDirections.setOnClickListener(buttonGetClosestDirections);
        prepareRetrofitBuilder();
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

    public void enableMapLocation() {
        if (mLocationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);

        }
    }

    public void prepareRetrofitBuilder() {
        builder = new Retrofit.Builder()
                .baseUrl(REST_SERVICE_URL)
                .addConverterFactory((GsonConverterFactory.create()));
        retrofit = builder.build();
        client = retrofit.create(Clientx.class);

        fitMarkersOnMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMapLocation();

        getDeviceLocationAndSendCoordinates();
    }

    TimerTask timerTaskObj = new TimerTask() {
        public void run() {
            reloadBestPath();
        }
    };

    private View.OnClickListener buttonClearOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (clr.getText().toString().toLowerCase().equals("start course")) {
                startAlgorithm();
                clr.setText("load Data");
            } else if(clr.getText().toString().toLowerCase().equals("load Data")) {
                clr.setVisibility(View.GONE);
            } else {
                timerObj.schedule(timerTaskObj, 0, 2000);
            }

            System.out.println("@@@BUTTON CLEAR CLICKED...");
        }
    };

    private void startAlgorithm() {
        Call<ResponseBody> call = client.startAlgorithm(1L);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("@@ ON FAILURE: " + t);
            }
        });
    }



    private void reloadBestPath() {
        getBestPath();
    }

    private View.OnClickListener buttonGetClosestDirections = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDirectionsToGoogleMap();
        }
    };

    private void getDirectionsToGoogleMap() {
        if(markers != null) {
            LatLng closestPoint = markers.get(0).getPosition();
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+closestPoint.longitude+","+closestPoint.latitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }


    public void getClientAndServicesLocations() {
        markers = new ArrayList<>();
        System.out.println("@@@ ENTER getClientAndServicesLocations");
        Call<TargetLocationsDto> call = client.getClientData(1L);
        call.enqueue(new Callback<TargetLocationsDto>() {
            @Override
            public void onResponse(Call<TargetLocationsDto> call, Response<TargetLocationsDto> response) {
                if(markers != null) markers.clear();

                System.out.println("@@@ onResponse getClientAndServicesLocations Begin");
                TargetLocationsDto targetLocationsDto = response.body();

                for (ServicePointDto servicePointDto : targetLocationsDto.getClientServicePoints()) {
                    double cordX = servicePointDto.getLocationDto().getCordinateX();
                    double cordY = servicePointDto.getLocationDto().getCordinateY();
                    LatLng latLng = new LatLng(cordX, cordY);
                    Marker m = mMap.addMarker(new MarkerOptions().position(latLng));
                    markers.add(m);
                }

                ClientDto clientDto = targetLocationsDto.getClientData();
                double clientCordX = clientDto.getClientLocation().getCordinateX();
                double clientCordY = clientDto.getClientLocation().getCordinateY();

                LatLng clientLatLng = new LatLng(clientCordX, clientCordY);
                Marker m = mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(clientLatLng));
                m.setZIndex(1);
                markers.add(m);
            }

            @Override
            public void onFailure(Call<TargetLocationsDto> call, Throwable t) {
                System.out.println("@@ ON FAILURE: " + t);
            }
        });
    }

    public void getBestPath() {
        System.out.println("@@@ ENTER getBestPath");
        Call<List<LocationDto>> call = client.getMostRecentResults();
        call.enqueue(new Callback<List<LocationDto>>() {
            @Override
            public void onResponse(Call<List<LocationDto>> call, Response<List<LocationDto>> response) {
                List<LocationDto> targetLocationsDto = response.body();
                int i = 0;

                clearMapAndReloadMarkers();

                LocationDto previousLocationDto = null;
                for (LocationDto servicePointDto : targetLocationsDto) {
                    double cordX = servicePointDto.getCordinateX();
                    double cordY = servicePointDto.getCordinateY();

                    if( i > 0 ) {
                        LatLng[] points = new LatLng[2];
                        points[0] = new LatLng(cordX, cordY);
                        points[1] = new LatLng(previousLocationDto.getCordinateX(),
                                previousLocationDto.getCordinateY());

                        polylines.add(mMap.addPolyline(new PolylineOptions()
                                .add(points)
                                .color(Color.parseColor("#009688"))
                                .width(6)));
                    }
                    previousLocationDto = servicePointDto;
                    i++;
                }
            }

            private void clearMapAndReloadMarkers() {
                if(polylines != null) {
                    for(Polyline polyline : polylines) {
                        polyline.remove();
                    }
                    polylines.clear();
                }
            }

            @Override
            public void onFailure(Call<List<LocationDto>> call, Throwable t) {
                //Toast.makeText(MapsActivity.this, "Error: ", Toast.LENGTH_SHORT).show();
                System.out.println("@@@ ON FAILUREEE (get best path): " + t);
            }
        });
    }


    private void fitMarkersOnMap() {
        if (markers == null || markers.size()== 0) {
            return;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }

        if(deviceLocation != null)
            builder.include(new LatLng(deviceLocation.getLongitude(), deviceLocation.getLatitude()));

        final LatLngBounds bounds = builder.build();
        final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        mMap.animateCamera(cameraUpdate);
    }

    private void locationTracker() {
      //  tracker.setOnLocationChanged(new OnLocationChanged() {
//            @Override
//            public void OnChange(Location location) {
//                if(location != null)
//                    System.out.println("@@@My location changed: " + location.getLatitude() + ", " + location.getLongitude());
//            }
//        });
    }

    private void getDeviceLocationAndSendCoordinates() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            final Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()) {
                        System.out.println("Found location;!!!");
                        Location currentLocation = (Location) task.getResult();
                        deviceLocation = currentLocation;
                        //moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                        //        DEFAULT_ZOOM);
                        sendCoordinates();
                    } else {
                        System.out.println("unable ot get location;!!!");
                    }
                }
            });
        } catch (SecurityException e) {
            System.out.println("@@GPS Security Exception: " + e);
        }
    }

    private void sendCoordinates() {
        if(deviceLocation != null) {
            System.out.println("@@@ Sending Device location...");
            Call<Boolean> call = client.sendCoordinates(deviceLocation.getLongitude(), deviceLocation.getLatitude());
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Boolean answer= response.body();
                    getClientAndServicesLocations();
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    System.out.println("@@@ ON FAILUREEE (get best path): " + t);
                }
            });
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        System.out.println("moved camera to longtitude:"+ latLng.longitude +" latitude: " + latLng.latitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

}
