package com.example.weather.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.constants.Constants;
import com.example.weather.activities.MainActivity;
import com.example.weather.preferences.Preferences;
import com.example.weather.R;
import com.example.weather.model.Pojo;
import com.example.weather.model.PostOfficePojo;
import com.example.weather.model.WeatherDetailPojo;
import com.example.weather.retrofit.Api;
import com.example.weather.retrofit.RetrofitData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {
    private static final String TAG = "WeatherFragment";
    private View view;
    private EditText pinCode,state,city;
    private TextView showResult,check;
    private TextView result;
    private ArrayList<PostOfficePojo> pojoArrayList;
    private MainActivity mainActivity;
    private Api api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);

        initView();

        setToolbarText();

        getDetails();

        onClickCheck();

        onClickShowResults();

        setHasOptionsMenu(true);

        return view;
    }

    private void onClickCheck() {
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showProgressDialog();
                hitPinCodeApi();
            }
        });
    }

    private void hitPinCodeApi() {

        Log.d(TAG, "hitApi: ");

        api = RetrofitData.getRetrofit(Constants.URL2).create(Api.class);

        Call<List<Pojo>> pojoCall = api.getPinCode(Integer.valueOf(pinCode.getText().toString()));

        pojoCall.enqueue(new Callback<List<Pojo>>() {
            @Override
            public void onResponse(Call<List<Pojo>> call, Response<List<Pojo>> response) {
                Log.d(TAG, "onResponse: ");
                mainActivity.dismissProgressBar();
                if (response.body().get(Constants.ZERO).getStatus().equals(getString(R.string.success))) {
                    pojoArrayList = response.body().get(Constants.ZERO).getPostOffice();
                    city.setText(pojoArrayList.get(Constants.ZERO).getDivision());
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

    private void setToolbarText() {
        mainActivity.setToolbarName(getString(R.string.weather_title));
    }

    private void onClickShowResults() {
        showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showProgressDialog();
                hitApi();
            }
        });

    }

    private void hitApi() {

        Log.d(TAG, "hitApi: ");

        api = RetrofitData.getRetrofit(Constants.URL).create(Api.class);

        Call<WeatherDetailPojo> pojoCall = api.getWeatherDetail(city.getText().toString(),Constants.KEY);

        pojoCall.enqueue(new Callback<WeatherDetailPojo>() {
            @Override
            public void onResponse(Call<WeatherDetailPojo> call, Response<WeatherDetailPojo> response) {
                Log.e(TAG, "onResponse: " );
                mainActivity.dismissProgressBar();
                result.setText(String.valueOf(getString(R.string.temperature)+response.body().main.getTemp()+
                        getString(R.string.max_temperature)+response.body().main.getTemp_max()+
                        +response.body().main.getTemp_min() +
                        getString(R.string.humidity)+response.body().main.getHumidity()+
                        getString(R.string.clouds)+response.body().clouds.getAll()));
            }

            @Override
            public void onFailure(Call<WeatherDetailPojo> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                mainActivity.dismissProgressBar();
            }
        });
    }


    private void initView() {
        pinCode = view.findViewById(R.id.pincode_ET);
        state = view.findViewById(R.id.state_ap_ET);
        city = view.findViewById(R.id.city_ap_ET);
        showResult = view.findViewById(R.id.show_result_btn_TV);
        result = view.findViewById(R.id.result_TV);
        check = view.findViewById(R.id.check_btn_TV);
        mainActivity = (MainActivity) getActivity();
        pojoArrayList =  new ArrayList<>();
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void getDetails() {
        city.setText(Preferences.getInstance().getSomeStringValue(getString(R.string.city)));
        pinCode.setText(String.valueOf(Preferences.getInstance().getSomeIntValue(getString(R.string.pincode))));
        state.setText(Preferences.getInstance().getSomeStringValue(getString(R.string.state)));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MainActivity mainActivity = (MainActivity)getActivity();
        switch (item.getItemId())
        {
            case R.id.my_profile:

                ProfileFragment fragment = new ProfileFragment();
                mainActivity.replaceFragment(R.id.container_FL,fragment);
                break;

            case R.id.logout:
                LandingFragment fragment1 = new LandingFragment();
                mainActivity.replaceFragment(R.id.container_FL,fragment1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}