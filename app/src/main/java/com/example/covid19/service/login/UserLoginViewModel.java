package com.example.covid19.service.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.covid19.model.UserLogin;

public class UserLoginViewModel extends AndroidViewModel {

    private UserLoginRepository userLoginRepository;
    private UserLogin userLogin;

    public UserLoginViewModel(@NonNull Application application, String username, String password) {
        super(application);
        userLoginRepository = new UserLoginRepository(application, username, password);
    }

    public UserLogin getUserLogin() {
        userLogin = userLoginRepository.getLoginFromNetwork();
        return userLogin;
    }
}
