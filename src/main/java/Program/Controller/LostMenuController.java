package Program.Controller;

import Program.Model.User;
import Program.View.GameView;
import Program.View.LostMenuView;
import javafx.scene.input.MouseEvent;

public class LostMenuController {
    User user;
    LostMenuView view;
    int difficulty;
    boolean wasDevilMove;





    ////methods////
    public void initializeController(User user, LostMenuView view, int difficulty, boolean wasDevilMove)
    {
        this.user = user;
        this.view = view;
        this.difficulty = difficulty;
        this.wasDevilMove = wasDevilMove;
    }



    public void restartGame(MouseEvent mouseEvent)
    {
        view.stopMusic();
        GameView gameView = new GameView(view.getStage(), user, difficulty, wasDevilMove);
    }



    public void back(MouseEvent mouseEvent)
    {
        view.stopMusic();
        MainMenuController mainMenuController = new MainMenuController(view.getStage(), user);
    }
}
