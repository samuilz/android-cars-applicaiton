package com.example.zahariev.androidcarsapplication.views.listcars;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zahariev.androidcarsapplication.R;
import com.example.zahariev.androidcarsapplication.models.Car;
import com.example.zahariev.androidcarsapplication.repositories.FirebaseRepository;
import com.example.zahariev.androidcarsapplication.repositories.base.Repository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListCarsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayAdapter<String> mCarsAdapter;
    private Repository<Car> mCarsRepository;
    private FirebaseFirestore mDb;

    public ListCarsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_cars, container, false);

        mDb = FirebaseFirestore.getInstance();

        mCarsAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1);
        ListView listView = root.findViewById(R.id.lv_cars);

        listView.setAdapter(mCarsAdapter);
        listView.setOnItemClickListener(this);

//        mDb.collection("cars")
//                .get()
//                .addOnCompleteListener(task -> {
//                    List<Car> cars = task.getResult().toObjects(Car.class);
//                    for (Car car : cars) {
//                        mCarsAdapter.add(String.format("%s -> %s", car.name, car.model));
//                    }
//                });

        mCarsRepository = new FirebaseRepository<>(Car.class);

        mCarsRepository.getAll(cars -> {
            for (Car currentCar : cars) {
                mCarsAdapter.add(String.format("%s - %s", currentCar.name, currentCar.model));
            }
        });

        return root;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

    }

    public static ListCarsFragment newInstance() {
        return new ListCarsFragment();
    }
}
