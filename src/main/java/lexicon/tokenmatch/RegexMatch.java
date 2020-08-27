package lexicon.tokenmatch;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegexMatch {
    
    public static void main(String[] args) {
        String regex = Stream.of("illegal", "hot tip", "fucking")
                             .map(String::toLowerCase)
                             .collect(Collectors.joining("|"));
        Pattern pattern = Pattern.compile("(.*\\s+|)(" + regex + ")(\\s+.*|)");
        
        //String comment = "Oh, dude, I LOVE the feature. As a novice investor I’m fucking proud of my 11.44% haha. The leaderboard is dope.";
        //String comment = "I just wanted to buy a security. There's a SEC.";
        String comment = "Hot tip: buy this stock now.";
        
        System.out.println(pattern.matcher(comment.toLowerCase()).matches());
    }
}
