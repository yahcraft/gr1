package Program.View;

import Program.Controller.GameController;
import Program.Model.GraphicModels.*;
import Program.Model.User;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameView {
    private GameController controller;
    private Stage stage;
    private Scene scene;
    private Group root;
    private CupHead cupHead;
    private FirstBoss firstBoss;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bomb> bombs;
    private ArrayList<Egg> eggs;
    private AudioClip music;
    ArrayList<MiniBoss> miniBosses;
    private boolean isDevilMode;
    private int difficulty;





    ////methods////
    public GameView(Stage stage, User user, int difficulty, boolean isDevilMode)
    {
        this.stage = stage;
        controller = new GameController(user, this);
        this.isDevilMode = isDevilMode;
        this.difficulty = difficulty;

        root = new Group();
        startMusic();

        setBackGround();

        eggs = new ArrayList<>();
        cupHead = new CupHead(this, root);
        firstBoss = new FirstBoss(this, root);
        bullets = new ArrayList<>();
        bombs = new ArrayList<>();
        miniBosses = new ArrayList<>();

        root.getChildren().add(cupHead.getNode());

        manageMiniBosses();

        loadFrontTrees();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        cupHead.setFocus();
    }



    private void startMusic()
    {
        music = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/music.mp3")).toString());
        music.setCycleCount(-1);
        music.play();
    }


    private void setBackGround()
    {
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/Textures/Game/Background.png")).toString());
        ImageView background = new ImageView(backgroundImage);
        root.getChildren().add(background);

        Image cloudsImage = new Image(Objects.requireNonNull(getClass().getResource("/Textures/Game/clouds.png")).toString());
        initializeBackground(cloudsImage, 0.6);

        Image landImage = new Image(Objects.requireNonNull(getClass().getResource("/Textures/Game/Land.png")).toString());
        initializeBackground(landImage, 4);
    }




    private void manageMiniBosses()
    {
        Random rand = new Random();


        Timeline miniBossSpawnerTimeline = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (rand.nextInt(100) < 6){
                    spawnMiniBosses();
                }
            }
        }));

        miniBossSpawnerTimeline.setCycleCount(-1);
        miniBossSpawnerTimeline.play();
    }



    private void spawnMiniBosses()
    {
        Random rand = new Random();
        int y = rand.nextInt(600);

        for (int i = 0; i < 3; i++){
            MiniBoss miniBoss;

            if (i == 2) {
                miniBoss = new MiniBoss(1280 + i * 125, y, root, this, "pink");
            }
            else {
                miniBoss = new MiniBoss(1280 + i * 125, y, root, this, "yellow");
            }

            miniBosses.add(miniBoss);
        }
    }



    private void loadFrontTrees()
    {
        Image treesImage = new Image(Objects.requireNonNull(getClass().getResource("/Textures/Game/Trees.png")).toString());
        initializeBackground(treesImage, 15);
    }



    private void initializeBackground(Image image, double dx)
    {
        ImageView image1 = new ImageView(image);
        ImageView image2 = new ImageView(image);
        image1.setFitHeight(710);
        image2.setFitHeight(710);
        image2.setX(2045);
        root.getChildren().add(image1);
        root.getChildren().add(image2);
        startBackgroundAnimation(image1, dx);
        startBackgroundAnimation(image2, dx);
    }




    private void startBackgroundAnimation(ImageView background, double dx)
    {
        AnimationTimer backgroundAnimation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                int x = (int) background.getX();

                if (x <= -2048){
                    background.setX(background.getX() + 2 * 2048);
                }
                else {
                    background.setX(background.getX() - dx);
                }
            }
        };

        backgroundAnimation.start();
    }



    public void addBomb(double x, double y)
    {
        Bomb bomb = new Bomb(x, y, root, this);
        bombs.add(bomb);
    }



    public void addBullet(double x, double y)
    {
        Bullet bullet = new Bullet(x, y, root, this);
        bullets.add(bullet);
    }



    public void addEgg(Egg egg)
    {
        eggs.add(egg);
    }




    public void addMiniBoss(MiniBoss miniBoss)
    {
        miniBosses.add(miniBoss);
    }



    //getters
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }


    public ArrayList<Bomb> getBombs() {
        return bombs;
    }


    public int getDifficulty()
    {
        return difficulty;
    }


    public ArrayList<Egg> getEggs()
    {
        return eggs;
    }

    public FirstBoss getFirstBoss()
    {
        return firstBoss;
    }

    public ArrayList<MiniBoss> getMiniBosses() {
        return miniBosses;
    }
}
