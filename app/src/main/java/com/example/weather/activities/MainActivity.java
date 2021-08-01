package com.example.weather.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.weather.preferences.Preferences;
import com.example.weather.R;
import com.example.weather.constants.Constants;
import com.example.weather.fragments.LandingFragment;
import com.example.weather.fragments.ProfileFragment;
import com.example.weather.fragments.RegisterFragment;
import com.example.weather.fragments.WeatherFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressBar;
    private Toolbar toolbar;
    private TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarText = findViewById(R.id.toolbar_text);
        toolbarText.setText("");

        LandingFragment landingFragment = new LandingFragment();
        addFragment(R.id.container_FL,landingFragment);
    }
    public void hideMenu()
    {
        toolbar.getMenu().clear();
    }
    public void setToolbarName(String title)
    {
        toolbarText.setText(title);
    }
    public void addFragment(int replaceId, Fragment fragment) {
        try {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(replaceId, fragment);
            ft.commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), Constants.ZERO);
    }

    //for close button in profile fragment
    @Override
    public boolean onSupportNavigateUp() {
        WeatherFragment fragment = new WeatherFragment();
        replaceFragment(R.id.container_FL,fragment);

        return false;
    }

    public void replaceFragment(int replaceId,Fragment replaceFragment)
    {
        try {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(replaceId,replaceFragment);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showProgressDialog() {
        progressBar = new ProgressDialog(this);
        try {
            progressBar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(progressBar.getWindow())
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressBar.setContentView(R.layout.progressdialog);
        progressBar.setCancelable(false);
        progressBar.setIndeterminate(true);
        progressBar.show();
    }

    public void dismissProgressBar()
    {
        progressBar.dismiss();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.container_FL);
        if (frag instanceof LandingFragment)
        {
            onBackClicked();
        }
        else if (frag instanceof RegisterFragment)
        {
            onBackClicked();
        }
        else if (frag instanceof WeatherFragment)
        {
            onBackClicked();
        }
        else if( frag instanceof ProfileFragment)
        {
            ((ProfileFragment)frag).onBackPress();
        }
        else
        {
            onBackClicked();
        }
    }

    private void onBackClicked() {
        Preferences.getInstance().alertDialogBox(this);
    }
}