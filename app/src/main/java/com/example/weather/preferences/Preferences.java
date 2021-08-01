package com.example.weather.preferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.example.weather.R;
import com.example.weather.activities.MainActivity;
import com.example.weather.constants.Constants;


public class Preferences {

    public static Preferences one_instance = null;

    Context context;
    public void init(Context context){
        this.context = context.getApplicationContext();
    }

    private Preferences()
    {
    }

    private Context getContext() {
        return context;
    }
    public static Context get() {
        return getInstance().getContext();
    }

    public static Preferences getInstance()
    {
        if (one_instance==null)
        {
            one_instance= new Preferences();
        }
        return one_instance;
    }


    public SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.details, Context.MODE_PRIVATE);
    }
    public void setSomeStringValue(String key, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(getContext()).edit();
        editor.putString(key , newValue);
        editor.commit();
    }
    public String getSomeStringValue(String key) {
        return getSharedPreferences(getContext()).getString(key , null);
    }

    public void setSomeIntValue(String key, int newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(getContext()).edit();
        editor.putInt(key , newValue);
        editor.commit();
    }
    public int getSomeIntValue(String key) {
        return getSharedPreferences(getContext()).getInt(key , 0);
    }



    public void alertDialogBox(final MainActivity mainActivity)
    {
        AlertDialog.Builder builder = new  AlertDialog.Builder(mainActivity);
        builder.setMessage(R.string.sure_exit)
                .setTitle(R.string.exit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainActivity.finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
