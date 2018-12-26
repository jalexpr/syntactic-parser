package ru.textanalysis.tfwwt.syntax.parser.api;

import ru.textanalysis.tfwwt.gama.main.Gama;
import ru.textanalysis.tfwwt.jmorfsdk.form.Form;
import ru.textanalysis.tfwwt.morphological.structures.storage.BearingPhraseList;
import ru.textanalysis.tfwwt.morphological.structures.storage.WordList;

import java.util.List;

public class SyntaxParser {
    private SyntaxParser() {}

    public static List<Form> getTreeSentence(String text) {
        Gama gama = new Gama();
        BearingPhraseList bearingPhraseList = gama.getMorfSentence(text);

    }

    private static void settingLinksBearingPhrases(BearingPhraseList bearingPhrases) {
        settingLinksWithinBearingPhrases(bearingPhrases);
        settingLinksMainWordsBearingPhrases(bearingPhrases);
    }

    private static void settingLinksWithinBearingPhrases(BearingPhraseList bearingPhrases) {
        bearingPhrases.forEach(words -> {
            settingLinksWithinOneBearingPhrase(words);
        });
    }

    private static void settingLinksWithinOneBearingPhrase(WordList words) {
        //todo:связывание
    }

    private static void settingLinksMainWordsBearingPhrases(BearingPhraseList bearingPhrases) {

    }

}
