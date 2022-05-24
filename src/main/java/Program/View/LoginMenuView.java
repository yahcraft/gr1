package Program.View;

import Program.Controller.LoginMenuController;
import Program.Model.GraphicModels.CustomTextField;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginMenuView{
    Stage stage;
    Scene scene;
    Group root;
    CustomTextField username;
    CustomTextField password;





    public LoginMenuView(Stage stage, LoginMenuController controller) throws IOException {
        this.stage = stage;
        initializeStage(controller);
    }



    private void initializeStage(LoginMenuController controller) {
        root = new Group();
        scene = new Scene(root);

        initializeStageSize();
        loadBackGround();
        loadElements(controller);
        initializeIcon();

        stage.setScene(scene);
        stage.show();
    }



    private void loadBackGround()
    {
        Image backGround = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Textures/LoginMenu/Background.png")));
        ImageView imageView = new ImageView();
        imageView.setImage(backGround);
        imageView.setX(0);
        imageView.setY(0);
        root.getChildren().add(imageView);
    }




    private void initializeStageSize()
    {
        stage.setMaxHeight(745); //720 + 25  for window bar
        stage.setMaxWidth(1280);
        stage.setMinHeight(745);
        stage.setMinWidth(1280);
        stage.setHeight(745);
        stage.setWidth(1280);
    }



    private void initializeIcon()
    {
        Image icon = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/Textures/Icon.png")));
        stage.setTitle("CupHead AP");
        stage.getIcons().add(icon);
    }



    private void loadElements(LoginMenuController controller)
    {
        username = new CustomTextField(300, 200, 300, 50,"username");
        password = new CustomTextField(680, 200, 300, 50,"password");

        Button loginButton = new Button();
        loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.login(username, password);
            }
        });

        Button registerButton = new Button();
        registerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.register(username, password);
            }
        });

        Button exitButton = new Button();
        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.exit();
            }
        });

        loadButtons(loginButton, registerButton, exitButton, username, password);

        root.getChildren().add(username.getNode());
        root.getChildren().add(password.getNode());
        root.getChildren().add(loginButton);
        root.getChildren().add(registerButton);
        root.getChildren().add(exitButton);
    }



    private void loadButtons(Button loginButton, Button registerButton, Button exitButton, CustomTextField username, CustomTextField password)
    {
        loginButton.setLayoutX(300);
        loginButton.setLayoutY(280);
        loginButton.setText("login");
        setButtonProperties(loginButton);

        registerButton.setLayoutX(680);
        registerButton.setLayoutY(280);
        registerButton.setText("register");
        setButtonProperties(registerButton);

        exitButton.setLayoutX(490);
        exitButton.setLayoutY(360);
        exitButton.setText("exit");
        setButtonProperties(exitButton);
    }



    private void setButtonProperties(Button button)
    {
        button.setStyle("-fx-background-radius: 15px; -fx-background-color: black");
        button.setPrefWidth(300);
        button.setPrefHeight(50);
        button.setTextFill(Color.WHITE);
        button.setFont(Font.font(25));

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Scale scale = new Scale(1.1, 1.1, button.getWidth() / 2, button.getHeight() / 2);
                button.getTransforms().add(scale);
                button.setStyle("-fx-background-radius: 15px; -fx-background-color: #141414");
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Scale scale = new Scale(1 / 1.1, 1 / 1.1, button.getWidth() / 2, button.getHeight() / 2);
                button.getTransforms().add(scale);
                button.setStyle("-fx-background-radius: 15px; -fx-background-color: black");
            }
        });
    }



    public Stage getStage()
    {
        return stage;
    }



    public void showInvalidUsernameFormat()
    {
        username.showWarning("invalid username format");
    }



    public void showExistingUsername()
    {
        username.showWarning("this username already exist");
    }



    public void showInvalidPasswordFormat()
    {
        password.showWarning("invalid password format");
    }



    public void showWrongPassword()
    {
        username.showWarning("username or password is wrong");
        password.showWarning("username or password is wrong");
    }
}
