package Program.Model.GraphicModels;

import javafx.animation.*;
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

public class Bullet {
    private ImageView bullet;
    private Rectangle bulletBorder;
    private ArrayList<Image> images;
    private int imageNumber;
    private Group root;
    private Timeline timeline;





    ////methods////
    public Bullet(double x, double y, Group root)
    {
        this.root = root;
        bullet = new ImageView();
        bullet.setX(x + 110);
        bullet.setY(y + 25);
        this.images = new ArrayList<>();
        imageNumber = -1;

        File[] imageFiles = new File("src/main/resources/Textures/Game/BulletShoot/").listFiles();

        for (File imageFile: imageFiles){
            try {
                images.add(new Image(new FileInputStream(imageFile.getPath())));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        root.getChildren().add(bullet);

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
            bullet.setImage(new Image(String.valueOf(getClass().getResource("/Textures/Game/bullet.png"))));
            bullet.setY(bullet.getY() + 25);
            return;
        }

        bullet.setImage(images.get(imageNumber));
    }



    private void moveImage()
    {
        Timeline transitionTimer = new Timeline(new KeyFrame(Duration.millis(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bullet.setX(bullet.getX() + (double) 35 / 5);
            }
        }));

        transitionTimer.setCycleCount(5);
        transitionTimer.play();
    }



    private void checkBorder()
    {
        if (bullet.getX() > 1280){
            root.getChildren().remove(bullet);
            timeline.stop();
        }
    }



    public ImageView getNode()
    {
        return this.bullet;
    }
}
