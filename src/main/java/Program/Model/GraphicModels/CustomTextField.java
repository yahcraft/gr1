package Program.Model.GraphicModels;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

public class CustomTextField {
    ImageView overlayImageView;
    TextField textField;
    private int width;
    private int height;
    private int x;
    private int y;





    ////methods////
    public CustomTextField(int x, int y, int width, int height, String label)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        addOverlay();
        addTextField(label);
    }



    private void addOverlay()
    {
        Image overlayImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Textures/LoginMenu/TextField.png")));
        overlayImageView = new ImageView(overlayImage);

        overlayImageView.setX(x);
        overlayImageView.setY(y);
        overlayImageView.setFitWidth(width);
        overlayImageView.setFitHeight(height);
        overlayImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }



    private void addTextField(String label)
    {
        textField = new TextField();

        textField.setPromptText(label);
        textField.setFont(Font.font(height / 3 + 3));
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setPrefWidth(width);
        textField.setPrefHeight(height);
        textField.setBackground(Background.fill(Color.TRANSPARENT));
    }



    public Group getNode()
    {
        return new Group(overlayImageView, textField);
    }



    public String getInput()
    {
        return textField.getText();
    }



    public void showWarning(String warningMessage)
    {
        textField.setPromptText(warningMessage);
        textField.setText("");
        textField.setStyle("-fx-prompt-text-fill: red");
    }



    public void showSuccessful(String successMessage)
    {
        textField.setPromptText(successMessage);
        textField.setText("");
        textField.setStyle("-fx-prompt-text-fill: green");
    }
}
