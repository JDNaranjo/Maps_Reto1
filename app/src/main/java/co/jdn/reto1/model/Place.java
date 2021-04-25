package co.jdn.reto1.model;

import android.media.Image;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Place {

    private String name;
    private LatLng position;
    private String address;
    private ArrayList<Uri> photo;

    public Place(String name, LatLng position, String address, ArrayList<Uri> photo) {
        this.name = name;
        this.position = position;
        this.address = address;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Uri> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<Uri> photo) {
        this.photo = photo;
    }
}
