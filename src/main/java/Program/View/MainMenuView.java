package Program.View;

import Program.Controller.MainMenuController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.Objects;

public class MainMenuView {
    Stage stage;
    Group root;
    Scene scene;
    Button newGameButton;
    Button profileButton;
    Button scoreboardButton;
    Button exitButton;





    ////methods////
    public MainMenuView(Stage stage, MainMenuController controller)
    {
        this.stage = stage;

        root = new Group();

        loadRoot(controller);

        /*FXMLLoader fxmlLoader = new FXMLLoader((Objects.requireNonNull(getClass().getResource("/fxml/MainMenu.fxml"))));

        try {
            fxmlLoader.setController(controller);
            Pane pane = fxmlLoader.load();
            root.getChildren().add(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }*/


        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CssFiles/MenuButton.css")).toString());
        stage.setScene(scene);
        stage.show();
    }



    private void loadRoot(MainMenuController controller)
    {
        loadBackGround();
        loadButtons(controller);
    }



    private void loadBackGround()
    {
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/Textures/MainMenu/Background.png")).toString());
        ImageView backGround = new ImageView(image);
        root.getChildren().add(backGround);
    }



    private void loadButtons(MainMenuController controller)
    {
        newGameButton = new Button("new game");
        newGameButton.setLayoutX(490);
        newGameButton.setLayoutY(210);
        initializeButton(newGameButton);
        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.statNewGame();
            }
        });

        profileButton = new Button("profile");
        profileButton.setLayoutX(490);
        profileButton.setLayoutY(280);
        initializeButton(profileButton);
        profileButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.enterProfile();
            }
        });

        scoreboardButton = new Button("scoreboard");
        scoreboardButton.setLayoutX(490);
        scoreboardButton.setLayoutY(350);
        initializeButton(scoreboardButton);
        scoreboardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.enterScoreboard();
            }
        });

        exitButton = new Button("exit");
        exitButton.setLayoutX(490);
        exitButton.setLayoutY(420);
        initializeButton(exitButton);
        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.exit();
            }
        });
    }



    private void initializeButton(Button button)
    {
        button.setId("MenuButton");

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setId("MenuButtonHighlighted");
                Scale scale = new Scale(1.1, 1.1, button.getPrefWidth() / 2, button.getPrefHeight() / 2);
                button.getTransforms().add(scale);
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setId("MenuButton");
                Scale scale = new Scale(1 / 1.1, 1 / 1.1, button.getPrefWidth() / 2, button.getPrefHeight() / 2);
                button.getTransforms().add(scale);
            }
        });
        
        root.getChildren().add(button);
    }



    public Stage getStage()
    {
        return stage;
    }
}
