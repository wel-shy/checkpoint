package uk.ac.ncl.djwelsh.checkpoint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 27/03/16.
 *
 * Class to manage Subject resources in database.
 */
public class SubjectsDataSource {

    private SQLiteDatabase database;
    private SQLHelper dbHelper;

    private String[] allColumns = {
            SQLHelper.SUBJECTS_COLUMN_ID,
            SQLHelper.SUBJECTS_COLUMN_NAME
    };

    public SubjectsDataSource(Context context) {

        dbHelper = new SQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {

        dbHelper.close();
    }

    /**
     * Save subject to database.
     *
     * @param name
     * @return
     */
    public Subject createSubject(String name) {

        ContentValues values = new ContentValues();
        values.put(SQLHelper.SUBJECTS_COLUMN_NAME, name);
        values.put(SQLHelper.SUBJECTS_COLUMN_SCORE, "0");

        long insertId = database.insert(SQLHelper.TABLE_SUBJECTS, null, values);

        Cursor cursor = database.query(SQLHelper.TABLE_SUBJECTS,
                allColumns, SQLHelper.SUBJECTS_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Subject newSubject = cursorToSubject(cursor);
        cursor.close();
        return newSubject;
    }

    /**
     * Delete subject.
     *
     * @param subject
     */
    public void deleteSubject(Subject subject) {
        long id = subject.getId();
        database.delete(SQLHelper.TABLE_SUBJECTS, SQLHelper.SUBJECTS_COLUMN_ID
                + " = " + id, null);
    }

    /**
     * Get subject by ID
     *
     * @param id
     * @return
     */
    public Subject getSubject(long id) {

        Subject subject = null;

        String query = "SELECT * FROM " + SQLHelper.TABLE_SUBJECTS + " WHERE " + SQLHelper.SUBJECTS_COLUMN_ID + " = " + id;
        Cursor cursor = database.rawQuery(query, null);

        if( cursor != null && cursor.moveToFirst() ) {
            subject = cursorToSubject(cursor);
            cursor.close();
        }

        return subject;
    }

    /**
     * Get all subjects.
     *
     * @return
     */
    public List<Subject> getAllSubjects() {
        List<Subject> comments = new ArrayList<Subject>();

        Cursor cursor = database.query(SQLHelper.TABLE_SUBJECTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Subject comment = cursorToSubject(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        return comments;
    }

    /**
     * Translate cursor to subject.
     *
     * @param cursor
     * @return
     */
    private Subject cursorToSubject(Cursor cursor) {
        Subject subject = new Subject();
        subject.setId(cursor.getLong(0));
        subject.setName(cursor.getString(1));
        return subject;
    }
}
