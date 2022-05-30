package Program.Model.GraphicModels;

import Program.View.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Egg
{
    private GameView view;
    private Group root;
    private ImageView egg;
    private Circle hitBox;
    private int theta;
    private Timeline timeline;
    private int speed;
    private boolean isHit;
    private int healthRemaining;





    ////methods////
    public Egg(double x, double y, Group root, GameView view)
    {
        speed = 10;
        this.view = view;
        view.addEgg(this);
        healthRemaining = 2;

        this.root = root;
        egg = new ImageView(String.valueOf(getClass().getResource("/Textures/Game/egg.png")));
        egg.setFitWidth(136);
        egg.setFitHeight(116);
        egg.setX(x);
        egg.setY(y - egg.getFitWidth() / 2);
        root.getChildren().add(egg);

        hitBox = new Circle(10 + 66 + egg.getX(), egg.getY() + 66, 66);

        showAnimation();
    }



    private void showAnimation()
    {
        timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                move();
                checkHitBox();
            }
        }));

        timeline.setCycleCount(-1);

        timeline.play();
    }



    private void move()
    {
        egg.setX(egg.getX() - speed);
        hitBox.setCenterX(hitBox.getCenterX() - speed);
        egg.setRotate(theta);
        theta += 5;

        if (egg.getX() > 1280){
            root.getChildren().remove(egg);
            timeline.stop();
            view.getEggs().remove(this);
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
        egg.setEffect(colorAdjust);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                egg.setEffect(null);
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
        root.getChildren().remove(egg);
        timeline.stop();
        Egg egg = this;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                view.getEggs().remove(egg);
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
