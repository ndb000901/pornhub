package xyz.wuhen.pornhub.service;

import java.util.ArrayList;

public class Signals {
    private ArrayList<Wifi> list;
    public ArrayList<Wifi> getList() {
        if(list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

}
