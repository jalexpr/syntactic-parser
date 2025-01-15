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

public class CaseHomonymyResolverService {

    public void resolveForSentence(Sentence sentence) {
        RulesForCaseHomonymy rules = new RulesForCaseHomonymy();

        for (BearingPhrase bearingPhrase : sentence.getBearingPhrases()) {
            processResearching(rules.getPretextRules(), bearingPhrase.getWords());
            processResearching(rules.getRules(), bearingPhrase.getWords());
        }
    }

    private void processResearching(List<Rule> rules, List<Word> words) {
        Word controlWord = null;
        Rule currentUsingRule = null;
        for (Word word : words) {
            byte wordTypeOfSpeech = word.getForms().get(0).getTypeOfSpeech();

            if (controlWord != null) {
                // Если после управляющего слова встречается предлог перед существительным, то
                // сбрасываем флаг, что управляющее слово найдено и переходим к следующему слову опорного оборота
                if (wordTypeOfSpeech == MorfologyParameters.TypeOfSpeech.PRETEXT
                || wordTypeOfSpeech == MorfologyParameters.TypeOfSpeech.VERB) {
                    controlWord = null;
                    currentUsingRule = null;
                }

                // Если после управляющего слова встречаем существительное, то вызываем алгоритм
                // снятия омонимии для данного зависимого слова и соответствующего правила
                if (wordTypeOfSpeech == MorfologyParameters.TypeOfSpeech.NOUN) {
                    resolveHomonymy(word, currentUsingRule);
                    controlWord = null;
                    currentUsingRule = null;
                }
            }

            if (controlWord == null) {
                for (Rule rule : rules) {
                    // Если в списке слов из правила есть текущее слово из предложения
                    if (rule.initialFormKeyIds().contains(word.getForms().get(0).getInitialFormKey())) {
                        controlWord = word; // управляющее слово найдено
                        currentUsingRule = rule;
                        break;
                    }
                }
            }
        }
    }

    private void resolveHomonymy(Word dependentWord, Rule rule) {
        List<Form> correctForms = new ArrayList<>();
        for (Form form : dependentWord.getForms()) {
            if (
                    rule.possibleCases().contains(
                            form.getMorfCharacteristicsByIdentifier(MorfologyParameters.Case.IDENTIFIER)
                    )
            ) {
                correctForms.add(form);
            }
        }

        if (!correctForms.isEmpty() && dependentWord.getForms().size() != correctForms.size()) {
            dependentWord.setForms(correctForms);
        }
    }
}
