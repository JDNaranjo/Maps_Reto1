package co.jdn.reto1.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import co.jdn.reto1.R;

public class PlacesAdapter extends RecyclerView.Adapter<PlaceView> {

    private ArrayList<Place> places;

    public PlacesAdapter() {
        places = new ArrayList<>();
    }

    public void addPlace(Place place){
        places.add(place);
    }

    @NonNull
    @Override
    public PlaceView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.placerow, null);

        ConstraintLayout rowroot = (ConstraintLayout) row;
        PlaceView placeView = new PlaceView(rowroot);

        return placeView;
    }

    //Change the info from the RecyclerView rows
    @Override
    public void onBindViewHolder(@NonNull PlaceView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

}
