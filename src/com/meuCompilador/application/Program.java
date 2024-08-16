package com.meuCompilador.application;

import java.util.List;
import java.util.Scanner;

import com.meuCompilador.analisadores.Lexico;
import com.meuCompilador.analisadores.Sematico;
import com.meuCompilador.analisadores.Sintatico;
import com.meuCompilador.exceptions.LexicoException;
import com.meuCompilador.exceptions.SemanticoException;
import com.meuCompilador.exceptions.SintaticoException;
import com.meuCompilador.model.entities.Token;
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
                        analiseLexica(sourceCode);
                        break;
                    case 2:
                        analiseSintatica(sourceCode);
                        break;
                    case 3:
                        analiseSemantica(sourceCode);
                        break;
                    case 4:
                        todasAnalises(sourceCode);
                        break;
                    case 5:
                        sourceCode = UI.openCode(sc);
                        break;
                    case 6:
                        System.out.println("Encerrado.");
                        break;
                    default:
                        System.out.println("Opção indisponivel");
                }
                
            }catch(LexicoException e) {
                UI.printErro(e.getHead(), sourceCode);
                System.out.println("Erro léxico: " + e.getMessage());
                
            }catch(SintaticoException e) {
                System.out.println("Erro sintático: " + e.getMessage());
            }catch (SemanticoException e){
                System.out.println(e.getMessage());
            }
            UI.pauseConsole(sc);
        } 
        sc.close();      
    }
    private static void analiseLexica (String sourceCode) throws LexicoException {
        UI.printTokens(Lexico.checkTokens(sourceCode));
    }

    private static void analiseSintatica (String sourceCode) throws LexicoException, SintaticoException {
        System.out.println("\n" + ((Sintatico.checkSyntax(Lexico.checkTokens(sourceCode))) ? "Sintaxe correta." : "Sintax incorreta")); 

    }

    private static void analiseSemantica (String sourceCode) throws LexicoException, SemanticoException {
        System.out.println((Sematico.check(Lexico.checkTokens(sourceCode))) ? "Semantica correta." : "");
    }

    private static void todasAnalises (String sourceCode) throws LexicoException, SintaticoException, SemanticoException {
        List<Token> list = Lexico.checkTokens(sourceCode);
        UI.printTokens(list);
        System.out.println("\n" + ((Sintatico.checkSyntax(list)) ? "Sintaxe correta." : "Sintax incorreta")); 
        System.out.println((Sematico.check(list)) ? "Semantica correta." : "");
    }
}