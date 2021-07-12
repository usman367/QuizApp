package com.example.quizapp2

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

//Made the class on click listener because we are creating the on click listener buttons for the text views (Step 4)
class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    // TODO (STEP 2: A global variables for current position and questions list.)
    // START
    private var mCurrentPosition: Int = 1 // Default and the first question position
    private var mQuestionsList: ArrayList<Question>? = null
    // END

    // TODO (STEP 5: A global variables for selected option.)
    // START
    private var mSelectedOptionPosition: Int = 0
    // END

    // TODO (STEP 3: Create a variable for getting the name from intent.)
    // START
    private var mUserName: String? = null
    // END

    //Used to store the number of correct answers
    private var mCorrectAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        // TODO (STEP 4: Get the NAME from intent and assign it the variable.)
        // START
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        //We then send it over to the results activity from the on click function
        // END

        //Gets the data
        mQuestionsList = Constants.getQuestions()

        //For testing purposes
//        Log.e("Questions Size", "${questionsList.size}")
//        for (i in questionsList) {
//            Log.e("Questions", i.question)
//        }

        setQuestion()

        // TODO (STEP 4: Set all the click events for Options using the interface onClick listener)
        // START
        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        //This will allow us to click the buttons
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        //END

        val btn_submit = findViewById<Button>(R.id.btn_submit)
        btn_submit.setOnClickListener(this)


    }

    private fun setQuestion() {

        val question: Question? = mQuestionsList!![mCurrentPosition - 1] // Getting the question from the list with the help of current position.

        //Each time we set a new question, this will just make sure that all the questions are set back to their default appearances
        defaultOptionsView()

        val btn_submit = findViewById<Button>(R.id.btn_submit)

        //If we are on the last question it will display "FINISH"
        if(mCurrentPosition == mQuestionsList!!.size) {
            btn_submit.text = "FINISH"
        }else {
            //Otherwise it will display submit
            btn_submit.text = "SUBMIT"
        }

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress =
            mCurrentPosition // Setting the current progress in the progressbar using the position of question

        val tv_progress = findViewById<TextView>(R.id.tv_progress)
        tv_progress.text =
            "$mCurrentPosition" + "/" + progressBar.getMax() // Setting up the progress text


        val tv_question = findViewById<TextView>(R.id.tv_question)
        val iv_image = findViewById<ImageView>(R.id.iv_image)
        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        // Now set the current question and the options in the UI
        tv_question.text = question!!.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
        // END
    }

    // TODO (STEP 8: Create a function to set default options view.)
    // START
    /**
     * A function to set default options view when the new question is loaded or when the answer is reselected.
     */
    private fun defaultOptionsView() {

        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        //For every option (text view) it sets the stroke colour to the default colour (grey)
        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            //Sets it back to the default appearance
            option.typeface = Typeface.DEFAULT
            //Sets the background the background file we created in the drawables folder
            option.background = ContextCompat.getDrawable(
                this@QuizQuestionsActivity,
                R.drawable.default_option_border_bg
            )
        }
    }

    //We had to implement this because we made the main class an on click listener
    override fun onClick(v: View?) {

        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)
        val btn_submit = findViewById<Button>(R.id.btn_submit)

        when (v?.id) {
            //When v.id is equal to tv_option_one
            R.id.tv_option_one -> {
                //Then set the selected option view to the first one
                selectedOptionView(tv_option_one, 1)
            }

            R.id.tv_option_two -> {

                selectedOptionView(tv_option_two, 2)
            }

            R.id.tv_option_three -> {

                selectedOptionView(tv_option_three, 3)
            }

            R.id.tv_option_four -> {

                selectedOptionView(tv_option_four, 4)
            }

            //the selected option position is 0 by default, this just setsit to 1 at the start
            //Ofc when the chooses their own answer this changes
            R.id.btn_submit -> {
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when {
                        //When there are still questions left, move on to the next question
                        mCurrentPosition <= mQuestionsList!!.size -> {

                            setQuestion()
                        }
                        else -> {
                            //Otherwise, tell them they have completed the quiz
                            //Length.short meaning it is displayed for a small period of time
                            Toast.makeText(this@QuizQuestionsActivity, "You have successfully completed the quiz.", Toast.LENGTH_SHORT).show()

                            // TODO (STEP 5: Now remove the toast message and launch the result screen which we have created and also pass the user name and score details to it.)
                            // START
                            val intent =
                                Intent(this@QuizQuestionsActivity, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                            // END
                        }
                    }
                } else {
                    //We get the question at the current list
                    //-1 because its an array list
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        //If its the wrong answer the, use the wrong_option drawable we have created
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        //Otherwise increase the number of correct answers
                        mCorrectAnswers++
                    }

                    // This is for correct answer
                    //Use the correct_answer drawable to highlight the correct answer in any case
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    //If we are at the last question, the buttons text changes to finish
                    if (mCurrentPosition == mQuestionsList!!.size) {
                        btn_submit.text = "FINISH"
                    } else {
                        //Otherwise it should be "next question"
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }

                    //Setting it to 0 wo we can move on to the next question
                    //(This whole loop only works when the selected option is 0, see above)
                    mSelectedOptionPosition = 0
                }
                }
            }
        }



    // TODO (STEP 6: Create a function for view for highlighting the selected option.)
    // START
    /**
     * A function to set the view of selected option view.
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        //Sets the buttons to their default design we created
        defaultOptionsView()

        //The button we have clicked on is now set to the new selected option position
        mSelectedOptionPosition = selectedOptionNum

        //Changes the text volor
        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        //Makes the text bold
        tv.setTypeface(tv.typeface, Typeface.BOLD)

        //Sets the background to the drawable we created (purple color)
        tv.background = ContextCompat.getDrawable(
            this@QuizQuestionsActivity,
            R.drawable.selected_option_border_bg
        )
    }


    /**
     * A function for answer view which is used to highlight the answer is wrong or right.
     */
    private fun answerView(answer: Int, drawableView: Int) {

        val tv_option_one = findViewById<TextView>(R.id.tv_option_one)
        val tv_option_two = findViewById<TextView>(R.id.tv_option_two)
        val tv_option_three = findViewById<TextView>(R.id.tv_option_three)
        val tv_option_four = findViewById<TextView>(R.id.tv_option_four)

        when (answer) {

            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
        }
    }






}