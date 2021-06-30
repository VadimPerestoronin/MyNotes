package ru.geekbrains.mynotes.domain;

public interface Callback<T>{

    void onSuccess(T result);
}
