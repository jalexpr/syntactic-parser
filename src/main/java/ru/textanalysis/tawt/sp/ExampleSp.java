package ru.textanalysis.tawt.sp;

import ru.textanalysis.tawt.ms.external.sp.BearingPhraseExt;
import ru.textanalysis.tawt.ms.internal.sp.BearingPhraseSP;
import ru.textanalysis.tawt.sp.api.SyntaxParser;

import java.util.List;

public class ExampleSp {
    public static void main(String[] args) {

//        BearingPhraseSP phrase1 = sp.getTreeSentence("Солнце село за село.");
//        System.out.println(phrase1);
//
        SyntaxParser sp = new SyntaxParser();
        sp.init();
        List<BearingPhraseSP> phrase1
                = sp.getTreeSentence("рецепт этого средства невероятно прост достаточно взять две ложки мёда и смешать с таким же количеством белой глины и будет пррпррпррпрр");
        phrase1.forEach(System.out::println);

        List<BearingPhraseSP> phrase
                = sp.getTreeSentence("Сегодня cтало ясно, что будет с российской валютой и евро");
        phrase.forEach(System.out::println);

        phrase
                = sp.getTreeSentence("Солнце село за село");
        phrase.forEach(System.out::println);

//        long start;
//        long end;
//
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10000 * (i + 1); j++) {
//                sp.getTreeSentence("Сегодня cтало ясно, что будет с российской валютой и евро");
//            }
//        }
//        for (int i = 0; i < 10; i++) {
//            start = System.currentTimeMillis();
//            for (int j = 0; j < 10000 * (i + 1); j++) {
//                sp.getTreeSentence("Сегодня cтало ясно, что будет с российской валютой и евро");
//            }
//            end = System.currentTimeMillis();
//            System.out.println(end - start);
//        }


        String sent = "Солнце село за село. Необходимость автоматической обработки предложения и составления графа " +
                "зависимости обусловлена тем, что для выделения понятий необходим анализ большого " +
                "количества предложений, что при ручной обработке заняло бы значительное количество " +
                "времени.";
        System.out.println(sent + ":");
        List<BearingPhraseExt> exts = sp.getTreeSentenceWithoutAmbiguity(sent);
        exts.forEach(bearingPhraseExt -> {
            System.out.println(bearingPhraseExt.getMainOmoForms());
        });
    }
}
