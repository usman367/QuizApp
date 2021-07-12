package com.example.quizapp2

//Creates a data class that will store all the wuestions later
data class Question (
    val id: Int,
    val question : String,
    val image : Int,
    val optionOne : String,
    val optionTwo : String,
    val optionThree : String,
    val optionFour : String,
    val correctAnswer: Int
)