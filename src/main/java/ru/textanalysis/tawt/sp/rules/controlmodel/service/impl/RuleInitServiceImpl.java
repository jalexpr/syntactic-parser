package ru.textanalysis.tawt.sp.rules.controlmodel.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;
import ru.textanalysis.tawt.sp.rules.controlmodel.constants.WordsDatabase;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.Rule;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.impl.NounsPersonRule;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.impl.PretextNounRule;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.impl.VerbsSensoryRule;
import ru.textanalysis.tawt.sp.rules.controlmodel.service.RuleInitService;
import ru.textanalysis.tawt.sp.rules.controlmodel.utils.FileWorker;

public class RuleInitServiceImpl implements RuleInitService {

    private final JMorfSdk jMorfSdk;

    public RuleInitServiceImpl() {
        this.jMorfSdk = JMorfSdkFactory.loadFullLibrary();
    }

    @Override
    public Map<Rule, Set<Integer>> getRulesToInitFormKeys(List<WordsDatabase> wordsDatabases) {
        Map<Rule, Set<Integer>> resultMap = new HashMap<>();
        for (WordsDatabase wordsDatabase : wordsDatabases) {
            Set<String> words = FileWorker.readFileFromPath(wordsDatabase.getPath());
            Set<Integer> wordsKeys = getWordsFormKeys(words);
            Rule rule = createRule(wordsDatabase);

            resultMap.put(rule, wordsKeys);
        }
        return resultMap;
    }

    private Rule createRule(WordsDatabase wordsDatabase) {
        switch (wordsDatabase) {
            case PRETEXT_DAT_PRED_CASE:
                return new PretextNounRule(wordsDatabase, List.of(
                        MorfologyParameters.Case.DATIVE,
                        MorfologyParameters.Case.PREPOSITIONA,
                        MorfologyParameters.Case.PREPOSITIONA1,
                        MorfologyParameters.Case.PREPOSITIONA2
                ));
            case PRETEXT_ROD_TVOR_CASE:
                return new PretextNounRule(wordsDatabase, List.of(
                        MorfologyParameters.Case.GENITIVE, MorfologyParameters.Case.ABLTIVE,
                        MorfologyParameters.Case.GENITIVE1, MorfologyParameters.Case.GENITIVE2
                ));
            case PRETEXT_VIN_PRED_CASE:
                return new PretextNounRule(wordsDatabase, List.of(
                        MorfologyParameters.Case.ACCUSATIVE, MorfologyParameters.Case.PREPOSITIONA,
                        MorfologyParameters.Case.ACCUSATIVE2, MorfologyParameters.Case.PREPOSITIONA1,
                        MorfologyParameters.Case.PREPOSITIONA2
                ));
            case PRETEXT_VIN_TVOR_CASE:
                return new PretextNounRule(wordsDatabase, List.of(
                        MorfologyParameters.Case.ACCUSATIVE, MorfologyParameters.Case.ABLTIVE,
                        MorfologyParameters.Case.ACCUSATIVE2
                ));
            case PRETEXT_ROD_CASE:
                return new PretextNounRule(wordsDatabase, List.of(
                        MorfologyParameters.Case.GENITIVE, MorfologyParameters.Case.GENITIVE1,
                        MorfologyParameters.Case.GENITIVE2
                ));
            case PRETEXT_PRED_CASE:
                return new PretextNounRule(wordsDatabase, List.of(
                        MorfologyParameters.Case.PREPOSITIONA, MorfologyParameters.Case.PREPOSITIONA1,
                        MorfologyParameters.Case.PREPOSITIONA2
                ));
            case PRETEXT_DAT_CASE:
                return new PretextNounRule(wordsDatabase, List.of(
                        MorfologyParameters.Case.DATIVE
                ));
            case PRETEXT_VIN_CASE:
                return new PretextNounRule(wordsDatabase, List.of(
                        MorfologyParameters.Case.ACCUSATIVE, MorfologyParameters.Case.ACCUSATIVE2
                ));
            case PRETEXT_TVOR_CASE:
                return new PretextNounRule(wordsDatabase, List.of(
                        MorfologyParameters.Case.ABLTIVE
                ));
            case NOUNS_PERSON_WORDS:
                return new NounsPersonRule(wordsDatabase);
            case VERBS_OF_SENSORY_WORDS:
                return new VerbsSensoryRule(wordsDatabase, List.of(
                        MorfologyParameters.Case.ACCUSATIVE, MorfologyParameters.Case.ACCUSATIVE2,
                        MorfologyParameters.Case.ABLTIVE
                ));
            default:
                throw new IllegalArgumentException("Неизвестная база слов: " + wordsDatabase);
        }
    }

    /**
     * Метод получения InitialFormKey для множества слов из базы данных.
     * @param words множество слов из базы данных для конкретного правила.
     * @return множество ключей начальной формы для данных слов.
     */
    private Set<Integer> getWordsFormKeys(Set<String> words) {
        Set<Integer> formKeys = new HashSet<>();

        for (String word : words) {
            List<Form> wordForms = jMorfSdk.getOmoForms(word.toLowerCase());
            if (wordForms.isEmpty()) continue;
            int formKey = wordForms.get(0).getInitialFormKey();
            formKeys.add(formKey);
        }

        return formKeys;
    }
}
