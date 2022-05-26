package Program.Model;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class User {
    private String username;
    private String password;
    private int easyScore;
    private int normalScore;
    private int hardScore;
    private int devilModeScore;
    private int time;
    private String imagePath;





    ////methods////
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        setRandomImage();
    }



    //setters
    public void setRandomImage()
    {
        //File directory = new File((getClass().getResource("profileImages")).toString());
        File directory = new File("src/main/resources/profileImages");
        File[] images = directory.listFiles();
        Random rand = new Random();
        File image = images[rand.nextInt(images.length)];
        imagePath = image.getPath();
    }


    //getters
    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public int getEasyScore() {
        return easyScore;
    }


    public int getNormalScore() {
        return normalScore;
    }


    public int getHardScore() {
        return hardScore;
    }


    public int getDevilModeScore()
    {
        return devilModeScore;
    }


    public int getTime()
    {
        return time;
    }



    public int getTotalScore()
    {
        int totalScore = 0;
        totalScore += easyScore;
        totalScore += normalScore * 2;
        totalScore += hardScore * 3;
        return totalScore;
    }


    public Image getImage() {
        try {
            return new Image(new FileInputStream(imagePath));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
