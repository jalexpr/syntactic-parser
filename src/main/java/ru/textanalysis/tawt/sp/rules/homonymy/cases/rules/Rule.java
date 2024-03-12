package ru.textanalysis.tawt.sp.rules.homonymy.cases.rules;

import java.util.Set;

import lombok.Builder;

@Builder
public record Rule(
        String ruleName,
        byte maxDistanceLeft,
        byte maxDistanceRight,
        Set<Long> possibleCases,
        Set<Integer> initialFormKeyIds
) {
}