package xyz.wuhen.pornhub.service;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class WifiScan {
    private Message message;
    Context context;

    public WifiScan(Context context) {
        this.context = context;
    }

    public List<ScanResult> scan() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

// Level of a Scan Result
        wifiManager.startScan();
        List<ScanResult> wifiList = wifiManager.getScanResults();
//        Log.d("haha", wifiList.toString());
//        for (ScanResult scanResult : wifiList) {
//
//            Log.d("haha", scanResult.SSID);
//            Log.d("haha", scanResult.BSSID);
//            Log.d("haha", String.valueOf(scanResult.level));
////            Log.d("haha", scanResult.);
////            int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
////            System.out.println("Level is " + level + " out of 5");
//        }

// Level of current connection
//        int rssi = wifiManager.getConnectionInfo().getRssi();
//        int level = WifiManager.calculateSignalLevel(rssi, 5);
//        System.out.println("Level is " + level + " out of 5");
        return wifiList;
    }
}
