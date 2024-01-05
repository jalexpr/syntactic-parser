package ru.textanalysis.tawt.sp.rules.controlmodel;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.ms.model.sp.Word;
import ru.textanalysis.tawt.sp.rules.controlmodel.constants.WordsDatabase;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.Rule;
import ru.textanalysis.tawt.sp.rules.controlmodel.service.RuleInitService;
import ru.textanalysis.tawt.sp.rules.controlmodel.service.impl.RuleInitServiceImpl;

public class HomonymyResolver {

    private final RuleInitService ruleInitService;

    /**
     * Средство для снятия омонимии.
     */
    public HomonymyResolver() {
        this.ruleInitService = new RuleInitServiceImpl();
    }

    /**
     * Разрешить омонимию для предложения.
     * @param sentence предложение.
     */
    public void resolveHomonymyForSentence(Sentence sentence) {
        List<WordsDatabase> wordsDatabases = List.of(
                WordsDatabase.PRETEXT_DAT_PRED_CASE,
                WordsDatabase.PRETEXT_ROD_TVOR_CASE,
                WordsDatabase.PRETEXT_VIN_PRED_CASE,
                WordsDatabase.PRETEXT_VIN_TVOR_CASE,
                WordsDatabase.PRETEXT_ROD_CASE,
                WordsDatabase.PRETEXT_PRED_CASE,
                WordsDatabase.PRETEXT_DAT_CASE,
                WordsDatabase.PRETEXT_VIN_CASE,
                WordsDatabase.PRETEXT_TVOR_CASE,
                WordsDatabase.NOUNS_PERSON_WORDS,
                WordsDatabase.VERBS_OF_SENSORY_WORDS
        );
        Map<Rule, Set<Integer>> rulesToInitFormKeys = ruleInitService.getRulesToInitFormKeys(wordsDatabases);
        for (BearingPhrase bearingPhrase : sentence.getBearingPhrases()) {
            for (Word word : bearingPhrase.getWords()) {
                for (Map.Entry<Rule, Set<Integer>> rule : rulesToInitFormKeys.entrySet()) {
                    if (word.getForms().isEmpty()) break;
                    int wordFormKey = word.getForms().get(0).getInitialFormKey();
                    if (rule.getValue().contains(wordFormKey)) {
                        rule.getKey().resolveHomonymyForSentence(sentence, rule.getValue());
                    }
                }
            }
        }
    }
}
