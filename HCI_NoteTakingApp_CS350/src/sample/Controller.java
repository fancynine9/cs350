package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;



public class Controller implements Initializable {

    // These need to be binded to the sampleFXML file buttons

    @FXML
    private TextArea notesAreaTextArea;
    @FXML
    private Button exitButton;
    @FXML
    private Button newNoteButton;
    @FXML
    private ListView<Note> listView;


    //This is the observable list we will see on the left side of application which
    // will be a collection of all the current notes
    private Note selectedNote = new Note("");
    private final ObservableList<Note> noteList = FXCollections.observableArrayList(Note.extractor);
    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
    private ChangeListener<Note> noteChangeListener;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Initialize the buttons to always be enabled
        newNoteButton.defaultButtonProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());
        exitButton.defaultButtonProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull()
                .or(modifiedProperty.not()));

        // This will create a list which contains notes and is added to the observable NoteList
        List<Note> notesList = new ArrayList<>();

        noteList.setAll(notesList);


        listView.getSelectionModel().selectedItemProperty().addListener(
                noteChangeListener = (observable, oldValue, newValue) -> {
                    System.out.println("Selected item: " + newValue);


                    selectedNote = newValue;
                    modifiedProperty.set(false);
                    if (newValue != null) {
                        notesAreaTextArea.setText(selectedNote.getNote());

                    }


                });


    }








}
