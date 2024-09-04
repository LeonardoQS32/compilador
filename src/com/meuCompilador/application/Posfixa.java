package com.meuCompilador.application;

import java.util.Stack;

public class Posfixa {
    public static void main(String[] args) {
        String exp = "a - b * c + d";
      //  Stack stack = new Stack<char>();
        Stack <Character>operadores = new Stack<Character>();
        String pos = "";

        for (int i = 0; i < exp.length(); i++) {
            if (Character.isLetter(exp.charAt(i))){
                pos += exp.charAt(i);
                
            }
            if (exp.charAt(i)  == '('){
                operadores.push((Character)'(');
            }
            if (exp.charAt(i) == '/' || exp.charAt(i) == '*' || exp.charAt(i) == '+' || exp.charAt(i) == '-'){
                operadores.push(exp.charAt(i));
            }
            if (exp.charAt(i) == ')'){
                char c = (char) operadores.pop();
                while (c != '(') {
                    pos+= c;
                    c = (char) operadores.pop();
                }
            }
        }
        while (!operadores.isEmpty()) {
            pos += operadores.pop();
        }
        System.out.println("\n\n" + pos);
    }
}
