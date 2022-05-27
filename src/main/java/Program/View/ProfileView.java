package Program.View;

import Program.Controller.ProfileController;
import Program.Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class ProfileView {
    ProfileController controller;
    Stage stage;
    Group root;
    Scene scene;
    Stage confirmationStage;




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

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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



    public void cancelDeleting()
    {
        confirmationStage.close();
    }


    public Stage getStage()
    {
        return stage;
    }
}
