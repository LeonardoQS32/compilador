package com.meuCompilador.application;

import java.util.List;
import java.util.Scanner;

import com.meuCompilador.analisadores.Lexico;
import com.meuCompilador.analisadores.Semantico;
import com.meuCompilador.analisadores.Sintatico;
import com.meuCompilador.exceptions.LexicoException;
import com.meuCompilador.exceptions.SemanticoException;
import com.meuCompilador.exceptions.SintaticoException;
import com.meuCompilador.gerador.Gerador;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.ui.UI;
import com.meuCompilador.util.MyFile;
public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int op = 0;
        String sourceCode = UI.openCode(sc);
        while (op != 7) {
            try {            
                UI.clearConsole();
                UI.printCode(sourceCode);
                op = UI.displayMenu(sc);
                sc.nextLine();

                UI.clearConsole();
                UI.printCode(sourceCode);
            
                switch (op) {
                    case 1:
                        fullAnalysis(sourceCode);
                        break;
                    case 2:
                        lexicalAnalysis(sourceCode);
                        break;
                    case 3:
                        syntacticAnalysis(sourceCode);
                        break;
                    case 4:
                        semanticAnalysis(sourceCode);
                        break;
                    case 5:
                        String assemblyCode = generateCode(sourceCode);
                        UI.pauseConsole(sc);
                        String pathSave = UI.chooseFolder(sc);
                        saveFile(assemblyCode, pathSave);
                        break;
                    case 6:
                        sourceCode = UI.openCode(sc);
                        break;
                    case 7:
                        System.out.println("Encerrado.");
                        break;
                    default:
                        System.out.println("Opção indisponivel");
                }
                
            }catch(LexicoException e) {
                UI.printErro(e.getHead(), sourceCode);
                UI.printTokens(e.getIncompleteList());
                System.out.println("Erro léxico: " + e.getMessage());
                
            }catch(SintaticoException e) {
                UI.printUnstacked(e.getIncompleteList());
                System.out.println("Erro sintático: " + e.getMessage());
            }catch (SemanticoException e){
                UI.printVars(e.getVars());
                System.out.println("Erro semantico: " + e.getMessage());
            }
            UI.pauseConsole(sc);
        } 
        sc.close();      
    }
    private static void lexicalAnalysis (String sourceCode) throws LexicoException {
        UI.printTokens(Lexico.checkTokens(sourceCode));
    }

    private static void syntacticAnalysis (String sourceCode) throws LexicoException, SintaticoException {
        UI.printUnstacked(Sintatico.getList(Lexico.checkTokens(sourceCode)));
    }

    private static void semanticAnalysis (String sourceCode) throws LexicoException, SemanticoException {
        UI.printVars(Semantico.getVars(Lexico.checkTokens(sourceCode)));
    }

    private static void fullAnalysis (String sourceCode) throws LexicoException, SintaticoException, SemanticoException {
        List<Token> list = Lexico.checkTokens(sourceCode);
        UI.printTokens(list);
        UI.printUnstacked(Sintatico.getList(list));
        UI.printVars(Semantico.getVars(list));
    }

    private static String generateCode (String sourceCode) throws LexicoException, SintaticoException, SemanticoException {
        List<Token> list = Lexico.checkTokens(sourceCode);
        fullAnalysis(sourceCode);
        String code = Gerador.generateCode(list);
        UI.printCode(code);
        return code;
    }

    private static void saveFile (String code, String path) {
        if (MyFile.writeFile(code, path)) System.out.println("Arquivo salvo em:\n" + path);
    }
}