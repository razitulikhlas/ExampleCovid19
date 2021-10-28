 package com.example.covid19.service.login;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.covid19.model.UserLogin;
import com.example.covid19.service.RetrofitInstance;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

 public class UserLoginRepository {
    private LoginEndPointInterface API;
    //private MutableLiveData<UserLogin> userLogin = new MutableLiveData<>();
    private final String apiKey = "454041184B0240FBA3AACD15A1F7A8BB";
    private final String contentType = "application/x-www-form-urlencoded";
    private String username;
    private String password;
    private String statusLogin = "empty";
    private UserLogin userLogin;

    private final ExecutorService networkExecutor = Executors.newFixedThreadPool(4);
    private final Executor mainThread = new Executor() {
        private Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };

    public UserLoginRepository(Application application, String username, String password){
        this.username = username;
        this.password = password;

        RetrofitInstance retrofitInstance = new RetrofitInstance();
        try {
            retrofitInstance.execLogin();
            API = retrofitInstance.getLoginAPI();
        }catch (Exception e){
            System.out.println("Error Retrofit Login"+e.toString());
            Toast.makeText(application.getApplicationContext(), "Proses gagal dilakukan", Toast.LENGTH_LONG).show();
        }

    }

    public UserLogin getLoginFromNetwork(){

        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Call<UserLogin> call = API.signIn(apiKey,contentType,username,password);
                    UserLogin login = call.execute().body();
                    if (login == null){
                        System.out.println("can't get object (account not found!)");
                        userLogin = login;
                        statusLogin = "false";
                    }else{
                        userLogin = login;
                        statusLogin = ""+login.getStatusLogin();
                    }



//                    call.enqueue(new Callback<UserLogin>() {
//                        @Override
//                        public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
//                            System.out.println("Response data login : "+ (response.body() != null ? response.body().getMessage() : null));
//                            statusLogin = response.body().getStatusLogin();
//                            System.out.println("statusLogin "+statusLogin);
//                        }
//
//                        @Override
//                        public void onFailure(Call<UserLogin> call, Throwable t) {
//                            System.out.println("statusLogin "+statusLogin);
//                            System.out.println("Response data login error : "+t.toString());
//                        }
//                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        while (statusLogin.equalsIgnoreCase("empty")){
            try {
                networkExecutor.awaitTermination(5,TimeUnit.SECONDS);
                System.out.println("process is waiting");
            }catch (Exception e){
                System.out.println("process is error"+e.toString());
            }
        }

        System.out.println("statusLogin "+statusLogin);
        return userLogin;
    }

}
