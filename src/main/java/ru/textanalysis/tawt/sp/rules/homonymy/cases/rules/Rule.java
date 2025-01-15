package ru.textanalysis.tawt.sp.rules.homonymy.cases.rules;

import java.util.Set;

import lombok.Builder;

/**
 * Правило снятия неоднозначности на основе модели управления.
 * @param ruleName Название правила (для логирования).
 * @param maxDistanceLeft Дистанция поиска слева от управляющего слова.
 * @param maxDistanceRight Дистанция поиска справа от управляющего слова.
 * @param possibleCases Возможные падежи зависимого слова.
 * @param initialFormKeyIds Идентификаторы управляющих слов для поиска их в предложении.
 */
@Builder
public record Rule(
        String ruleName,
        byte maxDistanceLeft,
        byte maxDistanceRight,
        Set<Long> possibleCases,
        Set<Integer> initialFormKeyIds
) {
}
