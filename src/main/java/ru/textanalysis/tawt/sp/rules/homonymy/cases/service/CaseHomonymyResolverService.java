package ru.textanalysis.tawt.sp.rules.homonymy.cases.service;

import java.util.ArrayList;
import java.util.List;

import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.ms.model.sp.Word;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.rules.Rule;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.rules.RulesForCaseHomonymy;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaseHomonymyResolverService {

    private int countOfResolvedWords = 0;
    private int countOfUnmodifiedWords = 0;

    public void resolveForSentence(Sentence sentence) {
        RulesForCaseHomonymy rules = new RulesForCaseHomonymy();

        for (BearingPhrase bearingPhrase : sentence.getBearingPhrases()) {
            processResearching(rules.getPretextRules(), bearingPhrase.getWords());
            processResearching(rules.getRules(), bearingPhrase.getWords());
        }

        log.info("Решенных слов: {}", countOfResolvedWords);
        log.info("Слов, которым решение не потребовалось: {}", countOfUnmodifiedWords);
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
                        continue;
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
            Utils.StaticLogger.logApplyingRule(word, rule, controlWord);
            Utils.StaticLogger.logWordFormsDiff(word, correctForms);
            word.setForms(correctForms);
            countOfResolvedWords++;
        } else {
            countOfUnmodifiedWords++;
        }
    }
}
