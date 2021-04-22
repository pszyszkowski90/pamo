package com.example.zad_1;


import java.util.List;

public class Question {
    String question;
    List<String> answers;
    String correctAnswer;

    public Question(String questionIn, List<String> answersIn, String correctAnswerIn) {
        question = questionIn;
        answers =answersIn;
        correctAnswer =  correctAnswerIn;
    }
}