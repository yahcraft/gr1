package Program.Model;

import java.util.ArrayList;
import java.util.Comparator;

public class Scoreboard {
    User user;
    ArrayList<User> users;





    ////methods////
    public Scoreboard(User user)
    {
        this.user = user;
        users = LoginMenu.getUsers();
    }



    //getters
    public User getUser()
    {
        return user;
    }



    public ArrayList<User> getSortedTotal()
    {
        ArrayList<User> usersSorted = new ArrayList<>(users);

        usersSorted.sort(Comparator.comparing(User::getTotalScore).thenComparing(User::getTotalScore).thenComparing(User::getUsername));

        return usersSorted;
    }



    public ArrayList<User> getSortedFirstPhase()
    {
        ArrayList<User> usersSorted = new ArrayList<>(users);

        usersSorted.sort(Comparator.comparing(User::getFirstPhaseScore).thenComparing(User::getTotalScore).thenComparing(User::getUsername));

        return usersSorted;
    }



    public ArrayList<User> getSortedSecondPhase()
    {
        ArrayList<User> usersSorted = new ArrayList<>(users);

        usersSorted.sort(Comparator.comparing(User::getSecondPhaseScore).thenComparing(User::getTotalScore).thenComparing(User::getUsername));

        return usersSorted;
    }



    public ArrayList<User> getSortedThirdPhase()
    {
        ArrayList<User> usersSorted = new ArrayList<>(users);

        usersSorted.sort(Comparator.comparing(User::getThirdPhaseScore).thenComparing(User::getTotalScore).thenComparing(User::getUsername));

        return usersSorted;
    }
}
