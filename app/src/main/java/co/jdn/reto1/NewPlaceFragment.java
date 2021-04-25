package co.jdn.reto1;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Binder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class NewPlaceFragment extends Fragment {

    private EditText nameTxt;
    private ImageButton locationBtn;
    private ImageButton addBtn;
    private Button registerBtn;
    private TextView addressTxt;

    private Context context;
    SharedPreferences preferences;

    Geocoder geocoder;

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

}