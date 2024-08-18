package com.meuCompilador.gerador;

import java.util.ArrayList;
import java.util.List;

import com.meuCompilador.model.entities.Token;
import com.meuCompilador.model.enums.TypeToken;

public class Gerador {
    private static String code;
    private static int ind;
    private static int contStr;
    private static int contWrite;
    private static int contRead;
    private static int contIncre;

    public static String gerar(List<Token> list) {
        ind = 2;
        contStr = 0;
        contWrite = 0;
        contRead = 0;
        contIncre = 0;
        code = "";
        code += ".data \n";
        gerarVar(list);
        List<Token> strs = contString(list);
        gerarStrings(strs);
        code += "\n.text \n";
        code += "\n.globl main \n";
        code += "\nmain: \n";
        while (ind < list.size()){
            switch (list.get(ind).getType()) {

                case TypeToken.LABEL_VAR:
                    skipDeclaration(list);
                    break;

                case TypeToken.WRITE:
                    gerarWrite(list);
                    break;
            
                case TypeToken.READ:
                    gerarRead(list);
                    break;

                case TypeToken.ASSIGN:
                    break;

                case TypeToken.ARITHMETIC:
                    gerarIncrement(list);
                    break;
                default:
                    break;
            }
            ind++;
        }
        code += "\nend:\n";
        code += "\tli $v0, 10\n";
        code += "\tsyscall \n";

        return code;
    }

    private static void skipDeclaration(List<Token> list) {
       while (list.get(ind).getType() != TypeToken.SEMICOLON) {
            ind++;
       }
    }

    private static void gerarVar (List<Token> list) {
        for (int i = 0; i < list.size();i++ ){
            if (list.get(i).getType() == TypeToken.LABEL_VAR){
                while (list.get(i).getType() != TypeToken.SEMICOLON) {
                    if (list.get(i).getType() == TypeToken.ID){
                        code += list.get(i).getLexema() + ": .word 0 \n";
                    }
                    i++;
                }
            }
        }
    }

    private static  List<Token> contString (List<Token> list) {
        List<Token> strs = new ArrayList<>();
        for (Token to : list) {
            if (to.getType() == TypeToken.STRING){
                to.setLexema(to.getLexema().replace("\'", "\""));
                strs.add(to);
            }
        }

        return strs;
    }

    private static void gerarStrings (List<Token> strs) {
        int i =0;
        for (Token token : strs) {
            code += "str" + i + ": .asciiz " + token.getLexema() + "\n"; 
            i++;
        }
    }

    private static void gerarWrite (List<Token> tokens) {
        ind += 2;
        code += "\nwrite" + contWrite + ": \n";
        while (tokens.get(ind).getType() != TypeToken.SEMICOLON) {
            if (tokens.get(ind).getType() == TypeToken.STRING){
                code += "\tli $v0, 4 \n";
                code += "\tla $a0, str" + contStr + "\n";
                code += "\tsyscall \n";
                contStr++;
            }
            if (tokens.get(ind).getType() == TypeToken.ID){
                code += "\tlw $a0, " + tokens.get(ind).getLexema() + "\n";
                code += "\tli $v0, 1 \n";
                code += "\tsyscall \n";
            }
            ind++;
        }
        contWrite++;
    }

    private static void gerarRead (List<Token> list) {
        ind += 2;
        code += "\nread" + contRead + ": \n";
        code += "\tli $v0, 5 \n";
        code += "\tsyscall \n";
        code += "\tsw $v0, " + list.get(ind).getLexema() + "\n";
        ind += 2;
        contRead++;
    }

    private static void gerarIncrement (List<Token> list) {
        if (list.get(ind).getLexema().equals("-") || list.get(ind).getLexema().equals("+")){
            code+= "\nincrement" + contIncre + ": \n";
            code += "\tlw $t0, " + list.get(ind+1).getLexema() + "\n";
            code += "\taddi $t0, $t0 " + list.get(ind).getLexema() + "1\n";
            code += "\tsw $t0, " + list.get(ind+1).getLexema() + "\n";
            contIncre++;
        }
        ind+= 2;
    }
}
