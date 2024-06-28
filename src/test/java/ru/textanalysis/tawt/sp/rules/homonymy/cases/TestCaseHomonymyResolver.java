package ru.textanalysis.tawt.sp.rules.homonymy.cases;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.sp.api.SyntaxParser;
import ru.textanalysis.tawt.sp.rules.homonymy.cases.utils.Utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCaseHomonymyResolver {

    private static final SyntaxParser sp = new SyntaxParser();
    private static final boolean LOGGER_ENABLED = false;
    private static final String PATH_TO_TEXT_FROM_RESOURCES =
            "books/Frolov_I__Vvedenie_v_filosofiu_www.Litmir.net_75763.txt";

    @BeforeAll
    public static void init() {
        sp.init();
    }

    @Test
    public void testAllTexts() {
        if (!LOGGER_ENABLED) {
            ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).setLevel(Level.OFF);
        }

        long startTime = System.currentTimeMillis();

        try {
            Sentence sentence = sp.getTreeSentence(Utils.FileReader.readLargeFileAsString(PATH_TO_TEXT_FROM_RESOURCES));
            BigDecimal percentageFull = Utils.Calculator
                    .getHomonymyPercentageFromSentence(sentence).get(
                            Utils.Calculator.TOTAL_PERCENTAGE
                    );
            BigDecimal percentageCase = Utils.Calculator
                    .getHomonymyPercentageFromSentence(sentence).get(
                            Utils.Calculator.CASE_PERCENTAGE
                    );
            log.info("--------------------------------------------------------------------------------");
            log.info("РЕЗУЛЬТАТ полный: Процент после: {" + percentageFull + "}.");
            log.info("РЕЗУЛЬТАТ падежный: Процент после: {" + percentageCase + "}.");
            log.info("--------------------------------------------------------------------------------");
            System.out.println("Full percentage: " + percentageFull);
            System.out.println("Case percentage: " + percentageCase);
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            log.info("Execution time of testAllTexts: " + duration + " ms");
            System.out.println("Execution time: " + duration);
        }
    }
}
