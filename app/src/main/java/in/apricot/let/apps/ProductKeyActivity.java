package in.apricot.let.apps;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductKeyActivity extends AppCompatActivity {


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText product_key;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");


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
        is_Productkey(pro_key);
    }

    private void is_Productkey(String key) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(key))
                {
                    Log.d("service","yes is has has");
                    editor.putString("product_key",key);
                    editor.commit();
                    myRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("username") && dataSnapshot.hasChild("password"))
                            {
                                String uname = dataSnapshot.child("username").getValue(String.class);
                                String pass = dataSnapshot.child("password").getValue(String.class);
                                Log.d("service","yes user he "+uname+" : "+pass);
                                editor.putString("uname",uname);
                                editor.putString("pass",pass);
                                editor.commit();
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                finish();
                            }
                            else
                            {
                                Log.d("service","nononono");
                                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("user check error ",databaseError.getDetails());
                        }
                    });
                }
                else
                {
                    Log.d("service","nononono");
                    Toast.makeText(getApplicationContext(),"Product key is not valid please check it.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("key check error ",databaseError.getDetails());
            }
        });
    }

}
