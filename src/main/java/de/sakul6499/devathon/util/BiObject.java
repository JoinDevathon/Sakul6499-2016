package de.sakul6499.devathon.util;

/**
 * Created by lukas on 06.11.16.
 */
public class BiObject<First, Second> {

    public First first;
    public Second second;

    public BiObject() {
        this.first = null;
        this.second = null;
    }

    public BiObject(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    public void set(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    public First getFirst() {
        return first;
    }

    public void setFirst(First first) {
        this.first = first;
    }

    public Second getSecond() {
        return second;
    }

    public void setSecond(Second second) {
        this.second = second;
    }
}
