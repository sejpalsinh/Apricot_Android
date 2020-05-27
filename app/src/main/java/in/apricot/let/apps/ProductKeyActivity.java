package in.apricot.let.apps;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProductKeyActivity extends AppCompatActivity {


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText product_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_key);
        preferences = getSharedPreferences("apricot", MODE_PRIVATE);
        editor = preferences.edit();
        product_key = findViewById(R.id.edt_product_key);
    }

    public void Check_Product_key(View view) {
        String pro_key = product_key.getText().toString();
        if(is_Productkey(pro_key))
        {
            editor.putString("product_key",pro_key);
            editor.commit();
            if(is_Userexist())
            {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
                finish();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Product key is not valid please check it.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean is_Productkey(String key) {

        //do firebase opration and return true is product key is exist...



        return true;
    }
    private boolean is_Userexist() {
        //do firebase opration and return true is user is exist and store uname and pass in sharedpref...

        //temp store for check
        editor.putString("uname","sejsinh01@gmail.com");
        editor.putString("pass","12341234");
        editor.commit();

        return true;
    }
}
