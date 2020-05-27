package in.apricot.let.apps;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
        }
        catch (Exception e)
        {
            System.out.println("thisthis : "+e);
        }
    }

    public void goto_webshow(View view) {
        startActivity(new Intent(getApplicationContext(),WebshowActivity.class));
    }

    public void goto_settings(View view) {
        startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
    }
}
