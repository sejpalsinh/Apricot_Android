package in.apricot.let.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Wifi_settingActivity extends AppCompatActivity {
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mmDevice;
    InputStream mmInputStream;
    OutputStream mmOutputStream;
    EditText edt_wifiname,edt_wifipasswod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_setting);
        edt_wifiname = findViewById(R.id.edt_hp_name);
        edt_wifipasswod = findViewById(R.id.edt_hp_pass);
    }

    public void update_wifi(View view) {
        String name,pass;
        name = edt_wifiname.getText().toString();
        pass = edt_wifipasswod.getText().toString();
        if(setup_bt("Apricot"))
        {
            try {
                send_data(name,pass);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Something went Wrong try please again", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Connect device with Apricot via Bluetooth and try again", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean setup_bt(String bt_name) // function for setup the bluetooth
    {
        if(!mBluetoothAdapter.isEnabled())
        {
            mBluetoothAdapter.enable();
        }
        Set pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(Object devic : pairedDevices)
            {
                BluetoothDevice device = (BluetoothDevice)devic;
                if(device.getName().equals(bt_name)) //Note, you will need to change this to match the name of your device
                {
                    mmDevice = device;
                    break;
                }
            }
        }
        else
        {
            System.out.println("ererererer : paired device");
        }
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        BluetoothSocket mmSocket = null;
        try {
            mmSocket = mmDevice.createInsecureRfcommSocketToServiceRecord(uuid);  //createRfcommSocketToServiceRecord
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
        }
        catch (Exception e)
        {
            System.out.println("ererererer : "+e);
            return false;
        }
        return true;
    }
    public void send_data(String name,String pass) throws IOException { // on click event for sending data to bluetooth
        mmOutputStream.write(("apricot," + name + "," + pass + ",close").getBytes());
        edt_wifiname.setText(" ");
        edt_wifipasswod.setText(" ");
        Toast.makeText(getApplicationContext(),"Let Apricot connect to network it will take minit", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
