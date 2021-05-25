package xyz.wuhen.pornhub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import xyz.wuhen.pornhub.service.Wifi;
import xyz.wuhen.pornhub.service.WifiScan;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanWifiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanWifiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static List<ScanResult> list;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScanWifiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanWifiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanWifiFragment newInstance(String param1, String param2) {
        ScanWifiFragment fragment = new ScanWifiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_wifi, container, false);
    }



    @Override
    public void onStart() {
        super.onStart();

        Button bt_update = getActivity().findViewById(R.id.ScanWifi_Button_Update);
        Button bt_add = getActivity().findViewById(R.id.ScanWifi_Button_Add);
        Button bt_back = getActivity().findViewById(R.id.ScanWifi_Button_Back);
        EditText editText_Index = getActivity().findViewById(R.id.ScanWifi_EditTextNumber_Index);

        show();

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_Index.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.context, "索引不可为空", Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog.Builder builderBack = new AlertDialog.Builder(getContext());
                builderBack.setTitle("增加监听信号");
                builderBack.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builderBack.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int index = Integer.parseInt(editText_Index.getText().toString());
                        if(list.size() != 0 && index >= 0 && index < list.size()) {
                            Wifi wifi = new Wifi(list.get(index).SSID,list.get(index).BSSID);
                            MainActivity.config.add_Signal(wifi);
                            Toast.makeText(MainActivity.context, "信号添加成功", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                AlertDialog dialog = builderBack.create();
                dialog.show();



            }
        });

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = getActivity().findViewById(R.id.ScanWifi_TextView_WifiList_List);
                textView.setText("正在刷新......");
                show();
                Toast.makeText(MainActivity.context, "刷新成功", Toast.LENGTH_LONG).show();
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_scanWifiFragment_to_signalSettingFragment);
            }
        });
    }

    public void show() {
        TextView textView = getActivity().findViewById(R.id.ScanWifi_TextView_WifiList_List);
        WifiScan wifiScan = new WifiScan(getActivity().getApplicationContext());
        ScanWifiFragment.list = wifiScan.scan();
        if(ScanWifiFragment.list == null) {
            return;
        }
        if(ScanWifiFragment.list.size() == 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for(ScanResult result : ScanWifiFragment.list) {
            stringBuilder.append("信号" + i + "\n");
            stringBuilder.append("名称：" + result.SSID + "\n");
            stringBuilder.append("MAC: " + result.BSSID + "\n");
            stringBuilder.append("强度: " + result.level + "\n");
            stringBuilder.append("---------------\n");
            i++;
        }
        textView.setText(stringBuilder);

    }

}