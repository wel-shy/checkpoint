package uk.ac.ncl.djwelsh.checkpoint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to manage db calls on card resources
 *
 * Created by Daniel on 27/03/16.
 */
public class CardsDataSource {

    private SQLiteDatabase database;
    private SQLHelper dbHelper;

    private String[] allColumns = {
            SQLHelper.CARDS_COLUMN_ID,
            SQLHelper.CARDS_COLUMN_NAME,
            SQLHelper.CARDS_COLUMN_QUESTION,
            SQLHelper.CARDS_COLUMN_ANSWER,
            SQLHelper.CARDS_COLUMN_CORRECT_COUNT,
            SQLHelper.CARDS_COLUMN_INCORRECT_COUNT,
            SQLHelper.CARDS_COLUMN_SUBJECT,
            SQLHelper.CARDS_COLUMN_RATING,
    };

    /**
     * Initialise database.
     *
     * @param context
     */
    public CardsDataSource(Context context) {

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
    public Card createCard(String[] data) {
        ContentValues values = new ContentValues();

        System.out.println(Arrays.toString(data));
        for (int i = 1; i < allColumns.length - 1; i++) {
            values.put(allColumns[i], data[i - 1]);
        }

        values.put(allColumns[1], data[0]); // name
        values.put(allColumns[2], data[1]); // question
        values.put(allColumns[3], data[2]); // answer
        values.put(allColumns[4], data[3]); // correct_count
        values.put(allColumns[5], data[4]); // incorrect_count
        values.put(allColumns[6], data[5]); // subject
        values.put(allColumns[7], data[6]); // rating

        long insertId = database.insert(SQLHelper.TABLE_CARDS, null, values);
        Cursor cursor = database.query(SQLHelper.TABLE_CARDS,
                allColumns, SQLHelper.CARDS_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Card newCard = cursorToCard(cursor);
        cursor.close();
        return newCard;
    }

    /**
     * Delete card from database.
     *
     * @param card
     */
    public void deleteCard(Card card) {
        long id = card.getId();
        System.out.println("Card deleted with id: " + id);
        database.delete(SQLHelper.TABLE_CARDS, SQLHelper.CARDS_COLUMN_ID
                + " = " + id, null);
    }

    /**
     * Get card from database.
     *
     * @param id
     * @return
     */
    public Card getCard(long id) {

        id++;
        Card card = null;

        System.out.println("Card search id" +  id);

        String query = "SELECT * FROM " + SQLHelper.TABLE_CARDS + " WHERE " + SQLHelper.CARDS_COLUMN_ID + " = " + id;
        Cursor cursor = database.rawQuery(query, null);

        if( cursor != null && cursor.moveToFirst() ) {
            card = cursorToCard(cursor);
            cursor.close();
        }

        System.out.println(card.toString());
        return card;
    }

    /**
     * Return all cards from database
     *
     * @return
     */
    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<Card>();

        Cursor cursor = database.query(SQLHelper.TABLE_CARDS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Card card = cursorToCard(cursor);
            cards.add(card);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return cards;
    }

    /**
     * Return all cards belonging to a subject.
     *
     * @param subject
     * @return
     */
    public List<Card> getCardBySubject(String subject) {
        List<Card> cards = new ArrayList<Card>();

        String query = "SELECT * FROM " + SQLHelper.TABLE_CARDS + " WHERE " + SQLHelper.CARDS_COLUMN_SUBJECT + " = " + subject;
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.getCount() == 0)
        {

        } else if( cursor != null && cursor.moveToFirst() ) {
            do {
                Card card = cursorToCard(cursor);
                cards.add(card);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return cards;
    }

    public Card updateCard(long id) {
        Card card = getCard(id);

        ContentValues values = new ContentValues();
        values.put(allColumns[1], card.getName());
        values.put(allColumns[2], card.getQuestion());
        values.put(allColumns[3], card.getAnswer());
        values.put(allColumns[4], card.getNumCorrect());
        values.put(allColumns[5], card.getNumIncorrect());
        values.put(allColumns[7], card.getRating());

        database.update(SQLHelper.TABLE_CARDS, values, allColumns[0] + " = ?", new String[]{String.valueOf(id)});

        return card;
    }

    /**
     * Parse cursor to card.
     *
     * @param cursor
     * @return
     */
    private Card cursorToCard(Cursor cursor) {
        Card card = new Card();

        card.setId(cursor.getLong(0));
        card.setName(cursor.getString(cursor.getColumnIndex(SQLHelper.CARDS_COLUMN_NAME)));
        card.setQuestion(cursor.getString(cursor.getColumnIndex(SQLHelper.CARDS_COLUMN_QUESTION)));
        card.setAnswer(cursor.getString(cursor.getColumnIndex(SQLHelper.CARDS_COLUMN_ANSWER)));
        card.setNumCorrect(Integer.valueOf(cursor.getString(cursor.getColumnIndex(SQLHelper.CARDS_COLUMN_CORRECT_COUNT))));
        card.setNumIncorrect(Integer.valueOf(cursor.getString(cursor.getColumnIndex(SQLHelper.CARDS_COLUMN_INCORRECT_COUNT))));
        System.out.println("CURSOR 7: " + cursor.getString(7));
        card.setSubject(cursor.getString(cursor.getColumnIndex(SQLHelper.CARDS_COLUMN_SUBJECT)));
        card.setRating(cursor.getString(cursor.getColumnIndex(SQLHelper.CARDS_COLUMN_RATING)));

        return card;
    }
}
