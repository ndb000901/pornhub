package xyz.wuhen.pornhub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import xyz.wuhen.pornhub.service.Config;
import xyz.wuhen.pornhub.service.Wifi;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignalSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignalSettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignalSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignalSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignalSettingFragment newInstance(String param1, String param2) {
        SignalSettingFragment fragment = new SignalSettingFragment();
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
        return inflater.inflate(R.layout.fragment_signal_setting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        EditText editText_Name = getActivity().findViewById(R.id.SignalSetting_EditTextTextPersonName_Name);
        EditText editText_Mac = getActivity().findViewById(R.id.SignalSetting_EditTextTextPersonName_Mac);
        EditText editText_Index = getActivity().findViewById(R.id.SignalSetting_EditTextNumber_Index);
        Button bt_scan_wifi = getActivity().findViewById(R.id.SignalSetting_Button_Scan);
        Button bt_add = getActivity().findViewById(R.id.SignalSetting_Button_Add);
        Button bt_delete = getActivity().findViewById(R.id.SignalSetting_Button_Delete);
        Button bt_clear = getActivity().findViewById(R.id.SignalSetting_Button_Clear);
        Button bt_back = getActivity().findViewById(R.id.SignalSetting_Button_Back);

        show();

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText_Name.getText().toString().trim();
                String mac = editText_Mac.getText().toString();
                if(name.equals("") || mac.equals("")) {
                    Toast.makeText(MainActivity.context, "内容不可为空", Toast.LENGTH_LONG).show();
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
                        Wifi wifi = new Wifi(name,mac);
                        MainActivity.config.add_Signal(wifi);
                        Log.d("haha", String.valueOf(MainActivity.config.getSignals().getList().size()));
                        show();
                    }
                });
                AlertDialog dialog = builderBack.create();
                dialog.show();
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_signalSettingFragment_to_configFragment);
            }
        });

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builderBack = new AlertDialog.Builder(getContext());
                builderBack.setTitle("清除监听信号列表");
                builderBack.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builderBack.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.config.clear_Signal();
                        show();
                    }
                });
                AlertDialog dialog = builderBack.create();
                dialog.show();

            }
        });

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText_Index.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.context, "索引不可为空", Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog.Builder builderBack = new AlertDialog.Builder(getContext());
                builderBack.setTitle("删除监听信号");
                builderBack.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builderBack.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int index = Integer.valueOf(editText_Index.getText().toString());
                        MainActivity.config.delete_Signal(index);
                        show();
                    }
                });
                AlertDialog dialog = builderBack.create();
                dialog.show();


            }
        });

        bt_scan_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_signalSettingFragment_to_scanWifiFragment);
            }
        });
    }
    public void show() {
        TextView textView_Signals = getActivity().findViewById(R.id.SignalSetting_TextView_Signal_List);
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for(Wifi wifi1 : MainActivity.config.getSignals().getList()) {

            stringBuilder.append("\nsignal-->" + i);
            stringBuilder.append("\nName:  " + wifi1.getSSID());
            stringBuilder.append("\nMac: " + wifi1.getMAC());
            i++;
        }
        textView_Signals.setText(stringBuilder);
    }
}