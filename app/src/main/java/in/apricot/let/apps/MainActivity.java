package in.apricot.let.apps;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {


    Intent mServiceIntent;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Myutil.isAllpremission(this);
        try {
            setContentView(R.layout.activity_main);
        }
        catch (Exception e)
        {
            System.out.println("thisthis : "+e);
        }
        preferences = getSharedPreferences("apricot", MODE_PRIVATE);
        Myutil.product_key_string = preferences.getString("product_key", "0");
        myRef = database.getReference("User/"+Myutil.product_key_string+"/");
        myRef.child("noty").setValue(0);
        editor = preferences.edit();
        if(preferences.getString("isNotify", "0").equals("1")) {
            my_service mYourService = new my_service();
            mServiceIntent = new Intent(this, mYourService.getClass());
            if (!isMyServiceRunning(mYourService.getClass())) {
                startService(mServiceIntent);
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    public void goto_webshow(View view) {
        if(!isOnline())
        {
            Toast.makeText(getApplicationContext(),"Please Check Internet Connection.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Myutil.isSomeConnected)
        {
            Toast.makeText(getApplicationContext(),"Some one is connected try after some time.", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(getApplicationContext(),WebshowActivity.class));
    }

    public void goto_settings(View view) {
        if(!isOnline())
        {
            Toast.makeText(getApplicationContext(),"Please Check Internet Connection.", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
    }
    public boolean isOnline() {
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
