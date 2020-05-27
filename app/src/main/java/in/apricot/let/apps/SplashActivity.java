package in.apricot.let.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences = getSharedPreferences("apricot", MODE_PRIVATE);
        editor = preferences.edit();



        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(!preferences.getString("islogin", " ").equals(" "))
                {
                    System.out.println("gogogogo");
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), ProductKeyActivity.class));
                    finish();
                }
            }
        },1000);

    }

}
