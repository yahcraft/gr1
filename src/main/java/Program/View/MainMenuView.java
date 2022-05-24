package Program.View;

import Program.Controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuView {
    Stage stage;
    Group root;
    Scene scene;





    ////methods////
    public MainMenuView(Stage stage, MainMenuController controller)
    {
        this.stage = stage;

        root = new Group();
        FXMLLoader fxmlLoader = new FXMLLoader((Objects.requireNonNull(getClass().getResource("/fxml/MainMenu.fxml"))));

        try {
            fxmlLoader.setController(controller);
            Pane pane = fxmlLoader.load();
            root.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


        //TODO: showing the primary scene
    }
}
