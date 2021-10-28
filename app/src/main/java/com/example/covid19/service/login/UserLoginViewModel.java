package com.example.covid19.service.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covid19.model.UserLogin;
import com.example.covid19.util.HandleResponse;

public class UserLoginViewModel extends AndroidViewModel {


    private Repository repository;
    public UserLoginViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public LiveData<HandleResponse<UserLogin,Throwable>> login(String username,String password){
        Log.e("TAG", "loginview1: " );

        return repository.login(username,password);
    }


}
