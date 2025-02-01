package ru.textanalysis.tawt.sp;

import ru.textanalysis.tawt.gama.Gama;
import ru.textanalysis.tawt.gama.GamaImpl;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;
import ru.textanalysis.tawt.ms.model.sp.BearingPhrase;
import ru.textanalysis.tawt.ms.model.sp.Sentence;
import ru.textanalysis.tawt.ms.model.sp.Word;
import ru.textanalysis.tawt.sp.api.SyntaxParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ExampleSp {
	public static final Gama gama = new GamaImpl();
	public static final SyntaxParser sp = new SyntaxParser();

	static {
		gama.init();
		sp.init();
	}

	public static void main(String[] args) throws IOException {
		Sentence sentence = sp.getTreeSentence("Прадед был бородат.");

		System.out.println(sentence);

//		System.out.println();
//		count("BGG_GL1.txt");
//		count("В XXI веке наибольшую ценность представляет информация.txt");
//		count("Толстой Лев. Война и мир. Том 1.txt");
//		count("Dostoevskii_Fedor_Prestuplenie_i_nakazanie_www_Litmir_net_7379.txt");
	}

	private static void count(String fileName) throws IOException {
		StringBuilder text = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line;
		while ((line = reader.readLine()) != null) {
			text.append(line);
		}
		reader.close();

		int countBefore = 0;
		for (var bearingPhrase : gama.getMorphSentence(text.toString()).getBearingPhrases()) {
			for (var word : bearingPhrase.getWords()) {
				if (multi(word.getOmoForms())) {
					countBefore++;
				}
			}
		}


		int countAfter = 0;

		for (BearingPhrase bearingPhrase : sp.getTreeSentence(text.toString()).getBearingPhrases()) {
			for (Word word : bearingPhrase.getWords()) {
				if (multi(word.getForms())) {
					countAfter++;
				}
			}
		}
		System.out.println(fileName + " countBefore " + countBefore + " countAfter " + countAfter);
	}

//	public static int oneWord(Word word) {
//		int count = 0;
//		if (one(word.getForms())) {
//			count++;
//		}
//		for (Word dependent : word.getDependents()) {
//			count += oneWord(dependent);
//		}
//		return count;
//	}

	public static boolean multi(List<Form> forms) {
		return forms.stream()
			.map(Form::getTypeOfSpeech)
			.distinct()
			.count() > 1;
	}

//	public static boolean one(List<Form> forms) {
//		return forms.stream()
//			.map(Form::getTypeOfSpeech)
//			.distinct()
//			.count() == 1;
//	}
}
