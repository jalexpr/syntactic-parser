package ru.textanalysis.tawt.sp.rules.controlmodel.rule.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.ms.model.sp.Word;
import ru.textanalysis.tawt.sp.rules.controlmodel.constants.WordsDatabase;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.Rule;
import ru.textanalysis.tawt.sp.rules.controlmodel.utils.StaticLogger;

/**
 * Снятие омонимии со слов существительных, обозначающих лицо, если у тех главное слово - существительное.
 * Пример: Изысканный стиль одежды архитектора.
 * Слово, обозначающее лицо, в данном случае будет находиться в родительном падеже.
 *
 * todo По наблюдения сущ + сущ (у второго всегда родительный падеж?)
 */
public class NounsPersonRule implements Rule {

    private final WordsDatabase wordsDatabase;

    public NounsPersonRule(WordsDatabase wordsDatabase) {
        this.wordsDatabase = wordsDatabase;
    }

    @Override
    public void resolveHomonymyForSentence(Sentence sentence, Set<Integer> wordsFormKeysFromDb) {
        for (BearingPhrase bearingPhrase : sentence.getBearingPhrases()) {
            if (bearingPhrase.getWords().isEmpty()) {
                continue;
            }

            Word prevWord = null;
            for (Word word : bearingPhrase.getWords()) {
                if (prevWord == null) {
                    prevWord = word;
                    continue;
                }

                if (word.getForms().isEmpty()) continue;
                int wordFormKey = word.getForms().get(0).getInitialFormKey();
                if (
                    wordsFormKeysFromDb.contains(wordFormKey) &&
                    word.getForms().get(0).getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.NOUN &&
                    prevWord.getForms().get(0).getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.NOUN
                ) {
                    List<Form> correctForms = word.getForms().stream()
                            .filter(form -> form.getMorfCharacteristicsByIdentifier(
                                    MorfologyParameters.Case.IDENTIFIER) == MorfologyParameters.Case.GENITIVE
                            )
                            .collect(Collectors.toList());

                    if (!correctForms.isEmpty() && word.getForms().size() != correctForms.size()) {
                        StaticLogger.printWordFormsDiff(word.getForms(), correctForms);
                        word.setForms(correctForms);
                    }
                }
                prevWord = word;
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NounsPersonRule that = (NounsPersonRule) object;
        return Objects.equals(wordsDatabase.getPath(), that.wordsDatabase.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordsDatabase.getPath());
    }
}
