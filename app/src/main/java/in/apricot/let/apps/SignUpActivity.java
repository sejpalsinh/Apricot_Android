package in.apricot.let.apps;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        preferences = getSharedPreferences("apricot", MODE_PRIVATE);
        editor = preferences.edit();
        key = preferences.getString("product_key", " ");
        myRef = myRef.child(key);

    }

    public void do_signup(View view) {
        EditText uname,pass,repass;
        uname = findViewById(R.id.edt_signup_email);
        pass = findViewById(R.id.edt_signup_pass);
        repass = findViewById(R.id.edt_signup_repass);
        String email,password,repassword;
        email = uname.getText().toString();
        password = pass.getText().toString();
        repassword = repass.getText().toString();
        if(password.equals(repassword) && !password.equals(""))
        {
            if(isValidEmail(email))
            {
                write_to_firebase(email,password);
                editor.putString("uname",email);
                editor.putString("pass",password);
                editor.putString("islogin","1");
                editor.commit();
                startActivity(new Intent(getApplicationContext(),InfoActivity.class));
                finish();
            }
            else
            {
                Toast.makeText(this, "Please Check your mail ID & try agian", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Both password must be same and not empty", Toast.LENGTH_SHORT).show();
            pass.setText("");
            repass.setText("");
        }
    }

    private void write_to_firebase(String uname,String pass) {
        //write this 2 string in db
        myRef.child("username").setValue(uname);
        myRef.child("password").setValue(pass);
    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
