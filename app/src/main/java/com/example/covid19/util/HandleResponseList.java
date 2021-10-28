package com.example.covid19.util;

import java.util.ArrayList;
import java.util.List;

public class HandleResponseList<O,T> {
    List<O> data = new ArrayList<>();
    T error;

    public List<O> getData() {
        return data;
    }

    public void setData(List<O> data) {
        this.data = data;
    }

    public T getError() {
        return error;
    }

    public void setError(T error) {
        this.error = error;
    }
}
