package in.apricot.let.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Intent mServiceIntent;
    private my_service mYourService;
    Switch sw_main,sw_noicy,sw_movement;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sw_noicy = findViewById(R.id.swch_noicy);
        sw_movement = findViewById(R.id.swch_moment);
        preferences = getSharedPreferences("apricot", MODE_PRIVATE);
        editor = preferences.edit();
        if(preferences.getString("isNotyMove", "0").equals("1"))
        {
            sw_movement.setChecked(true);
        }
        if(preferences.getString("isNotySound", "0").equals("1"))
        {
            sw_noicy.setChecked(true);
        }
    }


    public void on_movemnt_swtch(View view) {
        if(sw_movement.isChecked())
        {
            mYourService = new my_service();
            mServiceIntent = new Intent(this, mYourService.getClass());
            if (!isMyServiceRunning(mYourService.getClass())) {
                startService(mServiceIntent);
            }
            editor.putString("isNotyMove","1");
            editor.commit();
            Myutil.isNotfiOnMovement = true;
        }
        else
        {
            if(!sw_noicy.isChecked())
            {
                mYourService = new my_service();
                mServiceIntent = new Intent(this, mYourService.getClass());
                if (isMyServiceRunning(mYourService.getClass())) {
                    stopService(mServiceIntent);
                }
            }
            editor.putString("isNotyMove","0");
            editor.commit();
            Myutil.isNotfiOnMovement = false;
        }
    }

    public void on_noicey_swtch(View view) {
        if(sw_noicy.isChecked())
        {
            mYourService = new my_service();
            mServiceIntent = new Intent(this, mYourService.getClass());
            if (!isMyServiceRunning(mYourService.getClass())) {
                startService(mServiceIntent);
            }
            editor.putString("isNotySound","1");
            editor.commit();
            Myutil.isNotfiOnNoice = true;
        }
        else
        {
            if(!sw_movement.isChecked())
            {
                mYourService = new my_service();
                mServiceIntent = new Intent(this, mYourService.getClass());
                if (isMyServiceRunning(mYourService.getClass())) {
                    stopService(mServiceIntent);
                }
            }
            editor.putString("isNotySound","0");
            editor.commit();
            Myutil.isNotfiOnNoice = false;
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    public void LogOut(View view) {
        editor.putString("islogin"," ");
        editor.commit();
        startActivity(new Intent(getApplicationContext(), ProductKeyActivity.class));
        finish();
    }

    public void goToInfo(View view) {
        startActivity(new Intent(getApplicationContext(), InfoActivity.class));
    }

    public void btn_wifisetting(View view) {
        startActivity(new Intent(getApplicationContext(), Wifi_settingActivity.class));
    }
}
