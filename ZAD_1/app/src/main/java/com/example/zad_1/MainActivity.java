package com.example.zad_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    static int kcal = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void actionMainToFragmentBmi(View view){
        NavDirections action = FragmentMainDirections.actionMainToFragmentBmi();
        Navigation.findNavController(view).navigate(action);
    }
    public void actionMainToFragmentKcal(View view){
        NavDirections action = FragmentMainDirections.actionMainToFragmentKcal();
        Navigation.findNavController(view).navigate(action);
    }
    public void actionKcalToFragmentWhatEat(View view){
        NavDirections action = FragmentKcalDirections.actionFragmentKcalToFragmentWhatEat();
        Navigation.findNavController(view).navigate(action);
    }
    static void setKcal(int kcalIn){
        kcal = kcalIn;
    }
    static int getKcal(){
        return kcal;
    }

}