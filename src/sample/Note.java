package sample;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import java.awt.*;
import java.util.Objects;

public class Note {

    // This will be a note Object which will just be
    // an object that contains text, this will be an object
    // so we can call and get information from each set of notes when
    // we want to save/remove/update
    // in order to keep track of multiple (note) entries i.e. we will
    // use an arrayList of notes


    // Need to have a reference to the first ln of the note in order to reference it in the listView
    // THis will act as the title of the note

    private final StringProperty note = new SimpleStringProperty(this,"Note","");
    private final StringProperty noteTitle = new SimpleStringProperty(this,"Title","");


    //Constructor for a note object


    public Note(String note, String noteTitle) {
        this.note.set(note);
        this.noteTitle.set(noteTitle);
    }

    public Note(){
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

    public String getNoteTitle() {
        return noteTitle.get();
    }

    public StringProperty noteTitleProperty(){
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle.set(noteTitle);
    }

    public int hashCode() {
        return Objects.hash(note,noteTitle);
    }


    public static Callback<Note, Observable[]> extractor = n -> new Observable[]
            {n.noteTitleProperty()};


    public String toString() {
        return noteTitle.get();
    }
}
