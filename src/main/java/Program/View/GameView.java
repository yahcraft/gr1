package Program.View;

import Program.Controller.GameController;
import Program.Model.GraphicModels.Bomb;
import Program.Model.GraphicModels.Bullet;
import Program.Model.GraphicModels.CupHead;
import Program.Model.GraphicModels.FirstBoss;
import Program.Model.User;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class GameView {
    private GameController controller;
    private Stage stage;
    private Scene scene;
    private Group root;
    private CupHead cupHead;
    private FirstBoss firstBoss;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bomb> bombs;
    private AudioClip music;
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

        cupHead = new CupHead(this);
        firstBoss = new FirstBoss(this, root);
        bullets = new ArrayList<>();
        bombs = new ArrayList<>();

        root.getChildren().add(cupHead.getNode());

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
}
