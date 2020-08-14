package in.apricot.let.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Wifi_Info_Activity extends AppCompatActivity {
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mmDevice;
    InputStream mmInputStream;
    OutputStream mmOutputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi__info_);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBluetoothAdapter.isEnabled())
        {
            mBluetoothAdapter.enable();
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

    public void btn_wifi_page(View view) {
        if(setup_bt("Apricot"))
        {
            startActivity(new Intent(getApplicationContext(),Wifi_settingActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Connect device with Apricot via Bluetooth and try again", Toast.LENGTH_SHORT).show();
        }
    }
}
