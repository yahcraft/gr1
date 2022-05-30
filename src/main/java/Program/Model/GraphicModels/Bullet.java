package Program.Model.GraphicModels;

import Program.View.GameView;
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
    GameView view;
    private ImageView bullet;
    private Rectangle bulletBorder;
    private ArrayList<Image> images;
    private int imageNumber;
    private Group root;
    private Timeline timeline;
    private boolean isHit;
    private int explosionImageNumber;





    ////methods////
    public Bullet(double x, double y, Group root, GameView view)
    {
        this.view = view;
        this.root = root;

        bulletBorder = new Rectangle();
        bulletBorder.setWidth(32);
        bulletBorder.setHeight(8);

        bullet = new ImageView();
        bullet.setX(x + 110);
        bullet.setY(y + 25);
        bullet.setFitWidth(72);
        bullet.setFitHeight(15);
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
                if (imageNumber < images.size() && !isHit) {
                    updateImage();
                }
                else {
                    if (!isHit){
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
            bullet.setImage(new Image(String.valueOf(getClass().getResource("/Textures/Game/bullet.png"))));
            bullet.setY(bullet.getY() + 25);
            bulletBorder.setX(bullet.getX() + bullet.getFitWidth() / 2);
            bulletBorder.setY(bullet.getY());
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
                bulletBorder.setX(bulletBorder.getX() + (double) 35 / 5);
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




    public void hit()
    {
        isHit = true;
        File[] imageFiles = new File("src/main/resources/Textures/Game/BulletExplosion/").listFiles();
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


        bullet.setImage(images.get(0));
        bullet.setFitWidth(images.get(0).getWidth());
        bullet.setFitHeight(images.get(0).getHeight());
        bullet.setX(bullet.getX() + 72 - bullet.getFitWidth() / 2);
        bullet.setY(bullet.getY() + 5 - bullet.getFitHeight() / 2);

        Timeline explosionTimeline = new Timeline(new KeyFrame(Duration.millis(33), new EventHandler<ActionEvent>() {
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
            root.getChildren().remove(bullet);
            timeline.stop();
            return;
        }

         bullet.setImage(images.get(explosionImageNumber));
    }



    public ImageView getNode()
    {
        return this.bullet;
    }



    public Rectangle getHitBox()
    {
        return bulletBorder;
    }

    public boolean isHit()
    {
        return isHit;
    }
}
