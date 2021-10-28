package com.example.covid19.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.covid19.MainActivity;
import com.example.covid19.R;
import com.example.covid19.SignActivity;
import com.example.covid19.model.UserLogin;
import com.example.covid19.session.SessionManagerUtil;
import com.google.gson.Gson;

public class AccountFragment extends Fragment {

    private UserLogin user;
    private TextView username;
    private TextView fullname;
    private TextView email;
    private TextView detailUsername;
    private TextView detailFullname;
    private Button btnLogout;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String getUser = SessionManagerUtil.getInstance().getUserLogin(requireActivity());
        Gson gson = new Gson();
        user = gson.fromJson(getUser, UserLogin.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        username = view.findViewById(R.id.tvProfileUsername);
        fullname = view.findViewById(R.id.tvProfileFullname);
        email = view.findViewById(R.id.tvDetailEmail);
        detailUsername = view.findViewById(R.id.tvDetailUsername);
        detailFullname = view.findViewById(R.id.tvDetailFullname);
        btnLogout = view.findViewById(R.id.btnLogout);

        username.setText(user.getData().getUsername());
        fullname.setText(user.getData().getFullName());
        email.setText(user.getData().getEmail());
        detailUsername.setText(user.getData().getUsername());
        detailFullname.setText(user.getData().getFullName());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagerUtil.getInstance().endUserSession(requireActivity());
                startLoginActivity();
            }
        });

        return view;
    }

    private void startLoginActivity(){
        Intent intent = new Intent(requireActivity(), SignActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        requireActivity().startActivity(intent);
    }
}