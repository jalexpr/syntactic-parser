package ru.textanalysis.tawt.sp;

import ru.textanalysis.tawt.ms.internal.sp.BearingPhraseSP;
import ru.textanalysis.tawt.sp.api.SyntaxParser;

public class ExampleSp {
    public static void main(String[] args) {
        SyntaxParser sp = new SyntaxParser();
        sp.init();

        BearingPhraseSP phrase = sp.getTreeSentence("Солнце село за село.");
        System.out.println(phrase);
    }
}
