package co.jdn.reto1.model;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import co.jdn.reto1.R;

public class PlaceView extends RecyclerView.ViewHolder{

    private ConstraintLayout root;
    private TextView name;
    private TextView rating;
    private ImageView star;
    private ImageView photo;
    private ImageButton visibility;

    public PlaceView(ConstraintLayout root) {
        super(root);
        this.root = root;
        name = root.findViewById(R.id.nameLbl);
        rating = root.findViewById(R.id.ratingLbl);
        star = root.findViewById(R.id.imageView2);
        photo = root.findViewById(R.id.photoView);
        visibility = root.findViewById(R.id.visibilityBtn);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public TextView getName() {
        return name;
    }

    public TextView getRating() {
        return rating;
    }

    public ImageView getStar() {
        return star;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public ImageButton getVisibility() {
        return visibility;
    }
}
