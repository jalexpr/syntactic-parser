package ru.textanalysis.tfwwt.syntax.parser.api;

import ru.textanalysis.tfwwt.awf.AWFilter;
import ru.textanalysis.tfwwt.gama.main.Gama;
import ru.textanalysis.tfwwt.morphological.structures.internal.sp.SentenceSP;
import ru.textanalysis.tfwwt.morphological.structures.storage.ref.RefBearingPhraseList;

public class SyntaxParser {
    private AWFilter aWFilter = new AWFilter();

    //закрыть
    public SyntaxParser() {}

    public SentenceSP getTreeSentence(String text) {
        Gama gama = new Gama();
        gama.init();
        RefBearingPhraseList morphSentence = gama.getMorphSentence(text);
        SentenceSP sentence = new SentenceSP(morphSentence);
        sentence.applyFunction(aWFilter::useAWFilterForBearingPhraseList);
        return sentence;
    }

//    private void getTreeBearingPhrase(BearingPhraseSP bearingPhraseSP) {
//
//    }
//
//    private static void settingLinksBearingPhrases(BearingPhraseList bearingPhrases) {
//        settingLinksWithinBearingPhrases(bearingPhrases);
//        settingLinksMainWordsBearingPhrases(bearingPhrases);
//    }
//
//    private static void settingLinksWithinBearingPhrases(BearingPhraseList bearingPhrases) {
//        bearingPhrases.forEach(words -> {
//            settingLinksWithinOneBearingPhrase(words);
//        });
//    }
//
//    private static void settingLinksWithinOneBearingPhrase(WordList words) {
//        todo:связывание
//    }
//
//    private static void settingLinksMainWordsBearingPhrases(BearingPhraseList bearingPhrases) {
//
//    }

}
