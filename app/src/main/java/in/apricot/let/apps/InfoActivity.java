package in.apricot.let.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    public void go_to_mainscreen(View view) {
        startActivity(new Intent(getApplicationContext(),Wifi_Info_Activity.class));
        finish();
    }
}
