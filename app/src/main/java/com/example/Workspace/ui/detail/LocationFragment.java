package com.example.Workspace.ui.detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Workspace.R;
import com.example.Workspace.network.Workspace;
import com.example.Workspace.utilities.GoogleApiUrl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private boolean mMapReady = false;
    private Workspace mCurrentWorkspace;
    public LocationFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        MapFragment mapFragment = (MapFragment) getActivity()
                .getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mCurrentWorkspace = getArguments().getParcelable(GoogleApiUrl.CURRENT_LOCATION_DATA_KEY);
        return  view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapReady = true;
        mGoogleMap = googleMap;
        String currentLocationString = getContext().getSharedPreferences(
                GoogleApiUrl.CURRENT_LOCATION_SHARED_PREFERENCE_KEY, 0)
                .getString(GoogleApiUrl.CURRENT_LOCATION_DATA_KEY, null);
        String currentPlace[] = currentLocationString.split(",");

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(new LatLng(Double.valueOf(currentPlace[0])
                        , Double.valueOf(currentPlace[1])))
                .zoom(13)
                .bearing(0)
                .tilt(0)
                .build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mGoogleMap.addMarker(new MarkerOptions()
                .position((new LatLng(Double
                        .valueOf(currentPlace[0]), Double.valueOf(currentPlace[1]))))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_location)));
        mGoogleMap.addMarker(new MarkerOptions()
                .position((new LatLng(
                        mCurrentWorkspace.getPlaceLatitude(), mCurrentWorkspace.getPlaceLongitude())))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_map)));

        PolylineOptions joinTwoPlace = new PolylineOptions();
        joinTwoPlace.geodesic(true).add(new LatLng(Double.valueOf(currentPlace[0])
                , Double.valueOf(currentPlace[1])))
                .add(new LatLng(mCurrentWorkspace.getPlaceLatitude(), mCurrentWorkspace.getPlaceLongitude()))
                .width(5)
                .color(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

        mGoogleMap.addPolyline(joinTwoPlace);
    }
}
