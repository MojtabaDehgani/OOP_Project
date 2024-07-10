package GUI;

import game.card.Card;
import game.card.CardService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class CardPanel {

   // private final CardService cardService = new CardService();
    private final List<Card> cards = CardService.getAll();

    public static String tmpDir=System.getProperty("java.io.tmpdir")+"citywars"+File.separator+"cards"+File.separator+"images"+File.separator;
    private final VBox cardContainer = new VBox(10); // Container for cards with spacing
static {
    try {
        Files.createDirectories(Paths.get(tmpDir));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
    public void start(Stage primaryStage) {
        ScrollPane scrollPane = new ScrollPane(cardContainer); // Make it scrollable
        scrollPane.setFitToWidth(true);

        Button addCardButton = new Button("Add Card");
        Button back = new Button("Back");
        back.setOnMouseClicked(event -> {
            new AdminPanel().start(primaryStage);
        });
        addCardButton.setOnAction(e -> openAddCardWindow());

        VBox mainLayout = new VBox(10, addCardButton,back, scrollPane);
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Card App");
        primaryStage.show();
        refreshCards();
    }

    private VBox createCardUI(Card card) {
        VBox cardBox = new VBox(5);
        cardBox.setPadding(new Insets(10));
        cardBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        // Add card details
        HBox nameBox = new HBox(10, new Label("Name:"), new Label(card.name));
        HBox isSpecialBox = new HBox(10, new Label("Special:"), new Label(String.valueOf(card.isSpecial)));
        HBox defenceAttackBox = new HBox(10, new Label("Defense/Attack:"), new Label(String.valueOf(card.defenceAttack)));
        HBox durationBox = new HBox(10, new Label("Duration:"), new Label(String.valueOf(card.duration)));
        HBox playerDamageBox = new HBox(10, new Label("Player Damage:"), new Label(String.valueOf(card.playerDamage)));
        HBox upgradeLevelBox = new HBox(10, new Label("Upgrade Level:"), new Label(String.valueOf(card.upgradeLevel)));
        HBox upgradeCostBox = new HBox(10, new Label("Upgrade Cost:"), new Label(String.valueOf(card.upgradeCost)));
        HBox priceBox = new HBox(10, new Label("Price:"), new Label(String.valueOf(card.price)));

        // Add an image if available
        ImageView imageView = new ImageView();
        if (card.imagePath != null && !card.imagePath.isEmpty()) {
            imageView.setImage(new Image("file:"+tmpDir + card.imagePath));
        } else {
            imageView.setImage(new Image("file:"+tmpDir+"default.png"));
        }
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        cardBox.getChildren().addAll(imageView, nameBox, isSpecialBox, defenceAttackBox, durationBox, playerDamageBox, upgradeLevelBox, upgradeCostBox, priceBox);

        cardBox.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.PRIMARY){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO);

                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.get()==ButtonType.YES){
                    CardService.delete(card);
                    cards.remove(card);
                    refreshCards();
                }
            }else {
                openEditWindow(card);
            }
        });

        return cardBox;
    }

    private void openEditWindow(Card card) {
            Stage addStage = new Stage();
            VBox addBox = new VBox(10);
            ScrollPane scrollPane = new ScrollPane(addBox);
            addBox.setPadding(new Insets(10));

            // Create and add fields for adding
            TextField nameField = new TextField();
            CheckBox isSpecialField = new CheckBox();
            TextField defenceAttackField = new TextField();
            TextField durationField = new TextField();
            TextField playerDamageField = new TextField();
            TextField upgradeLevelField = new TextField();
            TextField upgradeCostField = new TextField();
            TextField priceField = new TextField();
            nameField.setText(card.name);
            isSpecialField.setSelected(card.isSpecial);
            defenceAttackField.setText(String.valueOf(card.defenceAttack));
            durationField.setText(String.valueOf(card.duration));
            playerDamageField.setText(String.valueOf(card.playerDamage));
            upgradeCostField.setText(String.valueOf(card.upgradeCost));
            priceField.setText(String.valueOf(card.price));
upgradeLevelField.setText(String.valueOf(card.upgradeLevel));
            Button uploadButton = new Button("Upload Image");
            FileChooser fileChooser = new FileChooser();
            final String[] imagePath = new String[2];
            uploadButton.setOnAction(e -> {
                File file = fileChooser.showOpenDialog(addStage);
                if (file != null) {
                    imagePath[0] = file.getAbsolutePath();
                    imagePath[1] = file.getName();
                }

            });

            Button saveButton = new Button("Save");
            saveButton.setOnAction(e -> {
                // Validate inputs
                if (validateInputs(nameField, defenceAttackField, durationField, playerDamageField, upgradeLevelField, upgradeCostField, priceField)) {
                    // Create a new card with the data
                    Card newCard = new Card(
                            nameField.getText(),
                            isSpecialField.isSelected(),
                            Integer.parseInt(defenceAttackField.getText()),
                            Integer.parseInt(durationField.getText()),
                            Integer.parseInt(playerDamageField.getText()),
                            Integer.parseInt(upgradeLevelField.getText()),
                            Integer.parseInt(upgradeCostField.getText()),
                            Integer.parseInt(priceField.getText())
                    );

                    if(imagePath[0]!=null) {

                        try {


                            Files.copy(Paths.get(imagePath[0]), Paths.get(tmpDir + imagePath[1]));

                        } catch (IOException ex) {
                            //    ex.printStackTrace();
                            System.out.println(ex.getMessage());
                        }
                        newCard.imagePath = imagePath[1];
                    }

                    newCard.id=card.id;
                    cards.remove(card);

                  if(  newCard.imagePath ==null){
                      newCard.imagePath=card.imagePath;
                  }
                    cards.add(newCard);
                    CardService.update(newCard);
                    refreshCards();
                    addStage.close();
                }
            });

            addBox.getChildren().addAll(
                    new Label("Name:"), nameField,
                    new Label("Special:"), isSpecialField,
                    new Label("Defense/Attack:"), defenceAttackField,
                    new Label("Duration:"), durationField,
                    new Label("Player Damage:"), playerDamageField,
                    new Label("Upgrade Level:"), upgradeLevelField,
                    new Label("Upgrade Cost:"), upgradeCostField,
                    new Label("Price:"), priceField,
                    uploadButton,
                    saveButton
            );

            Scene addScene = new Scene(scrollPane, 300, 450);
            addStage.setScene(addScene);
            addStage.setTitle("Edit Card");
            addStage.show();
        }
    private void openAddCardWindow() {
        Stage addStage = new Stage();
        VBox addBox = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(addBox);
        addBox.setPadding(new Insets(10));

        // Create and add fields for adding
        TextField nameField = new TextField();
        CheckBox isSpecialField = new CheckBox();
        TextField defenceAttackField = new TextField();
        TextField durationField = new TextField();
        TextField playerDamageField = new TextField();
        TextField upgradeLevelField = new TextField();
        TextField upgradeCostField = new TextField();
        TextField priceField = new TextField();

        Button uploadButton = new Button("Upload Image");
        FileChooser fileChooser = new FileChooser();
        final String[] imagePath = new String[2];
        uploadButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(addStage);
            if (file != null) {
                imagePath[0] = file.getAbsolutePath();
                imagePath[1] = file.getName();
            }
        });

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Validate inputs
            if (validateInputs(nameField, defenceAttackField, durationField, playerDamageField, upgradeLevelField, upgradeCostField, priceField)) {
                // Create a new card with the data
                Card newCard = new Card(
                        nameField.getText(),
                        isSpecialField.isSelected(),
                        Integer.parseInt(defenceAttackField.getText()),
                        Integer.parseInt(durationField.getText()),
                        Integer.parseInt(playerDamageField.getText()),
                        Integer.parseInt(upgradeLevelField.getText()),
                        Integer.parseInt(upgradeCostField.getText()),
                        Integer.parseInt(priceField.getText())
                );                    if(imagePath[0]!=null) {

                    try {


                        Files.copy(Paths.get(imagePath[0]), Paths.get(tmpDir + imagePath[1]));

                    } catch (IOException ex) {
                        //    ex.printStackTrace();
                        System.out.println(ex.getMessage());
                    }
                    newCard.imagePath = imagePath[1];
                }
                cards.add(newCard);
                CardService.create(newCard);
                refreshCards();
                addStage.close();
            }
        });

        addBox.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Special:"), isSpecialField,
                new Label("Defense/Attack:"), defenceAttackField,
                new Label("Duration:"), durationField,
                new Label("Player Damage:"), playerDamageField,
                new Label("Upgrade Level:"), upgradeLevelField,
                new Label("Upgrade Cost:"), upgradeCostField,
                new Label("Price:"), priceField,
                uploadButton,
                saveButton
        );

        Scene addScene = new Scene(scrollPane, 300, 450);
        addStage.setScene(addScene);
        addStage.setTitle("Add Card");
        addStage.show();
    }

    private boolean validateInputs(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText() == null || field.getText().trim().isEmpty()) {
                showAlert("Validation Error", "All fields must be filled.");
                return false;
            }

        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshCards() {
        cardContainer.getChildren().clear();
        for (Card card : cards) {
            cardContainer.getChildren().add(createCardUI(card));
        }
    }

}
