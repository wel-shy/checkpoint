package uk.ac.ncl.djwelsh.checkpoint;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Daniel on 28/03/16.
 *
 * Class that represents a quiz
 */
public class Quiz implements Parcelable{

    long id;
    int points;
    String type;
    Subject subject;
    Deck deck;
    int currentCard;
    int correctCount;
    int incorrectCount;
    int currentCorrectCount;
    int currentIncorrectCount;
    String date;

    public Quiz() {

    }

    /**
     * New quiz
     * @param subject
     * @param deck
     */
    public Quiz(Subject subject, Deck deck) {
        this.subject = subject;
        this.deck = deck;

        date = new java.util.Date().toString();
    }

    /**
     * Create quiz from a parcel.
     *
     * @param in
     */
    public Quiz(Parcel in) {
        points = in.readInt();
        subject = in.readParcelable(getClass().getClassLoader());
        deck = in.readParcelable(getClass().getClassLoader());
        currentCard = in.readInt();
        correctCount = in.readInt();
        incorrectCount = in.readInt();
        currentCorrectCount = in.readInt();
        currentIncorrectCount = in.readInt();
        date = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(int currentCard) {
        this.currentCard = currentCard;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getIncorrectCount() {
        return incorrectCount;
    }

    public void setIncorrectCount(int incorrectCount) {
        this.incorrectCount = incorrectCount;
    }

    public int getCurrentCorrectCount() {
        return currentCorrectCount;
    }

    public void setCurrentCorrectCount(int currentCorrectCount) {
        this.currentCorrectCount = currentCorrectCount;
    }

    public int getCurrentIncorrectCount() {
        return currentIncorrectCount;
    }

    public void setCurrentIncorrectCount(int currentIncorrectCount) {
        this.currentIncorrectCount = currentIncorrectCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Update total subject points.
     */
    public void finishQuiz() {
        String feedback = "You have scored " + points;
        subject.setPoints(subject.getPoints() + points);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(points);
        dest.writeParcelable(subject, flags);
        dest.writeParcelable(deck, flags);
        dest.writeInt(currentCard);
        dest.writeInt(correctCount);
        dest.writeInt(incorrectCount);
        dest.writeInt(currentCorrectCount);
        dest.writeInt(currentIncorrectCount);
        dest.writeString(date);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };
}
