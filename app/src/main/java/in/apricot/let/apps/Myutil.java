package in.apricot.let.apps;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.net.InetAddress;

import static androidx.core.content.ContextCompat.getSystemService;

public class Myutil {
    public static boolean isInternetNetwork(Context context)
    {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e)
        {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        return false;
    }
    public static boolean isAllpremission(Activity activity)
    {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.CAMERA
        };
        if (!hasPermissions(activity.getApplicationContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
            return hasPermissions(activity.getApplicationContext(), PERMISSIONS);
        }
        else
        {
            return true;
        }
    }
    public static String getProductKey()
    {
        return "ok";
    }
    public static String getUname()
    {
        return "ok";
    }
    public static String getPassword()
    {
        return "ok";
    }
    public static boolean isNotfiOnNoice =false;
    public static boolean isNotfiOnMovement =false;
    public static boolean isSomeConnected =false;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


}
