package xyz.wuhen.pornhub;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import xyz.wuhen.pornhub.service.Wifi;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhoneSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhoneSettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhoneSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhoneSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhoneSettingFragment newInstance(String param1, String param2) {
        PhoneSettingFragment fragment = new PhoneSettingFragment();
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
        return inflater.inflate(R.layout.fragment_phone_setting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        EditText editText_add = getActivity().findViewById(R.id.PhoneSetting_EditTextPhone_Add);
        EditText editText_delete = getActivity().findViewById(R.id.PhoneSetting_EditTextNumber_Delete);

        Button bt_add = getActivity().findViewById(R.id.PhoneSetting_Button_Add);
        Button bt_delete = getActivity().findViewById(R.id.PhoneSetting_Button_Delete);
        Button bt_clear = getActivity().findViewById(R.id.PhoneSetting_Button_Clear);
        Button bt_back = getActivity().findViewById(R.id.PhoneSetting_Button_Back);

        show();

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_add.getText().toString().trim().length() == 0) {
                    return;
                }

                AlertDialog.Builder builderBack = new AlertDialog.Builder(getContext());
                builderBack.setTitle("清除监听号码列表");
                builderBack.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builderBack.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.config.add_Phone(editText_add.getText().toString());
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
                if(editText_delete.getText().toString().trim().length() == 0) {
                    return;
                }

                AlertDialog.Builder builderBack = new AlertDialog.Builder(getContext());
                builderBack.setTitle("删除监听号码");
                builderBack.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builderBack.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int index = Integer.valueOf(editText_delete.getText().toString());
                        MainActivity.config.delete_Phone(index);
                        show();
                    }
                });
                AlertDialog dialog = builderBack.create();
                dialog.show();


            }
        });

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builderBack = new AlertDialog.Builder(getContext());
                builderBack.setTitle("清除监听号码列表");
                builderBack.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builderBack.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.config.clear_Phone();
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
                controller.navigate(R.id.action_phoneSettingFragment_to_configFragment);
            }
        });
    }

    public void show() {
        TextView textView_phoneList = getActivity().findViewById(R.id.PhoneSetting_TextView_Phone_List);
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for(String phone : MainActivity.config.getPhoneList()) {
            stringBuilder.append(i + "-->" + phone + "\n");
            i++;
        }
        textView_phoneList.setText(stringBuilder);
    }
}