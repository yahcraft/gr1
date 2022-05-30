package Program.View;

import Program.Controller.LostMenuController;
import Program.Model.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LostMenuView
{
    private Stage stage;
    private Scene scene;
    private Group root;
    private LostMenuController controller;
    private int time; //time in millis
    private AudioClip backgroundMusic;
    private ImageView cupHeadFigure;






    ////methods////
    public LostMenuView(Stage stage, User user, int time, int phase, Image background, int difficulty, boolean wasDevilMode, double percentage)
    {
        this.time = time;

        this.stage = stage;

        root = new Group();
        root.getChildren().add(new ImageView(background));

        FXMLLoader fxmlLoader = new FXMLLoader((Objects.requireNonNull(getClass().getResource("/fxml/LostMenu.fxml"))));

        try {
            Pane pane = fxmlLoader.load();
            controller = fxmlLoader.getController();
            root.getChildren().add(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        controller.initializeController(user, this, difficulty, wasDevilMode);

        loadQuote(phase);
        loadCupHeadFigure(percentage);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        loadBackgroundMusic();
    }



    private void loadBackgroundMusic()
    {
        backgroundMusic = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/music1.mp3")).toString());
        backgroundMusic.setRate(0.6);
        backgroundMusic.setCycleCount(-1);
        backgroundMusic.play();
    }



    private void loadQuote(int phase)
    {
        Label quote = new Label();

        if (phase == 1) {
            quote.setText("I own the air — I fly where eagles dare!");
        }


        quote.setLayoutX(440);
        quote.setLayoutY(310);
        quote.setWrapText(true);
        quote.setPrefWidth(400);
        quote.setBackground(Background.fill(Color.TRANSPARENT));
        quote.setFont(Font.font(20));
        quote.setAlignment(Pos.CENTER);
        root.getChildren().add(quote);

        Label timeLabel = new Label();
        timeLabel.setText("Time: " + TimeUnit.MILLISECONDS.toMinutes(time) + ":" +
                (TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));
        timeLabel.setLayoutX(590);
        timeLabel.setLayoutY(340);
        timeLabel.setWrapText(true);
        timeLabel.setPrefWidth(100);
        timeLabel.setBackground(Background.fill(Color.TRANSPARENT));
        timeLabel.setFont(Font.font(20));
        timeLabel.setAlignment(Pos.CENTER);
        root.getChildren().add(timeLabel);
    }



    private void loadCupHeadFigure(double percentage)
    {
        cupHeadFigure = new ImageView(String.valueOf(getClass().getResource("/Textures/LostMenu/cupHeadFigure.png")));
        cupHeadFigure.setX(640 - 236 + percentage * 236 * 2);
        cupHeadFigure.setY(360);
        root.getChildren().add(cupHeadFigure);
    }



    public void stopMusic()
    {
        backgroundMusic.stop();
    }



    public Stage getStage()
    {
        return stage;
    }
}
