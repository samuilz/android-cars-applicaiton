package com.example.zahariev.androidcarsapplication.views.listcars.listcarsbybrand;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zahariev.androidcarsapplication.R;
import com.example.zahariev.androidcarsapplication.models.Car;
import com.example.zahariev.androidcarsapplication.repositories.FirebaseRepository;
import com.example.zahariev.androidcarsapplication.repositories.base.Repository;
import com.example.zahariev.androidcarsapplication.views.listcars.listcarsbymodel.ListCarsByModelActivity;
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
public class ListCarsByBrandFragment extends Fragment {
    private List<Car> allCars;
    private static final String DATABASE_NAME = "brands";
    private ArrayAdapter<String> mCarsAdapter;
    private Repository<Car> mCarsRepository;
    private FirebaseFirestore mDb;
    private StorageReference mFirebaseStorage;

    public ListCarsByBrandFragment() {
        allCars = new ArrayList<>();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_cars, container, false);

        mDb = FirebaseFirestore.getInstance();

//        mCarsAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1);
        ListView listView = root.findViewById(R.id.lv_cars);

//        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            View header = getLayoutInflater().inflate(R.layout.list_cars_by_brand_layout, null);
//            listView.addHeaderView(header);
//        } else {
//
//        }

        View header = getLayoutInflater().inflate(R.layout.list_cars_by_brand_layout, null);
        listView.addHeaderView(header, null, false);

        mCarsRepository = new FirebaseRepository<>(Car.class, DATABASE_NAME);
        mFirebaseStorage = FirebaseStorage.getInstance().getReference();

        final CarAdapter[] carAdapter = new CarAdapter[1];
        mCarsRepository.getAll(
                cars -> {
                    allCars.addAll(cars);

                    if (getActivity() == null) {
                        return;
                    }

                    carAdapter[0] = new CarAdapter(getActivity());
                    listView.setAdapter(carAdapter[0]);
                    listView.setOnItemClickListener((adapterView, view, position,  l) -> {
//                        if (position == 0) {
//                            return;
//                        }

                        Intent listCarsByModel = new Intent(
                                getContext(),
                                ListCarsByModelActivity.class
                        );

                        String clickedCarBrand = allCars.get(position - 1).brand;
                        listCarsByModel.putExtra("CAR_BRAND", clickedCarBrand);
                        startActivity(listCarsByModel);
                    });
                });

        return root;
    }

    public static ListCarsByBrandFragment newInstance() {
        return new ListCarsByBrandFragment();
    }

    private class CarAdapter extends ArrayAdapter {
        CarAdapter(@NonNull Context context) {
            super(context, R.layout.custom_list_view_row, R.id.tv_brand, allCars);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.custom_list_view_row, parent, false);

            ImageView image = row.findViewById(R.id.iv_brand_image);
            TextView brand = row.findViewById(R.id.tv_brand);

            String carBrand = allCars.get(position).brand;
            brand.setText(carBrand);

            StorageReference carsRef = mFirebaseStorage
                    .child(String.format("brands/%s.png", carBrand.toLowerCase()));

            Glide.with(getContext())
                    .using(new FirebaseImageLoader())
                    .load(carsRef)
                    .into(image);

            return row;
        }
    }
}
