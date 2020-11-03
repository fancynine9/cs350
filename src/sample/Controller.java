package sample;

import javafx.scene.control.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import sample.BlankNote;



import java.net.URL;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;



public class Controller implements Initializable {

    // These need to be binded to the sampleFXML file buttons

    @FXML
    private TextArea notesAreaTextArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button newNoteButton;
    @FXML
    private ListView<Note> listView;
    @FXML
    private MenuItem exitButton;
    @FXML
    private Label titleOfNoteLabel;

    //This is the observable list we will see on the left side of application which
    // will be a collection of all the current notes
    private Note selectedNote;
    private final ObservableList<Note> notesList = FXCollections.observableArrayList(Note.extractor);
    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
    private ChangeListener<Note> noteChangeListener;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //We can initialize it with a blank note
                //class defined for it
        BlankNote.makeBlankNote(notesList);

        //It is making me use a sorted list here, im not sure why??/
        //Maybe we can try to sort the notes ny date/or last access??
        SortedList<Note> noteList = new SortedList<>(notesList);
        listView.setItems(noteList);

        newNoteButton.defaultButtonProperty().set(true);

        listView.getSelectionModel().selectedItemProperty().addListener(
                noteChangeListener = (observable, oldValue, newEntry) -> {

                    selectedNote = newEntry;
                    modifiedProperty.set(false);
                    if (newEntry != null) {
                        notesAreaTextArea.setText(selectedNote.getNote());
                        titleOfNoteLabel.setText(selectedNote.getNoteTitle());
                    }
                });
            listView.getSelectionModel().selectFirst();
    }
    @FXML
    public void createNewNote(){

        //Create a new Note
        Note newNote = new Note();
        newNote.setNote("Blank");
        newNote.setNoteTitle("New Note");
        //add it to the notes list
        notesList.add(newNote);

       //select it
        listView.getSelectionModel().selectLast();
    }


    //This method will set the modified property to be true whenever a key is pressed - i.e someone
    //types something in the notes area
    @FXML
    private void handleKeyAction(KeyEvent keyEvent) {
        modifiedProperty.set(true);
    }



    @FXML
    public void saveButton(ActionEvent actionEvent) {

        //select the note we are trying to save
        Note selectedNote = listView.getSelectionModel().getSelectedItem();


        //Get the first line from the textArea so we can assign it as the noteTitle
        String[] selectedNoteLnArray = notesAreaTextArea.getText().split("\n");
        String firstLnSelectedNote = selectedNoteLnArray[0];
        System.out.println(firstLnSelectedNote);


        listView.getSelectionModel().selectedItemProperty().removeListener(noteChangeListener);

        selectedNote.setNote(notesAreaTextArea.getText());
        selectedNote.setNoteTitle(firstLnSelectedNote);


        listView.getSelectionModel().selectedItemProperty().addListener(noteChangeListener);
        modifiedProperty.set(false);

    }

    public void exitProgram(ActionEvent actionEvent) {

        //There should be an save before exiting prompt which has a saveAll button
        System.exit(0);
    }
}