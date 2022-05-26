package Program.Model;

public class User {
    private String username;
    private String password;
    private int firstPhaseScore;
    private int secondPhaseScore;
    private int thirdPhaseScore;
    private int time;





    ////methods////
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }



    //getters
    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public int getFirstPhaseScore() {
        return firstPhaseScore;
    }


    public int getSecondPhaseScore() {
        return secondPhaseScore;
    }


    public int getThirdPhaseScore() {
        return thirdPhaseScore;
    }


    public int getTime()
    {
        return time;
    }



    public int getTotalScore()
    {
        int totalScore = 0;
        totalScore += firstPhaseScore;
        totalScore += secondPhaseScore * 2;
        totalScore += thirdPhaseScore * 3;
        return totalScore;
    }
}
