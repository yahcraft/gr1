package Program.View;

import Program.Controller.WonGameController;
import Program.Model.User;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class WonGameView {
    Stage stage;
    Scene scene;
    Group root;
    WonGameController controller;
    int time;
    AudioClip backgroundMusic;
    int score;





    ////methods////
    public WonGameView(Stage stage, User user, int time, int difficulty, boolean wasDevilMode, int health)
    {
        this.time = time;

        this.stage = stage;

        root = new Group();

        FXMLLoader fxmlLoader = new FXMLLoader((Objects.requireNonNull(getClass().getResource("/fxml/WonGame.fxml"))));

        try {
            Pane pane = fxmlLoader.load();
            controller = fxmlLoader.getController();
            root.getChildren().add(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        loadLabels(health, difficulty, wasDevilMode);


        controller.initializeController(user, this, difficulty, wasDevilMode);

        loadBackgroundMusic();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    private void loadBackgroundMusic()
    {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                backgroundMusic = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/menu.mp3")).toString());
                backgroundMusic.setCycleCount(-1);
                backgroundMusic.setVolume(0.6);
                backgroundMusic.play();
            }
        }, 1000);

    }




    private void loadLabels(int health, int difficulty, boolean wasDevilMode)
    {
        Label timeLabel = new Label();
        timeLabel.setText("Time: " + TimeUnit.MILLISECONDS.toMinutes(time) + ":" +
                (TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));
        timeLabel.setLayoutX(590);
        timeLabel.setLayoutY(300);
        timeLabel.setPrefWidth(300);
        timeLabel.setBackground(Background.fill(Color.TRANSPARENT));
        timeLabel.setFont(Font.font(40));
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setAlignment(Pos.CENTER);
        root.getChildren().add(timeLabel);


        Label scoreLabel = new Label();

        score = health * 5 - (int) TimeUnit.MILLISECONDS.toMinutes(time) / 5;

        if (score < 0){
            score = 1;
        }

        score *= difficulty;

        if (wasDevilMode){
            score *= 2;
        }

        scoreLabel.setText("score: " + score);
        scoreLabel.setLayoutX(590);
        scoreLabel.setLayoutY(360);
        scoreLabel.setPrefWidth(300);
        scoreLabel.setBackground(Background.fill(Color.TRANSPARENT));
        scoreLabel.setFont(Font.font(40));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setAlignment(Pos.CENTER);
        root.getChildren().add(scoreLabel);
    }



    public int getScore()
    {
        return score;
    }



    public Stage getStage(){
        return stage;
    }

    public int getTime() {
        return time;
    }

    public void stopMusic()
    {
        backgroundMusic.stop();
    }
}
