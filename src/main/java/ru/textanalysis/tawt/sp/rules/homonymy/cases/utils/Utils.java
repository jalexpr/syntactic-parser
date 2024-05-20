package ru.textanalysis.tawt.sp.rules.homonymy.cases.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.ms.model.sp.Word;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.rules.Rule;

import lombok.extern.slf4j.Slf4j;

/**
 * Служебный класс для тестирования алгоритмов снятия омонимии.
 */
@Slf4j
public abstract class Utils {
    /**
     * Класс для логирования результатов.
     */
    public static class StaticLogger {
        /**
         * Метод для логирования результатов снятия омонимии со слова.
         * @param word Исходное слово с которого снимается омонимия.
         * @param correctForms Список корректных морфологических наборов (форм) слова после снятия омонимии.
         */
        public static void logWordFormsDiff(Word word, List<Form> correctForms) {
            log.debug("Результаты снятия омонимии со слова: {" + word.getForms().get(0).getMyString() + "}");
            log.debug("Current forms = " + word.getForms().size() + "\n" + word.getForms());
            log.debug("New forms = " + correctForms.size() + "\n" + correctForms);
        }
        /**
         * Логирование применяемого правила для снятия омонимии.
         * @param word Зависимое слово.
         * @param rule Правило снятия омонимии.
         * @param controlWord Главное слово.
         */
        public static void logApplyingRule(Word word, Rule rule, Word controlWord) {
            log.info("Главное слово: " + controlWord.getForms().get(0).getMyString());
            log.info("Зависимое слово: " + word.getForms().get(0).getMyString());
            log.info("Правило: " + rule.ruleName());
        }
    }

    /**
     * Калькулятор.
     */
    public static class Calculator {
        public static final String TOTAL_PERCENTAGE = "TOTAL";
        public static final String CASE_PERCENTAGE = "CASE";

        /**
         * Метод для получения процента однозначных слов в предложении.
         * @param sentence Предложение.
         * @return Процент однозначных слов.
         */
        public static Map<String, BigDecimal> getHomonymyPercentageFromSentence(Sentence sentence) {
            Map<String, BigDecimal> nameToPercentageMap = new HashMap<>();

            BigDecimal totalSentenceWords = BigDecimal.ZERO;
            BigDecimal numberOfSingleDigitForms = BigDecimal.ZERO;
            BigDecimal numberOfSingleDigitCaseForms = BigDecimal.ZERO;
            BigDecimal totalPercentage;
            BigDecimal casePercentage;

            for (BearingPhrase bearingPhrase : sentence.getBearingPhrases()) {
                int phraseWordCount = bearingPhrase.getWords().size();
                totalSentenceWords = totalSentenceWords.add(new BigDecimal(phraseWordCount));

                for (Word word : bearingPhrase.getWords()) {
                    if (word.isOnlyOneForm()) {
                        numberOfSingleDigitForms = numberOfSingleDigitForms.add(BigDecimal.ONE);
                    }
                    Set<Long> cases = new HashSet<>();
                    for (Form form : word.getForms()) {
                        cases.add(form.getMorfCharacteristicsByIdentifier(MorfologyParameters.Case.IDENTIFIER));
                    }
                    if (cases.size() == 1) {
                        // Падежная неоднозначность снята
                        numberOfSingleDigitCaseForms = numberOfSingleDigitCaseForms.add(BigDecimal.ONE);
                    }
                }
            }

            // Проверяем, чтобы избежать деления на ноль
            if (!totalSentenceWords.equals(BigDecimal.ZERO)) {
                totalPercentage = numberOfSingleDigitForms.divide(totalSentenceWords, 4, RoundingMode.HALF_UP);
                casePercentage = numberOfSingleDigitCaseForms.divide(totalSentenceWords, 4, RoundingMode.HALF_UP);

                nameToPercentageMap.put(TOTAL_PERCENTAGE, totalPercentage);
                nameToPercentageMap.put(CASE_PERCENTAGE, casePercentage);
            }

            return nameToPercentageMap;
        }
    }

    /**
     * Служебный класс для чтения файлов.
     */
    public static class FileReader {
        public static String readLargeFileAsString(String path) {
            Path fullPath;
            try {
                fullPath = Paths.get(Objects.requireNonNull(FileReader.class.getClassLoader().getResource(path)).toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            StringBuilder contentBuilder = new StringBuilder();

            try {
                Files.lines(Paths.get(fullPath.toUri()), StandardCharsets.UTF_8).forEach(line -> {
                    contentBuilder.append(line).append("\n");
                });
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return contentBuilder.toString();
        }
        /**
         * Получение слов из файла.
         * @param path Путь к файлу.
         * @return Множество строковых представлений слов.
         */
        public static Set<String> readFromPath(String path) {
            Set<String> wordsSet = new HashSet<>();
            try (InputStream inputStream = FileReader.class.getClassLoader().getResourceAsStream(path)) {
                assert inputStream != null;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String cleanLine = line.replaceAll("[0-9.\\s]+", "");
                        if (!cleanLine.isEmpty()) {
                            wordsSet.add(cleanLine);
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return wordsSet;
        }
    }
}
