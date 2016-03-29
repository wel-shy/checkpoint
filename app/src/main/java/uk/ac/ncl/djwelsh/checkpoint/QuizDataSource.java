package uk.ac.ncl.djwelsh.checkpoint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 29/03/16.
 *
 * Class for managing quizzes in the database.
 */
public class QuizDataSource {

    private SQLiteDatabase database;
    private SQLHelper dbHelper;
    private Context context;

    private String[] allColumns = {

            SQLHelper.QUIZZES_COLUMN_ID,
            SQLHelper.QUIZZES_COLUMN_TYPE,
            SQLHelper.QUIZZES_COLUMN_SUBJECT,
            SQLHelper.QUIZZES_COLUMN_POINTS,
            SQLHelper.QUIZZES_COLUMN_DATE,
            SQLHelper.QUIZZES_COLUMN_CORRECT_COUNT,
            SQLHelper.QUIZZES_COLUMN_INCORRECT_COUNT,
    };

    /**
     * Initialise database.
     *
     * @param context
     */
    public QuizDataSource(Context context) {

        this.context = context;
        dbHelper = new SQLHelper(context);
    }

    /**
     * Open connection.
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Close connection.
     */
    public void close() {

        dbHelper.close();
    }

    /**
     * Create card and store in database.
     *
     * @param data
     * @return
     */
    public Quiz createQuiz(String[] data) {
        ContentValues values = new ContentValues();

        for (int i = 0; i < allColumns.length - 1; i++) {
            values.put(allColumns[i + 1], data[i]);
        }

        long insertId = database.insert(SQLHelper.TABLE_QUIZZES, null, values);
        Cursor cursor = database.query(SQLHelper.TABLE_QUIZZES,
                allColumns, SQLHelper.QUIZZES_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Quiz newQuiz = cursorToQuiz(cursor);
        cursor.close();
        return newQuiz;
    }

    /**
     * Delete card from database.
     *
     * @param card
     */
    public void deleteQuiz(Card card) {
        long id = card.getId();
        System.out.println("Quiz deleted with id: " + id);
        database.delete(SQLHelper.TABLE_QUIZZES, SQLHelper.QUIZZES_COLUMN_ID
                + " = " + id, null);
    }

    /**
     * Get card from database.
     *
     * @param id
     * @return
     */
    public Quiz getQuiz(long id) {

        id++;
        Quiz quiz = null;

        System.out.println("Card search id" +  id);

        String query = "SELECT * FROM " + SQLHelper.TABLE_QUIZZES + " WHERE " + SQLHelper.QUIZZES_COLUMN_ID + " = " + id;
        Cursor cursor = database.rawQuery(query, null);

        if( cursor != null && cursor.moveToFirst() ) {
            quiz = cursorToQuiz(cursor);
            cursor.close();
        }

        System.out.println(quiz.toString());
        return quiz;
    }

    /**
     * Return all cards from database
     *
     * @return
     */
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<Quiz>();

        Cursor cursor = database.query(SQLHelper.TABLE_QUIZZES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Quiz quiz = cursorToQuiz(cursor);
            quizzes.add(quiz);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return quizzes;
    }

    /**
     * Return all cards belonging to a subject.
     *
     * @param subject
     * @return
     */
    public List<Quiz> getQuizBySubject(String subject) {
        List<Quiz> cards = new ArrayList<Quiz>();

        String query = "SELECT * FROM " + SQLHelper.TABLE_QUIZZES + " WHERE " + SQLHelper.QUIZZES_COLUMN_SUBJECT + " = " + subject;
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.getCount() == 0)
        {

        } else if( cursor != null && cursor.moveToFirst() ) {
            do {
                Quiz quiz = cursorToQuiz(cursor);
                cards.add(quiz);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return cards;
    }

    /**
     * Parse cursor to card.
     *
     * @param cursor
     * @return
     */
    private Quiz cursorToQuiz(Cursor cursor) {
        Quiz quiz = new Quiz();
        Subject subject;

        SubjectsDataSource subjectDB = new SubjectsDataSource(context);
        subjectDB.open();
        subject = subjectDB.getSubject(Long.valueOf(cursor.getString(2)));

        quiz.setId(cursor.getLong(0));
        quiz.setType(cursor.getString(1));
        quiz.setSubject(subject);
        quiz.setPoints(Integer.valueOf(cursor.getString(3)));
        quiz.setDate(cursor.getString(4));
        quiz.setCorrectCount(Integer.valueOf(cursor.getColumnName(5)));
        quiz.setIncorrectCount(Integer.valueOf(cursor.getColumnName(6)));

        return quiz;
    }
}
