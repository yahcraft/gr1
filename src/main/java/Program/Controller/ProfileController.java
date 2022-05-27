package Program.Controller;

import Program.Model.LoginMenu;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

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
    private Rectangle imagePreview;
    @FXML
    private Text username;
    @FXML
    private Text password;


    ////methods////
    public void initializeController(User user, ProfileView profileView)
    {
        view = profileView;
        profile = new Profile(user);
        setTexts(user);
    }



    public void initializeNoText(User user, ProfileView profileView)
    {
        view = profileView;
        profile = new Profile(user);
    }



    private void setTexts(User user)
    {
        username.setText("username: " + user.getUsername());
        password.setText("password: " + user.getPassword());
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
        imagePreview.setFill(new ImagePattern(userImage));
    }



    public void nextImage(MouseEvent mouseEvent)
    {
        imagePreview.setFill(new ImagePattern(profile.getNextPreviewImage()));
    }



    public void previousImage(MouseEvent mouseEvent)
    {
        imagePreview.setFill(new ImagePattern(profile.getPreviousImage()));
    }



    public void saveImage(MouseEvent mouseEvent)
    {
        profile.savePreviewImage();
        imageCircle.setFill(new ImagePattern(profile.getUserImage()));
    }



    public void openFileChooser(MouseEvent mouseEvent)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(view.getStage());

        if (selectedFile != null) {
            profile.setCustomImage(selectedFile);
            imageCircle.setFill(new ImagePattern(profile.getUserImage()));
            imagePreview.setFill(new ImagePattern(profile.getPreviewImage()));
        }
    }



    public void changeUsername(MouseEvent mouseEvent)
    {

    }



    public void changePassword(MouseEvent mouseEvent)
    {

    }



    public void getConfirmation()
    {
        view.getConfirmation(profile.getUser());
    }



    public void deleteAccount()
    {
        profile.removeUser();

        try {
            LoginMenuController loginMenuController = new LoginMenuController(view.getStage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(MouseEvent mouseEvent)
    {
        try {
            LoginMenuController loginMenuController = new LoginMenuController(view.getStage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent)
    {
        MainMenuController mainMenuController = new MainMenuController(view.getStage(), profile.getUser());
    }
}

