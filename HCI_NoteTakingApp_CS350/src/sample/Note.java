package sample;


import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import java.util.Objects;

public class Note {

    // This will be a note Object which will just be
    // an object that contains text, this will be an object
    // so we can call and get information from each set of notes when
    // we want to save/remove/update
    // in order to keep track of multiple (note) entries i.e. we will
    // use an arrayList of notes

    private final StringProperty note = new SimpleStringProperty(this,"Note","");



    //Constructor for a note object
    public Note(String note) {

        this.note.set(note);
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public int hashCode() {
        return Objects.hash(note);
    }


    public static Callback<Note, Observable[]> extractor = n -> new Observable[]
            {n.noteProperty()};
}
