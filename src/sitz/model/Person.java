package sitz.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public interface Person {
    //Gets the persons first name and returns it as a string
    String getFirstName();

    //Sets the persons first name to the string passed to the method
    void setFirstName(String firstName);

    //Gets the value of the firstNameProperty
    StringProperty firstNameProperty();

    //Gets the persons last name and returns it as a string
    String getLastName();

    //Sets the persons last name to the string passed to the method
    void setLastName(String lastName);

    //Gets the value of the lastNameProperty
    StringProperty lastNameProperty();

    //Gets the persons birthday and returns it as a LocalDate
    LocalDate getBirthday();

    //Sets the persons birthday to the LocalDate passed to the method
    void setBirthday(LocalDate birthday);

    //Gets the value of the birthdayProperty
    ObjectProperty<LocalDate> birthdayProperty();

    //Gets the persons seat number and returns it as an int
    int getSeatNumber();

    //Sets the persons seat number to the int passed to the method
    void setSeatNumber(int seatNumber);

    //Gets the value of the seatNumberProperty
    IntegerProperty seatNumberProperty();

    //Gets the persons grade and returns it as an int
    int getGrade();

    //Sets the persons grade to the int passed to the method
    void setGrade(int grade);

    //Gets the value of the gradeProperty
    IntegerProperty gradeProperty();

    //Gets whether or not the person has been checked or not and returns it as a boolean
    boolean isChecked();

    //Sets the status of whether a person has been checked or not to the value of the boolean passed to it
    void setChecked(boolean checked);

    //Gets the value of the checkedProperty
    BooleanProperty checkedProperty();
}
