package Program.Model.GraphicModels;

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
    private ImageView bomb;
    private Rectangle bulletBorder;
    private ArrayList<Image> images;
    private int imageNumber;
    private Group root;
    private Timeline timeline;
    private int ySpeed;
    private int xSpeed;





    ////methods////
    public Bomb(double x, double y, Group root)
    {
        this.root = root;
        bomb = new ImageView();
        bomb.setX(x + 110);
        bomb.setY(y + 20);
        this.images = new ArrayList<>();
        imageNumber = -1;
        ySpeed = -20;
        xSpeed = 15;


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
                if (imageNumber < images.size()) {
                    updateImage();
                }
                else {
                    moveImage();
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
            return;
        }

        bomb.setImage(images.get(imageNumber));
    }



    private void moveImage()
    {
        bomb.setRotate(Math.atan((double) ySpeed / xSpeed) * 180 / Math.PI);

        Timeline transitionTimer = new Timeline(new KeyFrame(Duration.millis(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bomb.setX(bomb.getX() + (double) xSpeed / 5);
                bomb.setY(bomb.getY() + (double) ySpeed / 5);
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
            timeline.stop();
        }
    }



    public ImageView getNode()
    {
        return this.bomb;
    }
}