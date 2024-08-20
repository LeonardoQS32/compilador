package com.meuCompilador.analisadores;

import java.util.ArrayList;
import java.util.List;

import com.meuCompilador.exceptions.SemanticoException;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.model.enums.TypeToken;

public class Semantico {

    private static List<Token> vars;
    public static boolean check(List<Token> list) throws SemanticoException {
        vars = new ArrayList<>();
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
                throw new SemanticoException("variavel [" + list.get(i).getLexema() +"] na posição "+ list.get(i).getPosition() +" não foi criada", vars);
            }

            if (list.get(i).getType() == TypeToken.ASSIGN){
                int j = 0;
                while (list.get(j).getType() != TypeToken.SEMICOLON) {
                    if (list.get(j).getType() == TypeToken.ARITHMETIC && list.get(j).getLexema().equals("/")){
                        if (list.get(j+1).getType() == TypeToken.NUMBER ){
                            if(Double.parseDouble(list.get(i+1).getLexema()) == 0){
                                throw new SemanticoException("divisão por zero", vars);
                            }
                        }
                    }
                    j++;
                }
            }
        }
        return true;
    }

    public static List<Token> getVars(List<Token> list) throws SemanticoException {
        check(list);
        return vars;
    }
}