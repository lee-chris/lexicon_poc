package lexicon.levenshtein;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class LevenshteinTest {
    private final LevenshteinDistance levenshteinDistance;
    
    public LevenshteinTest() {
        levenshteinDistance = new LevenshteinDistance();
    }
    
    public String distance(String word1, String word2) {
        return "'" + word1 + "' matches to '" + word2 + "' with distance of " + levenshteinDistance.apply(word1, word2);
    }
    
    public static void main(String[] args) {
        LevenshteinTest levenshteinTest = new LevenshteinTest();
    
        System.out.println(levenshteinTest.distance("shit", "shit"));
        System.out.println(levenshteinTest.distance("Shit", "shit"));
        System.out.println(levenshteinTest.distance("shit", "shiit"));
        System.out.println(levenshteinTest.distance("sheet", "shit"));
        System.out.println(levenshteinTest.distance("sit", "shit"));
        System.out.println(levenshteinTest.distance("pass", "piss"));
        System.out.println(levenshteinTest.distance("shot", "shit"));
    }
}
