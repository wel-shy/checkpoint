package uk.ac.ncl.djwelsh.checkpoint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
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

        for (int i = 0; i < allColumns.length - 1; i++) {
            values.put(allColumns[i + 1], data[i]);
        }

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

        Card card = null;

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

    /**
     * Parse cursor to card.
     *
     * @param cursor
     * @return
     */
    private Card cursorToCard(Cursor cursor) {
        Card card = new Card();

        card.setId(cursor.getLong(0));
        card.setName(cursor.getString(1));
        card.setQuestion(cursor.getString(2));
        card.setAnswer(cursor.getString(3));
        card.setSubject(cursor.getString(4));
        card.setRating(cursor.getColumnName(5));

        return card;
    }
}
