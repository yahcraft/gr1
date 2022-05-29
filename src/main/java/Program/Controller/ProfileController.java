package Program.Controller;

import Program.Model.GraphicModels.CustomTextField;
import Program.Model.LoginMenu;
import Program.Model.MatchingStrings;
import Program.Model.Profile;
import Program.Model.User;
import Program.View.ProfileView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

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
    @FXML
    private Button changeUsernameButton;
    @FXML
    private Button changePasswordButton;





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



    public void loadUsernameSection()
    {
        view.loadUsernameSection();
    }



    public void changeUsername(CustomTextField usernameField)
    {
        String username = usernameField.getInput();

        if (isUsernameFormatInvalid(username)){
            view.showInvalidUsernameFormat();
        }
        else if (profile.doesUsernameExist(username)){
            view.showExistingUsername();
        }
        else {
            profile.changeUsername(username);
            view.showUsernameChanged();
            this.username.setText("username: " + username);
            LoginMenu.save();
        }
    }



    public void loadPasswordSection()
    {
        view.loadPasswordSection();
    }



    public void changePassword(CustomTextField passwordField)
    {
        String password = passwordField.getInput();

        if (isPasswordFormatInvalid(password)){
            view.showInvalidPasswordFormat();
        }
        else {
            profile.changePassword(password);
            view.showPasswordChanged();
            this.password.setText("password: " + password);
            LoginMenu.save();
        }
    }



    private boolean isUsernameFormatInvalid(String username)
    {
        return !MatchingStrings.USERNAME.matcher(username).matches() || username.length() < 3 || username.length() > 15;
    }



    private boolean isPasswordFormatInvalid(String password)
    {
        return !MatchingStrings.PASSWORD.matcher(password).matches() || password.length() < 5 || password.length() > 18 ||
                !(Pattern.compile("[a-zA-Z]").matcher(password).find() && Pattern.compile("[0-9]").matcher(password).find());
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

