package ru.textanalysis.tfwwt.syntax.parser.api;

import ru.textanalysis.tfwwt.awf.AWFilter;
import ru.textanalysis.tfwwt.gama.main.Gama;
import ru.textanalysis.tfwwt.morphological.structures.internal.sp.BearingPhraseSP;
import ru.textanalysis.tfwwt.morphological.structures.internal.sp.CursorToFormInWord;
import ru.textanalysis.tfwwt.morphological.structures.internal.sp.SentenceSP;
import ru.textanalysis.tfwwt.morphological.structures.internal.sp.WordSP;
import ru.textanalysis.tfwwt.morphological.structures.storage.ref.RefBearingPhraseList;
import ru.textanalysis.tfwwt.rules.compatibility.RelationshipHandler;

import java.util.List;

import static ru.textanalysis.tfwwt.morphological.structures.internal.sp.CursorToFormInWord.NOT_HAVE_EXACT_RELATION;

public class SyntaxParser {
    private AWFilter aWFilter = new AWFilter();
    private RelationshipHandler relationshipHandler = new RelationshipHandler();

    //закрыть
    public SyntaxParser() {
    }

    public SentenceSP getTreeSentence(String text) {
        Gama gama = new Gama();
        gama.init();
        RefBearingPhraseList morphSentence = gama.getMorphSentence(text);
        SentenceSP sentence = new SentenceSP(morphSentence);
        sentence.applyConsumer(aWFilter::useAWFilterForBearingPhraseList);
        sentence.applyConsumer(bearingPhrase -> bearingPhrase.applyConsumer(words -> words.forEach(WordSP::cleanNotRelation)));
        sentence.applyConsumer(bearingPhrase -> {
            bearingPhrase.applyConsumer(words -> {
                int leftB = 0;
                int rightB = 0;
                for (int i = 0; i < words.size(); i++) {
                    if (words.get(i).isContainsBearingForm()) {
                        if (leftB != rightB) {
                            leftB = rightB;
                        }
                        rightB = i;
                        searchRelation(leftB, rightB, words);
                    }
                }
            });
        });
        sentence.applyConsumer(bearingPhrase -> bearingPhrase.applyConsumer(words -> words.forEach(WordSP::cleanNotRelation)));
        sentence.applyConsumer(bearingPhrase -> {
            bearingPhrase.applyConsumer(words -> {
                int leftB = 0;
                int rightB = 0;
                for (int i = 0; i < words.size(); i++) {
                    if (words.get(i).isContainsBearingForm()) {
                        if (leftB != rightB) {
                            leftB = rightB;
                        }
                        rightB = i;
                        searchRelationBearingForm(leftB, rightB, words);
                    }
                }
            });
        });
        sentence.applyConsumer(BearingPhraseSP::searchMainOmoForm);
        return sentence;
    }

    private void searchRelationBearingForm(int leftB, int rightB, List<WordSP> words) {
        if (rightB - leftB > 1) {
            if (!relationshipHandler.establishRelation(rightB - leftB, words.get(leftB), words.get(rightB))) {
                final WordSP wordB = words.get(rightB);
                words.get(leftB).applyConsumer(omoForm -> {
                    if (!omoForm.haveMain()) {
                        omoForm.setMainCursors(new CursorToFormInWord(wordB, NOT_HAVE_EXACT_RELATION));
                        wordB.applyConsumer(omoFormB -> omoFormB.addDependentCursors(new CursorToFormInWord(words.get(leftB), omoForm.hashCode())));
                    }
                });
            }
        }
    }

    //todo test
    private void searchRelation(int leftB, int rightB, List<WordSP> words) {
        if (rightB - leftB > 1) {
            for (int i = rightB; i > leftB; i--) {
                for (int j = i - 1; j >= leftB; j--) {
                    relationshipHandler.establishRelation(i - j, words.get(j), words.get(i));
                }
            }

            WordSP wordB = words.get(rightB);
            for (int i = leftB + 1; i < rightB; i++) {
                final WordSP word = words.get(i);
                word.applyConsumer(omoForm -> {
                    if (!omoForm.haveMain()) {
                        omoForm.setMainCursors(new CursorToFormInWord(wordB, NOT_HAVE_EXACT_RELATION));
                        wordB.applyConsumer(omoFormB -> omoFormB.addDependentCursors(new CursorToFormInWord(word, omoForm.hashCode())));
                    }
                });
            }
        }
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
