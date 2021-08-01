package com.example.weather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weather.constants.Constants;
import com.example.weather.activities.MainActivity;
import com.example.weather.R;

public class LandingFragment extends Fragment {
    private static final String TAG = "LandingFragment";
    private EditText mobNumber;
    private View view;
    private TextView continueButton;
    private MainActivity mainActivity;
    private String mobileNumber;

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_landing, container, false);

        initView();

        setToolbarText();

        onClickContinue();

        return view;
    }

    private boolean validation() {

        if (mobNumber.getText().length()==Constants.ZERO)
        {
            settingError(mobNumber,getString(R.string.phone_empty_error));
        }
        else if (mobNumber.getText().length()<Constants.TEN)
        {
            settingError(mobNumber,getString(R.string.phone_valid_error));
        }
        else return true;

        return false;
    }

    private void settingError(EditText editText, String validationText) {
        editText.requestFocus();
        editText.setError(validationText);
    }

    private void setToolbarText() {
        mainActivity.setToolbarName(getString(R.string.landing_title));
    }

    private void navigate() {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.number),mobileNumber);
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(bundle);
        mainActivity.replaceFragment(R.id.container_FL,fragment);
    }

    private void onClickContinue() {
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileNumber = mobNumber.getText().toString();
                if (validation()) {

                    navigate();
                }
                else
                {
                    Log.e(TAG, getString(R.string.false_validation));
                }
            }
        });
    }

    private void initView() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mobNumber = view.findViewById(R.id.mob_ET);
        continueButton = view.findViewById(R.id.continue_btn_TV);
    }
}