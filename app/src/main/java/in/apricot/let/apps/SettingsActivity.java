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
        sw_main = findViewById(R.id.swch_main);
        sw_noicy = findViewById(R.id.swch_noicy);
        sw_movement = findViewById(R.id.swch_moment);
        preferences = getSharedPreferences("apricot", MODE_PRIVATE);
        editor = preferences.edit();
        if(preferences.getString("isNotify", "0").equals("1"))
        {
            sw_main.setChecked(true);
        }
        if(preferences.getString("isNotyMove", "0").equals("1"))
        {
            sw_movement.setChecked(true);
        }
        if(preferences.getString("isNotySound", "0").equals("1"))
        {
            sw_noicy.setChecked(true);
        }
    }

    public void on_notification_click(View view) {
        if(sw_main.isChecked())
        {
            editor.putString("isNotify","1");
            editor.commit();
           // Toast.makeText(this, "On", Toast.LENGTH_SHORT).show();
            sw_movement.setEnabled(true);
            sw_noicy.setEnabled(true);
            mYourService = new my_service();
            mServiceIntent = new Intent(this, mYourService.getClass());
            if (!isMyServiceRunning(mYourService.getClass())) {
                startService(mServiceIntent);
            }
        }

        else {
            editor.putString("isNotify","0");
            editor.putString("isNotyMove","0");
            editor.putString("isNotySound","0");
            editor.commit();
           // Toast.makeText(this, "off", Toast.LENGTH_SHORT).show();
            sw_movement.setChecked(false);
            sw_noicy.setChecked(false);
            sw_movement.setEnabled(false);
            sw_noicy.setEnabled(false);
            mYourService = new my_service();
            mServiceIntent = new Intent(this, mYourService.getClass());
            if (isMyServiceRunning(mYourService.getClass())) {
                stopService(mServiceIntent);
            }
        }
    }

    public void on_movemnt_swtch(View view) {
        if(sw_movement.isChecked())
        {
            editor.putString("isNotyMove","1");
            editor.commit();
            Myutil.isNotfiOnMovement = true;
        }
        else
        {
            editor.putString("isNotyMove","0");
            editor.commit();
            Myutil.isNotfiOnMovement = false;
        }
    }

    public void on_noicey_swtch(View view) {
        if(sw_noicy.isChecked())
        {
            editor.putString("isNotySound","1");
            editor.commit();
            Myutil.isNotfiOnNoice = true;
        }
        else
        {
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
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void goToInfo(View view) {
        startActivity(new Intent(getApplicationContext(), InfoActivity.class));
    }
}
