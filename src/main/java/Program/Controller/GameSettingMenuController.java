package Program.Controller;

import Program.Model.GameSettingMenu;
import Program.Model.User;
import Program.View.GameSettingMenuView;
import Program.View.GameView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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
        int difficulty = 0;

        if (selectedDifficulty.equals(easyButton.getText())){
            difficulty = 1;
        }
        else if (selectedDifficulty.equals(normalButton.getText())){
            difficulty = 2;
        }
        else if (selectedDifficulty.equals(hardButton.getText()) || selectedDifficulty.equals(devilModeButton.getText())){
            difficulty = 3;
        }

        GameView gameView = new GameView(view.getStage(), menu.getUser(), difficulty, selectedDifficulty.equals(devilModeButton.getText()));
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
