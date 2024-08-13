package com.meuCompilador.model.entities;

import com.meuCompilador.model.enums.TypeToken;

public class Token {

    private TypeToken type;
    private String lexema;
    private Position position;

    public Token(TypeToken type, String lexema, Position position) {
        this.type = type;
        this.lexema = lexema;
        this.position = position;
    }
    public Token() {
        }

    public TypeToken getType() {
        return type;
    }
    public void setType(TypeToken type) {
        this.type = type;
    }
    public String getLexema() {
        return lexema;
    }
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    @Override
    public String toString() {
        return  type+ "=" + lexema + " ("+ position.getLine() + ", "+ position.getColumn() + ")";
    }
    
}
