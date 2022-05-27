package Program.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoginMenu {
    private static ArrayList<User> users;





    ////methods////
    public LoginMenu()
    {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("src/main/resources/users.json");
            users = new Gson().fromJson(reader, new TypeToken<List<User>>() {}.getType());
            reader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (users == null){
            users = new ArrayList<>();
        }
    }



    public static void save()
    {
        try {
            /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = Files.newBufferedWriter(Path.of("src/main/resources/users.json"));

            if (users != null && users.size() != 0) {
                gson.toJson("mamad hasan", writer);
            }
            else {
                File oldFile = new File("src/main/resources/users.json");
                oldFile.delete();
                File file = new File("src/main/resources/users.json");
                file.createNewFile();
            }*/

            Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
            Path userPath = Path.of("src/main/resources/users.json");
            Writer writer = Files.newBufferedWriter(userPath);
            gsonBuilder.toJson(users, writer);
            writer.close();


            writer.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
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

    public void addUser(User user)
    {
        users.add(user);
    }

    public boolean isPasswordWrong(String username, String password)
    {
        for (User user: users){
            if (user.getUsername().equals(username)){
                return !user.getPassword().equals(password);
            }
        }

        return true;
    }



    //getters
    public User getUserFromUsername(String username)
    {
        for (User user: users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }

        return null;
    }



    public static ArrayList<User> getUsers()
    {
        return users;
    }
}
