package Program.Model;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Profile {
    User user;
    ArrayList<User> users;
    File[] previewImages;
    int previewImageNumber;






    ////methods////
    public Profile(User user)
    {
        this.user = user;
        users = LoginMenu.getUsers();

        File directory = new File("src/main/resources/profileImages");
        previewImages = directory.listFiles();
        setPreviewImageNumber();
    }



    public void removeUser()
    {
        users.remove(user);
        LoginMenu.save();

    }



    public void changeUsername(String username)
    {
        user.setUsername(username);
    }



    public void changePassword(String password)
    {
        user.setPassword(password);
    }



    //setters
    private void setPreviewImageNumber()
    {
        if (user.isProfileCustom()){
            previewImageNumber = 0;
            return;
        }

        for (int i = 0; i < previewImages.length; i++){
            if (previewImages[i].getPath().equals(user.getImagePath())){
                previewImageNumber = i;
                return;
            }
        }
    }



    public void setNewRandomImage()
    {
        user.setRandomImage();
        setPreviewImageNumber();
        user.setProfileCustom(false);
        LoginMenu.save();
    }



    public void savePreviewImage()
    {
        user.setImagePath(previewImages[previewImageNumber].getPath());
        user.setProfileCustom(false);
        LoginMenu.save();
    }



    public void setCustomImage(File file)
    {
        user.setImagePath(file.getPath());
        user.setProfileCustom(true);
        LoginMenu.save();
    }



    public Image getUserImage()
    {
        return user.getImage();
    }



    public Image getNextPreviewImage()
    {
        previewImageNumber++;

        if (previewImageNumber < previewImages.length){
            try {
                return new Image(new FileInputStream(previewImages[previewImageNumber]));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            previewImageNumber = 0;

            try {
                return new Image(new FileInputStream(previewImages[previewImageNumber]));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }



    public Image getPreviousImage()
    {
        previewImageNumber--;

        if (previewImageNumber < 0){
            previewImageNumber = previewImages.length - 1;

            try {
                return new Image(new FileInputStream(previewImages[previewImageNumber]));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                return new Image(new FileInputStream(previewImages[previewImageNumber]));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }



    public Image getPreviewImage()
    {
        try {
            return new Image(new FileInputStream(previewImages[previewImageNumber]));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    public User getUser()
    {
        return user;
    }


    public boolean doesUsernameExist(String username)
    {
        for (User user: users){
            if (user.getUsername().equals(username)){
                return true;
            }
        }

        return false;
    }
}
