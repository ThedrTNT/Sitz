package sitz;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sitz.model.Student;
import sitz.model.StudentDataWrapper;
import sitz.view.HomeScreenController;
import sitz.view.RootMenuController;
import sitz.view.StudentEditScreenController;
import sitz.view.StudentOverviewController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
        this.primaryStage.getIcons().add(new Image("file:eaglescouticon.png"));
        this.primaryStage.setTitle("Sitz: The Interactive Seating Experience");

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
        File file = getClassroomFilePath();
        if(file != null)
        {
            loadStudentDataFromFile(file);
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
            controller.showSelectedStudents();

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
            controller.setMainApp(this);
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

    public File getClassroomFilePath()
    {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if(filePath != null)
        {
            return new File(filePath);
        }
        else
        {
            return null;
        }
    }

    public void setClassroomFilePath(File file)
    {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if(file != null)
        {
            prefs.put("filePath", file.getPath());

            primaryStage.setTitle("Sitz: The Interactive Seating Experience - " + file.getName());
        }
        else
        {
            prefs.remove("filePath");
            primaryStage.setTitle("Sitz: The Interactive Seating Experience");
        }
    }

    public void loadStudentDataFromFile(File file)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(StudentDataWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StudentDataWrapper wrapper = (StudentDataWrapper) unmarshaller.unmarshal(file);

            studentData.clear();
            studentData.addAll(wrapper.getStudents());

            setClassroomFilePath(file);
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load the data from the file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveStudentDataToFile(File file)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(StudentDataWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StudentDataWrapper wrapper = new StudentDataWrapper();
            wrapper.setStudents(studentData);

            marshaller.marshal(wrapper, file);

            setClassroomFilePath(file);
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save the data to the file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
}
