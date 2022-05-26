package Program.Controller;

import Program.Model.Scoreboard;
import Program.Model.User;
import Program.View.ScoreboardView;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ScoreboardController
{
    Scoreboard scoreboard;
    ScoreboardView view;






    ////methods////
    public void setScoreboardController(User user, ScoreboardView view)
    {
        scoreboard = new Scoreboard(user);
        this.view = view;

    }



    @FXML
    public void sortTotal()
    {
        ArrayList<User> users = scoreboard.getSortedTotal();
        ArrayList<Text> texts = new ArrayList<>();
        setTexts(users, texts, 0);
        view.loadUsersText(texts);
    }



    @FXML
    private void sortEasy()
    {
        ArrayList<User> users = scoreboard.getSortedEasy();
        ArrayList<Text> texts = new ArrayList<>();
        setTexts(users, texts, 1);
        view.loadUsersText(texts);
    }



    @FXML
    private void sortNormal()
    {
        ArrayList<User> users = scoreboard.getSortedNormal();
        ArrayList<Text> texts = new ArrayList<>();
        setTexts(users, texts, 2);
        view.loadUsersText(texts);
    }



    @FXML
    private void sortHard()
    {
        ArrayList<User> users = scoreboard.getSortedHard();
        ArrayList<Text> texts = new ArrayList<>();
        setTexts(users, texts, 3);
        view.loadUsersText(texts);
    }



    @FXML
    private void sortDevilMode()
    {
        ArrayList<User> users = scoreboard.getSortedDevilMode();
        ArrayList<Text> texts = new ArrayList<>();
        setTexts(users, texts, 3);
        view.loadUsersText(texts);
    }



    @FXML
    private void back()
    {
        MainMenuController mainMenuController = new MainMenuController(view.getStage(), scoreboard.getUser());
    }



    private void setTexts(ArrayList<User> users, ArrayList<Text> texts, int phaseNumber)
    {
        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<Integer> times = new ArrayList<>();

        for (User user : users) {
            times.add(user.getTime());
            texts.add(new Text());

            switch (phaseNumber) {
                case (0):
                    scores.add(user.getTotalScore());
                    break;
                case (1):
                    scores.add(user.getEasyScore());
                    break;
                case (2):
                    scores.add(user.getNormalScore());
                    break;
                case (3):
                    scores.add(user.getHardScore());
                    break;
            }

            if (texts.size() == 10){
                break;
            }
        }

        for (int i = 0; i < users.size(); i++){
            texts.get(i).setText((i + 1) + "_ " + users.get(i).getUsername() + " score: " + scores.get(i));

            if ((users.size() > i + 1 && scores.get(i + 1).equals(scores.get(i))) || (i != 0 && scores.get(i - 1).equals(scores.get(i)))){
                texts.get(i).setText(texts.get(i).toString() + " time: " + times.get(i));
            }
        }

        for (int i = texts.size(); i < 10; i++){
            texts.add(new Text((i + 1) + "- "));
        }
    }
}
