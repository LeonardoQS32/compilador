package com.meuCompilador.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MyFile {
    
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

    public static String getPath (String stringpath) {
        Path path = Paths.get(stringpath);    
        return path.normalize().toString();
    }

    public static void displayFolder (String path) {
        File f = new File(path);
        File [] files = f.listFiles();
        for (File file : files){
            System.out.println(file.getName());
        }
    }

    public static boolean isFile (String path) {
        return (new File(path)).isFile();
    }

    public static boolean isFolder (String path) {
        return (new File(path)).isDirectory();
    }
}
