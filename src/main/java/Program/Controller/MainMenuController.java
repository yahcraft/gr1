package Program.Controller;

import Program.Model.MainMenu;
import Program.Model.User;
import Program.View.MainMenuView;
import Program.View.ScoreboardView;
import javafx.stage.Stage;


public class MainMenuController {
    private MainMenu menu;
    private MainMenuView view;






    ////methods////
    public MainMenuController(Stage stage, User user)
    {
        this.menu = new MainMenu(user);
        view = new MainMenuView(stage, this);
    }



    public void statNewGame()
    {

    }



    public void enterProfile()
    {

    }



    public void enterScoreboard()
    {
        ScoreboardView scoreboardView = new ScoreboardView(view.getStage(), menu.getUser());
    }



    public void exit()
    {
        LoginMenuController.exit();
    }
}
