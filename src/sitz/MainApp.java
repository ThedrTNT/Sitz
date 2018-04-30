package sitz;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitz.model.Student;
import sitz.view.HomeScreenController;
import sitz.view.RootMenuController;
import sitz.view.StudentEditScreenController;
import sitz.view.StudentOverviewController;

public class MainApp extends Application {


    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    public MainApp()
    {
        //This is sample data
        studentData.add(new Student("May", "Justin", 1));
        studentData.add(new Student("McCarroll", "David", 15));
        studentData.add(new Student("Conrad", "Bob", 2));
        studentData.add(new Student("Ward", "Cooper", 11));
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sitz");

        initRootLayout();

        showHomeScreen();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Give the controller access to the main app.
            RootMenuController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the student overview inside the root layout.
     */
    public void showStudentOverview() {
        try {
            // Load student overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StudentOverview.fxml"));
            AnchorPane studentOverview = (AnchorPane) loader.load();

            // Set student overview into the center of root layout.
            rootLayout.setCenter(studentOverview);

            // Give the controller access to the main app.
            StudentOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHomeScreen()
    {
        try {
            // Load student overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/HomeScreen.fxml"));
            AnchorPane homeScreen = (AnchorPane) loader.load();

            // Set student overview into the center of root layout.
            rootLayout.setCenter(homeScreen);

            // Give the controller access to the main app.
            HomeScreenController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showStudentEditScreen(Student student)
    {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StudentEditScreen.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage editStage = new Stage();
            editStage.setTitle("Edit Person");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            editStage.setScene(scene);

            // Set the person into the controller.
            StudentEditScreenController controller = loader.getController();
            controller.setEditStage(editStage);
            controller.setStudent(student);

            // Show the dialog and wait until the user closes it
            editStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Student> getStudentData() {
        return studentData;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void close()
    {
        primaryStage.close();
    }


    public void setStudentData(ObservableList<Student> list)
    {
        this.studentData = list;
    }
}
