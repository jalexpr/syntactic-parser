package ru.textanalysis.tawt.sp;

import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.sp.api.SyntaxParser;
import ru.textanalysis.tawt.sp.rules.controlmodel.utils.TestSentence;

public class ExampleHomonymyResolver {

    public static void main(String[] args) {
        SyntaxParser sp = new SyntaxParser();
        sp.init();

        Sentence sentence = sp.getTreeSentence(TestSentence.NounsPersonRule.SENTENCE_1);
        Sentence sentence2 = sp.getTreeSentence(TestSentence.VerbPrepositionRule.SENTENCE_1);
        Sentence sentence3 = sp.getTreeSentence(TestSentence.SensoryVerbsRule.SENTENCE_1);
    }
}
