package com.example.zad_1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.zad_1.FragmentQuiz.MyAlertDialogFragment.OnYesNoClick
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentQuiz.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentQuiz : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    var quizQuestionList: ArrayList<Question?>? = object : ArrayList<Question?>() {
        init {
            add(Question("Ile posiłków dziennie powinno się jeść?", ArrayList(Arrays.asList(
                    "2", "4", "5"
            )), "5"))
            add(Question("Ile litrów wody powinno się pić w ciągu dnia?", ArrayList(Arrays.asList(
                    "1", "1.5", "2"
            )), "1.5"))
            add(Question("Który z posiłków jest najważniejszy?", ArrayList(Arrays.asList(
                    "Śniadanie", "Obiad", "Kolacja"
            )), "Śniadanie"))
            add(Question("Co ile godzin powinno się spożywać posiłki?", ArrayList(Arrays.asList(
                    "1-2", "2-3", "3-4"
            )), "3-4"))
            add(Question("Ile godzin snu potrzebuje przeciętny człowiek?", ArrayList(Arrays.asList(
                    "4-6", "7-8", "9-10"
            )), "7-8"))
            add(Question("Czym najlepiej ugasić pragnienie po wysiłku fizycznym?", ArrayList(Arrays.asList(
                    "Woda", "Sok", "Kawa"
            )), "Woda"))
        }
    }
    private var handler // used to delay loading next flag
            : Handler? = null
    private val guessRows = 3 // number of rows displaying guess Buttons
    private val answerList: MutableList<String?>? = null
    private var correctAnswer // correct country for the current flag
            : String? = null
    private var totalGuesses // number of guesses made
            = 0
    private var correctAnswers // number of correct guesses
            = 0
    private var questionTextView: TextView? = null
    private var questionNumberTextView // shows current question #
            : TextView? = null
    private var guessLinearLayouts // rows of answer Buttons
            : Array<LinearLayout?>? = null
    private var answerTextView // displays correct answer
            : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        handler = Handler()
        questionNumberTextView = view.findViewById<View?>(R.id.questionNumberTextView) as TextView
        questionTextView = view.findViewById<View?>(R.id.question) as TextView
        guessLinearLayouts = arrayOfNulls<LinearLayout?>(3)
        guessLinearLayouts!![0] = view.findViewById<View?>(R.id.row1LinearLayout) as LinearLayout
        guessLinearLayouts!![1] = view.findViewById<View?>(R.id.row2LinearLayout) as LinearLayout
        guessLinearLayouts!![2] = view.findViewById<View?>(R.id.row3LinearLayout) as LinearLayout
        answerTextView = view.findViewById<View?>(R.id.answerTextView) as TextView
        for (row in guessLinearLayouts!!) {
            val button = row?.getChildAt(0) as Button
            button.setOnClickListener(guessButtonListener)
        }
        questionNumberTextView!!.setText(
                getString(R.string.question, 1, QUEST_IN_QUIZ))
        resetQuiz()
        return view
    }

    private fun disableButtons() {
        for (row in 0 until guessRows) {
            val guessRow = guessLinearLayouts?.get(row)
            if (guessRow != null) {
                for (i in 0 until guessRow.getChildCount()) guessRow.getChildAt(i).isEnabled = false
            }
        }
    }

    fun resetQuiz() {
        answerList?.clear()
        correctAnswers = 0
        totalGuesses = 0
        loadNextQuest()
    }

    class MyAlertDialogFragment : DialogFragment() {
        private var yesNoClick: OnYesNoClick? = null
        fun setOnYesNoClick(yesNoClick: OnYesNoClick?) {
            this.yesNoClick = yesNoClick
        }

        @SuppressLint("StringFormatMatches")
        override fun onCreateDialog(bundle: Bundle?): Dialog {
            val totalGuesses = arguments?.getInt("totalGuesses")
            val builder = AlertDialog.Builder(activity)

                builder.setMessage(
                        getString(R.string.results,
                                totalGuesses,
                                (100 * QUEST_IN_QUIZ / totalGuesses!!.toFloat())))

            builder.setPositiveButton(R.string.reset_quiz
            ) { _, id -> yesNoClick?.onYesClicked() }
            return builder.create()
        }

        interface OnYesNoClick {
            fun onYesClicked()
        }

        companion object {
            fun newInstance(totalGuesses: Int): MyAlertDialogFragment {
                val frag = MyAlertDialogFragment()
                val args = Bundle()
                args.putInt("totalGuesses", totalGuesses)
                frag.arguments = args
                return frag
            }
        }
    }

    private fun showYesNoDialog() {
        val yesNoAlert = MyAlertDialogFragment.newInstance(
                totalGuesses)
        fragmentManager?.let { yesNoAlert.show(it, "quiz results") }
        yesNoAlert.setOnYesNoClick(object : OnYesNoClick {
                override fun onYesClicked() {
                    resetQuiz()
                }
            })
    }

    private fun loadNextQuest() {
        val nextQuestion = quizQuestionList?.get(correctAnswers)
        if (nextQuestion != null) {
            correctAnswer = nextQuestion.correctAnswer
        }
        answerTextView?.setText("")
        questionTextView?.setText(nextQuestion?.question)
        questionNumberTextView?.setText(getString(
                R.string.question, correctAnswers + 1, QUEST_IN_QUIZ))
        for (row in 0 until guessRows) {
            for (column in 0 until (guessLinearLayouts?.get(row)?.childCount!!)) {
                val newGuessButton = guessLinearLayouts?.get(row)?.getChildAt(column) as Button
                newGuessButton.isEnabled = true
                if (nextQuestion != null) {
                    newGuessButton.text = nextQuestion.answers?.get(row)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private val guessButtonListener: View.OnClickListener = View.OnClickListener { v ->
        val guessButton = v as Button
        val guess = guessButton.text.toString()
        val answer = correctAnswer
        ++totalGuesses
        if (guess == answer) {
            ++correctAnswers
            answerTextView?.setText("$answer!")
            answerTextView?.setTextColor(
                    resources.getColor(R.color.correct_answer,
                            context?.getTheme()))
            disableButtons()
            if (correctAnswers == QUEST_IN_QUIZ) {
                showYesNoDialog()
            } else {
                handler?.postDelayed(
                        { loadNextQuest() }, 2000)
            }
        } else {
            answerTextView?.setText(R.string.incorrect_answer)
            answerTextView?.setTextColor(resources.getColor(
                    R.color.incorrect_answer, context?.getTheme()))
            guessButton.isEnabled = false
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1: String = "param1"
        private val ARG_PARAM2: String = "param2"
        private const val QUEST_IN_QUIZ = 6

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentQuiz.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): FragmentQuiz {
            val fragment = FragmentQuiz()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}