package com.example.project

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var textViewHighscore: TextView? = null
    private var textViewScore: TextView? = null

    private var highscore: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewHighscore = findViewById(R.id.text_view_highscore)
        textViewScore = findViewById(R.id.text_view_score)
        loadHighscore()



        val buttonStartQuiz = findViewById<Button>(R.id.button_start_quiz)
        buttonStartQuiz.setOnClickListener {
            startQuiz()
            }
    }

    private fun startQuiz() {
        val intent = Intent(this@MainActivity, QuizActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_QUIZ)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == Activity.RESULT_OK) {

                val score = data!!.getIntExtra(QuizActivity.EXTRA_SCORE, 0)
                textViewScore!!.text = "Score: $score"
                if (score > highscore) {
                    updateHighscore(score)
                }
            }
        }
    }

    private fun loadHighscore() {
        val prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        highscore = prefs.getInt(KEY_HIGHSCORE, 0)
        textViewHighscore!!.text = "Skor Tertinggi: $highscore"
    }

    private fun updateHighscore(highscoreNew: Int) {
        highscore = highscoreNew
        textViewHighscore!!.text = "Skor Tertinggi: $highscore"

        val prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(KEY_HIGHSCORE, highscore)
        editor.apply()
    }

    companion object {
        private val REQUEST_CODE_QUIZ = 1

        val SHARED_PREFS = "sharedPrefs"
        val KEY_HIGHSCORE = "keyHighscore"
    }
}