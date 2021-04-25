package co.jdn.reto1.model;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.jdn.reto1.R;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoView> {

    private ArrayList<Uri> photos;

    public PhotoAdapter(){
        photos = new ArrayList<>();
    }

    public void addPhoto(Uri uri){
        photos.add(uri);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View column = inflater.inflate(R.layout.contact_column, null);
        ConstraintLayout columnRoot = (ConstraintLayout) column;
        PhotoView photoView = new PhotoView(columnRoot);

        return photoView;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoView holder, int position) {
        holder.getPhoto().setImageURI(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


}
