package com.example.zahariev.androidcarsapplication.views.createcars;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.zahariev.androidcarsapplication.views.listcars.listcarsbybrand.ListCarsByBrandActivity;
import com.example.zahariev.androidcarsapplication.views.listcars.listspecificcar.ListSpecificCarActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCarsFragment extends Fragment {
    private static final int PICK_IMAGE = 100;
    private static final String DATABASE_NAME = "brands";
    private Repository<Car> mCarsRepository;
    private Repository<Car> mCarsBrandRepository;
    private Button mButton;
    private EditText mCarBrand;
    private EditText mCarModel;
    private EditText mCarDescription;
    private StorageReference mFirebaseStorage;
    private boolean mIsBrandExist;
    private boolean mIsCarExist;

    public CreateCarsFragment() {
        mIsBrandExist = false;
        mIsCarExist = false;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_cars, container, false);

        mCarsRepository = new FirebaseRepository<>(Car.class);
        mCarsBrandRepository = new FirebaseRepository<>(Car.class, DATABASE_NAME);
        mFirebaseStorage = FirebaseStorage.getInstance().getReference();

        mCarBrand = root.findViewById(R.id.et_cars_brand);
        mCarModel = root.findViewById(R.id.et_cars_model);
        mCarDescription = root.findViewById(R.id.et_cars_description);
        mButton = root.findViewById(R.id.btn_create);
        Button imageButton = root.findViewById(R.id.btn_image);

        mButton.setOnClickListener(click -> checkFields(
                mCarBrand.getText().toString(),
                mCarModel.getText().toString(),
                mCarDescription.getText().toString())
        );

        imageButton.setOnClickListener(click -> {
            Intent gallery = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);

            startActivityForResult(gallery, PICK_IMAGE);
        });

        return root;
    }

    private void intentHandle() {
        String carBrand = mCarBrand.getText().toString().trim();
        String carModel = mCarModel.getText().toString().trim();
        String carDescription = mCarDescription.getText().toString().trim();

        mButton.setOnClickListener(click -> {
            if (!checkFields(carBrand, carModel, carDescription)) {
                return;
            }

            Car car = new Car(carBrand, carModel, carDescription);

            isExist(car, mCarsRepository);

            Intent intent = new Intent(getContext(), ListCarsByBrandActivity.class);
            startActivity(intent);
        });
    }

    private void isExist(Car car, Repository<Car> repository) {
        repository.getAll(carList -> {
            for (Car currentCar : carList) {
                if (currentCar.brand.compareTo(car.brand) == 0) {
                    mIsBrandExist = true;

                    if (currentCar.model.compareTo(car.model) == 0) {
                        mIsCarExist = true;
                    }
                }
            }

            if (mIsCarExist) {
                makeToast("This car already exist!");
                return;
            } else {
                mCarsRepository.add(car);
                makeToast(String.format("Car %s - %s created!", car.brand, car.model));
            }

            if (!mIsBrandExist) {
                mCarsBrandRepository.add(car);
            }
        });
    }

    private void makeToast(String message) {
        Toast.makeText(getContext(),
                message,
                Toast.LENGTH_SHORT)
                .show();
    }

    private boolean checkFields(String... args) {
        if (args[0].equals("")) {
            makeToast("Please enter a brand!");
        } else if (args[1].equals("")) {
            makeToast("Please enter a model!");
        } else if (args[2].equals("")) {
            makeToast("Please enter a description!");
        } else {
            return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            if (selectedImage == null) {
                makeToast("Please upload image!");
            } else {
                saveImageToFirebaseStorage(selectedImage);
                intentHandle();
            }
        }
    }

    private void saveImageToFirebaseStorage(Uri image) {
        StorageReference carsRef = mFirebaseStorage
                .child(String.format("models/%s.jpg", mCarModel.getText().toString().toUpperCase()));

        carsRef.putFile(image);
    }

    public static CreateCarsFragment newInstance() {
        return new CreateCarsFragment();
    }
}
