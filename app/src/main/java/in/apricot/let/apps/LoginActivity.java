package in.apricot.let.apps;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String sp_uname,sp_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("apricot", MODE_PRIVATE);
        editor = preferences.edit();
        sp_uname = preferences.getString("uname", " ");
        sp_pass = preferences.getString("pass", " ");

    }

    public void make_login(View view) {
        EditText et_uname,et_pass;
        String uname,pass;
        et_uname = findViewById(R.id.edt_login_email);
        et_pass = findViewById(R.id.edt_login_pass);
        uname = et_uname.getText().toString();
        pass = et_pass.getText().toString();
        if(is_user(sp_uname,uname,sp_pass,pass))
        {
            editor.putString("islogin","1");
            editor.commit();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please check your email and password and try again.", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean is_user(String s1,String s2,String s3,String s4)
    {
        if(s1.equals(s2) && s3.equals(s4))
        {
            return true;
        }
        return false;
    }
}
