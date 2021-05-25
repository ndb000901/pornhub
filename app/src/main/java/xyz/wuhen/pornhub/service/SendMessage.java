package xyz.wuhen.pornhub.service;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.wuhen.pornhub.MainActivity;
import xyz.wuhen.pornhub.ScanWifiFragment;

public class SendMessage {
    private String phone;
    public static List<ScanResult> list;

    public SendMessage(String phone) {
        this.phone = phone;
    }
    public void send() {
        if(phone == null) {
            return;
        }

        ArrayList<Wifi> signals = MainActivity.config.getSignals().getList();
        ArrayList<Integer> dbList = new ArrayList<>();
        WifiScan wifiScan = new WifiScan(MainActivity.context);
        SendMessage.list = wifiScan.scan();
        if(SendMessage.list == null) {
            return;
        }
        if(SendMessage.list.size() == 0) {
            return;
        }

        for(Wifi wifi : signals) {
            int flag = 0;
            for(ScanResult result : SendMessage.list) {
                if(wifi.getMAC().equals(result.BSSID)) {
                    dbList.add(result.level);
                    flag = 1;
                    break;
                }
            }
            if(flag == 0) {
                dbList.add(0);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();

        String format = MainActivity.config.getMessageFormat();
        int i = 0;
        for(char ch : format.toCharArray()) {
            if(ch == '*') {
                stringBuilder.append(dbList.get(i) * -1);
                i++;
            } else {
                stringBuilder.append(ch);
            }

        }

        SmsManager massage = SmsManager.getDefault();
        massage.sendTextMessage(phone, null, stringBuilder.toString(), null, null);
        Toast.makeText(MainActivity.context, "发送成功" + stringBuilder, Toast.LENGTH_LONG).show();
    }

}
