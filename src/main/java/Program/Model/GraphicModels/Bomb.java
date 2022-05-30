package Program.Model.GraphicModels;

import Program.View.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Bomb {
    GameView view;
    private ImageView bomb;
    private Rectangle hitbox;
    private ArrayList<Image> images;
    private int imageNumber;
    private Group root;
    private Timeline timeline;
    private int ySpeed;
    private int xSpeed;
    boolean isHit;
    private int explosionImageNumber;


    ////methods////
    public Bomb(double x, double y, Group root, GameView view)
    {
        this.view = view;
        this.root = root;
        bomb = new ImageView();
        bomb.setX(x + 110);
        bomb.setY(y + 20);
        this.images = new ArrayList<>();
        imageNumber = -1;
        ySpeed = -20;
        xSpeed = 15;

        hitbox = new Rectangle();


        File[] imageFiles = new File("src/main/resources/Textures/Game/BulletShoot/").listFiles();

        for (File imageFile: imageFiles){
            try {
                images.add(new Image(new FileInputStream(imageFile.getPath())));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        root.getChildren().add(bomb);

        showAnimation();
    }



    private void showAnimation()
    {
        timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (imageNumber < images.size() && !isHit) {
                    updateImage();
                }
                else {
                    if (!isHit) {
                        moveImage();
                    }

                    checkBorder();
                }

            }
        }));

        timeline.setCycleCount(-1);

        timeline.play();
    }



    private void updateImage()
    {
        imageNumber++;

        if (imageNumber == images.size()){
            bomb.setImage(new Image(String.valueOf(getClass().getResource("/Textures/Game/bomb.png"))));
            bomb.setY(bomb.getY() - 15);
            bomb.setRotate(Math.atan((double) ySpeed / xSpeed) * 180 / Math.PI);
            hitbox.setX(bomb.getX() + bomb.getFitWidth() / 2);
            hitbox.setY(bomb.getY() + bomb.getFitWidth() / 2);
            hitbox.setWidth(bomb.getFitWidth());
            hitbox.setHeight(bomb.getFitHeight());
            return;
        }

        bomb.setImage(images.get(imageNumber));
    }



    private void moveImage()
    {
        bomb.setRotate(Math.atan((double) ySpeed / xSpeed) * 180 / Math.PI);
        hitbox.setRotate(Math.atan((double) ySpeed / xSpeed) * 180 / Math.PI);

        Timeline transitionTimer = new Timeline(new KeyFrame(Duration.millis(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bomb.setX(bomb.getX() + (double) xSpeed / 5);
                bomb.setY(bomb.getY() + (double) ySpeed / 5);
                hitbox.setX(hitbox.getX() + (double) xSpeed / 5);
                hitbox.setY(hitbox.getY() + (double) ySpeed / 5);
            }
        }));

        transitionTimer.setCycleCount(5);
        transitionTimer.play();

        ySpeed += 2;
    }



    private void checkBorder()
    {
        if (bomb.getX() > 1280 || bomb.getY() > 720){
            root.getChildren().remove(bomb);
            view.getBombs().remove(this);
            timeline.stop();
        }
    }



    public void hit()
    {
        isHit = true;
        File[] imageFiles = new File("src/main/resources/Textures/Game/BombExplosion/").listFiles();
        images.clear();
        explosionImageNumber = 0;

        for (File imageFile: imageFiles){
            try {
                images.add(new Image(new FileInputStream(imageFile.getPath())));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        bomb.setImage(images.get(0));
        bomb.setFitWidth(images.get(0).getWidth());
        bomb.setFitHeight(images.get(0).getHeight());
        bomb.setX(bomb.getX() - bomb.getFitWidth() / 2);
        bomb.setY(bomb.getY() - bomb.getFitHeight() / 2);

        Timeline explosionTimeline = new Timeline(new KeyFrame(Duration.millis(40), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateExplosionImage();
            }
        }));

        explosionTimeline.setCycleCount(images.size());
        explosionTimeline.play();
    }



    private void updateExplosionImage()
    {
        explosionImageNumber++;

        if (explosionImageNumber == images.size()){
            root.getChildren().remove(bomb);
            timeline.stop();
            view.getBombs().remove(this);
            return;
        }

        bomb.setImage(images.get(explosionImageNumber));
    }



    //getters
    public ImageView getNode()
    {
        return this.bomb;
    }



    public Rectangle getHitBox()
    {
        return hitbox;
    }


    public boolean isHit() {
        return isHit;
    }
}