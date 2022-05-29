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
import java.util.Random;

public class FirstBoss {
    private ImageView boss;
    private Rectangle hitBox;
    private Group root;
    private GameView view;
    private int dy;
    private boolean isAttacking;
    private int numberOfAttacksLeft;
    private int imageNumber;
    private ArrayList<Image> flyingImages;
    private ArrayList<Image> attackingImages;






    ////methods////
    public FirstBoss(GameView view, Group root)
    {
        this.root = root;
        this.view =view;
        dy = 12;
        loadFiles();

        boss = new ImageView(String.valueOf(getClass().getResource("/Textures/Game/Boss/Flying/1.png")));
        boss.setFitWidth(651);
        boss.setFitHeight(509);
        boss.setX(1280 - boss.getFitWidth() + 50);
        boss.setY(720 / 2 - boss.getFitHeight() / 2);

        root.getChildren().add(boss);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(75), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadAnimation();
                moveBoss();
                manageAttacking();
            }
        }));

        timeline.setCycleCount(-1);
        timeline.play();
    }



    private void loadFiles()
    {
        File[] flyingImageFiles = new File("src/main/resources/Textures/Game/Boss/Flying/").listFiles();
        flyingImages = new ArrayList<>();

        for (File imageFile: flyingImageFiles){
            try {
                flyingImages.add(new Image(new FileInputStream(imageFile)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        File[] attackingImageFiles = new File("src/main/resources/Textures/Game/Boss/Attacking/").listFiles();
        attackingImages = new ArrayList<>();

        for (File imageFile: attackingImageFiles){
            try {
                attackingImages.add(new Image(new FileInputStream(imageFile)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    private void loadAnimation()
    {
        if (isAttacking){
            boss.setImage(attackingImages.get(imageNumber));
            imageNumber++;

            if (imageNumber == attackingImages.size() - 1){
                Egg egg = new Egg(boss.getX(), boss.getY() + boss.getFitWidth() / 2, root);
            }

            if (imageNumber == attackingImages.size()){
                imageNumber = 0;
                numberOfAttacksLeft--;

                if (numberOfAttacksLeft == 0){
                    isAttacking = false;
                }
            }
        }
        else {
            boss.setImage(flyingImages.get(imageNumber));
            imageNumber++;

            if (imageNumber == flyingImages.size()){
                imageNumber = 0;
            }
        }
    }




    private void moveBoss()
    {
        Timeline movingTimeline = new Timeline(new KeyFrame(Duration.millis(12), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boss.setY(boss.getY() + (double) dy / 6);
            }
        }));
        movingTimeline.setCycleCount(6);
        movingTimeline.play();

        if ((boss.getY() < -75 && dy < 0) || (boss.getY() - 20 >= 720 - boss.getFitHeight() && dy > 0)){
            dy = -dy;
        }
    }



    private void manageAttacking()
    {
        if (!isAttacking){
            Random rand = new Random();

            isAttacking = rand.nextInt(100) < 2;
            numberOfAttacksLeft = rand.nextInt(3) + 1;
        }
    }
}
