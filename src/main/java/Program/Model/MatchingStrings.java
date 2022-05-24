package Program.Model;

import java.util.regex.Pattern;

public class MatchingStrings {
    public static Pattern USERNAME = Pattern.compile("\\w+");
    public static Pattern PASSWORD = Pattern.compile("[a-zA-Z0-9]+");
}
