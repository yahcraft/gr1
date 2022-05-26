package Program.Controller;

import Program.Model.GraphicModels.CustomTextField;
import Program.Model.LoginMenu;
import Program.Model.MatchingStrings;
import Program.Model.User;
import Program.View.LoginMenuView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginMenuController {
    private static LoginMenu menu;
    private static LoginMenuView view;





    ////methods////
    public  LoginMenuController(Stage stage) throws IOException {
        this.menu = new LoginMenu();
        this.view = new LoginMenuView(stage, this);
    }



    public void login(CustomTextField usernameField, CustomTextField passwordField)
    {
        String username = usernameField.getInput();
        String password = passwordField.getInput();

        if (username.length() == 0 && password.length() == 0){
            User user = new User("guest", "guest");
            MainMenuController mainMenuController = new MainMenuController(view.getStage(), user);
        }

        if (menu.isPasswordWrong(username, password)){
            view.showWrongPassword();
        }

        MainMenuController mainMenuController = new MainMenuController(view.getStage(), menu.getUserFromUsername(username));
    }



    private boolean isUsernameFormatInvalid(String username)
    {
        return !MatchingStrings.USERNAME.matcher(username).matches() || username.length() < 3;
    }



    private boolean isPasswordFormatInvalid(String password)
    {
        return !MatchingStrings.PASSWORD.matcher(password).matches() || password.length() < 5 ||
                !(Pattern.compile("[a-zA-Z]").matcher(password).find() && Pattern.compile("[0-9]").matcher(password).find());
    }



    public void register(CustomTextField usernameField, CustomTextField passwordField)
    {
        String username = usernameField.getInput();
        String password = passwordField.getInput();

        if (isUsernameFormatInvalid(username)){
            view.showInvalidUsernameFormat();
        }
        else if (menu.doesUsernameExist(username)){
            view.showExistingUsername();
        }
        else if (isPasswordFormatInvalid(password)){
            view.showInvalidPasswordFormat();
       }
        else {
            User user = new User(username, password);
            menu.addUser(user);
            menu.save();
            MainMenuController mainMenuController = new MainMenuController(view.getStage(), user);
        }
    }



    public static void exit()
    {
        menu.save();
        view.getStage().close();
    }
}
