package ru.textanalysis.tawt.sp.api;

import org.slf4j.LoggerFactory;
import ru.textanalysis.tawt.awf.AWF;
import ru.textanalysis.tawt.gama.main.Gama;
import ru.textanalysis.tawt.ms.external.sp.BearingPhraseExt;
import ru.textanalysis.tawt.ms.internal.sp.BearingPhraseSP;
import ru.textanalysis.tawt.ms.internal.sp.OmoFormSP;
import ru.textanalysis.tawt.ms.internal.sp.WordSP;
import ru.textanalysis.tawt.rfc.RulesForCompatibility;

import java.util.List;
import java.util.stream.Collectors;

import static ru.textanalysis.tawt.rfc.Utils.installCompatibility;

public class SyntaxParser implements ISyntaxParser {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    private Gama gama = new Gama();
    private AWF awf = new AWF();
    private RulesForCompatibility rules = new RulesForCompatibility();

    @Override
    public void init() {
        gama.init();
        awf.init();
        rules.init();
        log.debug("SP is initialized!");
    }

    @Override
    public List<BearingPhraseSP> getTreeSentence(String text) {
        List<BearingPhraseSP> bearingPhraseList = gama.getMorphSentence(text).stream().map(BearingPhraseSP::new).collect(Collectors.toList());
        bearingPhraseList.forEach(awf::applyAwfForBearingPhrase);
        bearingPhraseList.forEach(this::applyCompatibility);
        bearingPhraseList.forEach(BearingPhraseSP::searchMainOmoForm);
        return bearingPhraseList;
    }

    private void applyCompatibility(BearingPhraseSP bearingPhraseSP) {
        bearingPhraseSP.applyConsumer(word -> {
            establishCompatibility(word);
            attachmentToBearingWord(word);
            //todo
        });
    }

    private void establishCompatibility(List<WordSP> words) {
        for (int i = words.size() - 1; -1 < i; i--) {
            WordSP word = words.get(i);
            for (int j = words.size() - 1; i < j; j--) {
                if (rules.establishRelation(j - i, word, words.get(j))) {
                    //todo log;
                }
            }
        }
    }

    //todo добавить разеление на опорные и неопорные слова (делать двумя иттерациями)
    private void attachmentToBearingWord(List<WordSP> words) {
        for (int i = words.size() - 1; 0 < i; i--) {
            WordSP wordD = words.get(i);
            if (!wordD.haveMain()) {
                for (int j = i - 1; -1 < j; j--) {
                    WordSP wordM = words.get(j);
                    if (wordM.haveContainsBearingForm()) {
                        OmoFormSP omoFormM = wordM.getByFilter(OmoFormSP::haveBearingForm).get(0);
                        //todo добавить возможность привязывать не первый
                        OmoFormSP omoFormD;
                        List<OmoFormSP> omoFormsD = wordD.getByFilter(OmoFormSP::haveRelation);
                        if (omoFormsD.isEmpty()) {
                            omoFormD = wordD.getByFilter(omoFormSP -> true).get(0);
                        } else {
                            omoFormD = omoFormsD.get(0);
                        }
                        installCompatibility(wordM, wordD, omoFormM, omoFormD);
                        wordM.cleanNotRelation();
                        wordD.cleanNotRelation();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public List<BearingPhraseExt> getTreeSentenceWithoutAmbiguity(String text) {
        List<BearingPhraseSP> bearingPhraseSP = getTreeSentence(text);
       return bearingPhraseSP.stream().map(BearingPhraseSP::toExt).collect(Collectors.toList());
    }
}
