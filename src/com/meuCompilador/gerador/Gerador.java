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
    private static int countIf;
    private static int countIncre;
    private static int countAux;
    private static List<Token> list;

    private static void initializeObts (List<Token> listT) {
        list = listT;
        ind = 2;
        countStr = 0;
        countWrite = 0;
        countRead = 0;
        countIncre = 0;
        countIf = 0;
        countAux = 0;
        code = "";
    }

    public static String generateCode(List<Token> listT) {
        initializeObts(listT);
        generateStart();
        while (ind < list.size()){
            compare();
            ind++;
        }
        generateEnd();
        return code;
    }

    private static void compare() {
        switch (list.get(ind).getType()) {

            case TypeToken.LABEL_VAR:
                skipDeclaration();
                break;

            case TypeToken.WRITE:
                generateWrite();
                break;
        
            case TypeToken.READ:
                generateRead();
                break;

            case TypeToken.ASSIGN:
                break;

            case TypeToken.ARITHMETIC:
                generateIncrement();
                break;

            case TypeToken.IF:
                generateIf();
                break;
            default:
                break;
        }
    }

    private static void skipDeclaration() {
       while (list.get(ind).getType() != TypeToken.SEMICOLON) {
            ind++;
       }
    }

    private static void generateStart (){
        code += ".data \n";
        generateVar();
        generateStrings(countString());
        code += "\n.text \n";
        code += "\n.globl main \n";
        code += "\nmain: \n";
    }

    private static void generateEnd (){
        code += "\nend:\n";
        code += "\tli $v0, 10\n";
        code += "\tsyscall \n";
    }

    private static void generateVar () {
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

    private static  List<Token> countString () {
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

    private static void generateWrite () {
        ind += 2;
        code += "\nwrite" + countWrite + ": \n";
        while (list.get(ind).getType() != TypeToken.SEMICOLON) {
            if (list.get(ind).getType() == TypeToken.STRING){
                code += "\tli $v0, 4 \n";
                code += "\tla $a0, str" + countStr + "\n";
                code += "\tsyscall \n";
                countStr++;
            }
            if (list.get(ind).getType() == TypeToken.ID){
                code += "\tli $v0, 1 \n";
                code += "\tlw $a0, " + list.get(ind).getLexema() + "\n";
                code += "\tsyscall \n";
            }
            if (list.get(ind).getType() == TypeToken.NUMBER){
                code += "\tli $v0, 1 \n";
                code += "\tla $a0, " + list.get(ind).getLexema() + "\n";
                code += "\tsyscall \n";
            }
            ind++;
        }
        System.out.println("Saindo do write: " + list.get(ind).getType());
        countWrite++;
    }

    private static void generateRead () {
        ind += 2;
        code += "\nread" + countRead + ": \n";
        code += "\tli $v0, 5 \n";
        code += "\tsyscall \n";
        code += "\tsw $v0, " + list.get(ind).getLexema() + "\n";
        ind += 2;
        countRead++;
    }

    private static void generateIncrement () {
        if (list.get(ind).getLexema().equals("-") || list.get(ind).getLexema().equals("+")){
            code+= "\nincrement" + countIncre + ": \n";
            code += "\tlw $t0, " + list.get(ind+1).getLexema() + "\n";
            code += "\taddi $t0, $t0 " + list.get(ind).getLexema() + "1\n";
            code += "\tsw $t0, " + list.get(ind+1).getLexema() + "\n";
            countIncre++;
        }
        ind+= 2;
    }

    private static void generateIf(){
        ind++;
        int ifAtual = countIf;
        countIf++;
        code+= "\nif"+ifAtual+":";
        System.out.println("Verificando no if:" + list.get(ind).getType());
        while (list.get(ind).getType() != TypeToken.CLOSE_PAR){
            if (list.get(ind).getType() == TypeToken.LOGIC){
                if (list.get(ind+2).getType() == TypeToken.AND){
                    generateLogic("true" + countAux);
                    code+= "\tb if"+ifAtual+"False\n";
                    code+= "true"+ countAux + ":\n";
                    countAux++;
                }else {
                    generateLogic("if"+ ifAtual + "True");
                }
                
            }
            ind++;
        }
        System.out.println("Saiu do logic = " + list.get(ind).getType());
        //if (list.get(ind).getType() == TypeToken.CLOSE_PAR){
                code+= "\tb if"+ifAtual+"False\n";
                code+= "if"+ifAtual+"True:\n";
                ind+=2;
       // }
        while (list.get(ind).getType() != TypeToken.CLOSE_BRACE) {
            
            System.out.println("Token no if true = " + list.get(ind).getType());
            compare();
            System.out.println("Voltou do compare: " + list.get(ind).getType());
            ind++;
        }
        ind++;
        System.out.println("Token fora do if = "+ list.get(ind).getType());
        code += "\n\tb skip_false"+ifAtual+"";
        code+= "\nif"+ifAtual+"False: ";

        if (list.get(ind).getType() == TypeToken.ELSE){
            ind++;
            while (list.get(ind).getType() != TypeToken.CLOSE_BRACE) {
                compare();
                ind++;
            }
        }
        code += "\nskip_false"+ifAtual+":";
    }

     private static void generateLogic(String label) {
        code+= "\n\tlw $t0, "+ list.get(ind-1).getLexema();
        code+= "\n\tlw $t1, "+ list.get(ind+1).getLexema();
        code+= "\n";
        for (int i = 0; i < list.get(ind).getLexema().length(); i++){
            char logic = list.get(ind).getLexema().charAt(i);
            switch (logic) {
                case '<':
                    code += "\tblt $t0, $t1, "+label+"\n";
                    break;
                case '>':
                    code += "\tbgt $t0, $t1, "+label+"\n";
                    break;
                case '=':
                    code += "\tbeq $t0, $t1, "+label+"\n";            
                    break;
            }
        }
        ind++;
    }
}
