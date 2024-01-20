package ru.textanalysis.tawt.sp.rules.controlmodel.constants;

public enum WordsDatabase {

    NOUNS_PERSON_WORDS("words_databases/nouns_person.txt"),
    VERBS_OF_SENSORY_WORDS("words_databases/verbs_of_sensory_perception.txt"),

    // Слова предлоги.
    PRETEXT_DAT_CASE("pretext/dat_case.txt"),
    PRETEXT_DAT_PRED_CASE("pretext/dat_pred_case.txt"),
    PRETEXT_PRED_CASE("pretext/pred_case.txt"),
    PRETEXT_ROD_CASE("pretext/rod_case.txt"),
    PRETEXT_ROD_TVOR_CASE("pretext/rod_tvor_case.txt"),
    PRETEXT_TVOR_CASE("pretext/tvor_case.txt"),
    PRETEXT_VIN_CASE("pretext/vin_case.txt"),
    PRETEXT_VIN_PRED_CASE("pretext/vin_pred_case.txt"),
    PRETEXT_VIN_TVOR_CASE("pretext/vin_tvor_case.txt");

    private final String pathToFile;
    WordsDatabase(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getPath() {
        return this.pathToFile;
    }
}
