package Program.Controller;

import Program.Model.Game;
import Program.Model.User;
import Program.View.GameView;

public class GameController {
    private Game game;
    private GameView view;





    ////methods////
    public GameController(User user, GameView gameView)
    {
        game = new Game(user);
        this.view = gameView;
    }

    public User getUser()
    {
        return game.getUser();
    }
}
