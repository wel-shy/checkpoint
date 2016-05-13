package uk.ac.ncl.djwelsh.checkpoint;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel on 28/03/16.
 *
 * Class that represents a quiz
 */
public class Quiz implements Parcelable{

    private long id;
    private int points;
    private String quizType;
    private Subject subject;
    private Deck deck;
    private int currentCardIdx = 0;
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int currentCorrectCount = 0;
    private int currentIncorrectCount = 0;
    private String date;

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

        this.date = new java.util.Date().toString();
        this.currentCardIdx = 0;
        this.correctCount = 0;
        this.incorrectCount = 0;
        this.currentCorrectCount = 0;
        this.currentIncorrectCount = 0;
    }

    /**
     * Create quiz from a parcel.
     *
     * @param in
     */
    public Quiz(Parcel in) {
        this.id = in.readLong();
        this.points = in.readInt();
        this.quizType = in.readString();
        this.subject = in.readParcelable(getClass().getClassLoader());
        this.deck = in.readParcelable(getClass().getClassLoader());
        this.currentCardIdx = in.readInt();
        this.correctCount = in.readInt();
        this.incorrectCount = in.readInt();
        this.currentCorrectCount = in.readInt();
        this.currentIncorrectCount = in.readInt();
        this.date = in.readString();
    }

    // Getters and setters
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

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
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

    public int getCurrentCardIdx() {
        return currentCardIdx;
    }

    public void setCurrentCardIdx(int currentCardIdx) {
        this.currentCardIdx = currentCardIdx;
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

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write quiz to parcel.
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeInt(points);
        dest.writeString(quizType);
        dest.writeParcelable(subject, flags);
        dest.writeParcelable(deck, flags);
        dest.writeInt(currentCardIdx);
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

    public Card getCurrentCard() {

        return deck.getCard(currentCardIdx);
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", points=" + points +
                ", quizType='" + quizType + '\'' +
                ", subject=" + subject +
                ", deck=" + deck +
                ", currentCardIdx=" + currentCardIdx +
                ", correctCount=" + correctCount +
                ", incorrectCount=" + incorrectCount +
                ", currentCorrectCount=" + currentCorrectCount +
                ", currentIncorrectCount=" + currentIncorrectCount +
                ", date='" + date + '\'' +
                '}';
    }
}
