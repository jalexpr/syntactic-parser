package ru.textanalysis.tawt.sp.rules.homonymy.cm;

import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.sp.api.SyntaxParser;
import ru.textanalysis.tawt.sp.rules.homonymy.cm.utils.TestUtils;

/**
 * Класс для тестирования результатов снятия омонимии через алгоритм, основанный на модели управления.
 */
public class HomonymyControlModelApplication {

    public static void main(String[] args) {
        SyntaxParser sp = new SyntaxParser();
        sp.init();

        Sentence book1 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("D:\\tawt\\syntactic-parser\\src\\main\\resources\\books\\book1.txt"));
//        Sentence text1 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("D:\\tawt\\syntactic-parser\\src\\main\\resources\\texts\\text1.txt"));
//        Sentence text2 = sp.getTreeSentence(TestUtils.FileReader.readLargeFileAsString("D:\\tawt\\syntactic-parser\\src\\main\\resources\\texts\\text2.txt"));

//        Sentence sentenceTest = sp.getTreeSentence("Дедушкина мудрость удивила сына");
//        Sentence sentence3 = sp.getTreeSentence("Солнце село за село.");
//        Sentence sentence1 = sp.getTreeSentence(TestUtils.Sentences.S_1);
//        Sentence sentence7 = sp.getTreeSentence(TestUtils.Sentences.S_2);
//        Sentence sentence2 = sp.getTreeSentence(TestUtils.Sentences.S_3);
//        Sentence sentence4 = sp.getTreeSentence(TestUtils.Sentences.S_4);
//        Sentence sentence5 = sp.getTreeSentence(TestUtils.Sentences.S_5);
//        Sentence sentence6 = sp.getTreeSentence("Машины видел старый дед");
//        Sentence emotionalVerbsSentence1 = sp.getTreeSentence("Она гордилась достижениями своего сына, который успешно окончил университет." +
//                "Он удивлялся мудрости своего дедушки, который всегда находил верные слова." +
//                "Мы восхищались красотой заката, расцветавшего невероятными красками над горизонтом." +
//                "Она боялась грозы, которая приближалась с каждой минутой всё ближе." +
//                "Он надеялся на лучшее, несмотря на все трудности, с которыми ему приходилось столкнуться." +
//                "Мы радовались каждой встрече с друзьями, ведь это были моменты истинного счастья." +
//                "Она сожалела о потерянных возможностях, которые так и остались в прошлом." +
//                "Он сердился на себя за совершенные ошибки, но знал, что это был опыт." +
//                "Они завидовали успехам своих коллег, стремясь достичь таких же высот в карьере." +
//                "Я восхищаюсь талантом этого писателя, каждое его произведение — настоящее произведение искусства.");
//        Sentence stateVerbsSentence1 = sp.getTreeSentence("Он был удивлён результатами эксперимента, который перевернул его представление о физике." +
//                "Она оказалась вдохновлена искусством великих мастеров, посетив мировые художественные галереи." +
//                "Мы были ошеломлены красотой природы, когда впервые увидели горы." +
//                "Она оказалась поражена мудростью своего учителя, который дал ей ценный совет." +
//                "Они были обескуражены неожиданным поворотом событий в своей жизни." +
//                "Я был восхищен талантом молодого пианиста, его игра была великолепна." +
//                "Она была удивлена открытием, которое совершила во время своих исследований." +
//                "Они были разочарованы результатами матча, ожидая другого исхода игры." +
//                "Он был взволнован предстоящей встречей, которая могла изменить его карьеру." +
//                "Мы были покорены величием этого древнего замка, стоящего на холме.");
    }
}
