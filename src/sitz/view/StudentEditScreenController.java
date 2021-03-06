package sitz.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sitz.MainApp;
import sitz.model.Student;
import sitz.util.DateUtil;

import java.util.Date;

public class StudentEditScreenController
{
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField gradeField;
    @FXML
    private TextField seatNumberField;
    @FXML
    private TextField birthdayField;

    private Stage editStage;
    private Student student;
    private MainApp mainApp;
    private boolean okClicked = false;

    public StudentEditScreenController()
    {

    }

    @FXML
    public void initialize()
    {

    }

    public void setEditStage(Stage editStage)
    {
        this.editStage = editStage;
    }

    public void setStudent(Student student)
    {
        this.student = student;

        firstNameField.setText(student.getFirstName());
        lastNameField.setText(student.getLastName());
        gradeField.setText(Integer.toString(student.getGrade()));
        seatNumberField.setText(Integer.toString(student.getSeatNumber()));
        birthdayField.setText(DateUtil.format(student.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    public boolean isOkClicked()
    {
        return okClicked;
    }

    public void applyOk()
    {
        if(isInputValid())
        {
            student.setFirstName(firstNameField.getText());
            student.setLastName(lastNameField.getText());
            student.setGrade(Integer.parseInt(gradeField.getText()));
            student.setSeatNumber(Integer.parseInt(seatNumberField.getText()));
            student.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            editStage.close();
        }
    }

    public void applyCancel()
    {
        editStage.close();
    }

    public boolean isInputValid()
    {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() <= 0) {
            errorMessage += "No valid first name!\n";
        }

        if (lastNameField.getText() == null || lastNameField.getText().length() <= 0) {
            errorMessage += "No valid last name!\n";
        }

        if (gradeField.getText() == null || gradeField.getText().length() <= 0) {
            errorMessage += "No valid grade!\n";
        } else {
            try {
                int test = Integer.parseInt(gradeField.getText());
                if(test < 0)
                {
                    errorMessage += "Grade must be positive!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid grade! Must be an integer!";
            }
        }

        if (seatNumberField.getText() == null || seatNumberField.getText().length() <= 0) {
            errorMessage += "No valid seat!\n";
        } else {
            try {
                int test = Integer.parseInt(seatNumberField.getText());
                for(Student student : mainApp.getStudentData())
                {
                    if(student.getSeatNumber() == test && student != this.student)
                    {
                        errorMessage += "That seat is already assigned!\n";
                    }
                }
                if(test < 0)
                {
                    errorMessage += "No negative seat numbers!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid seat! Must be an integer!";
            }
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() <= 0) {
            errorMessage += "No valid birthday!\n";
        } else if (!DateUtil.validDate(birthdayField.getText())) {
            errorMessage += "Not a valid birthday format! Use mm.dd.yyyy!";
        }

        if(errorMessage.length() == 0)
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(editStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;

        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


}
