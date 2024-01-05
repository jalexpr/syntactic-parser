package ru.textanalysis.tawt.sp.rules.homonymy.cm.keywords;

public enum WordsDatabase {

    GENITIVE_AND_ACCUSATIVE_CASES("rules/words_databases/genitive_and_accusative_cases.txt"),

    // Слова предлоги.
    PRETEXT_DAT_CASE("rules/pretext/dat_case.txt"),
    PRETEXT_DAT_PRED_CASE("rules/pretext/dat_pred_case.txt"),
    PRETEXT_PRED_CASE("rules/pretext/pred_case.txt"),
    PRETEXT_ROD_CASE("rules/pretext/rod_case.txt"),
    PRETEXT_ROD_TVOR_CASE("rules/pretext/rod_tvor_case.txt"),
    PRETEXT_TVOR_CASE("rules/pretext/tvor_case.txt"),
    PRETEXT_VIN_CASE("rules/pretext/vin_case.txt"),
    PRETEXT_VIN_PRED_CASE("rules/pretext/vin_pred_case.txt"),
    PRETEXT_VIN_TVOR_CASE("rules/pretext/vin_tvor_case.txt");

    private final String pathToFile;
    WordsDatabase(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getPath() {
        return this.pathToFile;
    }
}
