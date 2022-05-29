package Program.View;

import Program.Controller.GameSettingMenuController;
import Program.Model.GameSettingMenu;
import Program.Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameSettingMenuView {
    GameSettingMenuController controller;
    Stage stage;
    Group root;
    Scene scene;





    ////methods////
    public GameSettingMenuView(Stage stage, User user)
    {
        this.stage = stage;

        root = new Group();

        FXMLLoader fxmlLoader = new FXMLLoader((Objects.requireNonNull(getClass().getResource("/fxml/GameSettingMenu.fxml"))));

        try {
            Pane pane = fxmlLoader.load();
            controller = fxmlLoader.getController();
            root.getChildren().add(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        controller.initializeController(user, this);

        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CssFiles/GameSettingMenu.css").toString());
        stage.setScene(scene);
        stage.show();
    }



    //getters
    public Stage getStage()
    {
        return stage;
    }
}
