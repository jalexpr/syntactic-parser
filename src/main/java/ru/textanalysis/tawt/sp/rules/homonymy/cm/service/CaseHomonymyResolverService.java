package ru.textanalysis.tawt.sp.rules.homonymy.cm.service;

import java.util.ArrayList;
import java.util.List;

import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.ms.model.sp.Word;
import ru.textanalysis.tawt.sp.rules.homonymy.cm.rules.Rule;
import ru.textanalysis.tawt.sp.rules.homonymy.cm.rules.RulesForCaseHomonymy;
import ru.textanalysis.tawt.sp.rules.homonymy.cm.utils.TestUtils;

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
        boolean isControlWordFound = false; // управляющее слово найдено
        for (Rule rule : rules) {
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
                        resolveHomonymy(word, rule);
                        isControlWordFound = false;
                    }
                }
                // Если в списке слов из правила есть текущее слово из предложения
                if (rule.initialFormKeyIds().contains(word.getForms().get(0).getInitialFormKey())) {
                    isControlWordFound = true; // управляющее слово найдено
                }
            }
        }
    }

    private void resolveHomonymy(Word word, Rule rule) {
        log.info("Слово: " + word.getForms().get(0).getInitialFormString());
        log.info("Правило: " + rule.ruleName());

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
            TestUtils.StaticLogger.logWordFormsDiff(word, correctForms);
            word.setForms(correctForms);
            countOfResolvedWords++;
        } else {
            log.info("После работы алгоритма формы слова {" + word.getForms().get(0).getMyString() + "} не изменились");
            countOfUnmodifiedWords++;
        }
    }
}
