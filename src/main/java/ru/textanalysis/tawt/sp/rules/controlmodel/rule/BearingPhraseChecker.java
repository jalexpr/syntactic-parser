package ru.textanalysis.tawt.sp.rules.controlmodel.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Word;
import ru.textanalysis.tawt.sp.rules.controlmodel.utils.StaticLogger;

public class BearingPhraseChecker {

    public static void processBearingPhraseDefault(
            BearingPhrase bearingPhrase, boolean flag,
            Set<Integer> ruleInitFormKeys, List<Long> possibleCases
    ) {
        for (Word word : bearingPhrase.getWords()) {
            if (word.getForms().isEmpty()) continue;
            if (flag) {
                if (word.getForms().get(0).getTypeOfSpeech() == MorfologyParameters.TypeOfSpeech.NOUN) {
                    List<Form> correctForms = new ArrayList<>();
                    for (Form form : word.getForms()) {
                        if (
                                possibleCases.contains(
                                        form.getMorfCharacteristicsByIdentifier(MorfologyParameters.Case.IDENTIFIER)
                                )
                        ) {
                            correctForms.add(form);
                        }
                    }

                    if (!correctForms.isEmpty() && word.getForms().size() != correctForms.size()) {
                        StaticLogger.printWordFormsDiff(word.getForms(), correctForms);
                        word.setForms(correctForms);
                    }

                    flag = false;
                }
                continue;
            }
            if (ruleInitFormKeys.contains(word.getForms().get(0).getInitialFormKey())) {
                flag = true;
            }
        }
    }
}
