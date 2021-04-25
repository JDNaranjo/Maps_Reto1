package co.jdn.reto1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import co.jdn.reto1.model.Place;

public class MapsFragment extends Fragment {

    private GoogleMap myMap;
    private LocationManager manager;
    private Marker myMarker;

    View popupView;
    PopupWindow popupWindow;
    Button button;

    SharedPreferences preferences;
    private Context context;

    private ArrayList<Place> places;

    View root;

    public MapsFragment() {
    }

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            myMap = googleMap;

            myMap.setMyLocationEnabled(true);

            LatLng pos = new LatLng(3.435412, -76.520313);
            myMarker = myMap.addMarker(new MarkerOptions().position(pos));
            myMarker.setVisible(false);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 12.0f));

            myMap.setOnMapLongClickListener(latLng -> {
                myMarker.setPosition(latLng);
                myMarker.setVisible(true);
                button.setVisibility(View.VISIBLE);
                showPopup(root);
                popupWindow.update(800, 250);
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_maps, container, false);
        context = this.getContext();

        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 11);

        popupView = getActivity().getLayoutInflater().inflate(R.layout.select_place, null);
        popupWindow = new PopupWindow(popupView, 800, 150);
        preferences = context.getSharedPreferences("Reto1", Context.MODE_PRIVATE);

        button = popupView.findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);

        if(preferences.getString("beforeFragment", "Maps").equals("NewPlace")){
            showPopup(root);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.myMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    public void showPopup(View anchorView) {

        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,150, 1750);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putFloat("Lat", (float) myMarker.getPosition().latitude)
                        .putFloat("Lng", (float) myMarker.getPosition().longitude)
                        .putBoolean("show", true)
                        .apply();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new NewPlaceFragment());
                transaction.commit();
            }
        });

    }

    @Override
    public void onStop() {
        preferences.edit().putString("beforeFragment", "")
                            .apply();
        if (popupWindow.isShowing())
            popupWindow.dismiss();
        super.onStop();
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }
}