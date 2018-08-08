package com.example.zahariev.androidcarsapplication.views.createcars;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import com.example.zahariev.androidcarsapplication.views.listcars.listcarsbybrand.ListCarsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCarsFragment extends Fragment {
    private static final int PICK_IMAGE = 100;
    private Repository<Car> mCarsRepository;
    private Button mButton;
    private EditText mCarBrand;
    private EditText mCarModel;
    private StorageReference mFirebaseStorage;

    public CreateCarsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_cars, container, false);

        mCarsRepository = new FirebaseRepository<>(Car.class);
        mFirebaseStorage = FirebaseStorage.getInstance().getReference();

        mCarBrand = root.findViewById(R.id.et_cars_name);
        mCarModel = root.findViewById(R.id.et_cars_model);
        Button imageButton = root.findViewById(R.id.btn_image);

        imageButton.setOnClickListener(click -> {
            imageButton.setOnClickListener(v -> {
                Intent gallery = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(gallery, PICK_IMAGE);
            });
        });

        mButton = root.findViewById(R.id.btn_create);

        return root;
    }

    private void intentHandle() {
        String carBrand = mCarBrand.getText().toString();
        String carModel = mCarModel.getText().toString();

        mButton.setOnClickListener(click -> {
            Car car = new Car(carBrand, carModel);
            mCarsRepository.add(car);

            Toast.makeText(getContext(),
                    String.format("Car %s - %s created!", carBrand, carModel),
                    Toast.LENGTH_SHORT)
                    .show();

            Intent intent = new Intent(getContext(), ListCarsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            saveImageToFirebaseStorage(selectedImage);
            intentHandle();
        }
    }

    private void saveImageToFirebaseStorage(Uri image) {
        StorageReference carsRef = mFirebaseStorage
                .child(String.format("images/%s.jpg", mCarModel.getText().toString()));

        carsRef.putFile(image);
    }

    public static CreateCarsFragment newInstance() {
        return new CreateCarsFragment();
    }
}
