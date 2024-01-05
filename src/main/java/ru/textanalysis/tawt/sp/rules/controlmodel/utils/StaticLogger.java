package ru.textanalysis.tawt.sp.rules.controlmodel.utils;

import java.util.List;

import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;

public class StaticLogger {

    public static void printWordFormsDiff(List<Form> wordForms, List<Form> correctForms) {
        System.out.println("___________________________________________________________");
        System.out.println("Current forms = " + wordForms.size() + "\n" + wordForms);
        System.out.println("New forms = " + correctForms.size() + "\n" + correctForms);
    }
}
