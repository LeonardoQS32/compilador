package com.meuCompilador.exceptions;

public class LexicoException extends Exception {
    private static final long serialVersionUID = 1L;
    private int head;
    public LexicoException(String message) {
        super(message);
    }

    public LexicoException(String message, int head) {
        super(message);
        this.head = head;
    }

    public int getHead() {
        return head;
    }
}
