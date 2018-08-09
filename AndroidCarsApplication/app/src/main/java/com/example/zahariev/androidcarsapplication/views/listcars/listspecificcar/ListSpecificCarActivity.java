package com.example.zahariev.androidcarsapplication.views.listcars.listspecificcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zahariev.androidcarsapplication.R;

public class ListSpecificCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_specific_car);

        ListSpecificCarFragment fragment = ListSpecificCarFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
