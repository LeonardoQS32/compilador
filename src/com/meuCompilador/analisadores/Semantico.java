package com.meuCompilador.analisadores;

import java.util.ArrayList;
import java.util.List;

import com.meuCompilador.exceptions.SemanticoException;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.model.enums.TypeToken;

public class Semantico {

    private static List<Token> vars;
    public static boolean check(List<Token> list) throws SemanticoException {
        List<Token> initializedVar = new ArrayList<>();
        vars = new ArrayList<>();
        for (int i = 0; i < list.size();i++ ){
            if (list.get(i).getType() == TypeToken.LABEL_VAR){
                while (list.get(i).getType() != TypeToken.SEMICOLON) {
                    if (list.get(i).getType() == TypeToken.ID){
                        if (vars.contains(list.get(i))){
                            throw new SemanticoException("variavel [" + list.get(i).getLexema() +"] na posição " + list.get(i).getPosition()+" já foi criada.", vars);
                        }else{
                            vars.add(list.get(i));
                        }
                    }
                    i++;
                }
            }
             
            if (list.get(i).getType() == TypeToken.ID){
                if (!vars.contains(list.get(i))){
                    throw new SemanticoException("variavel [" + list.get(i).getLexema() +"] na posição "+ list.get(i).getPosition() +" não foi criada", vars);
                    
                }
                if (list.get(i-2).getType() == TypeToken.READ && !initializedVar.contains(list.get(i-2))){
                    initializedVar.add(list.get(i));
                }
                if (list.get(i+1).getType() != TypeToken.ASSIGN){
                    if (!initializedVar.contains(list.get(i))){
                        throw new SemanticoException("variavel [" + list.get(i).getLexema() + "] na posição " + list.get(i).getPosition() + " não foi inicializada.", vars);
                    }
                } 
            }
        
            if (list.get(i).getType() == TypeToken.ASSIGN){
               int x = i;
                while (list.get(i).getType() != TypeToken.SEMICOLON) {
                    if (list.get(i).getType() == TypeToken.ID){
                        if (!vars.contains(list.get(i))){
                            throw new SemanticoException("variavel [" + list.get(i).getLexema() +"] na posição "+ list.get(i).getPosition() +" não foi criada", vars);
                        }
                        if (!initializedVar.contains(list.get(i))){
                            throw new SemanticoException("variavel [" + list.get(i).getLexema() + "] na posição " + list.get(i).getPosition() + " não foi inicializada.", vars);
                        }
                    }
                    if (list.get(i).getType() == TypeToken.ARITHMETIC && list.get(i).getLexema().equals("/")){
                        if (list.get(i+1).getType() == TypeToken.NUMBER ){
                            if(Double.parseDouble(list.get(i+1).getLexema()) == 0){
                                throw new SemanticoException("divisão por zero em " + list.get(i+1).getPosition(), vars);
                            }
                        }
                    }
                    i++;
                }
                if (!initializedVar.contains(list.get(x-1))){ 
                    initializedVar.add(list.get(x-1));
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