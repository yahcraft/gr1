package Program.Model.GraphicModels;

import Program.View.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FirstBoss {
    private ImageView boss;
    private ArrayList<Rectangle> hitBoxes;
    private Group root;
    private GameView view;
    private int dy;
    private boolean isAttacking;
    private int numberOfAttacksLeft;
    private int imageNumber;
    private ArrayList<Image> flyingImages;
    private ArrayList<Image> attackingImages;
    private int health;
    private Image healthBarOriginalImage;
    private ImageView healthBar;
    private Label healthLabel;






    ////methods////
    public FirstBoss(GameView view, Group root)
    {
        this.root = root;
        this.view =view;
        dy = 12;
        loadFiles();
        health = 8000;


        boss = new ImageView(String.valueOf(getClass().getResource("/Textures/Game/Boss/Flying/1.png")));
        boss.setFitWidth(651);
        boss.setFitHeight(509);
        boss.setX(1280 - boss.getFitWidth() + 50);
        boss.setY(720 / 2 - boss.getFitHeight() / 2);
        setHitBox();

        initializeHealth();

        root.getChildren().add(boss);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(75), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadAnimation();
                moveBoss();
                manageAttacking();
                manageHealth();

                Timeline hitCheckTimeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        checkHitBox();
                    }
                }));

                hitCheckTimeline.setCycleCount(6);
                hitCheckTimeline.play();
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



    private void initializeHealth()
    {
        healthBarOriginalImage = new Image(String.valueOf(getClass().getResource("/Textures/Game/bossHealthBar.png")));
        healthBar = new ImageView(healthBarOriginalImage);
        healthBar.setFitWidth(400);
        healthBar.setFitHeight(30);
        healthBar.setX(800);
        healthBar.setY(10);
        root.getChildren().add(healthBar);

        healthLabel = new Label(String.valueOf(health));
        healthLabel.setLayoutX(1202);
        healthLabel.setLayoutY(10);
        healthLabel.setPrefWidth(60);
        healthLabel.setPrefHeight(30);
        healthLabel.setBackground(Background.fill(Color.valueOf("111111")));
        healthLabel.setFont(Font.font("25"));
        healthLabel.setAlignment(Pos.CENTER);
        healthLabel.setTextFill(Color.BLUE);
        root.getChildren().add(healthLabel);
    }



    private void setHitBox()
    {
        hitBoxes = new ArrayList<>();

        Rectangle back = new Rectangle();
        back.setWidth(254);
        back.setHeight(379);
        back.setX(boss.getX() + 265);
        back.setY(boss.getY() + 75);

        Rectangle front = new Rectangle();
        front.setWidth(85);
        front.setHeight(318);
        front.setX(boss.getX() + 180);
        front.setY(boss.getY() + 115);

        Rectangle head = new Rectangle();
        head.setWidth(125);
        head.setHeight(205);
        head.setX(boss.getX() + 55);
        head.setY(boss.getY() + 160);

        Rectangle beak = new Rectangle();
        beak.setWidth(55);
        beak.setHeight(71);
        beak.setX(boss.getX());
        beak.setY(boss.getY() + 288);

        hitBoxes.add(back);
        hitBoxes.add(front);
        hitBoxes.add(head);
        hitBoxes.add(beak);
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




    private void manageHealth()
    {
        PixelReader reader = healthBarOriginalImage.getPixelReader();
        WritableImage newImage = new WritableImage(reader, health / 20, 30);
        healthBar.setImage(newImage);
        healthBar.setFitWidth(health / 20);
        healthLabel.setText(String.valueOf(health));
    }




    private void moveBoss()
    {
        Timeline movingTimeline = new Timeline(new KeyFrame(Duration.millis(12), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boss.setY(boss.getY() + (double) dy / 6);

                for (Rectangle hitBox: hitBoxes){
                    hitBox.setY(hitBox.getY() + (double) dy / 6);
                }
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



    private void checkHitBox()
    {
        ArrayList<Bullet> bullets = view.getBullets();
        ArrayList<Bomb> bombs = view.getBombs();

        for (Rectangle hitBox: hitBoxes) {
            for (Bullet bullet : bullets) {
                if (!bullet.isHit() && bullet.getHitBox().getBoundsInParent().intersects(hitBox.getBoundsInParent())) {
                    health -= 15 * (4 - view.getDifficulty()) / 3; // -> makes it so there will be 15 damage in easy, 10 in normal, 5 in hard and devil mode
                    bullet.hit();
                    startHitAnimation();
                }
            }

            for (Bomb bomb : bombs) {
                if (!bomb.isHit() && bomb.getHitBox().getBoundsInParent().intersects(hitBox.getBoundsInParent())) {
                    health -= 15 * 2 * (4 - view.getDifficulty()) / 3; // -> makes it so there will be 30 damage in easy, 20 in normal, 10 in hard and devil mode
                    bomb.hit();
                    startHitAnimation();
                }
            }
        }
    }



    private void startHitAnimation()
    {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.3);
        boss.setEffect(colorAdjust);
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                boss.setEffect(null);
            }
        }, 80);
    }
}
