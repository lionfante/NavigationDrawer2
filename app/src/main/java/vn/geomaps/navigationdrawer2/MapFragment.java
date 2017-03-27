package vn.geomaps.navigationdrawer2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
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

        DownloadBoreholes downloadBoreholes = new DownloadBoreholes();
        downloadBoreholes.execute(url);
        try {
            listMarker = downloadBoreholes.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("CountMarker", String.valueOf(listMarker.size()));
        for(int i = 0; i<5;i++){
                Log.d("Thongtinborehole: ",listMarker.get(i).getName());

        }


        map = googleMap;
        // For showing a move to my location button
        //map.setMyLocationEnabled(true);

        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


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
