package com.meuCompilador.gerador;

import java.util.ArrayList;
import java.util.List;

import com.meuCompilador.model.entities.Token;
import com.meuCompilador.model.enums.TypeToken;

public class Gerador {
    private static String code;
    private static int ind;
    private static int countStr;
    private static int countWrite;
    private static int countRead;
    private static int countIncre;

    private static void initializeObts () {
        ind = 2;
        countStr = 0;
        countWrite = 0;
        countRead = 0;
        countIncre = 0;
        code = "";
    }

    public static String generateCode(List<Token> list) {
        initializeObts();
        generateStart(list);
        while (ind < list.size()){
            switch (list.get(ind).getType()) {

                case TypeToken.LABEL_VAR:
                    skipDeclaration(list);
                    break;

                case TypeToken.WRITE:
                    generateWrite(list);
                    break;
            
                case TypeToken.READ:
                    generateRead(list);
                    break;

                case TypeToken.ASSIGN:
                    break;

                case TypeToken.ARITHMETIC:
                    generateIncrement(list);
                    break;
                default:
                    break;
            }
            ind++;
        }
        generateEnd();
        return code;
    }

    private static void skipDeclaration(List<Token> list) {
       while (list.get(ind).getType() != TypeToken.SEMICOLON) {
            ind++;
       }
    }

    private static void generateStart (List<Token> list){
        code += ".data \n";
        generateVar(list);
        generateStrings(countString(list));
        code += "\n.text \n";
        code += "\n.globl main \n";
        code += "\nmain: \n";
    }

    private static void generateEnd (){
        code += "\nend:\n";
        code += "\tli $v0, 10\n";
        code += "\tsyscall \n";
    }

    private static void generateVar (List<Token> list) {
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

    private static  List<Token> countString (List<Token> list) {
        List<Token> strs = new ArrayList<>();
        for (Token to : list) {
            if (to.getType() == TypeToken.STRING){
                to.setLexema(to.getLexema().replace("\'", "\""));
                strs.add(to);
            }
        }

        return strs;
    }

    private static void generateStrings (List<Token> strs) {
        int i =0;
        for (Token token : strs) {
            code += "str" + i + ": .asciiz " + token.getLexema() + "\n"; 
            i++;
        }
    }

    private static void generateWrite (List<Token> tokens) {
        ind += 2;
        code += "\nwrite" + countWrite + ": \n";
        while (tokens.get(ind).getType() != TypeToken.SEMICOLON) {
            if (tokens.get(ind).getType() == TypeToken.STRING){
                code += "\tli $v0, 4 \n";
                code += "\tla $a0, str" + countStr + "\n";
                code += "\tsyscall \n";
                countStr++;
            }
            if (tokens.get(ind).getType() == TypeToken.ID){
                code += "\tlw $a0, " + tokens.get(ind).getLexema() + "\n";
                code += "\tli $v0, 1 \n";
                code += "\tsyscall \n";
            }
            ind++;
        }
        countWrite++;
    }

    private static void generateRead (List<Token> list) {
        ind += 2;
        code += "\nread" + countRead + ": \n";
        code += "\tli $v0, 5 \n";
        code += "\tsyscall \n";
        code += "\tsw $v0, " + list.get(ind).getLexema() + "\n";
        ind += 2;
        countRead++;
    }

    private static void generateIncrement (List<Token> list) {
        if (list.get(ind).getLexema().equals("-") || list.get(ind).getLexema().equals("+")){
            code+= "\nincrement" + countIncre + ": \n";
            code += "\tlw $t0, " + list.get(ind+1).getLexema() + "\n";
            code += "\taddi $t0, $t0 " + list.get(ind).getLexema() + "1\n";
            code += "\tsw $t0, " + list.get(ind+1).getLexema() + "\n";
            countIncre++;
        }
        ind+= 2;
    }
}
