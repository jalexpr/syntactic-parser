import ru.textanalysis.tfwwt.morphological.structures.internal.sp.SentenceSP;
import ru.textanalysis.tfwwt.syntax.parser.api.SyntaxParser;

public class Runner {
    public static void main(String[] args) {
        SyntaxParser syntaxParser = new SyntaxParser();

        SentenceSP sentence = syntaxParser.getTreeSentence("Мама мыла раму");

        System.out.println(sentence);
    }
}
