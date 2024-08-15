package com.meuCompilador.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.meuCompilador.analisadores.Lexico;
import com.meuCompilador.analisadores.Sintatico;
import com.meuCompilador.exceptions.LexicoException;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.ui.UI;
public class Program {

    public static void main(String[] args) {
        int op = 0;
        String  sourceCode = "";
        List<Token> listTokens = new ArrayList<>();
        try (Scanner sc = new Scanner(System.in)){
            sourceCode = UI.openCode(sc);         
            listTokens = Lexico.checkTokens(sourceCode);
            while (op != 5) {
                UI.clearConsole();
                UI.printCode(sourceCode);
                op = UI.displayMenu(sc);
                sc.nextLine();

                UI.clearConsole();
                UI.printCode(sourceCode);
            
                switch (op) {
                    case 1:
                        UI.printTokens(listTokens);
                        break;

                    case 2:
                        System.out.println(Sintatico.checkSyntax(listTokens)); 
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Opção indisponivel");
                }
                UI.pauseConsole(sc);
            }       
        }catch(LexicoException e) {
            System.out.println(e.getMessage());
        }
    }
}