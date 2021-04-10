package com.example.zad_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBmi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBmi extends Fragment {
    private EditText weightValueView;
    private TextView bmiValueTextView;
    private EditText heightValueView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentBmi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Second.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBmi newInstance(String param1, String param2) {
        FragmentBmi fragment = new FragmentBmi();
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
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);
        weightValueView = (EditText) view.findViewById(R.id.weightBmiTextView);
        heightValueView = (EditText) view.findViewById(R.id.heightBmiTextView);
        bmiValueTextView = (TextView) view.findViewById(R.id.bmiValueTextView);
        weightValueView.addTextChangedListener(watcher);
        heightValueView.addTextChangedListener(watcher);
        // Inflate the layout for this fragment
        return view;
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
    private static final NumberFormat weightFormat =
            NumberFormat.getNumberInstance();
    private void calculate() {

        int height = Integer.parseInt(!heightValueView.getText().toString().isEmpty() ? heightValueView.getText().toString() : "0");
        double weight = Double.parseDouble(!weightValueView.getText().toString().isEmpty() ? weightValueView.getText().toString() : "0");
        double bmi = 0.0;
        if(height > 0 && weight > 0 ){
            bmi = weight/((height*height)*0.0001);
            bmi = bmi*100;
            bmi = Math.round(bmi);
            bmi = bmi/100;
            bmiValueTextView.setText(weightFormat.format(bmi));
        } else {
            bmiValueTextView.setText("0.0");
        }

    }
}