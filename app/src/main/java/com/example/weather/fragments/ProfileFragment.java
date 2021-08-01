package com.example.weather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.activities.MainActivity;
import com.example.weather.preferences.Preferences;
import com.example.weather.R;


public class ProfileFragment extends Fragment {
    private View view;
    private MainActivity mainActivity;
    private TextView data;

     public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mainActivity = (MainActivity)getActivity();
        data = view.findViewById(R.id.details_TV);
        mainActivity.hideMenu();
//        setHasOptionsMenu(false);
        setToolbarText();
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_icon);


        settingDetails();

//        mainActivity.onSupportNavigateUp();


        return view;
    }

    private void settingDetails() {
        data.setText(Preferences.getInstance().getSomeStringValue(getString(R.string.name))+"\n"+
                Preferences.getInstance().getSomeStringValue(getString(R.string.gender))+", "+
                Preferences.getInstance().getSomeIntValue(getString(R.string.age))+"\n"+
                Preferences.getInstance().getSomeStringValue(getString(R.string.address1))+", "+
                Preferences.getInstance().getSomeStringValue(getString(R.string.address2))+"\n"+
                Preferences.getInstance().getSomeStringValue(getString(R.string.state))+", "+
                Preferences.getInstance().getSomeIntValue(getString(R.string.pincode)));
    }

    private void setToolbarText() {
        mainActivity.setToolbarName(getString(R.string.view_profile));

    }

    public void onBackPress() {
         mainActivity.onSupportNavigateUp();
    }


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home/back button
//            case android.R.id.home:
//                requireActivity().finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}