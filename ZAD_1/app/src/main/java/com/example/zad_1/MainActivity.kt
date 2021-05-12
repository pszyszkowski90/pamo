package com.example.zad_1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun actionMainToFragmentBmi(view: View?) {
        val action = FragmentMainDirections.actionMainToFragmentBmi()
        if (view != null) {
            Navigation.findNavController(view).navigate(action)
        }
    }

    fun actionMainToFragmentKcal(view: View?) {
        val action = FragmentMainDirections.actionMainToFragmentKcal()
        if (view != null) {
            Navigation.findNavController(view).navigate(action)
        }
    }

    fun actionKcalToFragmentWhatEat(view: View?) {
        val action = FragmentKcalDirections.actionFragmentKcalToFragmentWhatEat()
        if (view != null) {
            Navigation.findNavController(view).navigate(action)
        }
    }

    fun actionMainToQuiz(view: View?) {
        val action = FragmentMainDirections.actionMainToFragmentQuiz()
        if (view != null) {
            Navigation.findNavController(view).navigate(action)
        }
    }

    fun actionMainToChart(view: View?) {
        val action = FragmentMainDirections.actionMainToFragmentChart()
        if (view != null) {
            Navigation.findNavController(view).navigate(action)
        }
    }

    companion object {
        private var kcal = -1
        @JvmName("setKcal1")
        fun setKcal(kcalIn: Int) {
            kcal = kcalIn
        }

        @JvmName("getKcal1")
        fun getKcal(): Int {
            return kcal
        }
    }
}