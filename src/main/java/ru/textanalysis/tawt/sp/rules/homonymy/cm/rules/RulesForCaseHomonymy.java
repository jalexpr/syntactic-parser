package ru.textanalysis.tawt.sp.rules.homonymy.cm.rules;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.sp.rules.homonymy.cm.keywords.WordsDatabase;
import ru.textanalysis.tawt.sp.rules.homonymy.cm.keywords.WordsLoader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class RulesForCaseHomonymy {

    private final List<Rule> pretextRules = new LinkedList<>();
    private final List<Rule> rules = new LinkedList<>();
    private final WordsLoader wordsLoader;

    public RulesForCaseHomonymy() {
        this.wordsLoader = new WordsLoader();

        // Pretext rules
        addRulesForPretext(pretextRules);

        rules.add(
                Rule.builder()
                        .ruleName("Слова, требующие Род. или Вин. падеж")
                        .maxDistanceLeft((byte) 0)
                        .maxDistanceRight((byte) 127)
                        .possibleCases(Set.of(
                                MorfologyParameters.Case.GENITIVE,
                                MorfologyParameters.Case.ACCUSATIVE
                        ))
                        .initialFormKeyIds(wordsLoader.getWords().get(WordsDatabase.GENITIVE_AND_ACCUSATIVE_CASES))
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
