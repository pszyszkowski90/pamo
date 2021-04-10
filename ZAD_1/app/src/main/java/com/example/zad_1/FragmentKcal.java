package com.example.zad_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentKcal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentKcal extends Fragment {
    private EditText weightValueView;
    private EditText heightValueView;
    private EditText ageValueView;
    private RadioGroup sexValueView;
    private TextView kcalValueTextView;
    private Button button;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentKcal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentKcal.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentKcal newInstance(String param1, String param2) {
        FragmentKcal fragment = new FragmentKcal();
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
        View view = inflater.inflate(R.layout.fragment_kcal, container, false);
        weightValueView = (EditText) view.findViewById(R.id.weightKcalTextView);
        heightValueView = (EditText) view.findViewById(R.id.heightKcalTextView);
        ageValueView = (EditText) view.findViewById(R.id.ageTextView);
        sexValueView = (RadioGroup) view.findViewById(R.id.sexTextView);
        kcalValueTextView = (TextView) view.findViewById(R.id.kcalValueTextView);
        button = (Button)view.findViewById(R.id.buttonWhatEat);
        button.setVisibility(View.GONE);
        weightValueView.addTextChangedListener(watcher);
        heightValueView.addTextChangedListener(watcher);
        ageValueView.addTextChangedListener(watcher);
        sexValueView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                calculate();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    private static final NumberFormat kcalFormat =
            NumberFormat.getNumberInstance();
    private void calculate() {

        int height = Integer.parseInt(!heightValueView.getText().toString().isEmpty() ? heightValueView.getText().toString() : "0");
        int age = Integer.parseInt(!ageValueView.getText().toString().isEmpty() ? ageValueView.getText().toString() : "0");
        double weight = Double.parseDouble(!weightValueView.getText().toString().isEmpty() ? weightValueView.getText().toString() : "0");
        String sex = "";
        RadioButton checkedRadioButton = (RadioButton)sexValueView.findViewById(sexValueView.getCheckedRadioButtonId());
        if(checkedRadioButton != null && checkedRadioButton.isChecked()){
            sex = (String) checkedRadioButton.getText();
        }

        int kcal = 0;
        if(height > 0 && weight > 0 && age > 0 && !sex.equals("")){
            if(sex.equals("Mężczyzna")){
                kcal = (int) (66.5 + (13.75*weight) + (5.003 * height) - (6.775*age));
            } else {
                kcal = (int) (655.1 + (9.563*weight) + (1.85 * height) - (4.676*age));

            }

            kcalValueTextView.setText(kcalFormat.format(kcal));
            button.setVisibility(View.VISIBLE);
            MainActivity.setKcal(kcal);
        } else {
            MainActivity.setKcal(0);
            button.setVisibility(View.GONE);
            kcalValueTextView.setText("0");
        }

    }
    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            calculate();
        }

    };
}