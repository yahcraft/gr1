package Program.Model.GraphicModels;

import Program.View.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Objects;

public class CupHead {
    private GameView view;
    private ImageView cupHead;
    private Rectangle collisionRectangle1;
    private Rectangle collisionRectangle2;
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






    ////methods////
    public CupHead(GameView view)
    {
        this.view = view;
        ds = 20;
        timeUntilNextShoot = 20;
        loadSounds();

        cupHead = new ImageView(Objects.requireNonNull(getClass().getResource("/Textures/Game/cupHead0.png")).toString());
        cupHead.setY(300);
        cupHead.setFitWidth(110);
        cupHead.setFitHeight(90);


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

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manageMovement();
                moveCupHead();
                checkShoot();
                changeImage();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        setFocus();
    }



    private void loadSounds()
    {
        bulletSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/shooting.mp3")).toString());
        bombSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/Sounds/bomb.mp3")).toString());
    }



    private void changeImage()
    {
        imageNumber++;

        imageNumber -= (imageNumber / 3 * 3); //makes it so the image number will loop between 0 and 2

        cupHead.setImage(new Image(Objects.requireNonNull(getClass().getResource("/Textures/Game/cupHead" + imageNumber + ".png")).toString()));
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
}
