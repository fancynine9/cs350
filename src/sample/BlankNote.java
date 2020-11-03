package sample;


import javafx.collections.ObservableList;
import sample.Note;

public class BlankNote {

    public static void makeBlankNote(ObservableList<Note> backingList) {
        backingList.add(new Note("Blank","New Note"));
    }


}