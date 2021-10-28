package com.example.covid19.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.covid19.R;

public class FailedLoginDialogFragment extends DialogFragment {

    private Button btnDialog;
    private TextView tvDialog;
    private String textDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_failed_login, null);
        btnDialog = view.findViewById(R.id.btnDialog);
        tvDialog = view.findViewById(R.id.tvNotifDialog);

        textDialog = this.getTag();
        tvDialog.setText(textDialog);

        builder.setView(view);

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FailedLoginDialogFragment.this.getDialog().cancel();
                System.out.println("close dialog");
            }
        });

        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getDialog().setCanceledOnTouchOutside(false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
