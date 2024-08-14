package com.meuCompilador.ui;

import java.util.List;

import com.meuCompilador.model.entities.Token;

public class UI {

    private static final int margin = 20;
    private static final int sizeDiv = 77;

    public static void printCode (String code) {
        System.out.println("_".repeat(sizeDiv));
        System.out.println(code);
        System.out.println("_".repeat(sizeDiv));
    }

    public static void printTokens (List<Token> list){
        System.out.println("_".repeat(sizeDiv));
        System.out.printf("|\t");
        System.out.printf("%-" + margin + "s" , "TOKEN");
        System.out.printf("|\t");
        System.out.printf("%-" + margin + "s" , "LEXEMA");
        System.out.printf("|\t");
        System.out.printf("%-" + margin + "s" , "(LINHA, COLUNA)");
        System.out.printf("|");
        System.out.println();
        System.out.println("_".repeat(sizeDiv));
        for (Token t : list) {
            System.out.printf("|\t");
            System.out.printf("%-" + margin + "s" , t.getType());
            System.out.printf("|\t");
            System.out.printf("%-" + margin + "s" , t.getLexema());
            System.out.printf("|\t");
            System.out.printf("%-" + margin + "s" , t.getPosition());
            System.out.printf("|");
            System.out.println();
            System.out.println("_".repeat(sizeDiv));
        }
    }

    public static void clearConsole (){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
