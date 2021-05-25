package xyz.wuhen.pornhub.service;

public class Wifi {
    private String SSID;
    private String MAC;
    private int db;

    public Wifi(String SSID,String MAC) {
        this.SSID = SSID;
        this.MAC = MAC;
    }

    public Wifi(String SSID,String MAC,int db) {
        this.SSID = SSID;
        this.MAC = MAC;
        this.db = db;
    }
    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }
}
