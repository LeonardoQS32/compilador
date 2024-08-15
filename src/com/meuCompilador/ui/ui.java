package com.meuCompilador.ui;

import java.util.List;
import java.util.Scanner;

import com.meuCompilador.model.entities.Token;
import com.meuCompilador.util.MyFile;

public class UI {

    private static final int margin = 20;
    private static final int sizeDiv = 77;

    public static String openCode(Scanner sc) {
        String typedPath, mergedPath, sourceCode ="";
        String savedPath = "src\\com\\meuCompilador\\examples\\";

        while (sourceCode.isBlank()) {
                System.out.println(savedPath + " :\n");
                MyFile.displayFolder(savedPath);
                System.out.print("\n\\");
                typedPath = sc.nextLine();

                mergedPath = savedPath + typedPath;
                if (MyFile.isFile(mergedPath)){
                    sourceCode = MyFile.getCode(mergedPath);
                }if (MyFile.isFolder(mergedPath)){
                    savedPath = MyFile.getPath(mergedPath) + "\\";
                }else if (MyFile.isFile(typedPath)){
                    sourceCode = MyFile.getCode(typedPath);
                } else if (MyFile.isFolder(typedPath)){
                    savedPath = typedPath + "\\";
                }
        }
        return sourceCode;
    }

    public static int displayMenu (Scanner sc) {
        System.out.println("O que deseja realizar com o codigo?");
        System.out.println("\n1- Analise lexico \n2- Analise sintatico \n3- Analise semantico \n4- Analise completa \n5- Sair");
        return sc.nextInt();
    }

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

    public static void pauseConsole (Scanner sc) {
        System.out.println("Precione qualquer tecla para continuar...");
        sc.nextLine();
    }
}
