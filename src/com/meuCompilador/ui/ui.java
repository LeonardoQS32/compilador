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
                clearConsole();
                System.out.println(savedPath + " :\n");
                MyFile.displayFolder(savedPath);
                System.out.println("\nDigite o nome do arquivo que deseja abrir ou outra pasta para acessar:");
                System.out.print("\\");
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

    public static String chooseFolder(Scanner sc) {
        String typedPath = "", mergedPath;
        String savedPath = "src\\com\\meuCompilador\\examples\\";

        while (!typedPath.equals("-1")) {
                clearConsole();
                System.out.println(savedPath + " :\n");
                MyFile.displayFolders(savedPath);
                System.out.println("Digite -1 para salvar na pasta atual ou digite outra pasta para acessar.");
                System.out.print("\\");
                typedPath = sc.nextLine();

                mergedPath = savedPath + typedPath;
                if (MyFile.isFolder(mergedPath)){
                    savedPath = MyFile.getPath(mergedPath) + "\\";
                } else if (MyFile.isFolder(typedPath)){
                    savedPath = typedPath + "\\";
                }
        }
        clearConsole();
        System.out.println(savedPath);
        System.out.println("Como deseja salvar o arquivo?");
        typedPath = sc.nextLine();
        savedPath += typedPath;
        if (!savedPath.endsWith(".asm")) savedPath += ".asm";

        return savedPath;
    }

    public static int displayMenu (Scanner sc) {
        System.out.println("O que deseja realizar com o codigo?");
        System.out.println("\n1- Analise completa "
                        +"\n2- Analise lexica "
                        +"\n3- Analise sintatica "
                        +"\n4- Analise semantica "
                        +"\n5- Gerar código assembly (MIPS)" 
                        +"\n6- Abrir outro código "
                        +"\n7- Encerrar");
        System.out.print(": ");
        return sc.nextInt();
    }

    public static void printCode (String code) {
        System.out.println("_".repeat(sizeDiv));
        System.out.println(code);
        System.out.println("_".repeat(sizeDiv));
        System.out.println();
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
        System.out.println();
    }

    public static void printUnstacked (List<String> unstackeds){
        for (String unstacked : unstackeds){
            System.out.println(unstacked);
        }
        System.out.println();
    }

    public static void printVars (List<Token> vars) {
        System.out.println("Variaveis criadas: ");
        System.out.print("| ");
        for (Token var : vars) {
            System.out.print(var.getLexema() + " | ");
        }
        System.out.println("\n");
    }

    public static void printErro(int position, String sourceCode) {
        UI.printCode(sourceCode.substring(0, position)
                +  "\u001B[31m" + sourceCode.charAt(position) + "\u001B[0m"
                + sourceCode.substring(position+1, sourceCode.length()));
    }

    public static void clearConsole (){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pauseConsole (Scanner sc) {
        System.out.println("\nPrecione enter para continuar...");
        sc.nextLine();
    }
}
