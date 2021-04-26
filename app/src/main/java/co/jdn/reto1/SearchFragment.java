package co.jdn.reto1;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import co.jdn.reto1.model.Place;
import co.jdn.reto1.model.PlacesAdapter;

public class SearchFragment extends Fragment {

    private RecyclerView placesViewList;
    private LinearLayoutManager layoutManager;
    private PlacesAdapter adapter;

    SharedPreferences preferences;
    private Context context;

    private EditText nameSearch;

    private Set<String> names;
    private Set<String> positions;
    private Set<String> addresses;
    private Set<String> uris;
    private Set<String> uriQuantity;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        context = this.getContext();
        preferences = context.getSharedPreferences("Reto1", Context.MODE_PRIVATE);

        placesViewList = root.findViewById(R.id.placesViewList);
        nameSearch = root.findViewById(R.id.nameSearch);

        placesViewList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        placesViewList.setLayoutManager(layoutManager);

        adapter = new PlacesAdapter();
        placesViewList.setAdapter(adapter);

        names = preferences.getStringSet("names", new ArraySet<>());
        positions = preferences.getStringSet("positions", new ArraySet<>());
        addresses = preferences.getStringSet("addresses", new ArraySet<>());
        uris = preferences.getStringSet("uris", new ArraySet<>());
        uriQuantity = preferences.getStringSet("uriQuantity", new ArraySet<>());

        if (!names.isEmpty()){

            String[] namesArray = Arrays.copyOf(names.toArray(), names.toArray().length, String[].class);
            String[] positionsArray = Arrays.copyOf(positions.toArray(), positions.toArray().length, String[].class);
            String[] addressArray = Arrays.copyOf(addresses.toArray(), addresses.toArray().length, String[].class);
            String[] urisArray = Arrays.copyOf(uris.toArray(), uris.toArray().length, String[].class);
            String[] uriQuantityArray = Arrays.copyOf(uriQuantity.toArray(), uriQuantity.toArray().length, String[].class);
            int start = 0;

            for (int i=0; i<namesArray.length; i++){

                String name = namesArray[i];
                System.out.println(namesArray.length);
                String[] pos = positionsArray[i].split(",");
                System.out.println(positionsArray.length);
                double lat = Double.parseDouble(pos[0]);
                double lng = Double.parseDouble(pos[1]);
                LatLng latLng = new LatLng(lat, lng);
                String address = addressArray[i];
                System.out.println(addressArray.length);
                System.out.println(uriQuantityArray.length);
                int quantity = Integer.parseInt(uriQuantityArray[i]);
                ArrayList<Uri> uris = new ArrayList<>();

                for (int j=start; j<quantity; j++){
                    uris.add(Uri.parse(urisArray[j]));
                    start=j;
                }

                Place newPlace = new Place(name, latLng, address, uris);
                adapter.addPlace(newPlace);

            }
        }

        return root;
    }

}