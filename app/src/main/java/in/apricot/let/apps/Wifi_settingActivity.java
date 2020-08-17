package in.apricot.let.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class Wifi_settingActivity extends AppCompatActivity {
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mmDevice;
    InputStream mmInputStream;
    OutputStream mmOutputStream;
    EditText edt_wifiname,edt_wifipasswod;
    TextView tv_wifi_info;
    TextView tv_hide1,tv_hide2;
    LinearLayout  lv_hide;
    Boolean is_bt_connect,is_bt_onprocess;
    Button btn_connect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_setting);
        edt_wifiname = findViewById(R.id.edt_hp_name);
        edt_wifipasswod = findViewById(R.id.edt_hp_pass);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        tv_wifi_info = findViewById(R.id.wifi_info);
        btn_connect = findViewById(R.id.bt_connect);
        tv_wifi_info.setText("please connect to apricot via bluetooth to proceed by click on connect");
        lv_hide = findViewById(R.id.lv_hide);
        tv_hide1 = findViewById(R.id.tv_hide1);
        tv_hide2 = findViewById(R.id.tv_hide2);
        tv_hide1.setVisibility(View.GONE);
        tv_hide2.setVisibility(View.GONE);
        edt_wifiname.setVisibility(View.GONE);
        edt_wifipasswod.setVisibility(View.GONE);
        findViewById(R.id.btn_wifi_update).setVisibility(View.GONE);
        is_bt_connect = false;
        is_bt_onprocess = false;
    }

    public void update_wifi(View view) {
        String name,pass;
        name = edt_wifiname.getText().toString();
        pass = edt_wifipasswod.getText().toString();
        if(is_bt_connect) {
            try {
                send_data(name, pass);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Something went Wrong try please again", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Something went Wrong With connection please reopen application", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean setup_bt() // function for setup the bluetooth
    {
        String bt_name = "Apricot";
        if(!pairDevice())
        {
            Toast.makeText(this, "Make sure Red light of Apricot is on", Toast.LENGTH_SHORT).show();
            return false;
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
        Toast.makeText(getApplicationContext(),"Let Apricot connect to network it will take 1 or 2 mint wait for green light ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    private boolean pairDevice() {
        if(!mBluetoothAdapter.isEnabled())
        {
            mBluetoothAdapter.enable();
        }
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice("B8:27:EB:DA:25:25");
        try {
            Log.d("pairDevice()", "Start Pairing...");
            Method m = device.getClass().getMethod("createBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
            Log.d("pairDevice()", "Pairing finished.");
            return true;
        } catch (Exception e) {
            Log.e("pairDevice()", e.getMessage());
            return false;
        }
    }


    public void btn_bt_connect(View view) {
        if(is_bt_onprocess)
        {
            Toast.makeText(getApplicationContext(),"Please wait process is on going", Toast.LENGTH_SHORT).show();
            return;
        }
        is_bt_onprocess = true;
        if(setup_bt())
        {
            tv_wifi_info.setText("please enter your home network name and password to connect your apricot to cloud. \n Dont worry, you only have to do this process once to set up your apricot in future it will automatically connect to your home network");
            btn_connect.setVisibility(View.GONE);
            is_bt_connect = true;
            tv_hide1.setVisibility(View.VISIBLE);
            tv_hide2.setVisibility(View.VISIBLE);
            edt_wifiname.setVisibility(View.VISIBLE);
            edt_wifipasswod.setVisibility(View.VISIBLE);
            findViewById(R.id.btn_wifi_update).setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Check your notification and try again", Toast.LENGTH_SHORT).show();
            tv_wifi_info.setText("please connect to apricot via bluetooth to proceed by click on connect \n\nMake sure red light of Apricot is on\n\nIf you will get any notification of pop up just click on pair and connect again");
        }
        is_bt_onprocess = false;
    }
}
