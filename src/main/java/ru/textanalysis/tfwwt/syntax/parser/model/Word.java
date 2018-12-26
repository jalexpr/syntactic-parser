package ru.textanalysis.tfwwt.syntax.parser.model;

import java.util.List;

public class Word {

    private List<Form> forms;
    private Word parent;
    private Word child;

    public Word(List<Form> forms) {
        this.forms = forms;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setChild(Word child) {
        this.child = child;
    }

    public Word getChild() {
        return child;
    }

    public void setParent(Word parent) {
        this.parent = parent;
    }

    public Word getParent() {
        return parent;
    }

    public boolean isGetBearingForm() {
        //todo
        return false;
    }



}
