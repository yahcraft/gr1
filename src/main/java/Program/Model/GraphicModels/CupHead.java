package Program.Model.GraphicModels;

import Program.View.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class CupHead {
    private GameView view;
    private ImageView cupHead;
    private ArrayList<Rectangle> hitBoxes;
    private int ds;
    private boolean isGoingUp;
    private boolean isGoingDown;
    private boolean isGoingLeft;
    private boolean isGoingRight;
    private double deltaX;
    private double deltaY;
    private boolean isShooting;
    private boolean isOnBomb;
    private int timeUntilNextShoot;
    private int imageNumber;
    private AudioClip bulletSound;
    private AudioClip bombSound;
    private AudioClip takingDamageSound;
    private boolean wasHitRecently;
    private int health;
    private int explosionImageNumber;
    private ArrayList<Image> images;
    private ArrayList<Image> explosionImages;
    private ImageView explosionOverlay;
    private Group root;
    private ImageView healthBar;
    private Text healthNumber;
    private ImageView attackIcon;
    Timeline timeline;






    ////methods////
    public CupHead(GameView view, Group root)
    {
        this.root = root;
        this.view = view;
        health = 12;
        ds = 20;
        timeUntilNextShoot = 20;
        loadSounds();
        loadImages();

        cupHead = new ImageView(Objects.requireNonNull(getClass().getResource("/Textures/Game/cupHead0.png")).toString());
        cupHead.setY(300);
        cupHead.setX(0);
        cupHead.setFitWidth(110);
        cupHead.setFitHeight(90);

        initializeHealthLabelAndIcon();
        initializeHitBox();


        cupHead.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                setKeys(keyEvent);
            }
        });
        cupHead.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                releaseKeys(keyEvent);
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manageMovement();
                moveCupHead();
                checkShoot();
                changeImage();
                checkHitBox();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        setFocus();
    }



    private void initializeHealthLabelAndIcon()
    {
        healthBar = new ImageView(Objects.requireNonNull(getClass().getResource("/Textures/Game/health.png")).toString());
        healthBar.setY(5);
        healthBar.setX(5);
        root.getChildren().add(healthBar);

        healthNumber = new Text(18, 25,  "HP: " + health);
        root.getChildren().add(healthNumber);

        attackIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Textures/Game/bulletIcon.png")).toString());
        attackIcon.setX(80);
        attackIcon.setY(0);
        attackIcon.setFitWidth(40);
        attackIcon.setFitHeight(40);
        root.getChildren().add(attackIcon);
    }



    private void loadSounds()
    {
        bulletSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/shooting.mp3")).toString());
        bombSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/bomb.mp3")).toString());
        takingDamageSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/takingDamage.wav")).toString());
    }



    private void initializeHitBox()
    {
        hitBoxes = new ArrayList<>();

        Rectangle front = new Rectangle(cupHead.getX() + 33, cupHead.getY() + 29, 75, 60);
        Rectangle back = new Rectangle(cupHead.getX() + 3, cupHead.getY() + 40, 30, 35);
        Rectangle head = new Rectangle(cupHead.getX() + 20, cupHead.getY() + 2, 65, 35);

        hitBoxes.add(front);
        hitBoxes.add(back);
        hitBoxes.add(head);
    }



    private void loadImages()
    {
        images = new ArrayList<>();
        explosionImages = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            images.add(new Image(Objects.requireNonNull(getClass().getResource("/Textures/Game/cupHead" + imageNumber + ".png")).toString()));
        }

        File[] explosionImageFiles = new File("src/main/resources/Textures/Game/PlaneDamage/").listFiles();
        explosionImageNumber = 0;

        for (File imageFile: explosionImageFiles){
            try {
                explosionImages.add(new Image(new FileInputStream(imageFile.getPath())));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }



    private void changeImage()
    {
        imageNumber++;

        imageNumber -= (imageNumber / 3 * 3); //makes it so the image number will loop between 0 and 2

        cupHead.setImage(images.get(imageNumber));
    }



    private void setKeys(KeyEvent keyEvent)
    {
        if (keyEvent.getCode().equals(KeyCode.W)  || keyEvent.getCode().equals(KeyCode.UP)){
            isGoingUp = true;
        }
        else if (keyEvent.getCode().equals(KeyCode.S)  || keyEvent.getCode().equals(KeyCode.DOWN)){
            isGoingDown = true;
        }
        else if (keyEvent.getCode().equals(KeyCode.A)  || keyEvent.getCode().equals(KeyCode.LEFT)){
            isGoingLeft = true;
        }
        else if (keyEvent.getCode().equals(KeyCode.D)  || keyEvent.getCode().equals(KeyCode.RIGHT)){
            isGoingRight = true;
        }
        else if (keyEvent.getCode().equals(KeyCode.SPACE)){
            isShooting = true;
        }
    }



    private void manageMovement()
    {
        deltaX = 0;
        deltaY = 0;

        if (isGoingLeft && cupHead.getX() >= ds){
            deltaX -= ds;
        }

        if (isGoingRight && cupHead.getX() <= 1280 - cupHead.getFitWidth() - ds){
            deltaX += ds;
        }

        if (isGoingUp && cupHead.getY() >= ds){
            deltaY -= ds;
        }

        if (isGoingDown && cupHead.getY() <= 720 - cupHead.getFitHeight() - ds){
            deltaY += ds;
        }

        /*Timeline animation = new Timeline();
        animation.setCycleCount(5);
        Duration time = new Duration(1);
        KeyFrame keyFrame = new KeyFrame(time, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                moveCupHead();
            }
        });


        animation.getKeyFrames().add(keyFrame);

        animation.play();*/
    }



    private void moveCupHead()
    {
        cupHead.setX(cupHead.getX() + deltaX / 5);
        cupHead.setY(cupHead.getY() + deltaY / 5);

        for (Rectangle hitBox: hitBoxes){
            hitBox.setX(hitBox.getX() + deltaX / 5);
            hitBox.setY(hitBox.getY() + deltaY / 5);
        }
    }



    private void releaseKeys(KeyEvent keyEvent)
    {
        if (keyEvent.getCode().equals(KeyCode.W)  || keyEvent.getCode().equals(KeyCode.UP)){
            isGoingUp = false;
        }
        else if (keyEvent.getCode().equals(KeyCode.S)  || keyEvent.getCode().equals(KeyCode.DOWN)){
            isGoingDown = false;
        }
        else if (keyEvent.getCode().equals(KeyCode.A)  || keyEvent.getCode().equals(KeyCode.LEFT)){
            isGoingLeft = false;
        }
        else if (keyEvent.getCode().equals(KeyCode.D)  || keyEvent.getCode().equals(KeyCode.RIGHT)){
            isGoingRight = false;
        }
        else if (keyEvent.getCode().equals(KeyCode.SPACE)){
            isShooting = false;
            shoot();
        }
        else if (keyEvent.getCode().equals(KeyCode.TAB)){
            isOnBomb = !isOnBomb;

            if (isOnBomb){
                attackIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/Textures/Game/bombIcon.png")).toString()));
            }
            else {
                attackIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/Textures/Game/bulletIcon.png")).toString()));
            }
        }
    }



    private void checkShoot()
    {
        if (!isShooting){
            return;
        }

        if (timeUntilNextShoot != 0){
            timeUntilNextShoot--;
            return;
        }

        shoot();
    }



    private void shoot()
    {
        if (isOnBomb){
            bombSound.play();
            view.addBomb(cupHead.getX(), cupHead.getY());
            timeUntilNextShoot = 40;
        }
        else {
            bulletSound.play();
            view.addBullet(cupHead.getX(), cupHead.getY());
            timeUntilNextShoot = 20;
        }
    }



    private void checkHitBox()
    {
        if (wasHitRecently){
            return;
        }

        ArrayList<Egg> eggs = view.getEggs();
        ArrayList<MiniBoss> miniBosses = view.getMiniBosses();
        FirstBoss firstBoss = view.getFirstBoss();

        for (Rectangle hitBox: hitBoxes) {
            for (Egg egg : eggs) {
                if (!egg.isHit() && !wasHitRecently && egg.getHitBox().getBoundsInParent().intersects(hitBox.getBoundsInParent())) {
                    health -= view.getDifficulty(); // -> makes it so cupHead loses 1 hp in easy, 2 in normal and 3 in hard and devil mode

                    if (health <= 0){
                        timeline.stop();
                        view.showPlayerLost();
                    }

                    egg.hit();
                    startHitAnimation();
                }
            }

            for (Rectangle bossHitBox: firstBoss.getHitBoxes()){
                if (!wasHitRecently && bossHitBox.getBoundsInParent().intersects(hitBox.getBoundsInParent())) {
                    health -= view.getDifficulty(); // -> makes it so cupHead loses 1 hp in easy, 2 in normal and 3 in hard and devil mode

                    if (health <= 0){
                        timeline.stop();
                        view.showPlayerLost();
                    }

                    startHitAnimation();
                }
            }

            for (MiniBoss minBoss: miniBosses){
                if (!minBoss.isHit() && !wasHitRecently && minBoss.getHitBox().getBoundsInParent().intersects(hitBox.getBoundsInParent())) {
                    health -= view.getDifficulty(); // -> makes it so cupHead loses 1 hp in easy, 2 in normal and 3 in hard and devil mode

                    if (health <= 0){
                        timeline.stop();
                        view.showPlayerLost();
                    }

                    minBoss.hit();
                    startHitAnimation();
                }
            }
        }
    }



    private void startHitAnimation()
    {
        wasHitRecently = true;
        takingDamageSound.play();
        takingDamageSound.setVolume(1);

        healthNumber.setText("HP: " + health);

        explosionOverlay = new ImageView();
        explosionOverlay.setX(cupHead.getX() + cupHead.getFitWidth() / 2 - explosionImages.get(0).getWidth() / 2);
        explosionOverlay.setY(cupHead.getY() + cupHead.getFitHeight() / 2 - explosionImages.get(0).getHeight() / 2);

        Timeline explosionTimeline = new Timeline(new KeyFrame(Duration.millis(62), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateExplosionOverlayImage();
            }
        }));

        explosionTimeline.setCycleCount(explosionImages.size() + 1);
        root.getChildren().add(explosionOverlay);
        explosionTimeline.play();

        Timeline hitTimeline = new Timeline(new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ColorAdjust effect = new ColorAdjust();
                effect.setBrightness(-0.75);

                cupHead.setEffect(effect);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        cupHead.setEffect(null);
                    }
                }, 150);
                }
        }));

        hitTimeline.setCycleCount(5);

        hitTimeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wasHitRecently = false;
            }
        });

        hitTimeline.play();
    }



    private void updateExplosionOverlayImage()
    {
        if (explosionImageNumber == explosionImages.size()){
            root.getChildren().remove(explosionOverlay);
            explosionImageNumber = 0;
        }

        explosionOverlay.setImage(explosionImages.get(explosionImageNumber));
        explosionImageNumber++;
    }



    public void stop()
    {
        timeline.stop();
    }



    //setters
    public void setFocus()
    {
        cupHead.requestFocus();
    }


    //getters
    public Group getNode()
    {
        Group group = new Group();
        group.getChildren().add(cupHead);
        return group;
    }

    public int getHealth() {
        return health;
    }
}
