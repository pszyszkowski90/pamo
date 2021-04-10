package com.example.zad_1;

import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWhatEat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWhatEat extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static TextView recipeView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentWhatEat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentWhatEat.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWhatEat newInstance(String param1, String param2) {
        FragmentWhatEat fragment = new FragmentWhatEat();
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

    private static final NumberFormat kcalFormat =
            NumberFormat.getNumberInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_what_eat, container, false);
        recipeView = (TextView) view.findViewById(R.id.recipe);
        int kcal = MainActivity.getKcal();
        if (kcal > 2000){
            recipeView.setText(HtmlCompat.fromHtml(getResources().getString(R.string.recipe1), 0));
        } else {
            recipeView.setText(HtmlCompat.fromHtml(getResources().getString(R.string.recipe2), 0));
        }

        return view;
    }
}