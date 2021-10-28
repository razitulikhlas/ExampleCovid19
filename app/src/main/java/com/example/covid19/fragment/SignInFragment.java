package com.example.covid19.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covid19.MainActivity;
import com.example.covid19.R;
import com.example.covid19.model.UserLogin;
import com.example.covid19.service.login.UserLoginViewModel;
import com.example.covid19.session.SessionManagerUtil;
import com.example.covid19.util.HandleResponse;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Calendar;

public class SignInFragment extends Fragment {

    private Button btnSignIn;
    private TextInputLayout inputUsername;
    private TextInputLayout inputPass;

    private UserLoginViewModel userLoginViewModel;

    private String username;
    private String password;


    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isAllowed = SessionManagerUtil.getInstance().isSessionActive(requireActivity(), Calendar.getInstance().getTime());
        if(isAllowed){
            startMainActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputUsername = view.findViewById(R.id.inputUsername);
        inputPass = view.findViewById(R.id.inputPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);

        userLoginViewModel = new ViewModelProvider(requireActivity()).get(UserLoginViewModel.class);


        btnSignIn.setOnClickListener(v -> {

            username = inputUsername.getEditText().getText().toString();
            password = inputPass.getEditText().getText().toString();

            if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
                showDialogFailedLogin("Email and Password must be filled");
            } else if (TextUtils.isEmpty(username)) {
                showDialogFailedLogin("Email must be filled");
            } else if (TextUtils.isEmpty(password)) {
                showDialogFailedLogin("Password must be filled");
            } else {
                showLoading();
                userLoginViewModel.login(username, password).observe(getViewLifecycleOwner(), new Observer<HandleResponse<UserLogin, Throwable>>() {
                    @Override
                    public void onChanged(HandleResponse<UserLogin, Throwable> userLoginThrowableHandleResponse) {
                        hideLoading();
                        if (userLoginThrowableHandleResponse.getData() != null) {
                            startAndStoreSession(userLoginThrowableHandleResponse.getData());
                            startMainActivity();
                            Log.e("TAG", "onChanged: " + userLoginThrowableHandleResponse.getData().getData().getUsername());
                        } else {
                            Log.e("TAG", "onChanged: " + userLoginThrowableHandleResponse.getError().getMessage());
                        }
                    }
                });
            }
        });
    }

    private void startAndStoreSession(UserLogin userLogin) {
        String formatToken = userLogin.getData().getEmail() + "_" + userLogin.getToken();
        System.out.println("format token : " + formatToken);

        Gson gson = new Gson();
        String json = gson.toJson(userLogin);

        SessionManagerUtil.getInstance().storeUserToken(requireActivity(), formatToken, json);
        SessionManagerUtil.getInstance().StartUserSession(requireActivity(), 5 * 60); //5 minutes
    }

    private void startMainActivity() {
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        requireActivity().startActivity(intent);
        requireActivity().finish();
    }

    private void showDialogFailedLogin(String textDialog) {
        DialogFragment fragment = new FailedLoginDialogFragment();
        fragment.show(getParentFragmentManager(), textDialog);
    }

    private void hideLoading() {
        Fragment prev = getParentFragmentManager().findFragmentByTag("loading");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }

    private void showLoading() {
        DialogFragment fragmentLoading = new LoadingDialogFragment();
        fragmentLoading.show(getParentFragmentManager(), "loading");
    }

}