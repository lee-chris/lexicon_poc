package lexicon.tokenmatch;

import java.util.ArrayList;
import java.util.List;

public class TokenMatch {
    public TokenMatch() {
    
    }
    
    public boolean match(String commentText, List<String[]> blacklist) {
        
        String[] words = commentText.split("\\s+");
        
        // .replaceAll("[^A-Za-z0-9 ]+", "");
        
        for (int i = 0; i < words.length; i++) {
            for (String[] banned : blacklist) {
                boolean match = false;
                for (int j = 0; j < banned.length; j++) {
                    if (!words[i + j].equals(banned[j])) {
                        match = false;
                        break;
                    }
                    match = true;
                }
    
                if (match) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        List<String[]> blacklist = new ArrayList<>();
        blacklist.add(new String[] {"illegal"});
        blacklist.add(new String[] {"hot", "tip"});
        
        String commentText = "hot tip Sell this stock. This company is involved with doing something ilegal.";
        
        System.out.println(new TokenMatch().match(commentText, blacklist));
    }
}
