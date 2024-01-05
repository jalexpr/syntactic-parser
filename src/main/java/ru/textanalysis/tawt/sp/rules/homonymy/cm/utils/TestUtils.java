package ru.textanalysis.tawt.sp.rules.homonymy.cm.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.ms.model.sp.Word;

import lombok.extern.slf4j.Slf4j;

/**
 * Служебный класс для тестирования алгоритмов снятия омонимии.
 */
@Slf4j
public abstract class TestUtils {
    /**
     * Класс с тестовыми предложениями.
     */
    public static class Sentences {
        public static final String S_1 =
                "Утром, перед выходом из дома, Андрей посмотрел в окно и увидел, что идет снег. " +
                        "По дороге на работу он зашел в кафе для быстрого завтрака. " +
                        "В офисе он сразу приступил к работе над проектом, " +
                        "но вскоре отвлекся на разговор с коллегой о последних новостях. " +
                        "После обеда он отправился в библиотеку за новыми книгами по маркетингу. " +
                        "Вечером, возвращаясь домой, он мечтал о предстоящем отпуске на море.";
        public static final String S_2 =
                "Изысканный стиль одежды архитектора, неординарные идеи дизайнера интерьеров, " +
                        "глубокие знания психолога о человеческом поведении, " +
                        "сложные программы разработчика ПО и тщательно продуманные эксперименты ученого биолога " +
                        "внесли значительный вклад в успешное завершение международного исследовательского проекта.";
        public static final String S_3 =
                "Видеть звёзды на ночном небе было необычайно приятно, " +
                        "ведь они могли слышать шум деревьев даже за кустами. " +
                        "Ощущать холод и чувствовать запах было для нас плохой затеей.";
        public static final String S_4 =
                "Пока она читала старинную книгу, найденную на чердаке, её ум погружался в таинственный мир прошлого, где каждая страница рассказывала невероятные истории о далеких землях. " +
                        "Когда маленький мальчик увидел море впервые, его глаза расширились от восхищения, и он, забыв обо всём на свете, побежал к волнам, чтобы ощутить их холодную пену на своих ногах. " +
                        "Профессор, который много лет посвятил изучению экзотических языков, с удивлением открыл для себя, что некоторые древние слова звучат удивительно современно, создавая мост между прошлым и настоящим. " +
                        "В тот вечер, когда они встретились после долгих лет разлуки, друзья делились воспоминаниями о своей юности, смеялись, плакали и обнимали друг друга, словно пытаясь наверстать упущенное время. " +
                        "После долгих лет борьбы с трудностями, она наконец-то добилась своей мечты - открыть собственную галерею искусств, где каждая картина рассказывала уникальную историю, отражая её безграничную страсть к живописи." +
                        "Наука требует не только упорного труда и постоянного стремления к знаниям, но и умения удивляться новым открытиям, которые порой полностью меняют представление о мире.";
        public static final String S_5 =
                "Каждый вечер после работы ужин готовит она отличный, вдыхая ароматы специй, которые наполняют кухню уютом и теплом." +
                        "Маленький мальчик восхищенно смотрит на звезды, мечтая стать космонавтом и исследовать неизведанные галактики." +
                        "Опытный садовник ухаживает за розами, внимательно подрезая каждый побег, чтобы обеспечить им лучшее цветение.";

    }

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
    }

    /**
     * Калькулятор.
     */
    public static class Calculator {
        /**
         * Метод для получения процента однозначных слов в предложении.
         * @param sentence Предложение.
         * @return Процент однозначных слов.
         */
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

    /**
     * Служебный класс для чтения файлов.
     */
    public static class FileReader {
        /**
         * Чтение содержимого текстового файла и возвращение его как одной строки.
         * @param path Путь к файлу.
         * @return Строка с содержимым файла.
         */
        public static String readAsString(String path) {
            try {
                // Чтение всех строк файла и объединение их в одну строку
                return new String(Files.readAllBytes(Paths.get(path)));
            } catch (IOException e) {
                e.printStackTrace();
                return null; // Возвращаем null или можно кинуть исключение в зависимости от требований к обработке ошибок
            }
        }
        public static String readLargeFileAsString(String path) {
            StringBuilder contentBuilder = new StringBuilder();

            try {
                Files.lines(Paths.get(path), StandardCharsets.UTF_8).forEach(line -> {
                    contentBuilder.append(line).append("\n"); // Добавление строки с новой строки
                });
            } catch (IOException e) {
                e.printStackTrace();
                return null; // Или обработать исключение по-другому
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
