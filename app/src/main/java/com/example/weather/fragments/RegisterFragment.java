package com.example.weather.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.constants.Constants;
import com.example.weather.activities.MainActivity;
import com.example.weather.preferences.Preferences;
import com.example.weather.R;
import com.example.weather.model.Pojo;
import com.example.weather.model.PostOfficePojo;
import com.example.weather.retrofit.Api;
import com.example.weather.retrofit.RetrofitData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private static final String TAG = "RegisterFragment";

    private View view;
    private EditText mobileNumber ,name ,gender ,dob ,address1,address2,pinCode,district,state;
    private Api api;
    private int pinCodeNum=0;
    private ArrayList<PostOfficePojo> pojoArrayList;
    private MainActivity mainActivity;
    private TextView registerButton;
    private int age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_register, container, false);

        initView();

        setToolbarText();

        getMobileNumber();

        onClickDob();

        getDetails();

        onClickRegister();

        return view;
    }
    private boolean validation() {
        if (mobileNumber.getText().length()==Constants.ZERO)
        {
            settingError(mobileNumber,getString(R.string.phone_empty_error));
        }
        else if (mobileNumber.getText().length()<Constants.TEN)
        {
            settingError(mobileNumber,getString(R.string.phone_valid_error));
        }
        else if (name.getText().length()== Constants.ZERO)
        {
            settingError(name,getString(R.string.name_empty_error));
        }
        else if (name.getText().length()< Constants.FOUR)
        {
            settingError(name,getString(R.string.name_valid_error));
        }
        else if (gender.getText().length()==Constants.ZERO)
        {
            settingError(gender,getString(R.string.gender_empty_error));
        }
        else if (gender.getText().length()<Constants.FOUR)
        {
            settingError(gender,getString(R.string.gender_valid_error));
        }
        else if (dob.getText().length()==Constants.ZERO)
        {
            settingError(dob,getString(R.string.dob_empty_error));
        }
        else if (address1.getText().length()==Constants.ZERO)
        {
            settingError(address1,getString(R.string.address_empty_error));
        }
        else if (address1.getText().length()<Constants.FOUR)
        {
            settingError(address1,getString(R.string.address_valid_error));
        }
        else if (pinCode.getText().length()==Constants.ZERO)
        {
            settingError(pinCode,getString(R.string.pincode_empty_error));
        }
        else if (pinCode.getText().length()<Constants.SIX)
        {
            settingError(pinCode,getString(R.string.pincode_valid_error));
        }
        else if (district.getText().length()==Constants.ZERO)
        {
            settingError(district,getString(R.string.district_empty_error));
        }
        else if (state.getText().length()==Constants.ZERO)
        {
            settingError(state,getString(R.string.state_empty_error));
        }
        else return true;

        return false;
    }

    private void settingError(EditText editText, String validationText) {
        editText.requestFocus();
        editText.setError(validationText);
    }

    private void setToolbarText() {
        mainActivity.setToolbarName(getString(R.string.register_btn));
    }

    private void onClickRegister() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    saveInPreferences();
                    WeatherFragment fragment = new WeatherFragment();
                    mainActivity.replaceFragment(R.id.container_FL, fragment);
                }
                else
                {
                    Log.e(TAG, getString(R.string.false_validation));
                }
            }
        });
    }

    private void saveInPreferences() {
        Preferences.getInstance().setSomeStringValue(getString(R.string.hint_mobile),mobileNumber.getText().toString());
        Preferences.getInstance().setSomeStringValue(getString(R.string.name),name.getText().toString());
        Preferences.getInstance().setSomeStringValue(getString(R.string.gender),gender.getText().toString());
        Preferences.getInstance().setSomeStringValue(getString(R.string.dob),dob.getText().toString());
        Preferences.getInstance().setSomeIntValue(getString(R.string.age),age);
        Preferences.getInstance().setSomeStringValue(getString(R.string.city),pojoArrayList.get(Constants.ZERO).getDivision());
        Preferences.getInstance().setSomeStringValue(getString(R.string.address1),address1.getText().toString());
        Preferences.getInstance().setSomeStringValue(getString(R.string.address2),address2.getText().toString());
        Preferences.getInstance().setSomeIntValue(getString(R.string.pincode),Integer.valueOf(pinCode.getText().toString()));
        Preferences.getInstance().setSomeStringValue(getString(R.string.district),district.getText().toString());
        Preferences.getInstance().setSomeStringValue(getString(R.string.state),state.getText().toString());
    }

    private int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
//        String ageS = ageInt.toString();

//        return ageS;
        return ageInt;
    }

    private void onClickDob() {
        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                {
                    openCalender();
                }
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalender();
            }
        });
    }

    private void openCalender() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        new DatePickerDialog(requireActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        dob.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                        age = getAge(year,monthOfYear,dayOfMonth);

                    }
                }, mYear, mMonth, mDay).show();
        mainActivity.hideKeyboard(requireActivity());
    }

    private void getDetails() {
        pinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>5)
                {
                    mainActivity.showProgressDialog();
                    pinCodeNum = Integer.valueOf(pinCode.getText().toString());
                    hitApi();
                    Log.d(TAG, "onTextChanged: ");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    Log.d(TAG, "afterTextChanged: ");
//                }
            }
        });

    }

    private void hitApi() {

        Log.d(TAG, "hitApi: ");

        api = RetrofitData.getRetrofit(Constants.URL2).create(Api.class);

        Call<List<Pojo>> pojoCall = api.getPinCode(pinCodeNum);

        pojoCall.enqueue(new Callback<List<Pojo>>() {
            @Override
            public void onResponse(Call<List<Pojo>> call, Response<List<Pojo>> response) {
                Log.d(TAG, "onResponse: ");
                mainActivity.dismissProgressBar();
                if (response.body().get(Constants.ZERO).getStatus().equals(getString(R.string.success))) {
                    pojoArrayList = response.body().get(Constants.ZERO).getPostOffice();
                    district.setText(pojoArrayList.get(Constants.ZERO).getDistrict());
                    state.setText(pojoArrayList.get(Constants.ZERO).getState());
                    mainActivity.hideKeyboard(requireActivity());

                }
                else
                {
                    Toast.makeText(requireActivity(), "Incorrect PinCode", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pojo>> call, Throwable t) {
                mainActivity.dismissProgressBar();
                Toast.makeText(requireActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void initView() {
        mainActivity = (MainActivity) getActivity();
        mobileNumber = view.findViewById(R.id.mobile_ET);
        name = view.findViewById(R.id.name_ET);
        gender = view.findViewById(R.id.gender_ET);
        dob = view.findViewById(R.id.dob_ET);
        address1 = view.findViewById(R.id.address1_ET);
        address2 = view.findViewById(R.id.address2_ET);
        pinCode = view.findViewById(R.id.pin_code_ET);
        district = view.findViewById(R.id.district_ET);
        state = view.findViewById(R.id.state_ET);
        registerButton= view.findViewById(R.id.register_btn_TV);
        pojoArrayList = new ArrayList<>();
    }

    private void getMobileNumber() {
        Bundle bundle = getArguments();
        String number = bundle.getString(getString(R.string.number));
        mobileNumber.setText(number);
    }
}