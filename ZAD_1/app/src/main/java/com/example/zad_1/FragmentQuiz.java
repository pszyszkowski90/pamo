package com.example.zad_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentQuiz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentQuiz extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int QUEST_IN_QUIZ = 6;

    ArrayList<Question> quizQuestionList = new ArrayList<Question>() {
        {
            add(new Question("Ile posiłków dziennie powinno się jeść?", new ArrayList<String>(Arrays.asList(
                    "2", "4", "5"
            )), "5"));
            add(new Question("Ile litrów wody powinno się pić w ciągu dnia?", new ArrayList<String>(Arrays.asList(
                    "1", "1.5", "2"
            )), "1.5"));
            add(new Question("Który z posiłków jest najważniejszy?", new ArrayList<String>(Arrays.asList(
                    "Śniadanie", "Obiad", "Kolacja"
            )), "Śniadanie"));
            add(new Question("Co ile godzin powinno się spożywać posiłki?", new ArrayList<String>(Arrays.asList(
                    "1-2", "2-3", "3-4"
            )), "3-4"));
            add(new Question("Ile godzin snu potrzebuje przeciętny człowiek?", new ArrayList<String>(Arrays.asList(
                    "4-6", "7-8", "9-10"
            )), "7-8"));
            add(new Question("Czym najlepiej ugasić pragnienie po wysiłku fizycznym?", new ArrayList<String>(Arrays.asList(
                    "Woda", "Sok", "Kawa"
            )), "Woda"));
        }
    };
    private Handler handler; // used to delay loading next flag
    private int guessRows = 3; // number of rows displaying guess Buttons
    private List<String> answerList;
    private String correctAnswer; // correct country for the current flag
    private int totalGuesses; // number of guesses made
    private int correctAnswers; // number of correct guesses
    private TextView questionTextView;
    private LinearLayout quizLayout; // layout that contains the quiz
    private TextView questionNumberTextView; // shows current question #
    private LinearLayout[] guessLinearLayouts; // rows of answer Buttons
    private TextView answerTextView; // displays correct answer

    public FragmentQuiz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentQuiz.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentQuiz newInstance(String param1, String param2) {
        FragmentQuiz fragment = new FragmentQuiz();
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
        View view =
                inflater.inflate(R.layout.fragment_quiz, container, false);


        handler = new Handler();
        questionNumberTextView =
                (TextView) view.findViewById(R.id.questionNumberTextView);
        questionTextView = (TextView) view.findViewById(R.id.question);
        guessLinearLayouts = new LinearLayout[3];
        guessLinearLayouts[0] =
                (LinearLayout) view.findViewById(R.id.row1LinearLayout);
        guessLinearLayouts[1] =
                (LinearLayout) view.findViewById(R.id.row2LinearLayout);
        guessLinearLayouts[2] =
                (LinearLayout) view.findViewById(R.id.row3LinearLayout);
        answerTextView = (TextView) view.findViewById(R.id.answerTextView);

        for (LinearLayout row : guessLinearLayouts) {
            Button button = (Button) row.getChildAt(0);
            button.setOnClickListener(guessButtonListener);
        }

        questionNumberTextView.setText(
                getString(R.string.question, 1, QUEST_IN_QUIZ));
        resetQuiz();
        return view;
    }


    private void disableButtons() {
        for (int row = 0; row < guessRows; row++) {
            LinearLayout guessRow = guessLinearLayouts[row];
            for (int i = 0; i < guessRow.getChildCount(); i++)
                guessRow.getChildAt(i).setEnabled(false);
        }
    }

    public void resetQuiz() {
        if (answerList != null) {
            answerList.clear();
        }

        correctAnswers = 0;
        totalGuesses = 0;
        loadNextQuest();
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        private OnYesNoClick yesNoClick;

        public static MyAlertDialogFragment newInstance(int totalGuesses) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("totalGuesses", totalGuesses);
            frag.setArguments(args);
            return frag;
        }

        public void setOnYesNoClick(OnYesNoClick yesNoClick) {
            this.yesNoClick = yesNoClick;
        }

        @Override
        public Dialog onCreateDialog(Bundle bundle) {
            int totalGuesses = getArguments().getInt("totalGuesses");
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity());
            builder.setMessage(
                    getString(R.string.results,
                            totalGuesses,
                            (100 * QUEST_IN_QUIZ / (double) totalGuesses)));

            builder.setPositiveButton(R.string.reset_quiz,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int id) {
                            yesNoClick.onYesClicked();
                        }
                    }
            );

            return builder.create();
        }


        public interface OnYesNoClick {
            void onYesClicked();
        }
    }

    private void showYesNoDialog() {
        MyAlertDialogFragment yesNoAlert = MyAlertDialogFragment.newInstance(
                totalGuesses);
        yesNoAlert.show(getFragmentManager(), "quiz results");

        yesNoAlert.setOnYesNoClick(new MyAlertDialogFragment.OnYesNoClick() {
            @Override
            public void onYesClicked() {
                resetQuiz();
            }

        });
    }

    private void loadNextQuest() {
        Question nextQuestion = quizQuestionList.get(correctAnswers);
        correctAnswer = nextQuestion.correctAnswer;
        answerTextView.setText("");

        questionTextView.setText(nextQuestion.question);
        questionNumberTextView.setText(getString(
                R.string.question, (correctAnswers + 1), QUEST_IN_QUIZ));

        for (int row = 0; row < guessRows; row++) {
            for (int column = 0;
                 column < guessLinearLayouts[row].getChildCount();
                 column++) {
                Button newGuessButton =
                        (Button) guessLinearLayouts[row].getChildAt(column);
                newGuessButton.setEnabled(true);
                newGuessButton.setText(nextQuestion.answers.get(row));
            }
        }
    }


    private View.OnClickListener guessButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button guessButton = ((Button) v);
            String guess = guessButton.getText().toString();
            String answer = correctAnswer;
            ++totalGuesses;

            if (guess.equals(answer)) {
                ++correctAnswers;
                answerTextView.setText(answer + "!");
                answerTextView.setTextColor(
                        getResources().getColor(R.color.correct_answer,
                                getContext().getTheme()));
                disableButtons();
                if (correctAnswers == QUEST_IN_QUIZ) {
                    showYesNoDialog();
                } else {
                    handler.postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    loadNextQuest();
                                }
                            }, 2000);
                }
            } else {
                answerTextView.setText(R.string.incorrect_answer);
                answerTextView.setTextColor(getResources().getColor(
                        R.color.incorrect_answer, getContext().getTheme()));
                guessButton.setEnabled(false);
            }
        }
    };
}