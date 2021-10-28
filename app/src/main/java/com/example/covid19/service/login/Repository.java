package com.example.covid19.service.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.covid19.model.UserLogin;
import com.example.covid19.service.RetrofitInstance;
import com.example.covid19.util.Constants;
import com.example.covid19.util.HandleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private Client client;
    private static Repository instance;
    private static final String APIKEY= "454041184B0240FBA3AACD15A1F7A8BB";
    private MutableLiveData<HandleResponse<UserLogin,Throwable>> liveData;
    private HandleResponse<UserLogin,Throwable> responseHandle ;
    public static Repository getInstance(){
        if(instance == null)
            instance = new Repository();
        return instance;
    }

    public Repository() {
        client= Client.getInstance();
        liveData = new MutableLiveData<>();
        responseHandle = new HandleResponse<>();
    }

    public LiveData<HandleResponse<UserLogin,Throwable>> login(String username, String password){
        Log.e("TAG", "login1: " );
        client.service(Constants.BASE_LOGIN).signInNew(APIKEY,username,password).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                Log.e("TAG", "login2: " );

                if(response.isSuccessful()){
                    responseHandle.setData(response.body());
                    liveData.postValue(responseHandle);
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Log.e("TAG", "login3: " );

                responseHandle.setError(t);
                liveData.postValue(responseHandle);
            }
        });
        return liveData;
    }


}
