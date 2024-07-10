package GUI;
import GUI.util.WindowsUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.User;
import user.UserService;

public class EditProfile extends Application {
public static User loggedIn;
    TextField node;
    TextField usernameField;
    TextField nicknameField;
    TextField emailField;
    TextField password;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Edit Profile");

        // Create the input fields
         usernameField = new TextField(loggedIn.username);
         nicknameField = new TextField(loggedIn.nickname);
         emailField = new TextField(loggedIn.email);
         password= new TextField(loggedIn.password);
        node = usernameField;

        // Create the save button
        Button saveButton = new Button("Save");
        saveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(UserService.getByUsername(usernameField.getText())!=null && !usernameField.getText().equalsIgnoreCase(loggedIn.username)){
                WindowsUtils.createAlertDialog("Error","Username","Username is Already Exist. Please Use another one");
                usernameField.setText(loggedIn.username);
                return;
            }
            saveProfile(
                usernameField.getText(),
                nicknameField.getText(),
                emailField.getText(),
                password.getText(), primaryStage
        );});

        // Add fields to the layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameField, nicknameField, emailField, password , saveButton);

        // Set the scene and show the stage
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to handle saving the profile
    private void saveProfile(String username, String nickname, String email, String password, Stage primaryStage) {
        if (loggedIn != null) {
          loggedIn.id=  UserService.getByUsername(loggedIn.username).id;
            loggedIn.username = username;
            loggedIn.nickname = nickname;
            loggedIn.email = email;
            loggedIn.password= password;
            System.out.println("Profile updated successfully!");
            UserService.update(loggedIn);
            try {
                new Main().start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}