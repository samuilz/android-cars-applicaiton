package com.example.zahariev.androidcarsapplication.repositories;

import com.example.zahariev.androidcarsapplication.repositories.base.Repository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.function.Consumer;

public class FirebaseRepository<T> implements Repository<T> {
    private final Class<T> mClass;
    private final String mCollectionName;
    private final FirebaseFirestore mDb;

    public FirebaseRepository(Class<T> clazz) {
        this.mDb = FirebaseFirestore.getInstance();
        this.mClass = clazz;
        this.mCollectionName = clazz.getSimpleName().toLowerCase() + "s";
    }

    @Override
    public void getAll(Consumer<List<T>> action) {
        mDb.collection(mCollectionName)
                .get()
                .addOnCompleteListener(task -> {
                    List<T> cars = task.getResult().toObjects(mClass);
                    action.accept(cars);
                });
    }

    @Override
    public void add(T item) {
        mDb.collection(mCollectionName)
                .add(item);
    }
}
