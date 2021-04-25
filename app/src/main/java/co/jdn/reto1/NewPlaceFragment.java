package co.jdn.reto1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import co.jdn.reto1.model.PhotoAdapter;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class NewPlaceFragment extends Fragment {

    private EditText nameTxt;
    private ImageButton locationBtn;
    private ImageButton addBtn;
    private Button registerBtn;
    private TextView addressTxt;

    private RecyclerView photosViewList;
    private LinearLayoutManager layoutManager;
    private PhotoAdapter photoAdapter;

    private ImageView photoView;

    private Context context;
    SharedPreferences preferences;

    Geocoder geocoder;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    public NewPlaceFragment() {
        // Required empty public constructor
    }

    public static NewPlaceFragment newInstance() {
        NewPlaceFragment fragment = new NewPlaceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_place, container, false);

        context = this.getContext();
        preferences = context.getSharedPreferences("Reto1", Context.MODE_PRIVATE);

        geocoder = new Geocoder(context);

        nameTxt = root.findViewById(R.id.nameTxt);
        locationBtn = root.findViewById(R.id.locationBtn);
        addBtn = root.findViewById(R.id.addBtn);
        registerBtn = root.findViewById(R.id.registerBtn);
        addressTxt = root.findViewById(R.id.addressTxt);
        photosViewList = root.findViewById(R.id.photosViewList);
        photoView = root.findViewById(R.id.photoView);

        photosViewList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        photosViewList.setLayoutManager(layoutManager);

        photoAdapter = new PhotoAdapter();
        photosViewList.setAdapter(photoAdapter);

        if (preferences.getBoolean("show",false) == true){
            nameTxt.setText(preferences.getString("Name", ""));
            addressTxt.setVisibility(View.VISIBLE);
            try {
                calculateAddress();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        preferences.edit().putBoolean("show", false).apply();

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putString("beforeFragment", "NewPlace")
                                    .putString("Name", nameTxt.getText().toString())
                                    .apply();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new MapsFragment());
                transaction.commit();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }else{
                        pickImageFromGallery();
                    }
                }else{
                    pickImageFromGallery();
                }
            }
        });

        return root;
    }


    private void calculateAddress() throws IOException {
        double lat = (double) preferences.getFloat("Lat", 0);
        double lng = (double) preferences.getFloat("Lng", 0);
        List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
        if (!addresses.isEmpty()){
            addressTxt.setText(addresses.get(0).getAddressLine(0));
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }else{
                    Toast.makeText(context, "Permissions denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == getActivity().RESULT_OK && requestCode == IMAGE_PICK_CODE){
            photoAdapter.addPhoto(data.getData());
        }
    }
}