package com.shubham.multiscreenquiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class QuizViewModel : ViewModel() {

    val quizQuestions = listOf(
        Question(
            text = "What is the main building bloack of a jetpack compose UI ?",
            options = listOf("Activity", "View", "Composable", "XML"),
            correctAnswerIndex = 2
        ),

        Question(
            text = "Which somposable is used to arrange items vertically ?",
            options = listOf("Row", "Column", "Box", "Spacer"),
            correctAnswerIndex = 1
        ),

        Question(
            text = "How do you remember a value that cab change (state) ?",
            options = listOf(
                "Var myValue = 0",
                "remember {mutableStateOf(0)",
                "val myValue = 0",
                "Button(onClick=...)"
            ),
            correctAnswerIndex = 1
        )
    )


    //---State---

    var currentQuestionIndex by mutableStateOf(0)
    var selectedOptionIndex by mutableStateOf(-1)
    var score by mutableStateOf(0)
    var quizFinished by mutableStateOf(false)

    val currentQuestion: Question
        get() = quizQuestions[currentQuestionIndex]


    //---Event---

    fun selectAnswer(index: Int) {
        selectedOptionIndex = index
    }

    fun nextQuestionOrFinish() {
        //check if the answer is correct and update the score

        if (selectedOptionIndex == currentQuestion.correctAnswerIndex) {
            score++
        }

        if (currentQuestionIndex < quizQuestions.size - 1) {
            currentQuestionIndex++
            selectedOptionIndex = -1
        } else {
            quizFinished = true

        }

    }

    fun resetQuiz(){
        currentQuestionIndex = 0
        selectedOptionIndex = -1
        score = 0
        quizFinished = false
    }
}