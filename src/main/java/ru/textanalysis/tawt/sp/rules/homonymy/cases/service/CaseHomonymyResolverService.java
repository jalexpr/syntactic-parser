package ru.textanalysis.tawt.sp.rules.homonymy.cases.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.ms.model.sp.Word;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.rules.Rule;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.rules.RulesForCaseHomonymy;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.utils.TestUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaseHomonymyResolverService {

    private int countOfResolvedWords = 0;
    private int countOfUnmodifiedWords = 0;

    public void resolveForSentence(Sentence sentence) {
        RulesForCaseHomonymy rules = new RulesForCaseHomonymy();

        for (BearingPhrase bearingPhrase : sentence.getBearingPhrases()) {
            processResearching(rules.getPretextRules(), bearingPhrase.getWords()); // Модель управления для предлогов

            processResearching(rules.getRules(), bearingPhrase.getWords()); // Модель управления
//            processCommonAlgorithm(bearingPhrase); // Общее правило для группы подлежащего и второстепенных членов
        }

        log.info("Решенных слов: {}", countOfResolvedWords);
        log.info("Слов, которым решение не потребовалось: {}", countOfUnmodifiedWords);
    }

    private void processCommonAlgorithm(BearingPhrase bearingPhrase) {
        if ( // Если есть группа подлежащего TODO, сейчас просто проверка главного слова
                isExactlyPoS(bearingPhrase.getMainWord(), MorfologyParameters.TypeOfSpeech.NOUN)
                &&
                isInitialFormExist(bearingPhrase.getMainWord())
        ) {
            keepOnlyInitialWordForm(List.of(bearingPhrase.getMainWord()));
            keepOnlyDerivativesWordForms(bearingPhrase, List.of(bearingPhrase.getMainWord()));
        }
    }

    /**
     * У данной словоформы существует начальная форма слова.
     * @param word Объект слова.
     * @return Есть начальная форма.
     */
    private boolean isInitialFormExist(Word word) {
        return word.getForms().stream()
                .anyMatch(Form::isInitialForm);
    }

    /**
     * Данная словоформа однозначно существительное.
     * @param word Объект слова.
     * @return Точно существительное.
     */
    private boolean isExactlyPoS(Word word, byte PoS) {
        if (word.isOnlyOneTypeOfSpeech()) {
            return word.getForms().get(0).getTypeOfSpeech() == PoS;
        }
        return false;
    }

    /**
     * Убрать у всех слов существительных опорного оборота, кроме excludedWords, начальные формы.
     * @param bearingPhrase Опорный оборот.
     * @param excludedWords Слова исключения (подразумевается группа подлежащего).
     */
    private void keepOnlyDerivativesWordForms(BearingPhrase bearingPhrase, List<Word> excludedWords) {
        bearingPhrase.getWords().stream()
                .filter(word -> word.getForms().get(0).getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.NOUN)
                .filter(word -> !word.isOnlyOneForm())
                .filter(word -> !excludedWords.contains(word))
                .forEach(word -> {
                    List<Form> derivativeForms = word.getForms().stream()
                            .filter(form -> !form.isInitialForm())
                            .collect(Collectors.toList());
                    if (!derivativeForms.isEmpty()) {
                        countOfResolvedWords++;
                        word.setForms(derivativeForms);
                    }
                });
    }

    /**
     * Оставить только начальные формы слов из группы подлежащего.
     * @param subjectGroup Слова из группы подлежащего.
     */
    private void keepOnlyInitialWordForm(List<Word> subjectGroup) {
        for (Word word : subjectGroup) {
            List<Form> initialForms = word.getForms().stream()
                    .filter(Form::isInitialForm)
                    .collect(Collectors.toList());
            if (!initialForms.isEmpty()) {
                countOfResolvedWords++;
                word.setForms(initialForms);
            }
        }
    }

    private void processResearching(List<Rule> rules, List<Word> words) {
        for (Rule rule : rules) {
            boolean isControlWordFound = false; // управляющее слово найдено
            Word controlWordForLogging = null; // управляющее слово для логирования
            for (Word word : words) {
                // Если найдено управляющее слово
                if (isControlWordFound) {
                    // Если после управляющего слова встречается предлог перед существительным, то
                    // сбрасываем флаг, что управляющее слово найдено и переходим к следующему слову опорного оборота
                    if (word.getForms().get(0).getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.PRETEXT) {
                        isControlWordFound = false;
                        continue;
                    }
                    // Если после управляющего слова встречаем существительное, то вызываем алгоритм
                    // снятия омонимии для данного зависимого слова и соответствующего правила
                    if (word.getForms().get(0).getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.NOUN) {
                        resolveHomonymy(word, rule, controlWordForLogging);
                        isControlWordFound = false;
                    }
                }
                // Если в списке слов из правила есть текущее слово из предложения
                if (rule.initialFormKeyIds().contains(word.getForms().get(0).getInitialFormKey())) {
                    isControlWordFound = true; // управляющее слово найдено
                    controlWordForLogging = word;
                }
            }
        }
    }

    private void resolveHomonymy(Word word, Rule rule, Word controlWord) {
        List<Form> correctForms = new ArrayList<>();
        for (Form form : word.getForms()) {
            if (
                    rule.possibleCases().contains(
                            form.getMorfCharacteristicsByIdentifier(MorfologyParameters.Case.IDENTIFIER)
                    )
            ) {
                correctForms.add(form);
            }
        }

        if (!correctForms.isEmpty() && word.getForms().size() != correctForms.size()) {
            TestUtils.StaticLogger.logApplyingRule(word, rule, controlWord);
            TestUtils.StaticLogger.logWordFormsDiff(word, correctForms);
            word.setForms(correctForms);
            countOfResolvedWords++;
        } else {
            countOfUnmodifiedWords++;
        }
    }
}
