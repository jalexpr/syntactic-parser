package ru.textanalysis.tawt.sp.rules.controlmodel.rule.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.sp.rules.controlmodel.constants.WordsDatabase;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.BearingPhraseChecker;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.Rule;

public class VerbsSensoryRule implements Rule {

    private final WordsDatabase wordsDatabase;
    private final List<Long> possibleCases;

    public VerbsSensoryRule(WordsDatabase wordsDatabase, List<Long> possibleCases) {
        this.wordsDatabase = wordsDatabase;
        this.possibleCases = possibleCases;
    }

    @Override
    public void resolveHomonymyForSentence(Sentence sentence, Set<Integer> ruleInitFormKeys) {
        for (BearingPhrase bearingPhrase : sentence.getBearingPhrases()) {
            boolean isSensoryVerbFound = false; // именно внутри опорного оборота todo
            BearingPhraseChecker.processBearingPhraseDefault(
                    bearingPhrase, isSensoryVerbFound, ruleInitFormKeys, possibleCases
            );
        }
    }

    @Override // todo
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        VerbsSensoryRule that = (VerbsSensoryRule) object;
        return wordsDatabase == that.wordsDatabase && Objects.equals(possibleCases, that.possibleCases);
    }

    @Override // todo
    public int hashCode() {
        return Objects.hash(wordsDatabase, possibleCases);
    }
}
