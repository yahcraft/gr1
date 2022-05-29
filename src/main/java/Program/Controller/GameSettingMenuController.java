package Program.Controller;

import Program.Model.GameSettingMenu;
import Program.Model.User;
import Program.View.GameSettingMenuView;
import Program.View.GameView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameSettingMenuController {
    GameSettingMenuView view;
    GameSettingMenu menu;
    String selectedDifficulty;
    @FXML
    private Button easyButton;
    @FXML
    private Button normalButton;
    @FXML
    private Button hardButton;
    @FXML
    private Button devilModeButton;





    ////methods////
    public void initializeController(User user, GameSettingMenuView gameSettingMenuView)
    {
        view = gameSettingMenuView;
        menu = new GameSettingMenu(user);

        selectedDifficulty = normalButton.getText();
        normalButton.setStyle("-fx-background-color: #3e3e3e");
    }



    public void startGame(MouseEvent mouseEvent)
    {
        GameView gameView = new GameView(view.getStage(), menu.getUser());
    }



    public void back(MouseEvent mouseEvent)
    {
        MainMenuController mainMenuController = new MainMenuController(view.getStage(), menu.getUser());
    }



    public void changeDifficulty(MouseEvent mouseEvent)
    {
        Button button = (Button) mouseEvent.getSource();
        selectedDifficulty = button.getText();

        easyButton.setStyle("-fx-background-color: black");
        normalButton.setStyle("-fx-background-color: black");
        hardButton.setStyle("-fx-background-color: black");
        devilModeButton.setStyle("-fx-background-color: black");

        button.setStyle("-fx-background-color: #3e3e3e");
    }



    public void highlightRectangle(MouseEvent mouseEvent)
    {
        Button button = (Button) mouseEvent.getSource();

        if (!button.getText().equals(selectedDifficulty)){
            button.setStyle("-fx-background-color: #141414");
        }
    }



    public void removeHighlight(MouseEvent mouseEvent)
    {
        Button button = (Button) mouseEvent.getSource();

        if (!button.getText().equals(selectedDifficulty)){
            button.setStyle("-fx-background-color: black");
        }
    }
}
