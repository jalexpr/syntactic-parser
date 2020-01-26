package ru.textanalysis.tawt.sp.api;

import ru.textanalysis.tawt.ms.external.sp.BearingPhraseExt;
import ru.textanalysis.tawt.ms.interfaces.InitializationModule;
import ru.textanalysis.tawt.ms.internal.sp.BearingPhraseSP;

import java.util.List;

public interface ISyntaxParser extends InitializationModule {
    List<BearingPhraseSP> getTreeSentence(String text);

    List<BearingPhraseExt> getTreeSentenceWithoutAmbiguity(String text);
}
