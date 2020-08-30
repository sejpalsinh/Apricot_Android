package in.apricot.let.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    Switch sw_noicy,sw_movement;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sw_noicy = findViewById(R.id.swch_noicy);
        sw_movement = findViewById(R.id.swch_moment);
        preferences = getSharedPreferences("apricot", MODE_PRIVATE);
        myRef = database.getReference("User/"+Myutil.product_key_string+"/");
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
            if(sw_noicy.isChecked())
            {
                myRef.child("device").child(Objects.requireNonNull(FirebaseInstanceId.getInstance().getToken())).setValue(3);
            }
            else
            {
                myRef.child("device").child(Objects.requireNonNull(FirebaseInstanceId.getInstance().getToken())).setValue(2);
            }
            editor.putString("isNotyMove","1");
            editor.commit();
            Myutil.isNotfiOnMovement = true;
        }
        else
        {
            if(sw_noicy.isChecked())
            {
                myRef.child("device").child(Objects.requireNonNull(FirebaseInstanceId.getInstance().getToken())).setValue(1);
            }
            else
            {
                myRef.child("device").child(Objects.requireNonNull(FirebaseInstanceId.getInstance().getToken())).setValue(0);
            }
            editor.putString("isNotyMove","0");
            editor.commit();
            Myutil.isNotfiOnMovement = false;
        }
    }

    public void on_noicey_swtch(View view) {
        if(sw_noicy.isChecked())
        {
            if(sw_movement.isChecked())
            {
                myRef.child("device").child(Objects.requireNonNull(FirebaseInstanceId.getInstance().getToken())).setValue(3);
            }
            else
            {
                myRef.child("device").child(Objects.requireNonNull(FirebaseInstanceId.getInstance().getToken())).setValue(1);
            }
            editor.putString("isNotySound","1");
            editor.commit();
            Myutil.isNotfiOnNoice = true;
        }
        else
        {
            if(sw_movement.isChecked())
            {
                myRef.child("device").child(Objects.requireNonNull(FirebaseInstanceId.getInstance().getToken())).setValue(2);
            }
            else
            {
                myRef.child("device").child(Objects.requireNonNull(FirebaseInstanceId.getInstance().getToken())).setValue(0);
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
