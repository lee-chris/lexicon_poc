package lexicon.soundex;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;

import java.util.stream.Stream;

public class SoundexTest {
    private final Soundex soundex;
    private final String word1;
    private final String word2;
    
    public SoundexTest(Soundex soundex, String word1, String word2) {
        this.soundex = soundex;
        this.word1 = word1;
        this.word2 = word2;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        str.append("'");
        str.append(word1);
        str.append("' translates to: ");
        str.append(soundex.soundex(word1));
        str.append("\n");
    
        str.append("'");
        str.append(word2);
        str.append("' translates to: ");
        str.append(soundex.soundex(word2));
        str.append("\n");
        
        str.append("soundex difference of '");
        str.append(word1);
        str.append("' and '");
        str.append(word2);
        str.append("' is: ");
        try {
            str.append(soundex.difference(word1, word2));
        } catch (EncoderException e) {
            str.append("Error: ");
            str.append(e.toString());
        }
        str.append("\n");
        
        return str.toString();
    }
    
    public static void main(String[] args) throws Exception {
        Stream.of(Soundex.US_ENGLISH, Soundex.US_ENGLISH_SIMPLIFIED, Soundex.US_ENGLISH_GENEALOGY)
                .forEach(s -> {
                    System.out.println("=======\n");
                    System.out.println(new SoundexTest(s, "shit", "shiit"));
                    System.out.println(new SoundexTest(s, "fuck face", "shit face"));
                    System.out.println(new SoundexTest(s, "suck face", "shit face"));
                    System.out.println(new SoundexTest(s, "zit face", "shit face"));
                    System.out.println(new SoundexTest(s, "zit", "shit"));
                    System.out.println(new SoundexTest(s, "sheet", "shit"));
                    System.out.println(new SoundexTest(s, "sit", "shit"));
                    System.out.println(new SoundexTest(s, "pass", "piss"));
                    System.out.println(new SoundexTest(s, "shot", "shit"));
                });
    }
}
