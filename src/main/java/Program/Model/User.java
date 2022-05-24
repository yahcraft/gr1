package Program.Model;

public class User {
    private String username;
    private String password;
    private int score;





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


    public int getScore() {
        return score;
    }
}
