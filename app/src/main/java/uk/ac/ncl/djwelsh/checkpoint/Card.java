package uk.ac.ncl.djwelsh.checkpoint;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel on 27/03/16.
 *
 * Representation of a Card.
 */
public class Card implements Parcelable{

    private long id;
    private String name;
    private String question;
    private String answer;
    private String subject;
    private String rating;
    private int numCorrect;
    private int numIncorrect;
    private double percentCorrect;

    public Card(){
    }

    /**
     * Constructor to initialise object.
     *
     * @param name name of card
     * @param question question
     * @param answer answer
     * @param subject subject
     * @param rating rating
     */
    public Card(String name, String question, String answer, String subject, String rating) {
        this.name = name;
        this.question = question;
        this.answer = answer;
        this.subject = subject;
        this.rating = rating;
    }

    /**
     * Create card from a parcel.
     *
     * @param in parcel
     */
    public Card(Parcel in) {

        this.id = in.readLong();
        this.name = in.readString();
        this.question = in.readString();
        this.answer = in.readString();
        this.subject = in.readString();
        this.rating = in.readString();
        this.numCorrect = in.readInt();
        this.numIncorrect = in.readInt();
        this.percentCorrect = in.readDouble();
    }

    /**
     * Get ID
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Set ID
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get Name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Question
     *
     * @return
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Set Question
     *
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Get Answer
     *
     * @return
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Set answer
     *
     * @param answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Get subject
     *
     * @return
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Return subject.
     *
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get rating
     *
     * @return
     */
    public String getRating() {
        return rating;
    }

    /**
     * Set rating
     *
     * @param rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Get number of correct answers.
     * @return
     */
    public int getNumCorrect() {
        return numCorrect;
    }

    /**
     * Set number of correct answers.
     *
     * @param numCorrect
     */
    public void setNumCorrect(int numCorrect) {
        this.numCorrect = numCorrect;
    }

    /**
     * Get number of wrong answers
     *
     * @return
     */
    public int getNumIncorrect() {
        return numIncorrect;
    }

    /**
     * Set number of wrong answers
     *
     * @param numIncorrect
     */
    public void setNumIncorrect(int numIncorrect) {
        this.numIncorrect = numIncorrect;
    }

    /**
     * Get percent of times answer has been correct.
     *
     * @return
     */
    public double getPercentCorrect() {
        return percentCorrect;
    }

    /**
     * Set percentage
     * @param precentCorrect
     */
    public void setPercentCorrect(double precentCorrect) {
        this.percentCorrect = precentCorrect;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (getId() != card.getId()) return false;
        if (!getName().equals(card.getName())) return false;
        if (!getQuestion().equals(card.getQuestion())) return false;
        if (!getAnswer().equals(card.getAnswer())) return false;
        if (!getSubject().equals(card.getSubject())) return false;
        return getRating().equals(card.getRating());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + getQuestion().hashCode();
        result = 31 * result + getAnswer().hashCode();
        result = 31 * result + getSubject().hashCode();
        result = 31 * result + getRating().hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write the card to parcel
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(subject);
        dest.writeString(rating);
        dest.writeInt(numCorrect);
        dest.writeInt(numIncorrect);
        dest.writeDouble(percentCorrect);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    /**
     * Update a card in SQLite if values have been changed.
     *
     * @param context
     */
    public void updateCard(Context context){
        CardsDataSource db = new CardsDataSource(context);
        db.open();
        db.updateCard(id);
        db.close();
    }
}
