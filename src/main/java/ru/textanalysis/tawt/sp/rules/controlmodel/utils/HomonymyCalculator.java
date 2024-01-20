package ru.textanalysis.tawt.sp.rules.controlmodel.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.ms.model.sp.Word;

public class HomonymyCalculator {

    public static BigDecimal getHomonymyPercentageFromSentence(Sentence sentence) {
        BigDecimal totalSentenceWords = BigDecimal.ZERO;
        BigDecimal numberOfSingleDigitForms = BigDecimal.ZERO;
        BigDecimal percentage = BigDecimal.ZERO;

        for (BearingPhrase bearingPhrase : sentence.getBearingPhrases()) {
            int phraseWordCount = bearingPhrase.getWords().size();
            totalSentenceWords = totalSentenceWords.add(new BigDecimal(phraseWordCount));

            for (Word word : bearingPhrase.getWords()) {
                if (word.isOnlyOneForm()) {
                    numberOfSingleDigitForms = numberOfSingleDigitForms.add(BigDecimal.ONE);
                }
            }
        }

        // Проверяем, чтобы избежать деления на ноль
        if (!totalSentenceWords.equals(BigDecimal.ZERO)) {
            percentage = numberOfSingleDigitForms.divide(totalSentenceWords, 4, RoundingMode.HALF_UP);
        }

        return percentage;
    }
}
