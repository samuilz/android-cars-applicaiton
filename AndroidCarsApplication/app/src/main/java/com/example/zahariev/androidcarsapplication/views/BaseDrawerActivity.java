package com.example.zahariev.androidcarsapplication.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.zahariev.androidcarsapplication.views.createcars.CreateCarsActivity;
import com.example.zahariev.androidcarsapplication.views.listcars.ListCarsActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

public abstract class BaseDrawerActivity extends AppCompatActivity {
    public BaseDrawerActivity() {
        // Required empty constructor
    }

    public void setUpDrawer() {
        PrimaryDrawerItem listCars = new PrimaryDrawerItem()
                .withIdentifier(ListCarsActivity.IDENTIFIER)
                .withName("Cars");

        PrimaryDrawerItem createCars = new PrimaryDrawerItem()
                .withIdentifier(CreateCarsActivity.IDENTIFIER)
                .withName("Create cars");


        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(getDrawerToolbar())
                .addDrawerItems(
                        listCars,
                        new DividerDrawerItem(),
                        createCars
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    int identifier = (int) drawerItem.getIdentifier();

                    if (getIdentifier() == identifier) {
                        return false;
                    }

                    Intent intent = getNextIntent(identifier);
                    if (intent == null) {
                        return false;
                    }

                    startActivity(intent);
                    return true;
                })
                .build();
    }

    private Intent getNextIntent(int identifier) {
        Intent intent = null;
        switch (identifier) {
            case ListCarsActivity.IDENTIFIER:
                intent = new Intent(this, ListCarsActivity.class);
                break;
            case CreateCarsActivity.IDENTIFIER:
                intent = new Intent(this, CreateCarsActivity.class);
                break;
        }

        return intent;
    }

    protected abstract long getIdentifier();

    protected abstract Toolbar getDrawerToolbar();

    @Override
    protected void onStart() {
        super.onStart();
        setUpDrawer();
    }
}

