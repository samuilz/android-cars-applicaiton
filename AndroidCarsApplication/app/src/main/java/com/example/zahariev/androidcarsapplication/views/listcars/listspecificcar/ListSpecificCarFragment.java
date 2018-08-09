package com.example.zahariev.androidcarsapplication.views.listcars.listspecificcar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zahariev.androidcarsapplication.R;
import com.example.zahariev.androidcarsapplication.models.Car;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListSpecificCarFragment extends Fragment {

    private StorageReference mFirebaseStorage;

    public ListSpecificCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_specific_car, container, false);

        Intent specificCar = getActivity().getIntent();
        Car car = (Car) specificCar.getSerializableExtra("CAR_OBJECT");

        TextView title = root.findViewById(R.id.tv_title);
        title.setText(String.format("%s %s", car.brand, car.model));

        ImageView wallpaper = root.findViewById(R.id.iv_car_specific);

        mFirebaseStorage = FirebaseStorage.getInstance().getReference();

        StorageReference carsRef = mFirebaseStorage
                .child(String.format("models/%s.jpg", car.model.toUpperCase()));

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(carsRef)
                .into(wallpaper);

        return root;
    }

    public static ListSpecificCarFragment newInstance() {
        return new ListSpecificCarFragment();
    }
}
