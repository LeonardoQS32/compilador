package com.meuCompilador.util;

import java.io.File;
import java.util.Scanner;

public class MyFile {
    
    //final static String pat = "src\\com\\meuCompilador\\examples\\";

    public static String getCode (String path)  {
        String code = "";
        try (Scanner sc = new Scanner (new File(path))){
            while (sc.hasNextLine()) {

                code+= sc.nextLine() + '\n';
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    public static void displayFiles (String path) {
        File f = new File(path);
        File [] files = f.listFiles(File :: isFile);
        for (File file : files){
            System.out.println(file.getName());
        }
    }
}
