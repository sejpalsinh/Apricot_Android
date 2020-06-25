package in.apricot.let.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Intent mServiceIntent;
    private my_service mYourService;
    Switch sw_main,sw_noicy,sw_movement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sw_main = findViewById(R.id.swch_main);
        sw_noicy = findViewById(R.id.swch_noicy);
        sw_movement = findViewById(R.id.swch_moment);
    }

    public void on_notification_click(View view) {
        if(sw_main.isChecked())
        {
            Toast.makeText(this, "On", Toast.LENGTH_SHORT).show();
            sw_movement.setEnabled(true);
            sw_noicy.setEnabled(true);
            mYourService = new my_service();
            mServiceIntent = new Intent(this, mYourService.getClass());
            if (!isMyServiceRunning(mYourService.getClass())) {
                startService(mServiceIntent);
            }
        }

        else {
            Toast.makeText(this, "off", Toast.LENGTH_SHORT).show();
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
            Myutil.isNotfiOnMovement = true;
        }
        else
        {
            Myutil.isNotfiOnMovement = false;
        }
    }

    public void on_noicey_swtch(View view) {
        if(sw_noicy.isChecked())
        {
            Myutil.isNotfiOnNoice = true;
        }
        else
        {
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
}
