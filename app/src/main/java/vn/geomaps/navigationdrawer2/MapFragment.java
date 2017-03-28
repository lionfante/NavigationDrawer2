package vn.geomaps.navigationdrawer2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.JsonToken;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lionf_000 on 23-Mar-17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static View view;
    MapView mapView;
    GoogleMap map;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_map,container,false);

        if(view != null){
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){
                parent.removeView(view);
            }
        }
        try{
            view = inflater.inflate(R.layout.fragment_map,container,false);
            mapView = (MapView) view.findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            mapView.onResume();

            MapsInitializer.initialize(getActivity().getApplicationContext());
            mapView.getMapAsync(MapFragment.this);


        }catch (InflateException e){

        }catch (Exception e){

        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String url = "https://geomaps.vn/GeoMaps/GetAllMarkers";
        String dataJSON = "";
        List<Marker> listMarker = null;
        String fileCache = getActivity().getCacheDir().getPath() + "/geomapsMarkersCache.txt";

        //check file cache
        //...
        File checkFileCache = new File(fileCache);
        StoreCache storeCache = new StoreCache(fileCache);
        if(checkFileCache.exists()){
            String s = storeCache.readFileCache();
            ReadBoreholes readBoreholes = new ReadBoreholes();
            try {
                listMarker = readBoreholes.readBoreholeArray(s);
                Log.d("LoadCache","Load vô đây");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            DownloadBoreholes downloadBoreholes = new DownloadBoreholes();
            downloadBoreholes.execute(url, fileCache);
            try {
                listMarker = downloadBoreholes.get();
                Log.d("LoadNew","Load new từ URL");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        map = googleMap;
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(),R.raw.style_map));
        UiSettings mapUiSettings = map.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(true);
        LatLng defaultLocation = new LatLng(10.7855247,106.7081242);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(defaultLocation).zoom(16).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        // For showing a move to my location button
        //map.setMyLocationEnabled(true);
        Marker marker;
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.point_icon24x30);
        for(int i=0;i<100;i++){
            marker = listMarker.get(i);
            MarkerOptions options = new MarkerOptions();
            options.position(new LatLng(marker.getLatitude(),marker.getLongtitude()));
            options.title(marker.getName());
            options.snippet(String.valueOf(marker.getLatitude()) + "," +String.valueOf(marker.getLongtitude()));
            options.icon(icon);
            map.addMarker(options);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
