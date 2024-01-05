package ru.textanalysis.tawt.sp.rules.controlmodel.rule;

import java.util.Set;

import ru.textanalysis.tawt.ms.model.sp.Sentence;

/**
 * Правило для снятия омонимии.
 */
public interface Rule {

    /**
     * Разрешить омонимию для конкретного предложения.
     * @param sentence предложение.
     * @param ruleInitFormKeys initFormKeys слов для отдельного правила.
     */
    void resolveHomonymyForSentence(Sentence sentence, Set<Integer> ruleInitFormKeys);
}
