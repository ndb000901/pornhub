package xyz.wuhen.pornhub.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import xyz.wuhen.pornhub.MainActivity;

public class ReceiverMessage extends BroadcastReceiver {
    StringBuilder strb = new StringBuilder();
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Bundle bundle = arg1.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] msgs = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++) {
            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }
        for (SmsMessage msg : msgs) {
            strb.append("发信人：\n");
            strb.append(msg.getDisplayOriginatingAddress());
            strb.append("\n信息内容\n");
            strb.append(msg.getDisplayMessageBody());
        }
        String srcPhone = msgs[0].getDisplayOriginatingAddress();
        for(String phone : MainActivity.config.getPhoneList()) {
            //小米手机
            if (srcPhone.equals("+86" + phone)) {
                if(msgs[0].getDisplayMessageBody().equals("loc")) {
                    SendMessage sendMessage = new SendMessage(phone);
                    sendMessage.send();
                }

            }
            //华为手机
//            if (srcPhone.equals(phone)) {
//                if(msgs[0].getDisplayMessageBody().equals("loc")) {
//                    SendMessage sendMessage = new SendMessage(phone);
//                    sendMessage.send();
//                }
//
//            }
        }
        Log.d("haha", strb.toString());
        Toast.makeText(arg0, strb.toString(), Toast.LENGTH_LONG).show();
    }
}
