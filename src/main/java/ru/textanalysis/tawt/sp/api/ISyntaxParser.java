package ru.textanalysis.tawt.sp.api;

import ru.textanalysis.tawt.ms.interfaces.InitializationModule;
import ru.textanalysis.tawt.ms.model.sp.Sentence;

public interface ISyntaxParser extends InitializationModule {

	Sentence getTreeSentence(String text);
}
