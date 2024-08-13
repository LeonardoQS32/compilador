package com.meuCompilador.analisadores;

import java.util.ArrayList;
import java.util.List;

import com.meuCompilador.model.entities.Position;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.model.enums.TypeToken;

public class Lexico {

    private static Position position;
    private static int head;
    private static int length;
    private static String sourceCode;
    private static List<Token> list; 

    private static final char []charIgnore = { '\t', '\r', '\f' , ' '};
    private static final char []charArithmetics = {  '/', '+' , '-', '*'};
    private static final char []charLogics = {  '<', '>' , '='};
    
    public static List<Token> checkTokens (String code) {
        list = new ArrayList<>();
        sourceCode = code;
        position = new Position();
        head = 0;
        q0();
        return list;
    }

    private static void q0 (){
        while (head < sourceCode.length()){
            length = 0;
            System.out.println("testando <"+ sourceCode.charAt(head)+" >, head: " + head);
            switch (sourceCode.charAt(head)) {
                case 'P':
                    forward();
                    q1();
                    break;

                case '\n':
                    System.out.println("achou barra, linha: " + position.getLine());
                    breakLine();
                    break;
                
                case '\0':
                    System.out.println("FIM");
                    return;
                
                default:
                    if (Character.isLetter(sourceCode.charAt(head))){
                        forward();
                        qId();
                    }else
                    if (Character.isDigit(sourceCode.charAt(head))){
                        forward();
                        qNum();
                    }else{
                        boolean p = false;
                        for (char c : charIgnore){
                            System.out.println("test");
                            if (sourceCode.charAt(head) == c) {
                                p = true;
                                break;
                            }
                        }
                        if (p){
                            forward();
                            break;
                        }else {
                            System.out.println("Caracter não identificado na linha " + position.getLine()+ " coluna " + position.getColumn());
                            return;
                        }
                    }
                    break;
            }
        }
    }


    private static void q1 (){
        System.out.println("testando r = " + sourceCode.charAt(head));
        if (sourceCode.charAt(head) == 'r'){
            forward();
            q2();
        }else {
            qId();
        }
    }

    private static void q2 (){
        System.out.println("testando o");
        if (sourceCode.charAt(head) == 'o'){
            forward();
            q3();
        }else {
            qId();
        }
    }

    private static void q3 (){
        System.out.println("testando g");

        if (sourceCode.charAt(head) == 'g'){
            forward();
            q4();
        }else {
            qId();
        }
    }

    private static void q4 (){
        System.out.println("testando r");

        if (sourceCode.charAt(head) == 'r'){
            forward();
            q5();
        }else {
            qId();
        }
    }

    private static void q5 (){
        System.out.println("testando a");

        if (sourceCode.charAt(head) == 'a'){
            forward();
            q6();
        }else {
            qId();
        }
    }

    private static void q6 (){
        System.out.println("testando m");

        if (sourceCode.charAt(head) == 'm'){
            forward();
            q7();
        }else {
            qId();
        }
    }

    private static void q7 (){
        System.out.println("testando a");

        if (sourceCode.charAt(head) == 'a'){
            forward();
            q8();
        }else {
            qId();
        }
    }

    private static void q8 (){
        if (!Character.isLetter(sourceCode.charAt(head)) && !Character.isDigit(sourceCode.charAt(head))){
            System.out.println("Inserindo programa");
            insereToken(TypeToken.LABEL_PROGRAM, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            qId();
        }
    }

    private static void qId () {
        if (Character.isLetter(sourceCode.charAt(head)) || Character.isDigit(sourceCode.charAt(head))){
            forward();
            qId();
        }else {
            insereToken(TypeToken.ID, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));     
        }
    }

    private static void qNum () {
        if ( Character.isDigit(sourceCode.charAt(head))){
            forward();
            qNum();
        }else {
            insereToken(TypeToken.NUMBER, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));     
        }
    }

    private static void insereToken (TypeToken type, String lexema, Position position){
        list.add(new Token(type, lexema, position));
    }
    private static void incrementHead () { head++;}

    private static void forward () {
        incrementHead();
        position.incrementColumn();
        length++;
    }

    private static void breakLine() {
        position.incrementLine();
        position.setColumn(1);
        incrementHead();
    }

}
