package in.apricot.let.apps;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {


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
    }

    public void goto_webshow(View view) {
        if(!isOnline())
        {
            Toast.makeText(getApplicationContext(),"Please Check Internet Connection.", Toast.LENGTH_SHORT).show();
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
