package com.example.quizapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Removes the bar at the top that shows battery, wifi connection etc
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        //Gets the button
        var btn_start = findViewById<Button>(R.id.btn_start)
        //When the button is pressed check if its empty
        btn_start.setOnClickListener {

            var et_name = findViewById<AppCompatEditText>(R.id.et_name)
            //If its empty ask the user to enter their name
            if(et_name.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }else{
                //Otherwise change the screen to the other class
                //We want to change the activity to the quiz questions activity
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                // START
                //To show the results on the results screen
                //Para 1, you put user name into it, para 2, where do you get your username from
                //This is then sent to the quiz question activity where we can retrieve it
                //( We need to sned it ver from this activity, to the question activity, to the results activity)
                intent.putExtra(Constants.USER_NAME, et_name.text.toString())
                // END
                //Starts the other page
                startActivity(intent)
                //Finishes this page
                finish()
            }
        }
    }
}