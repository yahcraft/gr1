package Program;

import Program.Controller.LoginMenuController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenuController menuController = new LoginMenuController(stage);
    }
}
