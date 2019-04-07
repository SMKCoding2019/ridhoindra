package com.example.project

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.example.project.QuizContract.QuestionsTable

import java.util.ArrayList


class QuizDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var db: SQLiteDatabase? = null

    val allQuestions: List<Question>
        get() {
            val questionList = ArrayList<Question>()
            db = readableDatabase
            val c = db!!.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null)

            if (c.moveToFirst()) {
                do {
                    val question = Question()
                    question.question = c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION))
                    question.option1 = c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1))
                    question.option2 = c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2))
                    question.option3 = c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3))
                    question.answerNr = c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR))
                    questionList.add(question)
                } while (c.moveToNext())
            }

            c.close()
            return questionList
        }

    override fun onCreate(db: SQLiteDatabase) {
        this.db = db

        val SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")"

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE)
        fillQuestionsTable()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME)
        onCreate(db)
    }

    fun fillQuestionsTable() {
        val q1 = Question("1+1 =", "2", "11", "13", 1)
        addQuestion(q1)
        val q2 = Question("2+2 =", "22", "4", "26", 2)
        addQuestion(q2)
        val q3 = Question("3+3 =", "33", "9", "6", 3)
        addQuestion(q3)
        val q4 = Question("4+4 =", "8", "1", "16", 1)
        addQuestion(q4)
        val q5 = Question("5+5 =", "25", "10", "5", 2)
        addQuestion(q5)
    }

    private fun addQuestion(question: Question) {
        val cv = ContentValues()
        cv.put(QuestionsTable.COLUMN_QUESTION, question.question)
        cv.put(QuestionsTable.COLUMN_OPTION1, question.option1)
        cv.put(QuestionsTable.COLUMN_OPTION2, question.option2)
        cv.put(QuestionsTable.COLUMN_OPTION3, question.option3)
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.answerNr)
        db!!.insert(QuestionsTable.TABLE_NAME, null, cv)
    }

    companion object {
        private val DATABASE_NAME = "MyAwesomeQuiz.db"
        private val DATABASE_VERSION = 1
    }
}
