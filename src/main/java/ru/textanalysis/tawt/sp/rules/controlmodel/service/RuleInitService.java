package ru.textanalysis.tawt.sp.rules.controlmodel.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.textanalysis.tawt.sp.rules.controlmodel.constants.WordsDatabase;
import ru.textanalysis.tawt.sp.rules.controlmodel.rule.Rule;

public interface RuleInitService {

    /**
     * Получить карту правил и множества слов к ним.
     * @param rulePaths пути к словам, используемым в правилах.
     * @return Map. Ключ - правило, значение - слова, участвующие в снятии омонимии по данному правилу.
     */
    Map<Rule, Set<Integer>> getRulesToInitFormKeys(List<WordsDatabase> rulePaths);
}
