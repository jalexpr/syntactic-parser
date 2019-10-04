package ru.textanalysis.tawt.sp.api;

import ru.textanalysis.tawt.awf.AWF;
import ru.textanalysis.tawt.gama.main.Gama;
import ru.textanalysis.tawt.ms.internal.sp.BearingPhraseSP;
import ru.textanalysis.tawt.ms.storage.BearingPhraseList;
import ru.textanalysis.tawt.ms.storage.WordList;
import ru.textanalysis.tawt.ms.storage.ref.RefBearingPhraseList;

public class SyntaxParser {
    //todo
    private Gama gama = new Gama();
    private AWF awf = new AWF();

    public void init() {
        gama.init();
        awf.init();
    }

    public BearingPhraseSP getTreeSentence(String text) {
        RefBearingPhraseList bearingPhraseList = gama.getMorphSentence(text);
        BearingPhraseSP bearingPhrase = new BearingPhraseSP(bearingPhraseList.get(0)); //todo пока что работа с первым опорным оборотом
        awf.applyAwfForBearingPhrase(bearingPhrase);
        bearingPhrase.searchMainOmoForm();
        return bearingPhrase;
    }

    private void settingLinksBearingPhrases(BearingPhraseList bearingPhrases) {
        settingLinksWithinBearingPhrases(bearingPhrases);
        settingLinksMainWordsBearingPhrases(bearingPhrases);
    }

    private void settingLinksWithinBearingPhrases(BearingPhraseList bearingPhrases) {
        bearingPhrases.forEach(this::settingLinksWithinOneBearingPhrase);
    }

    private void settingLinksWithinOneBearingPhrase(WordList words) {
        //todo:связывание
    }

    private void settingLinksMainWordsBearingPhrases(BearingPhraseList bearingPhrases) {

    }
}
