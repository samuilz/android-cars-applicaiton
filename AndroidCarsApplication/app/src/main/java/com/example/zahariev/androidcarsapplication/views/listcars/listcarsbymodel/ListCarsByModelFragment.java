package com.example.zahariev.androidcarsapplication.views.listcars.listcarsbymodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zahariev.androidcarsapplication.R;
import com.example.zahariev.androidcarsapplication.models.Car;
import com.example.zahariev.androidcarsapplication.repositories.FirebaseRepository;
import com.example.zahariev.androidcarsapplication.repositories.base.Repository;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListCarsByModelFragment extends Fragment {
    private StorageReference mFirebaseStorage;
    private ArrayAdapter<String> mCarsAdapter;
    private Repository<Car> mCarsRepository;
    private FirebaseFirestore mDb;

    public ListCarsByModelFragment() {
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

        mCarsAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1);
        ListView listView = root.findViewById(R.id.lv_cars);

        listView.setAdapter(mCarsAdapter);
//        listView.setOnItemClickListener(this);

        mCarsRepository = new FirebaseRepository<>(Car.class);

        mCarsRepository.getAll(cars -> {
            for (Car car : cars) {
                if (car.brand.equals(carBrand)) {
                    mCarsAdapter.add(String.format("%s - %s", car.brand, car.model));
                }
            }
        });

        return root;
    }

    public static ListCarsByModelFragment newInstance() {
        return new ListCarsByModelFragment();
    }
}
