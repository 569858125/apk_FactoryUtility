package com.datamax.factorytest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.SystemProperties;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.provider.Settings;
import android.util.Log;
import java.util.List;
import java.io.File;

@SuppressLint("NewApi")
public class Constant {
    private static String TAG = "DmLauncher_Constant";
    private static final String SD_PATH = "sdcard";
    private final static String STORAGE_PATH = "/storage";

    public static int getSettingsProviderInt(Context context, String key, int defaultValue) {
        return Settings.System.getInt(context.getContentResolver(), key, defaultValue);
    }

    public static boolean putSettingsProviderInt(Context context, String key, int value) {
        return Settings.System.putInt(context.getContentResolver(), key, value);
    }

    public static String getInitPassword() {
        String str = SystemProperties.get("ro.dvb.initAudio.password");
        if (str == null || str.length() == 0) {
            str = "0000";
        }
        return str;
    }

    public static String getSuperPassword() {
        String str = SystemProperties.get("ro.dvb.super.password");
        if (str == null || str.length() == 0) {
            str = "3547";
        }
        return str;
    }

    public static boolean isEthConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info.isConnectedOrConnecting()) {
            Log.d(TAG, "isEthConnected() true");
            return true;
        }

        return false;
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info.isConnectedOrConnecting()) {
            Log.d(TAG, "isWifiConnected() true");
            return true;
        }

        return false;
    }

    public static boolean isWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    public int isudiskExists() {
        File storage_file = new File(STORAGE_PATH);
        int num = 0;
        if (storage_file.exists() && storage_file.isDirectory()) {
            if (storage_file.listFiles() != null && storage_file.listFiles().length > 0) {
                for (File file : storage_file.listFiles()) {
                    String path = file.getAbsolutePath();
                    if (path.startsWith(STORAGE_PATH + "/" + "udisk")
                            && Environment.getStorageState(file).equals(Environment.MEDIA_MOUNTED)) {
                        num++;
                    }
                }
            }
            return num;
        }

        return 0;
    }

    public static boolean isSdcardExists() {
        File storage_file = new File(STORAGE_PATH);

        if (storage_file.exists() && storage_file.isDirectory()) {
            if (storage_file.listFiles() != null && storage_file.listFiles().length > 0) {
                for (File file : storage_file.listFiles()) {
                    String path = file.getAbsolutePath();
                    if (path.startsWith(STORAGE_PATH + "/" + SD_PATH)
                            && Environment.getStorageState(file).equals(Environment.MEDIA_MOUNTED)) {

                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static int isudiskExists(Context context) {
        Log.d(TAG, "isudiskExists");
        int num = 0;
        StorageManager storageManager = StorageManager.from(context.getApplicationContext());
        //    StorageManager storageManager = (StorageManager) context.getApplicationContext().getSystemService(Context.STORAGE_SERVICE);

        List<VolumeInfo> volumes = storageManager.getVolumes();

        Log.d(TAG, "volumes is null?  " + (volumes == null));

        for (VolumeInfo volInfo : volumes) {
            DiskInfo diskInfo = volInfo.getDisk();
            if (diskInfo != null && diskInfo.isUsb()) {
                String sdcardState = VolumeInfo.getEnvironmentForState(volInfo.getState());
                if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                    Log.d(TAG, "isudiskExists");
                    num++;
                }
            }
        }
        Log.d(TAG, "isudiskExists" + num);
        return num;
    }

    public static boolean isSdcardExists(Context context) {
        StorageManager storageManager = StorageManager.from(context.getApplicationContext());
        //    StorageManager storageManager = (StorageManager) context.getApplicationContext().getSystemService(Context.STORAGE_SERVICE);
        List<VolumeInfo> volumes = storageManager.getVolumes();
        for (VolumeInfo volInfo : volumes) {
            DiskInfo diskInfo = volInfo.getDisk();
            if (diskInfo != null && diskInfo.isSd()) {
                String sdcardState = VolumeInfo.getEnvironmentForState(volInfo.getState());
                if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                    return true;
                }
            }

        }
        return false;
    }

}
