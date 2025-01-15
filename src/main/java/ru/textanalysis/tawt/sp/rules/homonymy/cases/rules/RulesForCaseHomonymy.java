package ru.textanalysis.tawt.sp.rules.homonymy.cases.rules;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.keywords.WordsDatabase;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.keywords.WordsLoader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Инициализатор правил снятия падежной неоднозначности.
 */
@Slf4j
@Getter
public class RulesForCaseHomonymy {

    /**
     * Правила предложно-падежных конструкций.
     */
    private final List<Rule> pretextRules = new LinkedList<>();
    /**
     * Правила глагольного управления.
     */
    private final List<Rule> rules = new LinkedList<>();
    /**
     * Загрузчик слов.
     */
    private final WordsLoader wordsLoader;

    public RulesForCaseHomonymy() {
        this.wordsLoader = new WordsLoader(); // загрузка слов

        // Pretext rules
        addRulesForPretext(pretextRules); // инициализация правил предложно-падежных конструкций

        // Verb rules
        addRulesForVerb(rules); // инициализация правил глагольного управления
    }

    private void addRulesForVerb(List<Rule> rules) {
        rules.add(
                Rule.builder()
                        .ruleName("Глагол стремления к цели, требующий Род. или Вин. падеж")
                        .maxDistanceLeft((byte) 0)
                        .maxDistanceRight((byte) 127)
                        .possibleCases(Set.of(
                                MorfologyParameters.Case.GENITIVE,
                                MorfologyParameters.Case.ACCUSATIVE
                        ))
                        .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.GOAL_VERBS))
                        .build()
        );
        rules.add(
                Rule.builder()
                        .ruleName("Глагол зрительного восприятия, требующий Вин. падеж")
                        .maxDistanceLeft((byte) 0)
                        .maxDistanceRight((byte) 127)
                        .possibleCases(Set.of(
                                MorfologyParameters.Case.ACCUSATIVE
                        ))
                        .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.VISUAL_VERBS))
                        .build()
        );
        rules.add(
                Rule.builder()
                        .ruleName("Существительное, произведённое от переходного глагола, требующий Род. падеж")
                        .maxDistanceLeft((byte) 0)
                        .maxDistanceRight((byte) 127)
                        .possibleCases(Set.of(
                                MorfologyParameters.Case.GENITIVE
                        ))
                        .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.DERIVED_NOUNS_FROM_VERBS))
                        .build()
        );
        rules.add(
                Rule.builder()
                        .ruleName("Существительное, произведённое от переходного глагола, требующий Род. падеж")
                        .maxDistanceLeft((byte) 0)
                        .maxDistanceRight((byte) 127)
                        .possibleCases(Set.of(
                                MorfologyParameters.Case.GENITIVE
                        ))
                        .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.DERIVED_NOUNS_FROM_VERBS))
                        .build()
        );
        rules.add(
                Rule.builder()
                        .ruleName("Глагол страха, требующий Род. падеж")
                        .maxDistanceLeft((byte) 0)
                        .maxDistanceRight((byte) 127)
                        .possibleCases(Set.of(
                                MorfologyParameters.Case.GENITIVE
                        ))
                        .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.FEAR_VERBS))
                        .build()
        );
    }

    private void addRulesForPretext(List<Rule> rules) {
        rules.addAll(
                List.of(
                    Rule.builder()
                            .ruleName("Предлог, требующий Дат. или Пред. падеж")
                            .maxDistanceLeft((byte) 0)
                            .maxDistanceRight((byte) 127)
                            .possibleCases(Set.of(
                                    MorfologyParameters.Case.DATIVE,
                                    MorfologyParameters.Case.PREPOSITIONA
                            ))
                            .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.PRETEXT_DAT_PRED_CASE))
                            .build(),

                        Rule.builder()
                                .ruleName("Предлог, требующий Род. или Твор. падеж")
                                .maxDistanceLeft((byte) 0)
                                .maxDistanceRight((byte) 127)
                                .possibleCases(Set.of(
                                        MorfologyParameters.Case.GENITIVE,
                                        MorfologyParameters.Case.ABLTIVE
                                ))
                                .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.PRETEXT_ROD_TVOR_CASE))
                                .build(),

                        Rule.builder()
                                .ruleName("Предлог, требующий Вин. или Пред. падеж")
                                .maxDistanceLeft((byte) 0)
                                .maxDistanceRight((byte) 127)
                                .possibleCases(Set.of(
                                        MorfologyParameters.Case.ACCUSATIVE,
                                        MorfologyParameters.Case.PREPOSITIONA
                                ))
                                .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.PRETEXT_VIN_PRED_CASE))
                                .build(),

                        Rule.builder()
                                .ruleName("Предлог, требующий Вин. или Твор. падеж")
                                .maxDistanceLeft((byte) 0)
                                .maxDistanceRight((byte) 127)
                                .possibleCases(Set.of(
                                        MorfologyParameters.Case.ACCUSATIVE,
                                        MorfologyParameters.Case.ABLTIVE
                                ))
                                .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.PRETEXT_VIN_TVOR_CASE))
                                .build(),

                        Rule.builder()
                                .ruleName("Предлог, требующий Род. падеж")
                                .maxDistanceLeft((byte) 0)
                                .maxDistanceRight((byte) 127)
                                .possibleCases(Set.of(
                                        MorfologyParameters.Case.GENITIVE
                                ))
                                .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.PRETEXT_ROD_CASE))
                                .build(),

                        Rule.builder()
                                .ruleName("Предлог, требующий Пред. падеж")
                                .maxDistanceLeft((byte) 0)
                                .maxDistanceRight((byte) 127)
                                .possibleCases(Set.of(
                                        MorfologyParameters.Case.PREPOSITIONA
                                ))
                                .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.PRETEXT_PRED_CASE))
                                .build(),

                        Rule.builder()
                                .ruleName("Предлог, требующий Дат. падеж")
                                .maxDistanceLeft((byte) 0)
                                .maxDistanceRight((byte) 127)
                                .possibleCases(Set.of(
                                        MorfologyParameters.Case.DATIVE
                                ))
                                .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.PRETEXT_DAT_CASE))
                                .build(),

                        Rule.builder()
                                .ruleName("Предлог, требующий Вин. падеж")
                                .maxDistanceLeft((byte) 0)
                                .maxDistanceRight((byte) 127)
                                .possibleCases(Set.of(
                                        MorfologyParameters.Case.ACCUSATIVE
                                ))
                                .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.PRETEXT_VIN_CASE))
                                .build(),

                        Rule.builder()
                                .ruleName("Предлог, требующий Твор. падеж")
                                .maxDistanceLeft((byte) 0)
                                .maxDistanceRight((byte) 127)
                                .possibleCases(Set.of(
                                        MorfologyParameters.Case.ABLTIVE
                                ))
                                .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.PRETEXT_TVOR_CASE))
                                .build()
                )
        );
    }
}
