package xyz.wuhen.pornhub;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import xyz.wuhen.pornhub.service.Wifi;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        TextView signalList = getActivity().findViewById(R.id.Home_TextView_Signals_List);
        Button help = getActivity().findViewById(R.id.Home_Button_Help);
        Button config = getActivity().findViewById(R.id.Home_Button_Config);
        show();

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_helpFragment);
            }
        });

        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_configFragment);
            }
        });


    }

    public void show() {
        showSignals();
        showPhoneList();
        showFormat();
    }

    public void showSignals() {
        TextView textView_Signals = getActivity().findViewById(R.id.Home_TextView_Signals_List);
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

    public void showPhoneList() {
        TextView textView_phoneList = getActivity().findViewById(R.id.Home_TextView_Phone_List);
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for(String phone : MainActivity.config.getPhoneList()) {
            stringBuilder.append(i + "-->" + phone + "\n");
            i++;
        }
        textView_phoneList.setText(stringBuilder);
    }

    public void showFormat() {
        TextView textView = getActivity().findViewById(R.id.Home_TextView_Format_List);
        textView.setText(MainActivity.config.getMessageFormat());
    }
}