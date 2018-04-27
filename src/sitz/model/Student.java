package sitz.model;

import javafx.beans.property.*;
import javafx.scene.control.CheckBox;
import sitz.util.DateUtil;

import java.time.LocalDate;

public class Student
{
    private StringProperty firstName;
    private StringProperty lastName;
    private ObjectProperty<LocalDate> birthday;
    private IntegerProperty seatNumber;
    private IntegerProperty grade;
    private BooleanProperty checked;

    public Student()
    {
        this(null,null, true, 1, 1, "01.01.1999");
    }

    public Student(String lastName, String firstName, int seatNumber)
    {
        this(lastName, firstName, true, seatNumber, 1, "01.01.1999");
    }

    public Student(String lastName, String firstName, boolean checked, int seatNumber, int grade, String birthday)
    {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.birthday = new SimpleObjectProperty<LocalDate>(DateUtil.parse(birthday));
        this.seatNumber = new SimpleIntegerProperty(seatNumber);
        this.grade = new SimpleIntegerProperty(grade);
        this.checked = new SimpleBooleanProperty(checked);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public int getSeatNumber() {
        return seatNumber.get();
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber.set(seatNumber);
    }

    public IntegerProperty seatNumberProperty() {
        return seatNumber;
    }

    public void setGrade(int grade) {
        this.grade.set(grade);
    }

    public IntegerProperty gradeProperty() {
        return grade;
    }

    public int getGrade()
    {
        return grade.get();
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public boolean isChecked() {
        return checked.get();
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }
}
