package sitz.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import sitz.MainApp;
import sitz.model.Student;

import java.io.File;
import java.util.Optional;

public class RootMenuController
{
    @FXML
    private MainApp mainApp;

    public RootMenuController()
    {
    }

    @FXML
    public void handleOverviewButton()
    {
        mainApp.showStudentOverview();
    }

    public void handleHomeButton()
    {
        mainApp.showHomeScreen();
    }

    public void handleNewButton()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to create a new file without saving?");
        alert.initOwner(mainApp.getPrimaryStage());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            //Clears observable list of students and whatever we use to store the file it is saved in

            mainApp.getStudentData().clear();
            mainApp.setClassroomFilePath(null);
            mainApp.showHomeScreen();
        }
    }

    public void handleOpenButton()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to open a different file without saving?");
        alert.initOwner(mainApp.getPrimaryStage());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            //Open a file browser for user to select classroom to import
            //Assign file path to an instance variable to pass to a file reader to import the data
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

            File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

            if(file != null)
            {
                mainApp.loadStudentDataFromFile(file);
            }
            mainApp.showHomeScreen();
        }
    }

    public void handleSaveButton()
    {
        //Writes the data to the file selected when opened
        //If no file was opened and is a new classroom then handle this as if it were the save button
        File studentFile = mainApp.getClassroomFilePath();
        if(studentFile != null)
        {
            mainApp.saveStudentDataToFile(studentFile);
        }
        else
        {
            handleSaveAsButton();
        }
    }

    public void handleSaveAsButton()
    {
        //Open file browser for user to select what to save the file as
        //Save new file name in instance variable
        //Write to new file
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if(file != null)
        {
            if(!file.getPath().endsWith(".xml"))
            {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveStudentDataToFile(file);
        }
    }

    public void handleCloseButton()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to quit without saving?");
        alert.initOwner(mainApp.getPrimaryStage());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            mainApp.close();
        }
    }

    public void handleSelectAll()
    {
        for(Student student : mainApp.getStudentData())
        {
            student.setChecked(true);
        }
        mainApp.showHomeScreen();
    }

    public void handleDeselectAll()
    {
        for(Student student : mainApp.getStudentData())
        {
            student.setChecked(false);
        }
        mainApp.showHomeScreen();
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }
}
