package com.meuCompilador.application;

import java.util.List;
import java.util.Scanner;

import com.meuCompilador.analisadores.Lexico;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.util.MyFile;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("Arquivos na pasta:");
        MyFile.displayFiles();
        System.out.println("Digite o nome do arquivo que compilar: ");
        String nomeArquivo = sc.nextLine();
        System.out.println("\n\nCONTEUDO DO ARQUIVO: ");
        String sourceCode = MyFile.getCode(nomeArquivo);
        System.out.println(sourceCode);
        List<Token> listTokens = Lexico.checkTokens(sourceCode);
        System.out.println(listTokens.toString());
        sc.close();   
    }
}