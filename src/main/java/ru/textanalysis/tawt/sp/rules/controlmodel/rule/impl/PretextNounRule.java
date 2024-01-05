package ru.textanalysis.tawt.sp.rules.controlmodel.rule.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.sp.rules.controlmodel.constants.WordsDatabase;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.BearingPhraseChecker;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.Rule;

public class PretextNounRule implements Rule {

    private final WordsDatabase wordsDatabase;
    private final List<Long> possibleCases;

    public PretextNounRule(WordsDatabase wordsDatabase, List<Long> possibleCases) {
        this.wordsDatabase = wordsDatabase;
        this.possibleCases = possibleCases;
    }

    @Override
    public void resolveHomonymyForSentence(Sentence sentence, Set<Integer> pretextInitialFormKeys) {
        boolean isPretextFound = false;
        for (BearingPhrase bearingPhrase : sentence.getBearingPhrases()) {
            BearingPhraseChecker.processBearingPhraseDefault(
                    bearingPhrase, isPretextFound, pretextInitialFormKeys, possibleCases
            );
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PretextNounRule that = (PretextNounRule) object;
        return Objects.equals(wordsDatabase.getPath(), that.wordsDatabase.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordsDatabase.getPath());
    }
}
