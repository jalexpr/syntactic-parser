package ru.textanalysis.tawt.sp;

import ru.textanalysis.tawt.sp.api.SyntaxParser;

public class ExampleSp {

	public static void main(String[] args) {
		SyntaxParser sp = new SyntaxParser();
		sp.init();

		System.out.println(
			sp.getTreeSentence("Солнце село за село.")
		);

		System.out.println(
			sp.getTreeSentence("рецепт этого средства невероятно прост достаточно взять две ложки мёда и смешать с таким же количеством белой глины и будет пррпррпррпрр")
		);

		System.out.println(
			sp.getTreeSentence("Сегодня стало ясно, что будет с российской валютой и евро")
		);

		System.out.println(sp.getTreeSentence("Взъерошенная ребенком добрая мама мыла раму, опрокинула ведро"));

		long start;
		long end;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10000 * (i + 1); j++) {
				sp.getTreeSentence("Сегодня стало ясно, что будет с российской валютой и евро");
			}
		}
		for (int i = 0; i < 10; i++) {
			start = System.currentTimeMillis();
			for (int j = 0; j < 10000 * (i + 1); j++) {
				sp.getTreeSentence("Сегодня стало ясно, что будет с российской валютой и евро");
			}
			end = System.currentTimeMillis();
			System.out.println(end - start);
		}

		String sent = "Солнце село за село. Необходимость автоматической обработки предложения и составления графа " +
			"зависимости обусловлена тем, что для выделения понятий необходим анализ большого " +
			"количества предложений, что при ручной обработке заняло бы значительное количество " +
			"времени.";
		System.out.println(sent + ":" + sp.getTreeSentence(sent));
	}
}
