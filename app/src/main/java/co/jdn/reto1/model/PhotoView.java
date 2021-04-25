package co.jdn.reto1.model;

import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import co.jdn.reto1.R;

public class PhotoView extends RecyclerView.ViewHolder {

    private ConstraintLayout root;


    private ImageView photo;

    public PhotoView(ConstraintLayout root) {
        super(root);
        this.root = root;
        photo = root.findViewById(R.id.photoView);
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

}
