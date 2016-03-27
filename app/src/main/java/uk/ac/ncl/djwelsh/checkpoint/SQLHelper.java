package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Daniel on 27/03/16.
 *
 * Class to help SQLite Database usage.
 */
public class SQLHelper extends SQLiteOpenHelper {

    //DB
    private static final String DATABASE_NAME = "cards.db";
    private static final int DATABASE_VERSION = 2;

    //Subjects
    public static final String TABLE_SUBJECTS = "subjects";
    public static final String SUBJECTS_COLUMN_ID = "_id";
    public static final String SUBJECTS_COLUMN_NAME = "name";

    //Cards
    public static final String TABLE_CARDS = "cards";
    public static final String CARDS_COLUMN_ID = "_id";
    public static final String CARDS_COLUMN_NAME = "name";
    public static final String CARDS_COLUMN_QUESTION = "question";
    public static final String CARDS_COLUMN_ANSWER = "answer";
    public static final String CARDS_COLUMN_RATING = "rating";
    public static final String CARDS_COLUMN_SUBJECT = "subject";

    // Database creation sql statements
    private static final String CREATE_SUBJECTS_TABLE = "create table "
            + TABLE_SUBJECTS + "(" + SUBJECTS_COLUMN_ID
            + " integer primary key autoincrement, " + SUBJECTS_COLUMN_NAME
            + " text not null);";

    private static final String CREATE_CARDS_TABLE = "create table "
            + TABLE_CARDS
            + "(" + SUBJECTS_COLUMN_ID + " integer primary key autoincrement, "
            + CARDS_COLUMN_NAME + " text not null, "
            + CARDS_COLUMN_QUESTION + " text not null, "
            + CARDS_COLUMN_ANSWER + " text not null, "
            + CARDS_COLUMN_RATING + " text not null, "
            + CARDS_COLUMN_SUBJECT + " text not null, "
            + "FOREIGN KEY (" + CARDS_COLUMN_SUBJECT + ") REFERENCES " + TABLE_SUBJECTS + " (" + SUBJECTS_COLUMN_ID + ")"
            + ");";

    /**
     * Initialise database.
     * @param context
     */
    public SQLHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        context.deleteDatabase(DATABASE_NAME);
    }

    /**
     * Create tables when database is created.
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_SUBJECTS_TABLE);
        db.execSQL(CREATE_CARDS_TABLE);
    }

    /**
     * Allow for foreign keys on configure.
     *
     * @param db
     */
    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    /**
     * Upgrade database version.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        onCreate(db);
    }
}
