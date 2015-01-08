package de.opendatalab.kastanien;

public interface ResultHandler<T> {
    void onResponse(T response);
}
