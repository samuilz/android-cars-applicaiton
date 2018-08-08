package com.example.zahariev.androidcarsapplication.repositories.base;

public interface Storage<T> {
    T getImage();

    void addImage(T image);
}
