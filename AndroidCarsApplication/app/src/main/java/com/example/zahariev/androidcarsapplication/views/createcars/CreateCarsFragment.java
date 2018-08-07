package com.example.zahariev.androidcarsapplication.views.createcars;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zahariev.androidcarsapplication.R;
import com.example.zahariev.androidcarsapplication.models.Car;
import com.example.zahariev.androidcarsapplication.repositories.FirebaseRepository;
import com.example.zahariev.androidcarsapplication.repositories.base.Repository;
import com.example.zahariev.androidcarsapplication.views.listcars.ListCarsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCarsFragment extends Fragment {
    private Repository<Car> mCarsRepository;

    public CreateCarsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_cars, container, false);

        mCarsRepository = new FirebaseRepository<>(Car.class);

        EditText carName = root.findViewById(R.id.et_cars_name);
        EditText carModel = root.findViewById(R.id.et_cars_model);
//        EditText carDescription = root.findViewById(R.id.et_cars_description);

        Button button = root.findViewById(R.id.btn_create);
        button.setOnClickListener(click -> {
            String name = carName.getText().toString();
            String model = carModel.getText().toString();
            Car car = new Car(name, model);
            mCarsRepository.add(car);

            Toast.makeText(getContext(),
                    String.format("Car %s - %s created!", name, model),
                    Toast.LENGTH_SHORT)
                    .show();

            Intent intent = new Intent(getContext(), ListCarsActivity.class);
            startActivity(intent);
        });

        return root;
    }

    public static CreateCarsFragment newInstance() {
        return new CreateCarsFragment();
    }
}
