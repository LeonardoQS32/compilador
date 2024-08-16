package com.meuCompilador.analisadores;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.meuCompilador.model.entities.Token;
import com.meuCompilador.model.enums.TypeToken;

public class Sintatico {

    private static List<Token> listTokens;
    private static Stack<Token> stackTokens = new Stack<>();

    public static boolean checkSyntax (List<Token> list) {
        listTokens = new ArrayList<>(list);
        listTokens.add(new Token(TypeToken.SIGN, "$", null));
        stackTokens.push(new Token(TypeToken.SIGN, "$", null));
        stackTokens.push(new Token(TypeToken.NTPROGRAM, "", null));
        while (!stackTokens.isEmpty() && !listTokens.isEmpty() && compare()){
            
        }
        return (stackTokens.isEmpty() && listTokens.isEmpty());
    }

    private static boolean compare (){
        TypeToken primeiroLista = listTokens.get(0).getType();
        TypeToken topoPilha = stackTokens.pop().getType();

        if (topoPilha == primeiroLista){
            listTokens.remove(0);
        }else if (topoPilha == TypeToken.NTPROGRAM && primeiroLista == TypeToken.LABEL_PROGRAM){// se <PROGRAM> == Label_program
            p0();
        } else if (topoPilha == TypeToken.NTCOMMAND_LIST){ // pilha == <COMAND_LIST>
            // lista == id, arit, label_var, write, read, if, else, while, for
            if (primeiroLista == TypeToken.ID || primeiroLista == TypeToken.ARITHMETIC || primeiroLista == TypeToken.LABEL_VAR 
            || primeiroLista == TypeToken.WRITE || primeiroLista == TypeToken.READ || primeiroLista == TypeToken.IF 
            || primeiroLista == TypeToken.ELSE || primeiroLista == TypeToken.WHILE || primeiroLista == TypeToken.FOR){
                p1();
            }else if (primeiroLista == TypeToken.CLOSE_BRACE){// <COMANDO_LIST> == close_brace
                p2();
            }
        }else if (topoPilha == TypeToken.NTCOMMAND){
            if (primeiroLista == TypeToken.ID || primeiroLista == TypeToken.ARITHMETIC){// <COMAND> == ID OU <COMAND> == ARIT
                p6();
            }else if (primeiroLista == TypeToken.LABEL_VAR){ // <COMAND> == Label_var
                p3();
            }else if (primeiroLista == TypeToken.WRITE){// <COMAND> == write
                p4();
            }else if (primeiroLista == TypeToken.READ){// <COMAND> == read
                p5();
            }else if (primeiroLista == TypeToken.IF || primeiroLista == TypeToken.ELSE){// <COMAND> == if ou <COMANDO> == else
                p7();
            }else if (primeiroLista == TypeToken.WHILE){// <COMAND> == while
                p8();
            }else if (primeiroLista == TypeToken.FOR){// <COMAND> == for
                p9();
            }
        }else if (topoPilha == TypeToken.NTDECLARATION && primeiroLista == TypeToken.LABEL_VAR){// <DECLARATION> == Label_var
            p10();
        }else if (topoPilha == TypeToken.NTWRITE && primeiroLista == TypeToken.WRITE){//<WRITE> == WRITE
            p11();
        }else if (topoPilha == TypeToken.NTREAD && primeiroLista == TypeToken.READ){// <READ> == read
            p12();
        }else if (topoPilha == TypeToken.NTASSIGNS){
            if (primeiroLista == TypeToken.ID || primeiroLista == TypeToken.ARITHMETIC){// <ASSINGS> == ID OU <ASSINGS> == ARIT
                p13();
            }
        }else if (topoPilha == TypeToken.NTIF){
            if (primeiroLista == TypeToken.IF){// <IF> == IF
                p14();
            }else if (primeiroLista == TypeToken.ELSE){// <IF> == ELSE
                p15();
            }
        }else if (topoPilha == TypeToken.NTWHILE && primeiroLista == TypeToken.WHILE){// <WHILE> == WHILE
            p16();
        }else if (topoPilha == TypeToken.NTFOR && primeiroLista == TypeToken.FOR){// <FOR> == FOR
            p17();
        }else if (topoPilha == TypeToken.NTVAR_LIST){
            if (primeiroLista == TypeToken.SEMICOLON){ // <VAR_LIST> == SEMICOLON
                p19();
            }else if (primeiroLista == TypeToken.COMMA){// <VAR_LIST> == COMMA
                p18();
            }
        }else if (topoPilha == TypeToken.NTMORE_VAR && primeiroLista == TypeToken.COMMA){// <MORE_VAR> == COMMA
            p20();
        }else if (topoPilha == TypeToken.NTCONTENT_WRITE){
            if (primeiroLista == TypeToken.ID || primeiroLista == TypeToken.STRING || primeiroLista == TypeToken.NUMBER){// <CONTENT_WRITE> == STRING OU <CONTENT_WRITE> == ID OU <CONTENT_WRITE> == NUMBER
                p21();
            }
        }else if (topoPilha == TypeToken.NTCONTENT_LIST){
            if (primeiroLista == TypeToken.CLOSE_PAR){// <CONTENT_LIST> == CLOSE_PAR
                p23();
            }else if (primeiroLista == TypeToken.COMMA){// <CONTENT_LIST> == COMMA
                p22();
            }
        }else if (topoPilha == TypeToken.NTMORE_CONTENT && primeiroLista == TypeToken.COMMA){// <MORE_CONTENT> == COMMA
            p24();
        }else if(topoPilha == TypeToken.NTCONTENT){
            if (primeiroLista == TypeToken.ID){// <CONTENT> == ID
                p25();
            }else if (primeiroLista == TypeToken.STRING){// <CONTENT> == STRING
                p26();
            }else if (primeiroLista == TypeToken.NUMBER){// <CONTENT> == NUMBER
                p27();
            }
        }else if (topoPilha == TypeToken.NTASSIGN){
            if (primeiroLista == TypeToken.ID){// <ASSIGN> == ID
                p28();
            }else if (primeiroLista == TypeToken.ARITHMETIC){//<ASSIGN> == ARITMETIC
                p29();
            }
        }else if (topoPilha == TypeToken.NTARIT){
            // <ARIT> == NUMBER OU <ARIT_LIST> == ID OU <ARIT_LIST> == OPEN_PAR
            if (primeiroLista == TypeToken.NUMBER || primeiroLista == TypeToken.ID || primeiroLista == TypeToken.OPEN_PAR){
                p30();
            }
        }else if (topoPilha == TypeToken.NTVALUE){
            if (primeiroLista == TypeToken.NUMBER){// <VALUE> == NUMBER
                p32();
            }else if (primeiroLista == TypeToken.ID){ // <VALUE> == ID
                p31();
            } else if (primeiroLista == TypeToken.OPEN_PAR){// <VALUE> == OPEN_PAR
                p33();
            }
        }else if (topoPilha == TypeToken.NTARIT_LIST){
            if (primeiroLista == TypeToken.CLOSE_PAR || primeiroLista == TypeToken.SEMICOLON){// <ARIT_LIST> == CLOSE_PAR OU <ARIT_LIST> == SEMICOLON
                p35();
            }else if (primeiroLista == TypeToken.ARITHMETIC){// <ARIT_LIST> == ARIT
                p34();
            }
        }else if (topoPilha == TypeToken.NTMORE_ARIT && primeiroLista == TypeToken.ARITHMETIC){// <MORE_ARIT> == ARIT
            p36();
        }else if (topoPilha == TypeToken.NTEXPRESSION){
            //<EXPRESSION> == NUMBER OU <EXORESSION> == ID OU <EXPRESSION> == OPEN_PAR
            if (primeiroLista == TypeToken.NUMBER || primeiroLista == TypeToken.ID || primeiroLista == TypeToken.OPEN_PAR){
                p37();
            }
        }else if (topoPilha == TypeToken.NTCONDITION) {
            //<CONDITION> == NUMBER OU <CONDITION> == ID OU <CONDITION> == OPEN_PAR
            if (primeiroLista == TypeToken.NUMBER || primeiroLista == TypeToken.ID || primeiroLista == TypeToken.OPEN_PAR){
                p38();
            }
        }else if (topoPilha == TypeToken.NTCONDITION_LIST){
            //<CONDITION_LIST> == CLOSE_PAR OU <CONDITION_LIST> == SEMICOLON
            if (primeiroLista == TypeToken.CLOSE_PAR || primeiroLista == TypeToken.SEMICOLON){
                p40();
                 //<CONDITION_LIST> == AND OU <CONDITION_LIST> == OR
            }else if (primeiroLista == TypeToken.AND || primeiroLista == TypeToken.OR){
                p39();
            }
        }else if (topoPilha == TypeToken.NTMORE_CONDITION) {
            if (primeiroLista == TypeToken.AND || primeiroLista == TypeToken.OR){// <MORE_CONDITION> == AND OU <MORE_CONDITION> == OR 
                p41();
            }
        }else if (topoPilha == TypeToken.NTCONNECTIVES){
            if (primeiroLista == TypeToken.AND) {// <CONNECTIVES> == AND
                p42();
            }else if (primeiroLista == TypeToken.OR){// <CONNECTIVES> == OR
                p43();
            }
        }else if(!stackTokens.isEmpty()){
            System.out.println(primeiroLista + " Inesperado " + " em " + listTokens.get(0).getPosition());
            return false;
        }
        System.out.println("--" + topoPilha);
        return true;
    }

    private static void p0 () {
        stackTokens.push(new Token(TypeToken.CLOSE_BRACE, null, null));
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_BRACE, null, null));
        stackTokens.push(new Token(TypeToken.LABEL_PROGRAM, null, null));
    }

    private static void p1 () {
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, null, null));
        stackTokens.push(new Token(TypeToken.NTCOMMAND, null, null));
    }

    private static void p2 () {
    }

    private static void p3 () {
        stackTokens.push(new Token(TypeToken.NTDECLARATION, null, null));
    }

    private static void p4 () {
        stackTokens.push(new Token(TypeToken.NTWRITE, null, null));
    }

    private static void p5 () { 
        stackTokens.push(new Token(TypeToken.NTREAD, null, null));
    }

    private static void p6 () {
        stackTokens.push(new Token(TypeToken.NTASSIGNS, null, null));
    }

    private static void p7 () {
        stackTokens.push(new Token(TypeToken.NTIF, null, null));
    }

    private static void p8 () {
        stackTokens.push(new Token(TypeToken.NTWHILE, null, null));
    }

    private static void p9 () {
        stackTokens.push(new Token(TypeToken.NTFOR, null, null));
    }

    private static void p10 () {
        stackTokens.push(new Token(TypeToken.SEMICOLON, null, null));
        stackTokens.push(new Token(TypeToken.NTVAR_LIST, null, null));
        stackTokens.push(new Token(TypeToken.ID, null, null));
        stackTokens.push(new Token(TypeToken.LABEL_VAR, null, null));
    }
    
    private static void p11 () {  
        stackTokens.push(new Token(TypeToken.SEMICOLON, null, null));
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, null, null));
        stackTokens.push(new Token(TypeToken.NTCONTENT_WRITE, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, null, null));
        stackTokens.push(new Token(TypeToken.WRITE, null, null));
    }
    
    private static void p12 () {
        stackTokens.push(new Token(TypeToken.SEMICOLON, null, null));
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, null, null));
        stackTokens.push(new Token(TypeToken.ID, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, null, null));
        stackTokens.push(new Token(TypeToken.READ, null, null));
    }
    
    private static void p13 () {
        stackTokens.push(new Token(TypeToken.SEMICOLON, null, null));
        stackTokens.push(new Token(TypeToken.NTASSIGN, null, null));
    }
   
    private static void p14 () {
        stackTokens.push(new Token(TypeToken.CLOSE_BRACE, null, null));
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_BRACE, null, null));
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, null, null));
        stackTokens.push(new Token(TypeToken.NTEXPRESSION, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, null, null));
        stackTokens.push(new Token(TypeToken.IF, null, null));
    }

    private static void p15 () {
        stackTokens.push(new Token(TypeToken.CLOSE_BRACE, null, null));
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_BRACE, null, null));
        stackTokens.push(new Token(TypeToken.ELSE, null, null));
    }

    private static void p16 () {
        stackTokens.push(new Token(TypeToken.CLOSE_BRACE, null, null));
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_BRACE, null, null));
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, null, null));
        stackTokens.push(new Token(TypeToken.NTEXPRESSION, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, null, null));
        stackTokens.push(new Token(TypeToken.WHILE, null, null));
    }

    private static void p17 () {
        stackTokens.push(new Token(TypeToken.CLOSE_BRACE, null, null));
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_BRACE, null, null));
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, null, null));
        stackTokens.push(new Token(TypeToken.NTASSIGN, null, null));
        stackTokens.push(new Token(TypeToken.SEMICOLON, null, null));
        stackTokens.push(new Token(TypeToken.NTEXPRESSION, null, null));
        stackTokens.push(new Token(TypeToken.SEMICOLON, null, null));
        stackTokens.push(new Token(TypeToken.NTASSIGN, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, null, null));
        stackTokens.push(new Token(TypeToken.FOR, null, null));
    }

    private static void p18 () {
        stackTokens.push(new Token(TypeToken.NTVAR_LIST, null, null));
        stackTokens.push(new Token(TypeToken.NTMORE_VAR, null, null));
    }

    private static void p19 () {
    }

    private static void p20 () {
        stackTokens.push(new Token(TypeToken.ID, null, null));
        stackTokens.push(new Token(TypeToken.COMMA, null, null));
    }

    private static void p21 () {  
        stackTokens.push(new Token(TypeToken.NTCONTENT_LIST, null, null));
        stackTokens.push(new Token(TypeToken.NTCONTENT, null, null));
    }

    private static void p22 () {
        stackTokens.push(new Token(TypeToken.NTCONTENT_LIST, null, null));
        stackTokens.push(new Token(TypeToken.NTMORE_CONTENT, null, null));
    }

    private static void p23 () {   
    }

    private static void p24 () {
        stackTokens.push(new Token(TypeToken.NTCONTENT, null, null));
        stackTokens.push(new Token(TypeToken.COMMA, null, null));
    }

    private static void p25 () {
        stackTokens.push(new Token(TypeToken.ID, null, null));
    }
    private static void p26 () {
        stackTokens.push(new Token(TypeToken.STRING, null, null));
    }

    private static void p27 () {
        stackTokens.push(new Token(TypeToken.NUMBER, null, null));
    }

    private static void p28 () {  
        stackTokens.push(new Token(TypeToken.NTARIT, null, null));
        stackTokens.push(new Token(TypeToken.ASSIGN, null, null));
        stackTokens.push(new Token(TypeToken.ID, null, null));
    }

    private static void p29 () {   
        stackTokens.push(new Token(TypeToken.ID, null, null));
        stackTokens.push(new Token(TypeToken.ARITHMETIC, null, null));
    }

    private static void p30 () {
        stackTokens.push(new Token(TypeToken.NTARIT_LIST, null, null));
        stackTokens.push(new Token(TypeToken.NTVALUE, null, null));
    }

    private static void p31 () { 
        stackTokens.push(new Token(TypeToken.ID, null, null));
    }

    private static void p32 () {
        stackTokens.push(new Token(TypeToken.NUMBER, null, null));
    }

    private static void p33 () {
        
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, null, null));
        stackTokens.push(new Token(TypeToken.NTARIT, null, null));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, null, null));
    }

    private static void p34 () {
        stackTokens.push(new Token(TypeToken.NTARIT_LIST, null, null));
        stackTokens.push(new Token(TypeToken.NTMORE_ARIT, null, null));
    }

    private static void p35 () {
    }

    private static void p36 () {   
        stackTokens.push(new Token(TypeToken.NTVALUE, null, null));
        stackTokens.push(new Token(TypeToken.ARITHMETIC, null, null));
    }

    private static void p37 () {    
        stackTokens.push(new Token(TypeToken.NTCONDITION_LIST, null, null));
        stackTokens.push(new Token(TypeToken.NTCONDITION, null, null));
    }

    private static void p38 () {
        stackTokens.push(new Token(TypeToken.NTVALUE, null, null));
        stackTokens.push(new Token(TypeToken.LOGIC, null, null));
        stackTokens.push(new Token(TypeToken.NTVALUE, null, null));
    }

    private static void p39 () {
        stackTokens.push(new Token(TypeToken.NTCONDITION_LIST, null, null));
        stackTokens.push(new Token(TypeToken.NTMORE_CONDITION, null, null));
    }

    private static void p40 () {
    }

    private static void p41 () { 
        stackTokens.push(new Token(TypeToken.NTCONDITION, null, null));
        stackTokens.push(new Token(TypeToken.NTCONNECTIVES, null, null));
    }

    private static void p42 () {
        stackTokens.push(new Token(TypeToken.AND, null, null));
    }
    private static void p43 () {
        stackTokens.push(new Token(TypeToken.OR, null, null));
    }
}
