package ru.textanalysis.tawt.sp.rules.homonymy.cases.keywords;

/**
 * Пути к файлам(словарям) управляющих(главных) слов.
 */
public enum WordsDatabase {
    // Слова предлоги.
    PRETEXT_DAT_CASE("rules/pretext/dat_case.txt"),
    PRETEXT_DAT_PRED_CASE("rules/pretext/dat_pred_case.txt"),
    PRETEXT_PRED_CASE("rules/pretext/pred_case.txt"),
    PRETEXT_ROD_CASE("rules/pretext/rod_case.txt"),
    PRETEXT_ROD_TVOR_CASE("rules/pretext/rod_tvor_case.txt"),
    PRETEXT_TVOR_CASE("rules/pretext/tvor_case.txt"),
    PRETEXT_VIN_CASE("rules/pretext/vin_case.txt"),
    PRETEXT_VIN_PRED_CASE("rules/pretext/vin_pred_case.txt"),
    PRETEXT_VIN_TVOR_CASE("rules/pretext/vin_tvor_case.txt"),
    // Слова глаголы.
    GOAL_VERBS("rules/verb/goal_verbs.txt"),
    VISUAL_VERBS("rules/verb/visual_verbs.txt"),
    DERIVED_NOUNS_FROM_VERBS("rules/verb/nouns_derived_from_transitive_verbs.txt"),
    FEAR_VERBS("rules/verb/fear_verbs.txt");

    private final String pathToFile;
    WordsDatabase(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getPath() {
        return this.pathToFile;
    }
}
