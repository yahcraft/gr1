package Program.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class LoginMenu {
    ArrayList<User> users;





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



    public void save()
    {
        try {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter("src/main/resources/users.json");

            if (users != null && users.size() != 0) {
                gson.toJson(users, writer);
            }
            else {
                File file = new File("src/main/resources/users.json");
                file.delete();
            }

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
}
