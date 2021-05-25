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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormatSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormatSettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormatSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormatSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormatSettingFragment newInstance(String param1, String param2) {
        FormatSettingFragment fragment = new FormatSettingFragment();
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
        return inflater.inflate(R.layout.fragment_format_setting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button bt_update = getActivity().findViewById(R.id.FormatSetting_Button_Update);
        Button bt_back = getActivity().findViewById(R.id.FormatSetting_Button_Back);
        EditText editText = getActivity().findViewById(R.id.Format_editTextTextPersonName);

        show();
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_formatSettingFragment_to_configFragment);
            }
        });

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String format = editText.getText().toString();
                if(format.trim().length() == 0) {
                    Toast.makeText(MainActivity.context, "内容不可为空", Toast.LENGTH_LONG).show();
                    return;
                }
                int i = 0;
                for(char ch : format.toCharArray()) {
                    if(ch == '*') {
                        i++;
                    }
                }

                if(i != MainActivity.config.getSignalNumber()) {
                    Toast.makeText(MainActivity.context, "发送成功" + "星号(*)占位符必须与监听信号数对应", Toast.LENGTH_LONG).show();
                    return;
                }


                AlertDialog.Builder builderBack = new AlertDialog.Builder(getContext());
                builderBack.setTitle("更新消息格式");
                builderBack.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builderBack.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.config.setMessageFormat(format);
                        show();
                    }
                });
                AlertDialog dialog = builderBack.create();
                dialog.show();
            }
        });
    }

    public void show() {
        TextView textView = getActivity().findViewById(R.id.FormatSetting_TextView_Format);
        textView.setText(MainActivity.config.getMessageFormat());
    }
}