package uk.ac.ncl.djwelsh.checkpoint;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel on 27/03/16.
 *
 * Class to represent a Subject
 */
public class Subject implements Parcelable {

    private long id;
    private String name;
    private int points;

    public Subject() {}

    /**
     * Create from values
     *
     * @param id
     * @param name
     */
    public Subject(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Create from parcel
     *
     * @param in
     */
    public Subject(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
    }

    // Getters and setters
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (getId() != subject.getId()) return false;
        return getName().equals(subject.getName());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write to parcel
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>() {
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
}
