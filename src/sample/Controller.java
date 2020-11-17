package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import sample.BlankNote;


import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
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
    private Button deleteButton;
    @FXML
    private Button newNoteButton;
    @FXML
    private ListView<Note> listView;
    @FXML
    private MenuItem exitButton;
    @FXML
    private MenuItem redMenuItemButton;
    @FXML
    private MenuItem greenMenutItemButton;
    @FXML
    private MenuItem blueMenuItemButton;


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

        //This also seems to occur once - need to get it to repeat --

        
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
        enableSelectText();
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



    //THe AutoUpdate has a problem, once you are typing the notes area, the
    // note in the listview get deselected -- so the autoupdate needs to keep
    // track of the note that's being typed in

    //If it is too difficult to do, we need to modify the existing autosave model
    // where the listview is selected

    public void updateNote() {
        Note selectedNote = listView.getSelectionModel().getSelectedItem();
        listView.getSelectionModel().selectedItemProperty().removeListener(noteChangeListener);
        selectedNote.setNote(notesAreaTextArea.getText());
        selectedNote.setNoteTitle(titleOfNoteField.getText());
        listView.getSelectionModel().selectedItemProperty().addListener(noteChangeListener);
        modifiedProperty.set(false);
    }

    @FXML
    public void deleteButton(ActionEvent actionEvent) {
        listView.getSelectionModel().selectedItemProperty().removeListener(noteChangeListener);
        Note selectedNote = listView.getSelectionModel().getSelectedItem();
        notesList.remove(selectedNote);

        titleOfNoteField.setText("New Note");
        listView.getSelectionModel().selectedItemProperty().addListener(noteChangeListener);

    }



    //This is the method we will use to get the selected text in a text area
    // and apply modifications to that selected text
    public void enableSelectText(){

        notesAreaTextArea.setOnContextMenuRequested(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                System.out.println("selected text:"
                        + notesAreaTextArea.getSelectedText());
            }
        });
        }

        //These dont do anything yet
        //need to figure out why

    @FXML
    public void changeColorRed(ActionEvent actionEvent) {
        String selectedText = notesAreaTextArea.getSelectedText();
        Text text = new Text(30,80,selectedText);
        text.setFill(Color.RED);

    }
    @FXML
    public void changeColorBlue(ActionEvent actionEvent) {
        String selectedText = notesAreaTextArea.getSelectedText();
        Text text = new Text(30,80,selectedText);
        text.setFill(Color.BLUE);
    }

    @FXML
    public void changeColorGreen(ActionEvent actionEvent) {
        String selectedText = notesAreaTextArea.getSelectedText();
        Text text = new Text(30,80,selectedText);
        text.setFill(Color.GREEN);

    }



    // this does not work

    @FXML
    public void makeBold(ActionEvent actionEvent) {

        //Get selected Text
        String selectedText = notesAreaTextArea.getSelectedText();

        //Make a text Object and make that text bold
        Text StringToText = new Text();
        StringToText.setText(selectedText);
        StringToText.setFont(javafx.scene.text.Font.font(Font.BOLD));

        //Turn that text back into a string
        String boldText = StringToText.getText();

        notesAreaTextArea.getSelectedText().replace(notesAreaTextArea.getSelectedText(),boldText);

    }
    @FXML
    public void makeItalics(ActionEvent actionEvent) {
    }
    @FXML
    public void makeUnderline(ActionEvent actionEvent) {
    }



    class autoUpdate extends TimerTask {
        public void run() {
            updateNote();
            System.out.println("Update Successful");
        }
    }
}



