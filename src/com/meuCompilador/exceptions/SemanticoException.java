package com.meuCompilador.exceptions;

import java.util.List;

import com.meuCompilador.model.entities.Token;

public class SemanticoException extends Exception {
    private static final long serialVersionUID = 1L;
    private List<Token> vars;
    public SemanticoException(String message) {
        super(message);
    }

    public SemanticoException(String message, List<Token> vars) {
        super(message);
        this.vars = vars;
    }

    public List<Token> getVars() {
        return vars;
    }
}
