package sitz.view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;
import sitz.MainApp;
import sitz.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class HomeScreenController
{
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, Boolean> checkboxColumn;
    @FXML
    private ListView<String> studentList;

    private MainApp mainApp;

    public HomeScreenController()
    {

    }

    public void initialize() {
        studentTable.setEditable(true);
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        checkboxColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Student, Boolean> param) {
                Student student = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(student.isChecked());

                booleanProp.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        student.setChecked(newValue);
                        System.out.println(student.isChecked());
                    }
                });
                return booleanProp;
            }
        });

        checkboxColumn.setCellFactory(new Callback<TableColumn<Student, Boolean>, TableCell<Student, Boolean>>() {
            @Override
            public TableCell<Student, Boolean> call(TableColumn<Student, Boolean> param) {
                CheckBoxTableCell<Student, Boolean> cell = new CheckBoxTableCell<Student, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

    }

    public void handleRandomizeButton()
    {
        Stack<Integer> seatNumbers = new Stack<Integer>();
        for (int i = 0; i < mainApp.getStudentData().size(); i++)
        {
            seatNumbers.push(mainApp.getStudentData().get(i).getSeatNumber());
        }

        Collections.shuffle(seatNumbers);

        for (int i = 0; i < mainApp.getStudentData().size(); i++)
        {
            mainApp.getStudentData().get(i).setSeatNumber(seatNumbers.pop());
        }

        showSelectedStudents();



    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        studentTable.setItems(mainApp.getStudentData());

        //List is a temporary way to display names and seats. Will add a better visual if time allows.
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        for(Student student : mainApp.getStudentData())
        {
            if(student.isChecked())
            {
                studentNames.add("Seat " + student.getSeatNumber() + ": " + student.getFirstName() + " " + student.getLastName());
            }
        }
        studentList.setItems(studentNames);
    }

    public void showSelectedStudents()
    {
        //List is a temporary way to display names and seats. Will add a better visual if time allows.
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        for(Student student : mainApp.getStudentData())
        {
            if(student.isChecked())
            {
                studentNames.add("Seat " + student.getSeatNumber() + ": " + student.getFirstName() + " " + student.getLastName());
            }
        }
        studentList.setItems(studentNames);
    }
}
