package co.jdn.reto1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import co.jdn.reto1.model.PlacesAdapter;

public class SearchFragment extends Fragment {

    private RecyclerView placesViewList;
    private LinearLayoutManager layoutManager;
    private PlacesAdapter adapter;

    SharedPreferences preferences;
    private Context context;

    private EditText nameSearch;

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

        return root;
    }

    @Override
    public void onStop() {

        super.onStop();
    }
}