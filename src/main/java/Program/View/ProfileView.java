package Program.View;

import Program.Controller.ProfileController;
import Program.Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

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
}
