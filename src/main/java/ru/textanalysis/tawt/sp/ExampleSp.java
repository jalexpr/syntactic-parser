package ru.textanalysis.tawt.sp;

import ru.textanalysis.tawt.ms.internal.sp.SentenceSP;
import ru.textanalysis.tawt.sp.api.SyntaxParser;

public class ExampleSp {
    public static void main(String[] args) {
        SyntaxParser syntaxParser = new SyntaxParser();

        SentenceSP sentence = syntaxParser.getTreeSentence("Мама мыла раму");

        System.out.println(sentence);
    }
}
