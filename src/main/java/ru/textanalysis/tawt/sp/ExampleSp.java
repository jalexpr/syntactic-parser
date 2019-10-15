package ru.textanalysis.tawt.sp;

import ru.textanalysis.tawt.ms.internal.sp.BearingPhraseSP;
import ru.textanalysis.tawt.sp.api.SyntaxParser;

import java.util.List;

public class ExampleSp {
    public static void main(String[] args) {

//        BearingPhraseSP phrase1 = sp.getTreeSentence("Солнце село за село.");
//        System.out.println(phrase1);
//
        SyntaxParser sp = new SyntaxParser();
        sp.init();
        List<BearingPhraseSP> phrase
                = sp.getTreeSentence("Стало ясно, что будет с российской валютой.");
        phrase.forEach(System.out::println);
    }
}
