package com.meuCompilador.application;

import java.util.Scanner;

import com.meuCompilador.analisadores.Lexico;
import com.meuCompilador.analisadores.Sintatico;
import com.meuCompilador.exceptions.LexicoException;
import com.meuCompilador.ui.UI;
public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int op = 0;
        String sourceCode = UI.openCode(sc);
        while (op != 6) {
            try {            
                UI.clearConsole();
                UI.printCode(sourceCode);
                op = UI.displayMenu(sc);
                sc.nextLine();

                UI.clearConsole();
                UI.printCode(sourceCode);
            
                switch (op) {
                    case 1:
                        UI.printTokens(Lexico.checkTokens(sourceCode));
                        break;
                    case 2:
                        System.out.println(Sintatico.checkSyntax(Lexico.checkTokens(sourceCode))); 
                        break;
                    case 5:
                        sourceCode = UI.openCode(sc);
                        break;
                    case 6:
                        System.out.println("Encerrado.");
                    default:
                        System.out.println("Opção indisponivel");
                }
                
            }catch(LexicoException e) {
                System.out.println(e.getMessage());
            }
            UI.pauseConsole(sc);
        } 
        sc.close();      
    }
}