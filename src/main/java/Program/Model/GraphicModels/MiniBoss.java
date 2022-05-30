package Program.Model.GraphicModels;

import Program.View.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MiniBoss {
    private GameView view;
    private Group root;
    private ImageView miniboss;
    private Circle hitBox;
    private Timeline timeline;
    private int speed;
    private boolean isHit;
    private int healthRemaining;
    private int mainY;
    private ArrayList<Image> images;
    private int imageNumber;
    private int timeUntilImageChange;





    ////methods////
    public MiniBoss(double x, double y, Group root, GameView view, String color)
    {
        speed = 5;
        mainY = (int) y;
        this.view = view;
        view.addMiniBoss(this);
        healthRemaining = 2;
        this.root = root;
        timeUntilImageChange = 3;

        loadImages(color);

        miniboss.setFitWidth(136);
        miniboss.setFitHeight(116);
        miniboss.setX(x);
        miniboss.setY(y - miniboss.getFitWidth() / 2);
        root.getChildren().add(miniboss);

        hitBox = new Circle(10 + 66 + miniboss.getX(), miniboss.getY() + 66, 66);


        showAnimation();
    }



    private void loadImages(String color)
    {
        images = new ArrayList<>();
        miniboss = new ImageView();
        File[] directory = new File("src/main/resources/Textures/Game/" + color + "MiniBosses/").listFiles();

        for (File imageFile: directory){
            try {
                images.add(new Image(new FileInputStream(imageFile)));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        miniboss.setImage(images.get(0));
    }



    private void showAnimation()
    {
        timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                move();
                manageAnimation();
                checkHitBox();
            }
        }));

        timeline.setCycleCount(-1);

        timeline.play();
    }



    private void move()
    {
        miniboss.setX(miniboss.getX() - speed);
        hitBox.setCenterX(hitBox.getCenterX() - speed);

        miniboss.setY(mainY + 20 * Math.sin(Math.toRadians((1280 - miniboss.getX()))));

        if (miniboss.getX() < -60){
            root.getChildren().remove(miniboss);
            timeline.stop();
            view.getMiniBosses().remove(this);
        }
    }




    private void manageAnimation()
    {
        timeUntilImageChange--;

        if (timeUntilImageChange == 0) {
            imageNumber++;
            timeUntilImageChange = 3;

            if (imageNumber == images.size()) {
                imageNumber = 0;
            }

            miniboss.setImage(images.get(imageNumber));
        }
    }



    private void checkHitBox()
    {
        ArrayList<Bullet> bullets = view.getBullets();
        ArrayList<Bomb> bombs = view.getBombs();

        for (Bullet bullet : bullets) {
            if (!bullet.isHit() && bullet.getHitBox().getBoundsInParent().intersects(hitBox.getBoundsInParent())) {
                bullet.hit();
                startHitAnimation(1);
            }
        }

        for (Bomb bomb : bombs) {
            if (!bomb.isHit() && bomb.getHitBox().getBoundsInParent().intersects(hitBox.getBoundsInParent())) {
                bomb.hit();
                startHitAnimation(2);
            }
        }
    }

    private void startHitAnimation(int damage)
    {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.5);
        miniboss.setEffect(colorAdjust);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                miniboss.setEffect(null);
            }
        }, 80);

        healthRemaining -= damage;

        if (healthRemaining <= 0) {
            hit();
        }
    }


    public void hit()
    {
        isHit = true;
        root.getChildren().remove(miniboss);
        timeline.stop();
        MiniBoss miniBoss = this;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                view.getMiniBosses().remove(miniBoss);
            }
        }, 200);
    }



    //getters
    public boolean isHit()
    {
        return  isHit;
    }



    public Circle getHitBox()
    {
        return hitBox;
    }
}

