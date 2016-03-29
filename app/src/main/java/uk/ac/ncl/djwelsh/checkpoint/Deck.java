package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Daniel on 29/03/16.
 *
 * Represents a deck of flash cards.
 */
public class Deck implements Parcelable{

    List<Card> cards;
    int deckLength = cards.size();
    CardsDataSource cardsDB;

    public Deck(Subject subject, Context context) {

        cardsDB = new CardsDataSource(context);
        cardsDB.open();

        cards = cardsDB.getCardBySubject(String.valueOf(subject.getId()));

        cardsDB.close();
    }

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public Deck(Parcel in) {

        cards = new ArrayList<Card>();
        this.deckLength = in.readInt();

        for(int i = 0; i < deckLength; i++) {
            Card card = in.readParcelable(getClass().getClassLoader());
            cards.add(card);
        }
    }

    public void sortByDifficulty() {


    }

    public void randomiseDeck() {
        Collections.shuffle(cards, new Random(System.currentTimeMillis()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(deckLength);

        for (int i = 0; i < cards.size(); i++) {
            dest.writeParcelable(cards.get(i), flags);
        }
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Deck> CREATOR = new Parcelable.Creator<Deck>() {
        public Deck createFromParcel(Parcel in) {
            return new Deck(in);
        }

        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };

}
