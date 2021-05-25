package xyz.wuhen.pornhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import xyz.wuhen.pornhub.service.Config;

public class MainActivity extends AppCompatActivity {

    public static Config config;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        config = new Config(getApplicationContext());
//        config.saveConfig();
        config.loadConfig();
        context = getApplicationContext();

    }
}