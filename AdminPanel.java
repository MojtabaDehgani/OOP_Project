package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPanel {

    public void start(Stage primaryStage) {

        VBox mainLayout = new VBox(10);
        mainLayout.setPrefSize(200,200);
        mainLayout.setPadding(new Insets(10));
        Button c = new Button("Card panel");

        Button u = new Button("Show users");
        Button b = new Button("Back");
c.setPrefSize(200,20);
u.setPrefSize(200,20);
b.setPrefSize(200,20);
        mainLayout.getChildren().addAll(c,u,b);
        Scene scene = new Scene(mainLayout, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Card App");
        primaryStage.show();

        c.setOnMouseClicked((e)->{
             new CardPanel().start(primaryStage);
        });
        u.setOnMouseClicked((e)->{
            primaryStage.setTitle("Player Information");

            PlayerView playerView = new PlayerView();
            Scene scene2 = new Scene(playerView.getView(), 800, 600);

            primaryStage.setScene(scene2);
            primaryStage.show();
        });
        b.setOnMouseClicked((e)->{
            try {

                new Main().start(primaryStage);
            } catch (Exception ex) {

            }
        });
    }

}
