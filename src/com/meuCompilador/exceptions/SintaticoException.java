package com.meuCompilador.exceptions;

import java.util.List;

public class SintaticoException extends Exception {
    private static final long serialVersionUID = 1L;
    private  List<String> incompleteList;
    public SintaticoException(String message) {
        super(message);
    }
    public SintaticoException(String message, List<String> incompleteList) {
        super(message);
        this.incompleteList = incompleteList;
    }
    public List<String> getIncompleteList() {
        return incompleteList;
    }
}
