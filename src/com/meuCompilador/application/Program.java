package com.meuCompilador.application;

import java.util.List;
import java.util.Scanner;

import com.meuCompilador.analisadores.Lexico;
import com.meuCompilador.exceptions.LexicoException;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.ui.UI;
import com.meuCompilador.util.MyFile;

public class Program {

    public static void main(String[] args) {
        int op = 0;
        String fileName, sourceCode;
        String path = "src\\com\\meuCompilador\\examples\\";

        try (Scanner sc = new Scanner(System.in)){

            System.out.println("\nArquivos disponiveis na pasta examples: \n");
            MyFile.displayFiles(path);
            System.out.println("\nDigite o nome do arquivo que deseja abrir ou -1 para especificar outro: ");
            fileName = sc.nextLine();
            if (fileName.equals("-1")){
                System.out.println("Por favor, especifica corretamente qual arquivo deseja abrir: ");
                fileName = sc.nextLine();
            }

            sourceCode = MyFile.getCode(fileName);
            List<Token> listTokens = Lexico.checkTokens(sourceCode);
            while (op != 5) {
                UI.clearConsole();
                UI.printCode(sourceCode);
            
                System.out.println("O que deseja realizar com o código? \n1- Analise léxica \n2- Analise sintática \n3- Analise semâtica \n4- Analise completa \n5- Sair");
                op = sc.nextInt();
                sc.nextLine();

                UI.clearConsole();
                UI.printCode(sourceCode);
            
                switch (op) {
                    case 1:
                        UI.printTokens(listTokens);
                        break;
                    case 4:
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

    }
}