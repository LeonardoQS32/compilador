package com.meuCompilador.application;

import java.util.List;
import java.util.Scanner;

import com.meuCompilador.analisadores.Lexico;
import com.meuCompilador.exceptions.LexicoException;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.ui.UI;
public class Program {

    public static void main(String[] args) {
        int op = 0;
        String  sourceCode = "";
        Scanner sc = new Scanner(System.in);
        try{
            sourceCode = UI.openCode(sc);         
            List<Token> listTokens = Lexico.checkTokens(sourceCode);
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
                    case 5:
                        break;
                    default:
                        System.out.println("Opção indisponivel");
                }
                System.out.println("\nPrecione qualquer tecla para continuar...");
                sc.nextLine();
        
            }
            
        }catch(LexicoException e) {
            System.out.println(e.getMessage());
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        sc.close();

    }
}