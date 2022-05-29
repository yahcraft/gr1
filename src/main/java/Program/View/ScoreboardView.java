package Program.View;

import Program.Controller.ScoreboardController;
import Program.Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ScoreboardView {
    ScoreboardController controller;
    Stage stage;
    Group root;
    Scene scene;
    ArrayList<Text> texts;





    public ScoreboardView(Stage stage, User user)
    {
        this.stage = stage;

        root = new Group();

        //loadRoot(controller);

        FXMLLoader fxmlLoader = new FXMLLoader((Objects.requireNonNull(getClass().getResource("/fxml/Scoreboard.fxml"))));

        try {
            //fxmlLoader.setController(controller);
            Pane pane = fxmlLoader.load();
            controller = fxmlLoader.getController();
            root.getChildren().add(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        controller.setScoreboardController(user, this);
        controller.sortTotal();

        for (int i = 3; i < texts.size(); i++){
            ImageView image = new ImageView(Objects.requireNonNull(getClass().getResource("/Textures/Scoreboard/normal.png")).toString());
            image.setLayoutX(texts.get(i).getLayoutX() - 5);
            image.setLayoutY(texts.get(i).getLayoutY() - 30);
            root.getChildren().add(image);
        }

        for (Text text: texts){
            root.getChildren().add(text);
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public void loadUsersText(ArrayList<Text> newtexts)
    {
        this.texts = newtexts;

        texts.get(0).setLayoutX(390 + 5);
        texts.get(0).setLayoutY(20 + 30);
        texts.get(0).setStyle("-fx-pref-width: 400; -fx-pref-height: 50; -fx-font-size: 25; -fx-fill: #ffae00");

        texts.get(1).setLayoutX(225 + 5);
        texts.get(1).setLayoutY(80 + 30);
        texts.get(1).setStyle("-fx-pref-width: 400; -fx-pref-height: 50; -fx-font-size: 25; -fx-fill: #9c9c9c");

        texts.get(2).setLayoutX(655 + 5);
        texts.get(2).setLayoutY(80 + 30);
        texts.get(2).setStyle("-fx-pref-width: 400; -fx-pref-height: 50; -fx-font-size: 25; -fx-fill: #a04b00");

        loadNormalTexts(texts);
    }



    private void loadNormalTexts(ArrayList<Text> texts)
    {
        for (int i = 3; i < 10; i++){
            texts.get(i).setLayoutX(655 - i % 2 * (400 + 30) + 5);
            texts.get(i).setLayoutY(170 + (i - 3) / 2 * (50 + 10) + 30);
            texts.get(i).setStyle("-fx-pref-width: 400; -fx-pref-height: 50; -fx-font-size: 25; -fx-fill: White");
        }

        texts.get(9).setLayoutX(490);
    }



    public Stage getStage()
    {
        return stage;
    }

}
