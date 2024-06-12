package ru.textanalysis.tawt.sp.rules.homonymy.cases.keywords;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.utils.Utils;

import lombok.Getter;

/**
 * Загрузчик слов из словарей в соответствующую хэш-мапу.
 */
@Getter
public class WordsLoader {

    @Getter
    private final Map<WordsDatabase, Set<Integer>> words;
    private final JMorfSdk jMorfSdk;

    public WordsLoader() {
        this.jMorfSdk = JMorfSdkFactory.loadFullLibrary();
        this.words = loadWords();
    }

    /**
     * Метод загрузки слов в структуру данных.
     * @return мапа, где ключ - словарь управляющих слов, а значение - множество идентификаторов слов.
     */
    private Map<WordsDatabase, Set<Integer>> loadWords() {
        Map<WordsDatabase, Set<Integer>> words = new HashMap<>();
        for (WordsDatabase database : WordsDatabase.values()) {
            Set<String> wordSet = Utils.FileReader.readFromPath(database.getPath());
            Set<Integer> formKeys = getWordsFormKeys(wordSet);
            words.put(database, formKeys);
        }
        return words;
    }

    /**
     * Метод получения InitialFormKey для множества слов из словаря.
     * @param words множество слов из словаря для конкретного правила.
     * @return множество ключей начальной формы для данных слов.
     */
    private Set<Integer> getWordsFormKeys(Set<String> words) {
        Set<Integer> formKeys = new HashSet<>();

        for (String word : words) {
            List<Form> wordForms = jMorfSdk.getOmoForms(word.toLowerCase());
            if (wordForms.isEmpty()) continue;
            int formKey = wordForms.get(0).getInitialFormKey();
            formKeys.add(formKey);
        }

        return formKeys;
    }
}
