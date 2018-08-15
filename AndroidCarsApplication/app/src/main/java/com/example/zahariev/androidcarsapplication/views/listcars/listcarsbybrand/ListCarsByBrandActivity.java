package com.example.zahariev.androidcarsapplication.views.listcars.listcarsbybrand;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.zahariev.androidcarsapplication.R;
import com.example.zahariev.androidcarsapplication.views.BaseDrawerActivity;

public class ListCarsByBrandActivity extends BaseDrawerActivity {
    public static final int IDENTIFIER = 1;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cars);

        mToolBar = findViewById(R.id.drawer_toolbar);

        ListCarsByBrandFragment fragment = ListCarsByBrandFragment.newInstance();

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

