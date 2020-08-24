package lexicon.fuzzymatcher;

import com.intuit.fuzzymatcher.component.MatchService;
import com.intuit.fuzzymatcher.domain.*;
import com.intuit.fuzzymatcher.function.PreProcessFunction;
import com.intuit.fuzzymatcher.function.TokenizerFunction;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FuzzyMatcher {
    private final String[] blacklist;
    
    public FuzzyMatcher(String... blacklist) {
        this.blacklist = blacklist;
    }
    
    /**
     * Input document as text matched against blacklist document as text.
     *
     * @param commentText
     * @return
     */
    public boolean containsTextMatch(String commentText) {
        // build input document
        Document comment = new Document.Builder("comment")
                .addElement(new Element.Builder<>()
                    .setValue(commentText)
                    .setType(ElementType.TEXT)
                    .createElement()
                )
                .createDocument();
        
        // build blacklist as text
        Document.Builder documentBuilder = new Document.Builder("blacklist");
        for (String word : blacklist) {
            documentBuilder.addElement(new Element.Builder<>().setValue(word).setType(ElementType.TEXT).createElement());
        }
        Document blacklist = documentBuilder.createDocument();
    
        // check for match
        Map<String, List<Match<Document>>> result = new MatchService().applyMatchByDocId(comment, Collections.singletonList(blacklist));
        return result.containsKey("comment");
    }
    
    /**
     * Input document as name matched against blacklist document as name.
     *
     * @param commentText
     * @return
     */
    public boolean containsNameMatch(String commentText) {
        // build input document
        Document comment = new Document.Builder("comment")
                                   .addElement(new Element.Builder<>()
                                                       .setValue(commentText)
                                                       .setType(ElementType.NAME)
                                                       .createElement()
                                   )
                                   .createDocument();
        
        // build blacklist as text
        Document.Builder documentBuilder = new Document.Builder("blacklist");
        for (String word : blacklist) {
            documentBuilder.addElement(new Element.Builder<>().setValue(word).setType(ElementType.NAME).createElement());
        }
        Document blacklist = documentBuilder.createDocument();
        
        // check for match
        Map<String, List<Match<Document>>> result = new MatchService().applyMatchByDocId(comment, Collections.singletonList(blacklist));
        return result.containsKey("comment");
    }
    
    public boolean containsCustomMatch(String commentText) {
        // build input document
        Document comment = new Document.Builder("comment")
                                   .addElement(new Element.Builder<>()
                                                       .setValue(commentText)
                                                       .setType(ElementType.TEXT)
                                                       .setPreProcessingFunction(PreProcessFunction.none())
                                                       .setTokenizerFunction(TokenizerFunction.valueTokenizer()) // don't tokenize the string
                                                       .createElement()
                                   )
                                   .createDocument();
        
        // build blacklist as text
        Document.Builder documentBuilder = new Document.Builder("blacklist");
        for (String word : blacklist) {
            documentBuilder.addElement(new Element.Builder<>()
                                               .setValue(word)
                                               .setType(ElementType.TEXT)
                                               .setPreProcessingFunction(PreProcessFunction.none())
                                               .setTokenizerFunction(TokenizerFunction.valueTokenizer())
                                               .createElement());
        }
        Document blacklist = documentBuilder.createDocument();
        
        // check for match
        Map<String, List<Match<Document>>> result = new MatchService().applyMatchByDocId(comment, Collections.singletonList(blacklist));
        return result.containsKey("comment");
    }

    public static void main(String[] args) {
        FuzzyMatcher fuzzyMatcher = new FuzzyMatcher("shit", "fuck head");

        // Match Found
        // The comment text matches exactly to an element of the black list.
        System.out.println("Contains match: " + fuzzyMatcher.containsTextMatch("shit"));
        
        // No match found
        // All tokens of the comment element need to be present in one of the elements of the blacklist document.
        System.out.println("Contains multi word match: " + fuzzyMatcher.containsTextMatch("shit head"));
    
        // Match found
        // The TEXT matching compares all tokens, like a java Set.containsAll.
        System.out.println("Contains multi word match: " + fuzzyMatcher.containsTextMatch("head fuck"));
    
        // Match Found
        // The soundex of the comment text matches to the soundex of one of the blacklist elements.
        System.out.println("Contains name match: " + fuzzyMatcher.containsNameMatch("shiitt"));
    
        // No match found
        // All tokens of the comment element need to be present in one of the elements of the blacklist document.
        System.out.println("Contains multi word name match: " + fuzzyMatcher.containsNameMatch("shit head"));
    
        // Match found
        // The matching compares all tokens, like a java Set.containsAll.
        System.out.println("Contains multi word name match: " + fuzzyMatcher.containsNameMatch("hed fuk"));
    
        // No Match found
        System.out.println("Contains multi word match: " + fuzzyMatcher.containsCustomMatch("head fuck"));
        
        // Match found
        System.out.println("Contains multi word match: " + fuzzyMatcher.containsCustomMatch("fuck head"));
    
        // No match found
        System.out.println("Contains multi word match: " + fuzzyMatcher.containsCustomMatch("hed fuk"));
    }
}
