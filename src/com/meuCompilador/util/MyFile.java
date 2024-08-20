package com.meuCompilador.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public static boolean writeFile (String code, String path) {    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
                bw.write(code);
                bw.newLine();
                return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void displayFolder (String path) {
        File f = new File(path);
        File [] files = f.listFiles();
        for (File file : files){
            System.out.println(file.getName());
        }
    }

    public static void displayFiles (String path) {
        File f = new File(path);
        File [] files = f.listFiles(File :: isFile);
        for (File file : files){
            System.out.println(file.getName());
        }
    }

    public static void displayFolders (String path) {
        File f = new File(path);
        File [] files = f.listFiles(File :: isDirectory);
        for (File file : files){
            System.out.println(file.getName());
        }
    }

    public static String getPath (String stringpath) {
        Path path = Paths.get(stringpath);    
        return path.normalize().toString();
    }
    
    public static boolean isFile (String path) {
        return (new File(path)).isFile();
    }

    public static boolean isFolder (String path) {
        return (new File(path)).isDirectory();
    }
}
