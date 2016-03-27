package uk.ac.ncl.djwelsh.checkpoint;

/**
 * Created by Daniel on 27/03/16.
 */
public class Subject {

    private long id;
    private String name;

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

    public String toString() {
        return getName();
    }
}
