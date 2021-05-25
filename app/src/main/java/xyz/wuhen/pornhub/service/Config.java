package xyz.wuhen.pornhub.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import xyz.wuhen.pornhub.R;

public class Config {
    private int signalNumber;
    private int phoneNumber;
    private Signals signals;
    private String messageFormat;
    private String help;
    private ArrayList<String> phoneList;
    private Context context;

    public Config(Context context) {
        this.context = context;
        this.phoneList = new ArrayList<>();
        this.phoneNumber = 0;
        this.signalNumber = 0;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSignalNumber() {
        return signalNumber;
    }

    public void setSignalNumber(int signalNumber) {
        this.signalNumber = signalNumber;
    }

    public Signals getSignals() {
        if(this.signals == null) {
            signals = new Signals();
        }
        return signals;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
        saveConfig();
    }

    public String getHelp() {
        return help;
    }

    public ArrayList<String> getPhoneList() {
        return phoneList;
    }

    public void update_messageFormat(String messageFormat) {
        if(messageFormat == null) {
            messageFormat = new String("");
        }
        this.messageFormat = messageFormat;
        saveConfig();
    }

    public void add_Signal(Wifi wifi) {
        this.getSignals().getList().add(wifi);
        this.setSignalNumber(this.getSignalNumber() + 1);
        saveConfig();
    }

    public void add_Phone(String phone) {
        if(this.phoneList == null) {
            this.phoneList = new ArrayList<>();
        }
        this.getPhoneList().add(phone);
        this.setPhoneNumber(this.getPhoneNumber() + 1);
        saveConfig();
    }

    public void delete_Phone(int index) {
        if(this.getPhoneList().size() != 0 && index >= 0 && index < this.getPhoneList().size()) {
            this.getPhoneList().remove(index);
            this.setPhoneNumber(this.getPhoneNumber() + 1);
            saveConfig();
        }

    }

    public void delete_Signal(int index) {
        if(this.getSignals().getList().size() != 0 && index >= 0 && index < this.getSignals().getList().size()) {
            this.getSignals().getList().remove(index);
            this.setSignalNumber(this.getSignalNumber() - 1);
            saveConfig();
        }
    }

    public void clear_Phone() {
        this.getPhoneList().clear();
        this.setPhoneNumber(0);
        saveConfig();
    }

    public void clear_Signal() {
        this.getSignals().getList().clear();
        this.setSignalNumber(0);
        saveConfig();
    }

    public void saveConfig() {
        String phoneList = context.getString(R.string.data_phone_list_name);
        String signals = context.getString(R.string.data_signals_name);
        String count = context.getString(R.string.data_count);
        String messageFormat = context.getString(R.string.data_messageFormat_name);

        String key_phone = context.getString(R.string.key_phone);
        String key_signal_Name = context.getString(R.string.key_signal_name);
        String key_signal_Mac = context.getString(R.string.key_signal_Mac);
        String key_phoneN = context.getString(R.string.key_phone_number);
        String key_signalN = context.getString(R.string.key_signal_number);
        String key_format = context.getString(R.string.key_format);

        SharedPreferences shp_count = context.getSharedPreferences(count,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_count = shp_count.edit();
        editor_count.putInt(key_phoneN,getPhoneNumber());
        editor_count.putInt(key_signalN,getSignalNumber());
        editor_count.apply();



        SharedPreferences shp_phone = context.getSharedPreferences(phoneList,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_phone = shp_phone.edit();
        editor_phone.clear();
        editor_phone.apply();
        for(int i = 1;i <= this.getPhoneList().size();i++)
        {
            editor_phone.putString(key_phone + (i - 1),this.getPhoneList().get(i - 1));
        }
        editor_phone.apply();


        SharedPreferences shp_signal = context.getSharedPreferences(signals,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_signal = shp_signal.edit();
        editor_signal.clear();
        editor_signal.apply();
        for(int i = 1;i <= this.getSignals().getList().size();i++)
        {
            editor_signal.putString(key_signal_Name + (i - 1),this.getSignals().getList().get(i - 1).getSSID());
            editor_signal.putString(key_signal_Mac + (i - 1),this.getSignals().getList().get(i - 1).getMAC());
        }
        editor_signal.apply();


        SharedPreferences shp_format = context.getSharedPreferences(messageFormat,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_format = shp_format.edit();
        editor_format.putString(key_format,this.getMessageFormat());
        editor_format.apply();


    }
    public void loadConfig() {
        String phoneList = context.getString(R.string.data_phone_list_name);
        String signals = context.getString(R.string.data_signals_name);
        String count = context.getString(R.string.data_count);
        String messageFormat = context.getString(R.string.data_messageFormat_name);

        String key_phone = context.getString(R.string.key_phone);
        String key_signal_Name = context.getString(R.string.key_signal_name);
        String key_signal_Mac = context.getString(R.string.key_signal_Mac);
        String key_phoneN = context.getString(R.string.key_phone_number);
        String key_signalN = context.getString(R.string.key_signal_number);
        String key_format = context.getString(R.string.key_format);

        SharedPreferences shp_count = context.getSharedPreferences(count,Context.MODE_PRIVATE);
        if(shp_count.getAll().size() != 0) {
            this.setPhoneNumber(shp_count.getInt(key_phoneN,0));
            this.setSignalNumber(shp_count.getInt(key_signalN,0));
        }

        SharedPreferences shp_phone = context.getSharedPreferences(phoneList,Context.MODE_PRIVATE);
        if(shp_phone.getAll().size() != 0) {
            for(int i = 0;i < this.getPhoneNumber();i++) {
                this.getPhoneList().add(shp_phone.getString(key_phone + (i),"null"));
            }
        }

        SharedPreferences shp_signals = context.getSharedPreferences(signals,Context.MODE_PRIVATE);
        if(shp_signals.getAll().size() != 0) {
            for(int i = 0;i < this.getSignalNumber();i++) {
                Wifi wifi = new Wifi(shp_signals.getString(key_signal_Name + i,"null"),shp_signals.getString(key_signal_Mac + 1,"null"));
                this.getSignals().getList().add(wifi);
            }
        }

        SharedPreferences shp_format = context.getSharedPreferences(messageFormat,Context.MODE_PRIVATE);
        if(shp_format.getAll().size() != 0) {
            this.setMessageFormat(shp_format.getString(key_format,"null"));
        }

    }


}
