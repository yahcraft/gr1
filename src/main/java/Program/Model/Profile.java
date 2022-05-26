package Program.Model;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

public class Profile {
    User user;
    ArrayList<User> users;





    ////methods////
    public Profile(User user)
    {
        this.user = user;
        users = LoginMenu.getUsers();
    }



    public Image getUserImage()
    {
        return user.getImage();
    }



    public void setNewRandomImage()
    {
        user.setRandomImage();
    }
}
