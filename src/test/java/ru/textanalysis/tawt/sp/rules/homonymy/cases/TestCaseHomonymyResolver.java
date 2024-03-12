package ru.textanalysis.tawt.sp.rules.homonymy.cases;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.sp.api.SyntaxParser;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.utils.TestUtils;

public class TestCaseHomonymyResolver {

    private static SyntaxParser sp = new SyntaxParser();

    @BeforeAll
    public static void init() {
        sp.init();
    }

    @Test
    public void testAllTexts() {
//        Sentence book1 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("books/book1.txt"));
//        Sentence text1 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("texts/text1.txt"));
//        Sentence text2 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("texts/text2.txt"));
//        Sentence text3 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("texts/news_09_03.txt"));
//        Sentence text4 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("books/kd/part1.txt"));

        Sentence book1 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("books/dead_souls.txt"));
        Sentence book2 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("books/princess_mary.txt"));
        Sentence book3 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("books/dog_heart.txt"));
        Sentence book4 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("books/man_into.txt"));
        Sentence news1 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("texts/news1.txt"));
        Sentence skazka1 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("books/car_cyltan.txt"));
        Sentence skazka2 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("books/neznakomka.txt"));

        // Сравнительный анализ
        Sentence comparing1 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("texts/visual_verbs_1.txt"));
        Sentence comparing2 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("texts/derived_nouns_from_verbs.txt"));
        Sentence comparing3 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("texts/fear_verbs.txt"));
        Sentence comparing4 = sp.getTreeSentence("Проплыв сто метров от берега можно увидеть плавание дельфинов.");
    }
}
