package uk.ac.ncl.djwelsh.checkpoint;

import java.sql.Timestamp;

/**
 * Created by Daniel on 28/03/16.
 *
 * Class that represents a quiz
 */
public class Quiz {

    int points;
    Subject subject;
    Card[] deck;
    int currentCard;
    int correctCount;
    int incorrectCount;
    int currentCorrectCount;
    int currentIncorrectCount;
    String date;


    /**
     * New quiz
     * @param subject
     * @param deck
     */
    public Quiz(Subject subject, Card[] deck) {
        this.subject = subject;
        this.deck = deck;

        date = new java.util.Date().toString();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Card[] getDeck() {
        return deck;
    }

    public void setDeck(Card[] deck) {
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
}
