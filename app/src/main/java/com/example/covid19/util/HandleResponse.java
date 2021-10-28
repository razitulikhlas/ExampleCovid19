package com.example.covid19.util;

import java.util.ArrayList;
import java.util.List;

public class HandleResponse<O,T> {
    O data;
    T error;

    public O getData() {
        return data;
    }

    public void setData(O data) {
        this.data = data;
    }

    public T getError() {
        return error;
    }

    public void setError(T error) {
        this.error = error;
    }
}
