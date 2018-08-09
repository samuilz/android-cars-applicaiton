package com.example.zahariev.androidcarsapplication.views.listcars.listcarsbymodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.zahariev.androidcarsapplication.R;
import com.example.zahariev.androidcarsapplication.models.Car;
import com.example.zahariev.androidcarsapplication.repositories.FirebaseRepository;
import com.example.zahariev.androidcarsapplication.repositories.base.Repository;
import com.example.zahariev.androidcarsapplication.views.customviews.CustomView;
import com.example.zahariev.androidcarsapplication.views.listcars.listspecificcar.ListSpecificCarActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListCarsByModelFragment extends Fragment implements AdapterView.OnItemClickListener {
    private StorageReference mFirebaseStorage;
    private ArrayAdapter<String> mCarsAdapter;
    private Repository<Car> mCarsRepository;
    private FirebaseFirestore mDb;
    private List<Car> mCars;

    public ListCarsByModelFragment() {
        mCars = new ArrayList<>();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_cars_by_model, container, false);

        mDb = FirebaseFirestore.getInstance();

        Intent intentFromListCarsFragment = getActivity().getIntent();
        String carBrand = intentFromListCarsFragment
                .getStringExtra("CAR_BRAND");

        //        setSupportActionBar(mToolBar);

        mFirebaseStorage = FirebaseStorage.getInstance().getReference();

        StorageReference carsRef = mFirebaseStorage
                .child(String.format("brands/%s.png", carBrand.toLowerCase()));

        ImageView wallpaper = root.findViewById(R.id.cv_car_brand);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(carsRef)
                .into(wallpaper);

        mCarsAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1);
        ListView listView = root.findViewById(R.id.lv_cars);

        listView.setAdapter(mCarsAdapter);
        listView.setOnItemClickListener(this);

        mCarsRepository = new FirebaseRepository<>(Car.class);

        mCarsRepository.getAll(cars -> {
            for (Car car : cars) {
                if (car.brand.equals(carBrand)) {
                    mCarsAdapter.add(car.model);
                    mCars.add(car);
                }
            }
        });

        return root;
    }

    public static ListCarsByModelFragment newInstance() {
        return new ListCarsByModelFragment();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent specificCar = new Intent(
                getContext(),
                ListSpecificCarActivity.class
        );

        Car car = mCars.get(position);
        specificCar.putExtra("CAR_OBJECT", car);
        startActivity(specificCar);
    }
}
