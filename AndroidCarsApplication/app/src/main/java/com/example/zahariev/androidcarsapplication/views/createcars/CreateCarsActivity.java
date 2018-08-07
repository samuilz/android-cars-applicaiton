package com.example.zahariev.androidcarsapplication.views.createcars;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.zahariev.androidcarsapplication.R;
import com.example.zahariev.androidcarsapplication.views.BaseDrawerActivity;

public class CreateCarsActivity extends BaseDrawerActivity {
    public static final int IDENTIFIER = 2;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cars);

        mToolBar = findViewById(R.id.drawer_toolbar);
//        setSupportActionBar(mToolBar);

        CreateCarsFragment fragment = CreateCarsFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @Override
    protected long getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    protected Toolbar getDrawerToolbar() {
        return mToolBar;
    }
}
