package com.example.zad_1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentBmi.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentBmi : Fragment() {
    private var weightValueView: EditText? = null
    private var bmiValueTextView: TextView? = null
    private var heightValueView: EditText? = null

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_bmi, container, false)
        weightValueView = view?.findViewById<View?>(R.id.weightBmiTextView) as EditText
        heightValueView = view.findViewById<View?>(R.id.heightBmiTextView) as EditText
        bmiValueTextView = view.findViewById<View?>(R.id.bmiValueTextView) as TextView
        weightValueView!!.addTextChangedListener(watcher)
        heightValueView!!.addTextChangedListener(watcher)
        // Inflate the layout for this fragment
        return view
    }

    private val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            calculate()
        }
    }

    private fun calculate() {
        val height: Int = if (heightValueView?.text.toString().isNotEmpty()) heightValueView?.text.toString().toInt() else 0
        val weight: Double = if (weightValueView?.text.toString().isNotEmpty()) weightValueView?.text.toString().toDouble() else 0.0
        var bmi: Double
        if (height > 0 && weight > 0) {
            bmi = weight / (height * height * 0.0001)
            bmi *= 100
            bmi = bmi.roundToInt().toDouble()
            bmi /= 100
            bmiValueTextView?.text = weightFormat.format(bmi)
        } else {
            bmiValueTextView?.text = "0.0"
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1: String = "param1"
        private const val ARG_PARAM2: String = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Second.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): FragmentBmi {
            val fragment = FragmentBmi()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }

        private val weightFormat = NumberFormat.getNumberInstance()
    }
}