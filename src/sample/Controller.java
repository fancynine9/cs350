package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import sample.BlankNote;


import javax.swing.*;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.*;
import java.util.Timer;
import java.util.logging.Handler;


public class Controller implements Initializable {

    // These need to be binded to the sampleFXML file buttons

    @FXML
    private TextField titleOfNoteField;
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


    //This is the observable list we will see on the left side of application which
    // will be a collection of all the current notes
    private Note selectedNote;
    private final ObservableList<Note> notesList = FXCollections.observableArrayList(Note.extractor);
    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
    private ChangeListener<Note> noteChangeListener;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //AutoSave needs to be implemented in the initializable
        // whenever the list view is selected, execute the update function,
        // have a timer that saves every minute i.e. every 6000 ms


        //We can initialize it with a blank note
        //class defined for it
        BlankNote.makeBlankNote(notesList);

        //It is making me use a sorted list here, im not sure why??/
        //Maybe we can try to sort the notes ny date/or last access??
        SortedList<Note> noteList = new SortedList<>(notesList);
        listView.setItems(noteList);


        /**
         * THis is a mouse handler and whenever a mouse click is detected it will execute
         * a update action
         *
         * this is busted... it needs to execute the update action if a new note is selected as well
         */
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                updateNote();
            }
        });


        //This autoUpdate() only saves the current note that is selected
        Timer timer = new Timer();
        timer.schedule(new autoUpdate(),60000);

        newNoteButton.defaultButtonProperty().set(true);

        listView.getSelectionModel().selectedItemProperty().addListener(
                noteChangeListener = (observable, oldValue, newEntry) -> {
                    selectedNote = newEntry;
                    modifiedProperty.set(false);

                    if (newEntry != null) {
                        notesAreaTextArea.setText(selectedNote.getNote());
                        titleOfNoteField.setText(selectedNote.getNoteTitle());

                    }
                });

        listView.getSelectionModel().selectFirst();

    }

    @FXML
    public void createNewNote() {

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

    }


    public void updateNote() {

        Note selectedNote = listView.getSelectionModel().getSelectedItem();


        listView.getSelectionModel().selectedItemProperty().removeListener(noteChangeListener);
        selectedNote.setNote(notesAreaTextArea.getText());
        selectedNote.setNoteTitle(titleOfNoteField.getText());
        listView.getSelectionModel().selectedItemProperty().addListener(noteChangeListener);
        modifiedProperty.set(false);


    }

    public void exitProgram(ActionEvent actionEvent) {

        //There should be an save before exiting prompt which has a saveAll button
        System.exit(0);
    }


    //OK SO THIS... its on a mouse event, so the idea is if the mouse is clicked
    // in the listview it saves,
    //But....
    //It only saves if it is selected on the listview item


    public void updateAction(MouseEvent arg0) {
        System.out.println(listView.getSelectionModel().getSelectedItem() + "was saved");
    }


    class autoUpdate extends TimerTask {
        public void run() {
            updateNote();
            System.out.println("Update Successful");
        }
    }

}


