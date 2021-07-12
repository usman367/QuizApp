package com.example.quizapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // TODO (STEP 6: Hide the status bar and get the details from intent and set it to the UI. And also add a click event to the finish button.)
        // START
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val tv_name = findViewById<TextView>(R.id.tv_name)
        val tv_score = findViewById<TextView>(R.id.tv_score)
        val btn_finish = findViewById<Button>(R.id.btn_finish)


        //Gets the data from the intents we sent from the question activity
        //We use getString Extra because we are receiving a string
        val userName = intent.getStringExtra(Constants.USER_NAME)
        //Sets it to the text view we created fpr this acivity
        tv_name.text = userName

        //Gets the data from the intents we sent from the question activity
        //We use getIntExtra because we are receiving an int
        //We give a default value emaning if the value wasn't sent over we use 0 as the default value
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        //Sets the data on the text view for this activity
        tv_score.text = "Your Score is $correctAnswers out of $totalQuestions."

        //If they press the button it sends them over to the main activity
        btn_finish.setOnClickListener {
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
        }
        // END
    }
}