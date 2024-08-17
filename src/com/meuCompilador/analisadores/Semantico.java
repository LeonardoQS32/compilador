package com.meuCompilador.analisadores;

import java.util.ArrayList;
import java.util.List;

import com.meuCompilador.exceptions.SemanticoException;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.model.enums.TypeToken;

public class Semantico {

    public static boolean check(List<Token> list) throws SemanticoException {
        List<Token> vars = new ArrayList<>();
        for (int i = 0; i < list.size();i++ ){
            if (list.get(i).getType() == TypeToken.LABEL_VAR){
                while (list.get(i).getType() != TypeToken.SEMICOLON) {
                    if (list.get(i).getType() == TypeToken.ID){
                        vars.add(list.get(i));
                    }
                    i++;
                }
            }
            
            if (list.get(i).getType() == TypeToken.ID && !vars.contains(list.get(i))){
                throw new SemanticoException("Erro semantico: variavel " + list.get(i).getLexema() + " não foi criada");
            }
        }
        return true;
    }
}