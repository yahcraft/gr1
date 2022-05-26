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



    public ArrayList<User> getSortedEasy()
    {
        ArrayList<User> usersSorted = new ArrayList<>(users);

        usersSorted.sort(Comparator.comparing(User::getEasyScore).thenComparing(User::getTotalScore).thenComparing(User::getUsername));

        return usersSorted;
    }



    public ArrayList<User> getSortedNormal()
    {
        ArrayList<User> usersSorted = new ArrayList<>(users);

        usersSorted.sort(Comparator.comparing(User::getNormalScore).thenComparing(User::getTotalScore).thenComparing(User::getUsername));

        return usersSorted;
    }



    public ArrayList<User> getSortedHard()
    {
        ArrayList<User> usersSorted = new ArrayList<>(users);

        usersSorted.sort(Comparator.comparing(User::getHardScore).thenComparing(User::getTotalScore).thenComparing(User::getUsername));

        return usersSorted;
    }



    public ArrayList<User> getSortedDevilMode()
    {
        ArrayList<User> usersSorted = new ArrayList<>(users);

        usersSorted.sort(Comparator.comparing(User::getDevilModeScore).thenComparing(User::getTotalScore).thenComparing(User::getUsername));

        return usersSorted;
    }
}
