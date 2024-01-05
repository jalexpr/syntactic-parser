package ru.textanalysis.tawt.sp.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import ru.textanalysis.tawt.awf.AmbiguityWordsFilter;
import ru.textanalysis.tawt.awf.AmbiguityWordsFilterImpl;
import ru.textanalysis.tawt.gama.GamaImpl;
import ru.textanalysis.tawt.ms.convector.GamaToSpConvector;
import ru.textanalysis.tawt.ms.grammeme.ShortBearingForm;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.ms.model.sp.Word;
import ru.textanalysis.tawt.rfc.RulesForCompatibility;
import ru.textanalysis.tawt.rfc.RulesForCompatibilityImpl;
import ru.textanalysis.tawt.sp.rules.homonymy.cm.service.CaseHomonymyResolverService;
import ru.textanalysis.tawt.sp.rules.homonymy.cm.utils.TestUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SyntaxParser implements ISyntaxParser {

	private final GamaImpl gama = new GamaImpl();
	private final AmbiguityWordsFilter awf = new AmbiguityWordsFilterImpl();
	private final RulesForCompatibility rules = new RulesForCompatibilityImpl();
	private final GamaToSpConvector convector = new GamaToSpConvector();

	@Override
	public void init() {
		gama.init();
		awf.init();
		rules.init();
		log.debug("SP is initialized!");
	}

	@Override
	public Sentence getTreeSentence(String text) {
		Sentence sentence = convector.convert(gama.getMorphSentence(text));
		sentence.applyForEachBearingPhrases(awf::applyAwfForBearingPhrase);
		sentence.applyForEachBearingPhrases(this::applyCompatibility);
		sentence.applyForEachBearingPhrases(this::applyCompatibilityForBearingForm);
		sentence.applyForEachBearingPhrases(this::searchMainForm);
		applyCompatibilityForSentence(sentence);
		log.info("Предложение: " + text);
		BigDecimal percentageBefore = TestUtils.Calculator.getHomonymyPercentageFromSentence(sentence);
		processResolvingCaseHomonymyForSentence(sentence);
		BigDecimal percentageAfter = TestUtils.Calculator.getHomonymyPercentageFromSentence(sentence);
		log.info("РЕЗУЛЬТАТ: Процент до: {" + percentageBefore + "}. Процент после: {" + percentageAfter + "}.");
		return sentence;
	}

	private void applyCompatibilityForSentence(Sentence sentence) { //todo привести в полное соответствие со схемой
		List<Word> words = sentence.getBearingPhrases().stream()
			.map(BearingPhrase::getMainWord)
			.collect(Collectors.toList());
		applyCompatibility(words);
		List<Word> mains = words.stream()
			.filter(word -> !word.haveMain())
			.collect(Collectors.toList());
		sentence.setMainWord(mains);
	}

	private void processResolvingCaseHomonymyForSentence(Sentence sentence) {
		CaseHomonymyResolverService service = new CaseHomonymyResolverService();
		service.resolveForSentence(sentence);
	}

	private void applyCompatibility(BearingPhrase bearingPhrase) {
		bearingPhrase.applyConsumer(this::applyCompatibility);
	}

	private void applyCompatibility(List<Word> words) {
		int distance = 4; // todo вынести в настройки?
		int firstIndex = 0;
		int lastIndex = words.size() - 1;
		for (int i = lastIndex; -1 < i; i--) {
			int leftNeighbor = i - 1;
			int rightNeighbor = i + 1;
			Word word = words.get(i);
			if (word.haveContainsBearingForm()) {
				for (int j = Math.max(firstIndex, leftNeighbor); Math.max(firstIndex, leftNeighbor - distance) < j; j--) {
					Word left = words.get(j);
					if (!word.haveRelationship(left)) {
						if (!left.haveContainsBearingForm()) {
							rules.establishRelation(word, left);
						} else {
							break;
						}
					}
				}
				for (int j = Math.min(lastIndex, rightNeighbor); j < Math.min(lastIndex, rightNeighbor + distance); j++) {
					Word right = words.get(j);
					if (!word.haveRelationship(right)) {
						if (!right.haveContainsBearingForm()) {
							rules.establishRelation(word, right);
						} else {
							break;
						}
					}
				}

				//todo возможно другое направление, противоречие в тексте и блок-схеме
				for (int j = Math.min(lastIndex, rightNeighbor); j < Math.min(lastIndex, rightNeighbor + distance); j++) {
					Word right = words.get(j);
					if (!word.haveRelationship(right)) {
						if (!right.haveContainsBearingForm()) {
							word.addDependent(right);
							right.addMain(word);
						} else {
							break;
						}
					}
				}
			}
		}
	}

	private void applyCompatibilityForBearingForm(BearingPhrase bearingPhrase) {
		bearingPhrase.applyConsumer(words -> {
			for (int i = 0; i < words.size(); i++) {
				Word word = words.get(i);
				if (!word.haveMain() && word.haveContainsBearingForm()) {
					for (int j = i + 1; j < words.size(); j++) {
						Word right = words.get(j);
						if (!right.haveMain() && right.haveContainsBearingForm()) {
							if (!rules.establishRelation(word, right)) {
								rules.establishRelation(right, word);
							}
						}
					}
				}
			}
		});
	}

	private void searchMainForm(BearingPhrase bearingPhrase) {
		Word main = bearingPhrase.applyFunction(this::searchMainForm);
		bearingPhrase.setMainWord(main);
	}

	private Word searchMainForm(List<Word> words) {
		List<Word> lonely = words.stream()
			.filter(word -> !word.haveMain() && word.haveContainsBearingForm())
			.collect(Collectors.toList());
		if (lonely.size() == 0) {
			log.info("Не удалось найти подходящие слово в роли главного опорного слова");
			return words.stream()
				.filter(Word::haveContainsBearingForm)
				.findFirst().orElse(words.get(words.size() - 1));
		} else if (lonely.size() == 1) {
			return lonely.get(0);
		} else {
			return lonely.stream()
				.filter(word -> word.getForms().stream().anyMatch(form -> ShortBearingForm.contains(form.getTypeOfSpeech())))
				.findFirst().orElse(words.get(words.size() - 1));
		}
	}
}
