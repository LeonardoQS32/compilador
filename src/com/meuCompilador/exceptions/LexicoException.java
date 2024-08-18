package com.meuCompilador.exceptions;

import java.util.List;

import com.meuCompilador.model.entities.Token;

public class LexicoException extends Exception {
    private static final long serialVersionUID = 1L;
    private int head;
    private List<Token> incompleteList;
    public LexicoException(String message) {
        super(message);
    }

    public LexicoException(String message, int head) {
        super(message);
        this.head = head;
    }

    public LexicoException(String message, int head, List<Token> incompleteList) {
        super(message);
        this.head = head;
        this.incompleteList = incompleteList;
    }

    public int getHead() {
        return head;
    }

    public List<Token> getIncompleteList() {
        return incompleteList;
    }
}
