package uk.ac.ncl.djwelsh.checkpoint;

/**
 * Created by Daniel on 28/03/16.
 *
 * Class to represent a quick fire quiz.
 */
public class QuickFireQuiz extends Quiz {

    private int timer = 120;

    public QuickFireQuiz(Subject subject, Card[] deck, int timer) {
        super(subject, deck);
        this.timer = timer;
    }
}
