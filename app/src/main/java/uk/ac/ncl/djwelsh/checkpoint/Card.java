package uk.ac.ncl.djwelsh.checkpoint;

/**
 * Created by Daniel on 27/03/16.
 *
 * Representation of a Card.
 */
public class Card {

    private long id;
    private String name;
    private String question;
    private String answer;
    private String subject;
    private String rating;

    public Card(){

    }

    public Card(String name, String question, String answer, String subject, String rating) {
        this.name = name;
        this.question = question;
        this.answer = answer;
        this.subject = subject;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", subject='" + subject + '\'' +
                ", rating='" + rating + '\'' +
                '}';
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
}
