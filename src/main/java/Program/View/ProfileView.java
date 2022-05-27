package Program.View;

import Program.Controller.ProfileController;
import Program.Model.GraphicModels.CustomTextField;
import Program.Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ProfileView {
    ProfileController controller;
    Stage stage;
    Group root;
    Scene scene;
    Stage confirmationStage;
    Button changeUsernameButton;
    Button changePasswordButton;
    CustomTextField usernameField;
    CustomTextField passwordField;




    ////methods////
    public ProfileView(Stage stage, User user)
    {
        this.stage = stage;

        root = new Group();

        FXMLLoader fxmlLoader = new FXMLLoader((Objects.requireNonNull(getClass().getResource("/fxml/Profile.fxml"))));

        try {
            Pane pane = fxmlLoader.load();
            controller = fxmlLoader.getController();
            root.getChildren().add(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        controller.initializeController(user, this);
        controller.loadUserImage();
        loadChangeButtons();

        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CssFiles/Profile.css").toString());
        stage.setScene(scene);
        stage.show();
    }

    private void loadChangeButtons()
    {
        changeUsernameButton = new Button("change username");
        changeUsernameButton.setLayoutX(490);
        changeUsernameButton.setLayoutY(305);
        changeUsernameButton.setPrefWidth(300);

        changeUsernameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.loadUsernameSection();
            }
        });

        changePasswordButton = new Button("change password");
        changePasswordButton.setLayoutX(490);
        changePasswordButton.setLayoutY(365);
        changePasswordButton.setPrefWidth(300);

        changePasswordButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.loadPasswordSection();
            }
        });

        root.getChildren().add(changeUsernameButton);
        root.getChildren().add(changePasswordButton);
    }


    public void getConfirmation(User user)
    {
        confirmationStage = new Stage();

        confirmationStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!confirmationStage.isFocused()){
                    confirmationStage.close();
                }
            }
        });

        Scene confirmationScene;
        Group confirmationRoot = new Group();

        confirmationStage.setHeight(200);
        confirmationStage.setWidth(500);
        confirmationStage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader((Objects.requireNonNull(getClass().getResource("/fxml/ProfileConfirmation.fxml"))));

        try {
            Pane pane = fxmlLoader.load();
            ProfileController profileController = new ProfileController();
            profileController.initializeNoText(user, this);
            confirmationRoot.getChildren().add(pane);
            addConfirmationButtons(profileController, confirmationRoot);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        confirmationScene = new Scene(confirmationRoot);
        confirmationScene.getStylesheets().add(getClass().getResource("/CssFiles/Profile.css").toString());
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();
    }



    private void addConfirmationButtons(ProfileController profileController, Group confirmationGroup)
    {
        Button yesButton = new Button("yes");
        yesButton.setLayoutX(65);
        yesButton.setLayoutY(75);
        yesButton.setPrefWidth(150);

        yesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                profileController.deleteAccount();
            }
        });

        Button noButton = new Button("no");
        noButton.setLayoutX(255);
        noButton.setLayoutY(75);
        noButton.setPrefWidth(150);

        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cancelDeleting();
            }
        });

        confirmationGroup.getChildren().add(yesButton);
        confirmationGroup.getChildren().add(noButton);
    }



    public void loadUsernameSection()
    {
        root.getChildren().remove(changeUsernameButton);

        usernameField = new CustomTextField(490, 305, 300, 50, "new username");
        Button usernameButton = new Button("save username");
        usernameButton.setLayoutX(880);
        usernameButton.setLayoutY(305);
        usernameButton.setPrefWidth(300);

        usernameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.changeUsername(usernameField);
            }
        });

        root.getChildren().add(usernameField.getNode());
        root.getChildren().add(usernameButton);
    }



    public void loadPasswordSection()
    {
        root.getChildren().remove(changePasswordButton);

        passwordField = new CustomTextField(490, 365, 300, 50, "new password");
        Button passwordButton = new Button("save password");
        passwordButton.setLayoutX(880);
        passwordButton.setLayoutY(365);
        passwordButton.setPrefWidth(300);

        passwordButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.changePassword(passwordField);
            }
        });

        root.getChildren().add(passwordField.getNode());
        root.getChildren().add(passwordButton);
    }



    public void cancelDeleting()
    {
        confirmationStage.close();
    }



    public void showInvalidUsernameFormat()
    {
        usernameField.showWarning("invalid username format");
    }



    public void showExistingUsername()
    {
        usernameField.showWarning("this username already exists");
    }



    public void showUsernameChanged()
    {
        usernameField.showSuccessful("username changed successfully");
    }



    public void showInvalidPasswordFormat()
    {
        passwordField.showWarning("invalid password format");
    }



    public void showPasswordChanged()
    {
        passwordField.showSuccessful("password changed successfully");
    }



    public Stage getStage()
    {
        return stage;
    }
}
