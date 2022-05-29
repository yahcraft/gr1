package Program.Model.GraphicModels;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Egg
{
    private Group root;
    private ImageView egg;
    private Rectangle hitBox;
    private int theta;
    private Timeline timeline;
    int speed;





    ////methods////
    public Egg(double x, double y, Group root)
    {
        speed = 10;

        this.root = root;
        egg = new ImageView(String.valueOf(getClass().getResource("/Textures/Game/egg.png")));
        egg.setFitWidth(136);
        egg.setFitHeight(116);
        egg.setX(x);
        egg.setY(y - egg.getFitWidth() / 2);
        root.getChildren().add(egg);

        showAnimation();
    }



    private void showAnimation()
    {
        timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                move();
            }
        }));

        timeline.setCycleCount(-1);

        timeline.play();
    }



    private void move()
    {
        egg.setX(egg.getX() - speed);
        egg.setRotate(theta);
        theta += 5;

        if (egg.getX() > 1280){
            root.getChildren().remove(egg);
            timeline.stop();
        }
    }
}
