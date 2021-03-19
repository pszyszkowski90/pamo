package com.example.zad_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private EditText weightValueView;
    private TextView bmiValueTextView;
    private EditText heightValueView;
    private static final NumberFormat weightFormat =
            NumberFormat.getNumberInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weightValueView = (EditText) findViewById(R.id.weightTextView);
        heightValueView = (EditText) findViewById(R.id.heightTextView);
        bmiValueTextView = (TextView) findViewById(R.id.bmiValueTextView);

        weightValueView.addTextChangedListener(watcher);
        heightValueView.addTextChangedListener(watcher);
    }
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