package com.meuCompilador.exceptions;

import java.util.List;

import com.meuCompilador.model.entities.Token;
import com.meuCompilador.ui.UI;

public class LexicoException extends Exception {
    private static final long serialVersionUID = 1L;

    public LexicoException(String message) {
        super(message);
    }

    public LexicoException(String message, String code, int head, List<Token> list) {
        super(message);
        String x = code.substring(0, head)
        +  "\u001B[31m" + code.charAt(head) + "\u001B[0m"
        + code.substring(head+1, code.length());
        UI.printCode(x);
        UI.printTokens(list);
    }
}
