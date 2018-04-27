package sitz.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import sitz.MainApp;

public class RootMenuController
{
    @FXML
    private MainApp mainApp;
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
        //To be implemented
        //Clears observable list of students and whatever we use to store the file it is saved in
    }

    public void handleOpenButton()
    {
        //To be implemented
        //Open a file browser for user to select classroom to import
        //Assign file path to an instance variable to pass to a file reader to import the data
    }

    public void handleSaveButton()
    {
        //To be implemented
        //Writes the data to the file selected when opened
        //If no file was opened and is a new classroom then handle this as if it were the save button
    }

    public void handleSaveAsButton()
    {
        //To be implemented
        //Open file browser for user to select what to save the file as
        //Save new file name in instance variable
        //Write to new file
    }

    public void handleCloseButton()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Close");
        alert.setHeaderText("Closing without saving");
        alert.setContentText("You are closing without saving. Any unsaved changes will be lost.");

        alert.showAndWait();
        mainApp.close();
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }
}
