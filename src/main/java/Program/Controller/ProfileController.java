package Program.Controller;

import Program.Model.Profile;
import Program.Model.User;
import Program.View.ProfileView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ProfileController {
    ProfileView view;
    Profile profile;

    @FXML
    private Circle imageCircle;
    @FXML
    private Button nextButton;
    @FXML
    private Button previousButton;
    @FXML
    private Button saveImage;
    @FXML
    private Button panel;
    @FXML
    private Rectangle imagePreview;


    ////methods////
    public void initializeController(User user, ProfileView profileView)
    {
        view = profileView;
        profile = new Profile(user);
    }



    public void loadUserImage()
    {
        Image userImage = profile.getUserImage();
        imageCircle.setFill(new ImagePattern(userImage));

        imagePreview.setFill(new ImagePattern(profile.getUserImage()));
    }



    public void setNewRandomImage(MouseEvent mouseEvent)
    {
        profile.setNewRandomImage();
        Image userImage = profile.getUserImage();
        imageCircle.setFill(new ImagePattern(userImage));
    }



    public void nextImage(MouseEvent mouseEvent)
    {

    }



    public void previousImage(MouseEvent mouseEvent)
    {

    }



    public void saveImage(MouseEvent mouseEvent)
    {

    }



    public void openPanel(MouseEvent mouseEvent)
    {

    }
}

