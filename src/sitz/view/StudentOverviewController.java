package sitz.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sitz.MainApp;
import sitz.model.Student;
import sitz.util.DateUtil;

public class StudentOverviewController
{
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label seatNumberLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label gradeLabel;

    private MainApp mainApp;

    public StudentOverviewController()
    {

    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Clear person details.
        showStudentDetails(null);

        // Listen for selection changes and show the person details when changed.
        studentTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showStudentDetails(newValue));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        studentTable.setItems(mainApp.getStudentData());
    }

    public void showStudentDetails(Student student)
    {
        if(student != null)
        {
            firstNameLabel.setText(student.getFirstName());
            lastNameLabel.setText(student.getLastName());
            gradeLabel.setText(Integer.toString(student.getGrade()));
            seatNumberLabel.setText(Integer.toString(student.getSeatNumber()));
            birthdayLabel.setText(DateUtil.format(student.getBirthday()));
        }
        else
        {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            gradeLabel.setText("");
            seatNumberLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    @FXML
    public void handleDeleteStudent()
    {
        int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();

        if(selectedIndex >= 0) {
            studentTable.getItems().remove(selectedIndex);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select the student you wish to delete");

            alert.showAndWait();
        }
    }

    @FXML
    public void handleNewStudent()
    {
        Student newStudent = new Student();
        boolean okClicked = mainApp.showStudentEditScreen(newStudent);
        if(okClicked)
        {
            mainApp.getStudentData().add(newStudent);
        }
    }

    public void handleEditStudent()
    {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null)
        {
            boolean okClicked = mainApp.showStudentEditScreen(selectedStudent);
            if(okClicked)
            {
                showStudentDetails(selectedStudent);
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select the student you wish to edit");

            alert.showAndWait();
        }
    }
}
