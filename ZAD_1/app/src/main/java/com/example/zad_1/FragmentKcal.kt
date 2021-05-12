package com.example.zad_1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.NumberFormat

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentKcal.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentKcal : Fragment() {
    private var weightValueView: EditText? = null
    private var heightValueView: EditText? = null
    private var ageValueView: EditText? = null
    private var sexValueView: RadioGroup? = null
    private var kcalValueTextView: TextView? = null
    private var button: Button? = null

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_kcal, container, false)
        weightValueView = view.findViewById<View?>(R.id.weightKcalTextView) as EditText
        heightValueView = view.findViewById<View?>(R.id.heightKcalTextView) as EditText
        ageValueView = view.findViewById<View?>(R.id.ageTextView) as EditText
        sexValueView = view.findViewById<View?>(R.id.sexTextView) as RadioGroup
        kcalValueTextView = view.findViewById<View?>(R.id.kcalValueTextView) as TextView
        button = view.findViewById<View?>(R.id.buttonWhatEat) as Button
        button!!.visibility = View.GONE
        weightValueView!!.addTextChangedListener(watcher)
        heightValueView!!.addTextChangedListener(watcher)
        ageValueView!!.addTextChangedListener(watcher)
        sexValueView!!.setOnCheckedChangeListener { _, _ -> calculate() }

        // Inflate the layout for this fragment
        return view
    }

    private fun calculate() {
        val height: Int = if (heightValueView?.text.toString().isNotEmpty()) heightValueView?.text.toString().toInt() else 0
        val age: Int = if (ageValueView?.text.toString().isNotEmpty()) ageValueView?.text.toString().toInt()  else 0
        val weight: Int = if (weightValueView?.text.toString().isNotEmpty()) weightValueView?.text.toString().toInt() else 0
        var sex = ""
        var checkedRadioButton: RadioButton? = null
        if(sexValueView?.checkedRadioButtonId != null ){
            checkedRadioButton = sexValueView!!.findViewById<View?>(sexValueView?.checkedRadioButtonId!!) as? RadioButton
        }

        if (checkedRadioButton != null && checkedRadioButton.isChecked) {
            sex = checkedRadioButton.text as String
        }
        val kcal: Int
        if (height > 0 && weight > 0 && age > 0 && sex != "") {
            kcal = if (sex == "Mężczyzna") {
                (66.5 + 13.75 * weight + 5.003 * height - 6.775 * age).toInt()
            } else {
                (655.1 + 9.563 * weight + 1.85 * height - 4.676 * age).toInt()
            }
            kcalValueTextView?.text = kcalFormat.format(kcal.toLong())
            button?.visibility = View.VISIBLE
            MainActivity.setKcal(kcal)
        } else {
            MainActivity.setKcal(0)
            button?.visibility = View.GONE
            kcalValueTextView?.text = "0"
        }
    }

    private val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            calculate()
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
         * @return A new instance of fragment FragmentKcal.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): FragmentKcal {
            val fragment = FragmentKcal()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }

        private val kcalFormat = NumberFormat.getNumberInstance()
    }
}