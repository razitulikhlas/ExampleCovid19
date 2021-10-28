package com.example.covid19.fragment;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.covid19.MainActivity;
import com.example.covid19.MappingDataCovid;
import com.example.covid19.R;
import com.example.covid19.model.CaseCovid;
import com.example.covid19.model.UserLogin;
import com.example.covid19.room.AppDatabase;
import com.example.covid19.room.DataCovid;
import com.example.covid19.room.DataCovidViewModel;
import com.example.covid19.service.data_covid.CaseCovidViewModel;
import com.example.covid19.service.login.UserLoginViewModel;
import com.example.covid19.session.SessionManagerUtil;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.List;

public class SignInFragment extends Fragment {

    private Button btnSignIn;
    private TextInputLayout inputUsername;
    private TextInputLayout inputPass;

    private UserLoginViewModel userLoginViewModel;
    private CaseCovidViewModel caseCovidViewModel;
    private DataCovidViewModel dataCovidViewModel;
    private String username;
    private String password;
    private UserLogin userLogin;

    private List<CaseCovid> allCaseCovid;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        inputUsername = view.findViewById(R.id.inputUsername);
        inputPass = view.findViewById(R.id.inputPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = inputUsername.getEditText().getText().toString();
                password = inputPass.getEditText().getText().toString();

                if (TextUtils.isEmpty(username)&&TextUtils.isEmpty(password)){
                    showDialogFailedLogin("Email and Password must be filled");
                }else if (TextUtils.isEmpty(username)){
                    showDialogFailedLogin("Email must be filled");
                }else if (TextUtils.isEmpty(password)){
                    showDialogFailedLogin("Password must be filled");
                }else{
                    LoginTask task = new LoginTask(requireActivity().getApplication(), username, password);
                    task.execute();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class LoginTask extends AsyncTask<Void, Void, UserLogin>{

        private Application application;
        private String username;
        private String password;

        public LoginTask(Application application, String username, String password) {
            this.application = application;
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected UserLogin doInBackground(Void... voids) {
            System.out.println("exec async background");
            userLoginViewModel = new UserLoginViewModel(application, username, password);
            userLogin = userLoginViewModel.getUserLogin();

            caseCovidViewModel = new CaseCovidViewModel(application);
            allCaseCovid = caseCovidViewModel.getAllCaseCovid();

            return userLogin;
        }

        @Override
        protected void onPostExecute(UserLogin s) {
            if (s != null){
                if (s.getStatusLogin() && !(allCaseCovid.isEmpty())){
                    pushLocalStorage(allCaseCovid);
                    hideLoading();
                    startAndStoreSession();
                    startMainActivity();
                }else{
                    hideLoading();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            showDialogFailedLogin("Oops... Process Login Failed, Try Again Later");
                        }
                    }, 1000);   //1 seconds
                }
            }else{
                hideLoading();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        showDialogFailedLogin("Oops... Process Login Failed, Try Again Later");
                    }
                }, 1000);   //1 seconds
            }
        }
    }

    private void startAndStoreSession(){
        String formatToken = userLogin.getData().getEmail()+"_"+userLogin.getToken();
        System.out.println("format token : "+formatToken);

        Gson gson = new Gson();
        String json = gson.toJson(userLogin);

        SessionManagerUtil.getInstance().storeUserToken(requireActivity(), formatToken, json);
        SessionManagerUtil.getInstance().StartUserSession(requireActivity(), 5 * 60); //5 minutes
    }

    private void startMainActivity(){
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        requireActivity().startActivity(intent);
    }

    private void showDialogFailedLogin(String textDialog){
        DialogFragment fragment = new FailedLoginDialogFragment();
        fragment.show(getParentFragmentManager(), textDialog);
    }

    private void hideLoading(){
        Fragment prev = getParentFragmentManager().findFragmentByTag("loading");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }

    private void showLoading(){
        DialogFragment fragmentLoading = new LoadingDialogFragment();
        fragmentLoading.show(getParentFragmentManager(), "loading");
    }

    private void pushLocalStorage(List<CaseCovid> caseCovids){

        System.out.println("size push to local storage : "+caseCovids.size());

        dataCovidViewModel = new ViewModelProvider(requireActivity()).get(DataCovidViewModel.class);
        dataCovidViewModel.deleteAllDataCovid();

        List<DataCovid> inputDataCovid = MappingDataCovid.getMapping(caseCovids);

        System.out.println("get mapping : "+inputDataCovid.size());


        InsertTask insertTask = new InsertTask(inputDataCovid);
        insertTask.execute();


//        for (int i=0; i < caseCovids.size(); i++){
//            String country = caseCovids.get(i).getCountry();
//            String flag = caseCovids.get(i).getCountryInfo().getFlag();
//            int cases = caseCovids.get(i).getCases();
//            int todayCases = caseCovids.get(i).getTodayCases();
//            int death = caseCovids.get(i).getDeaths();
//            int recovered = caseCovids.get(i).getRecovered();
//            int todayRecovered = caseCovids.get(i).getCases();
//            int active = caseCovids.get(i).getActive();
//            int critical = caseCovids.get(i).getCritical();
//            int population = caseCovids.get(i).getPopulation();
//            String continent = caseCovids.get(i).getContinent();
//
//            DataCovid dataCovid = new DataCovid();
//            dataCovid.uid = i+1;
//            dataCovid.country = country;
//            dataCovid.flag = flag;
//            dataCovid.cases = cases;
//            dataCovid.todayCases = todayCases;
//            dataCovid.death = death;
//            dataCovid.recovered = recovered;
//            dataCovid.todayRecovered = todayRecovered;
//            dataCovid.active = active;
//            dataCovid.critical = critical;
//            dataCovid.population = population;
//            dataCovid.continent = continent;
//
//            InsertTask task = new InsertTask(dataCovid, i+1);
//            task.execute();
//
//        }
    }

    private class InsertTask extends AsyncTask<Void, Void, Void>{
        private List<DataCovid> input;
        //private int uid;

        public InsertTask(List<DataCovid> input) {
            this.input = input;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            dataCovidViewModel.insertAll(input);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            System.out.println("success insert local storage");
        }
    }
}