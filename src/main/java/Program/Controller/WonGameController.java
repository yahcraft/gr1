package Program.Controller;

import Program.Model.LoginMenu;
import Program.Model.User;
import Program.View.WonGameView;
import javafx.scene.input.MouseEvent;

public class WonGameController {
    User user;
    WonGameView view;
    int difficulty;
    boolean wasDevilMode;





    ////methods////
    public void initializeController(User user, WonGameView wonGameView, int difficulty, boolean wasDevilMode)
    {
        this.user = user;
        this.view =wonGameView;
        this.difficulty = difficulty;
        this.wasDevilMode = wasDevilMode;
    }



    public void openMainMenu(MouseEvent mouseEvent)
    {
        view.stopMusic();
        int score = view.getScore();
        int time = view.getTime();

        if (score > user.getTotalScore()){
            user.setTotalScore(score);
            user.setTime(time);
            LoginMenu.save();
        }

        MainMenuController mainMenuController = new MainMenuController(view.getStage(), user);
    }
}
