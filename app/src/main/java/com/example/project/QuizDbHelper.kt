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
        val q6 = Question("6+8 =", "14", "11", "13", 1)
        addQuestion(q6)
        val q7 = Question("4+9 =", "22", "21", "26", 2)
        addQuestion(q7)
        val q8 = Question("6+25 =", "33", "32", "31", 3)
        addQuestion(q8)
        val q9 = Question("8+24 =", "32", "31", "30", 1)
        addQuestion(q9)
        val q10 = Question("6+7 =", "15", "13", "14", 2)
        addQuestion(q10)
        val q11 = Question("9+7 =", "16", "11", "13", 1)
        addQuestion(q11)
        val q12 = Question("11+25 =", "22", "35", "26", 2)
        addQuestion(q12)
        val q13 = Question("3+36 =", "33", "38", "39", 3)
        addQuestion(q13)
        val q14 = Question("12+26 =", "38", "31", "16", 1)
        addQuestion(q14)
        val q15 = Question("14+16 =", "25", "30", "35", 2)
        addQuestion(q15)
        val q16 = Question("23+12 =", "35", "31", "33", 1)
        addQuestion(q16)
        val q17 = Question("23+31 =", "55", "54", "44", 2)
        addQuestion(q17)
        val q18 = Question("7+55 =", "52", "64", "62", 3)
        addQuestion(q18)
        val q19 = Question("13+63 =", "76", "71", "75", 1)
        addQuestion(q19)
        val q20 = Question("26+28 =", "55", "54", "44", 2)
        addQuestion(q20)
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
