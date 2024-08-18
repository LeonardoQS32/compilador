package com.meuCompilador.analisadores;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.meuCompilador.exceptions.SintaticoException;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.model.enums.TypeToken;

public class Sintatico {

    private static List<Token> listTokens;
    private static Stack<Token> stackTokens;
    private static List<String> unstackedList;

    public static boolean checkSyntax (List<Token> list) throws SintaticoException {
        unstackedList = new ArrayList<>();
        listTokens = new ArrayList<>(list);
        stackTokens = new Stack<>();
        listTokens.add(new Token(TypeToken.SIGN, "$"));
        stackTokens.push(new Token(TypeToken.SIGN, "$"));
        stackTokens.push(new Token(TypeToken.NTPROGRAM, "<PROGRAM>"));
        while (!stackTokens.isEmpty() && !listTokens.isEmpty() ){
            compare();
        }

        return stackTokens.isEmpty() && listTokens.isEmpty();   
    }

    public static List<String> getList (List<Token> list) throws SintaticoException {
        checkSyntax(list);
        return unstackedList;
    }

    private static void compare () throws SintaticoException{   
        String lexema = stackTokens.peek().getLexema();
        TypeToken topoPilha = stackTokens.pop().getType();
        TypeToken primeiroLista = listTokens.get(0).getType();
        if (topoPilha == primeiroLista){
            listTokens.remove(0);
        }else{
            switch (topoPilha) {
                case TypeToken.NTPROGRAM:
                    // <PROGRAM> == Label_program
                    if (primeiroLista == TypeToken.LABEL_PROGRAM)
                        p0();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;
                
                case TypeToken.NTCOMMAND_LIST:
                    //<COMANDO_LIST> == id ou arithmetic ou label_var ou write ou read ou if ou else ou while ou for
                    if (primeiroLista == TypeToken.ID || primeiroLista == TypeToken.ARITHMETIC || primeiroLista == TypeToken.LABEL_VAR 
                    || primeiroLista == TypeToken.WRITE || primeiroLista == TypeToken.READ || primeiroLista == TypeToken.IF 
                    || primeiroLista == TypeToken.ELSE || primeiroLista == TypeToken.WHILE || primeiroLista == TypeToken.FOR)
                        p1();
                    else if (primeiroLista == TypeToken.CLOSE_BRACE)// <COMANDO_LIST> == close_brace
                        p2();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;
                
                case TypeToken.NTCOMMAND:
                    // <COMAND> == ID OU <COMAND> == ARIT
                    if (primeiroLista == TypeToken.ID || primeiroLista == TypeToken.ARITHMETIC)
                        p6();
                    else if (primeiroLista == TypeToken.LABEL_VAR)// <COMAND> == Label_var
                        p3();
                    else if (primeiroLista == TypeToken.WRITE)// <COMAND> == write
                        p4();
                    else if (primeiroLista == TypeToken.READ)// <COMAND> == read
                        p5();
                    else if (primeiroLista == TypeToken.IF || primeiroLista == TypeToken.ELSE)// <COMAND> == if ou <COMANDO> == else
                        p7();
                    else if (primeiroLista == TypeToken.WHILE)// <COMAND> == while
                        p8();
                    else if (primeiroLista == TypeToken.FOR)// <COMAND> == for
                        p9();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTDECLARATION:
                    //<DECLARATION> == Label_var
                    if (primeiroLista == TypeToken.LABEL_VAR)
                        p10();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;    

                case TypeToken.NTWRITE:
                    //<WRITE> == WRITE
                    if (primeiroLista == TypeToken.WRITE)
                        p11();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTREAD:
                    //<READ> == READ
                    if (primeiroLista == TypeToken.READ)
                        p12();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTASSIGNS:
                    //<ASSIGNS> == ID OU <ASSIGNS> == ARITHMETIC
                    if (primeiroLista == TypeToken.ID || primeiroLista == TypeToken.ARITHMETIC)
                        p13();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTIF:
                    if (primeiroLista == TypeToken.IF)// <IF> == IF
                        p14();
                    else if (primeiroLista == TypeToken.ELSE)// <IF> == ELSE
                        p15();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTWHILE:
                    //<WHILE> == WHILE
                    if (primeiroLista == TypeToken.WHILE)
                        p16();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTFOR:
                    //<FOR> == FOR
                    if (primeiroLista == TypeToken.FOR)
                        p17();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTVAR_LIST:
                    if (primeiroLista == TypeToken.SEMICOLON)// <VAR_LIST> == SEMICOLON
                        p19();
                    else if (primeiroLista == TypeToken.COMMA)// <VAR_LIST> == COMMA
                        p18();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTMORE_VAR:
                    //<MORE_VAR> == COMMA
                    if (primeiroLista == TypeToken.COMMA)
                        p20();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTCONTENT_WRITE:
                    // <CONTENT_WRITE> == STRING OU <CONTENT_WRITE> == ID OU <CONTENT_WRITE> == NUMBER
                    if (primeiroLista == TypeToken.ID || primeiroLista == TypeToken.STRING || primeiroLista == TypeToken.NUMBER)
                        p21();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTCONTENT_LIST:
                    if (primeiroLista == TypeToken.CLOSE_PAR)// <CONTENT_LIST> == CLOSE_PAR
                        p23();
                    else if (primeiroLista == TypeToken.COMMA)// <CONTENT_LIST> == COMMA
                        p22();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTMORE_CONTENT:
                    //<MORE_CONTENT> == COMMA
                    if (primeiroLista == TypeToken.COMMA)
                        p24();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTCONTENT:
                    if (primeiroLista == TypeToken.ID)// <CONTENT> == ID
                        p25();
                    else if (primeiroLista == TypeToken.STRING)// <CONTENT> == STRING
                        p26();
                    else if (primeiroLista == TypeToken.NUMBER)// <CONTENT> == NUMBER
                        p27();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTASSIGN:
                    if (primeiroLista == TypeToken.ID)// <ASSIGN> == ID
                        p28();
                    else if (primeiroLista == TypeToken.ARITHMETIC)//<ASSIGN> == ARITMETIC
                        p29();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTARIT:
                    // <ARIT> == NUMBER OU <ARIT_LIST> == ID OU <ARIT_LIST> == OPEN_PAR
                    if (primeiroLista == TypeToken.NUMBER || primeiroLista == TypeToken.ID || primeiroLista == TypeToken.OPEN_PAR)
                        p30();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTVALUE:
                    if (primeiroLista == TypeToken.NUMBER)// <VALUE> == NUMBER
                        p32();
                    else if (primeiroLista == TypeToken.ID)// <VALUE> == ID
                        p31();
                     else if (primeiroLista == TypeToken.OPEN_PAR)// <VALUE> == OPEN_PAR
                        p33();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTARIT_LIST:
                    // <ARIT_LIST> == CLOSE_PAR OU <ARIT_LIST> == SEMICOLON
                    if (primeiroLista == TypeToken.CLOSE_PAR || primeiroLista == TypeToken.SEMICOLON)
                        p35();
                    else if (primeiroLista == TypeToken.ARITHMETIC)// <ARIT_LIST> == ARIT
                        p34();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTMORE_ARIT:
                    //<MORE_ARIT> == ARITHMETIC
                    if (primeiroLista == TypeToken.ARITHMETIC)
                        p36();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTEXPRESSION:
                    //<EXPRESSION> == NUMBER OU <EXORESSION> == ID OU <EXPRESSION> == OPEN_PAR
                    if (primeiroLista == TypeToken.NUMBER || primeiroLista == TypeToken.ID || primeiroLista == TypeToken.OPEN_PAR)
                        p37();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTCONDITION:
                    //<CONDITION> == NUMBER OU <CONDITION> == ID OU <CONDITION> == OPEN_PAR
                    if (primeiroLista == TypeToken.NUMBER || primeiroLista == TypeToken.ID || primeiroLista == TypeToken.OPEN_PAR)
                        p38();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTCONDITION_LIST:
                    //<CONDITION_LIST> == CLOSE_PAR OU <CONDITION_LIST> == SEMICOLON
                    if (primeiroLista == TypeToken.CLOSE_PAR || primeiroLista == TypeToken.SEMICOLON)
                        p40();
                    //<CONDITION_LIST> == AND OU <CONDITION_LIST> == OR
                    else if (primeiroLista == TypeToken.AND || primeiroLista == TypeToken.OR)
                        p39();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTMORE_CONDITION:
                    // <MORE_CONDITION> == AND OU <MORE_CONDITION> == OR 
                    if (primeiroLista == TypeToken.AND || primeiroLista == TypeToken.OR)
                        p41();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;

                case TypeToken.NTCONNECTIVES:
                    if (primeiroLista == TypeToken.AND) // <CONNECTIVES> == AND
                        p42();
                    else if (primeiroLista == TypeToken.OR)// <CONNECTIVES> == OR
                        p43();
                    else throw new SintaticoException (primeiroLista + " inesperado.", unstackedList);
                        break;
                default:
                    if ( !stackTokens.isEmpty()) throw new SintaticoException("era esperado um " + lexema, unstackedList);
            }
        }
        unstackedList.add(lexema);
    }

    private static void p0 () {
        stackTokens.push(new Token(TypeToken.CLOSE_BRACE, "close_brace"));
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, "<COMMAD_LIST>"));
        stackTokens.push(new Token(TypeToken.OPEN_BRACE, "open_brace"));
        stackTokens.push(new Token(TypeToken.LABEL_PROGRAM, "label_program"));
    }

    private static void p1 () {
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, "<COMMAD_LIST>"));
        stackTokens.push(new Token(TypeToken.NTCOMMAND, "<COMMAND>"));
    }

    private static void p2 () {
        
    }

    private static void p3 () {
        stackTokens.push(new Token(TypeToken.NTDECLARATION, "<DECLARATION>"));
    }

    private static void p4 () {
        stackTokens.push(new Token(TypeToken.NTWRITE, "<WRITE>"));
    }

    private static void p5 () { 
        stackTokens.push(new Token(TypeToken.NTREAD, "<READ>"));
    }

    private static void p6 () {
        stackTokens.push(new Token(TypeToken.NTASSIGNS, "<ASSIGNS>"));
    }

    private static void p7 () {
        stackTokens.push(new Token(TypeToken.NTIF, "<IF>"));
    }

    private static void p8 () {
        stackTokens.push(new Token(TypeToken.NTWHILE, "<WHILE>"));
    }

    private static void p9 () {
        stackTokens.push(new Token(TypeToken.NTFOR, "<FOR>"));
    }

    private static void p10 () {
        stackTokens.push(new Token(TypeToken.SEMICOLON, "semicolon"));
        stackTokens.push(new Token(TypeToken.NTVAR_LIST, "<VAR_LIST>"));
        stackTokens.push(new Token(TypeToken.ID, "id"));
        stackTokens.push(new Token(TypeToken.LABEL_VAR, "label_var"));
    }
    
    private static void p11 () {  
        stackTokens.push(new Token(TypeToken.SEMICOLON, "semicolon"));
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, "close_par"));
        stackTokens.push(new Token(TypeToken.NTCONTENT_WRITE, "<CONTENT_WRITE>"));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, "open_par"));
        stackTokens.push(new Token(TypeToken.WRITE, "write"));
    }
    
    private static void p12 () {
        stackTokens.push(new Token(TypeToken.SEMICOLON, "semicolon"));
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, "close_par"));
        stackTokens.push(new Token(TypeToken.ID, "id"));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, "open_par"));
        stackTokens.push(new Token(TypeToken.READ, "read"));
    }
    
    private static void p13 () {
        stackTokens.push(new Token(TypeToken.SEMICOLON, "semicolon"));
        stackTokens.push(new Token(TypeToken.NTASSIGN, "<ASSIGN>"));
    }
   
    private static void p14 () {
        stackTokens.push(new Token(TypeToken.CLOSE_BRACE, "close_brace"));
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, "<COMMAD_LIST>"));
        stackTokens.push(new Token(TypeToken.OPEN_BRACE, "open_brace"));
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, "close_par"));
        stackTokens.push(new Token(TypeToken.NTEXPRESSION, "<EXPRESSION>"));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, "open_par"));
        stackTokens.push(new Token(TypeToken.IF, "if"));
    }

    private static void p15 () {
        stackTokens.push(new Token(TypeToken.CLOSE_BRACE, "close_brace"));
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, "<COMMAD_LIST>"));
        stackTokens.push(new Token(TypeToken.OPEN_BRACE, "open_brace"));
        stackTokens.push(new Token(TypeToken.ELSE, "else"));
    }

    private static void p16 () {
        stackTokens.push(new Token(TypeToken.CLOSE_BRACE, "close_brace"));
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, "<COMMAD_LIST>"));
        stackTokens.push(new Token(TypeToken.OPEN_BRACE, "open_brace"));
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, "close_par"));
        stackTokens.push(new Token(TypeToken.NTEXPRESSION, "<EXPRESSION>"));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, "open_par"));
        stackTokens.push(new Token(TypeToken.WHILE, "while"));
    }

    private static void p17 () {
        stackTokens.push(new Token(TypeToken.CLOSE_BRACE, "close_brace"));
        stackTokens.push(new Token(TypeToken.NTCOMMAND_LIST, "<COMMAD_LIST>"));
        stackTokens.push(new Token(TypeToken.OPEN_BRACE, "open_brace"));
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, "close_par"));
        stackTokens.push(new Token(TypeToken.NTASSIGN, "<ASSIGN>"));
        stackTokens.push(new Token(TypeToken.SEMICOLON, "semicolon"));
        stackTokens.push(new Token(TypeToken.NTEXPRESSION, "<EXPRESSION>"));
        stackTokens.push(new Token(TypeToken.SEMICOLON, "semicolon"));
        stackTokens.push(new Token(TypeToken.NTASSIGN, "<ASSIGN>"));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, "open_par"));
        stackTokens.push(new Token(TypeToken.FOR, "for"));
    }

    private static void p18 () {
        stackTokens.push(new Token(TypeToken.NTVAR_LIST, "<VAR_LIST>"));
        stackTokens.push(new Token(TypeToken.NTMORE_VAR, "<MORE_VAR>"));
    }

    private static void p19 () {
        
    }

    private static void p20 () {
        stackTokens.push(new Token(TypeToken.ID, "id"));
        stackTokens.push(new Token(TypeToken.COMMA, "comma"));
    }

    private static void p21 () {  
        stackTokens.push(new Token(TypeToken.NTCONTENT_LIST, "<CONTENT_LIST>"));
        stackTokens.push(new Token(TypeToken.NTCONTENT, "<CONTENT>"));
    }

    private static void p22 () {
        stackTokens.push(new Token(TypeToken.NTCONTENT_LIST, "<CONTENT_LIST>"));
        stackTokens.push(new Token(TypeToken.NTMORE_CONTENT, "<MORE_CONTENT>"));
    }

    private static void p23 () {  
         
    }

    private static void p24 () {
        stackTokens.push(new Token(TypeToken.NTCONTENT, "<CONTENT>"));
        stackTokens.push(new Token(TypeToken.COMMA, "comma"));
    }

    private static void p25 () {
        stackTokens.push(new Token(TypeToken.ID, "id"));
    }
    private static void p26 () {
        stackTokens.push(new Token(TypeToken.STRING, "string"));
    }

    private static void p27 () {
        stackTokens.push(new Token(TypeToken.NUMBER, "number"));
    }

    private static void p28 () {  
        stackTokens.push(new Token(TypeToken.NTARIT, "<ARITHMETIC>"));
        stackTokens.push(new Token(TypeToken.ASSIGN, "assign"));
        stackTokens.push(new Token(TypeToken.ID, "id"));
    }

    private static void p29 () {   
        stackTokens.push(new Token(TypeToken.ID, "id"));
        stackTokens.push(new Token(TypeToken.ARITHMETIC, "arithmetic"));
    }

    private static void p30 () {
        stackTokens.push(new Token(TypeToken.NTARIT_LIST, "<VAR_LIST>"));
        stackTokens.push(new Token(TypeToken.NTVALUE, "<VALUE>"));
    }

    private static void p31 () { 
        stackTokens.push(new Token(TypeToken.ID, "id"));
    }

    private static void p32 () {
        stackTokens.push(new Token(TypeToken.NUMBER, "number"));
    }

    private static void p33 () {
        
        stackTokens.push(new Token(TypeToken.CLOSE_PAR, "close_par"));
        stackTokens.push(new Token(TypeToken.NTARIT, "<ARITHMETIC>"));
        stackTokens.push(new Token(TypeToken.OPEN_PAR, "open_par"));
    }

    private static void p34 () {
        stackTokens.push(new Token(TypeToken.NTARIT_LIST, "<VAR_LIST>"));
        stackTokens.push(new Token(TypeToken.NTMORE_ARIT, "<MORE_ARIT>"));
    }

    private static void p35 () {
        
    }

    private static void p36 () {   
        stackTokens.push(new Token(TypeToken.NTVALUE, "<VALUE>"));
        stackTokens.push(new Token(TypeToken.ARITHMETIC, "arithmetic"));
    }

    private static void p37 () {    
        stackTokens.push(new Token(TypeToken.NTCONDITION_LIST, "<CONDITION_LIST>"));
        stackTokens.push(new Token(TypeToken.NTCONDITION, "<CONDITION>"));
    }

    private static void p38 () {
        stackTokens.push(new Token(TypeToken.NTVALUE, "<VALUE>"));
        stackTokens.push(new Token(TypeToken.LOGIC, "logic"));
        stackTokens.push(new Token(TypeToken.NTVALUE, "<VALUE>"));
    }

    private static void p39 () {
        stackTokens.push(new Token(TypeToken.NTCONDITION_LIST, "<CONDITION_LIST>"));
        stackTokens.push(new Token(TypeToken.NTMORE_CONDITION, "<MORE_CONDITION>"));
    }

    private static void p40 () {
        
    }

    private static void p41 () { 
        stackTokens.push(new Token(TypeToken.NTCONDITION, "<CONDITION>"));
        stackTokens.push(new Token(TypeToken.NTCONNECTIVES, "<CONNECTIVES>"));
    }

    private static void p42 () {
        stackTokens.push(new Token(TypeToken.AND, "and"));
    }
    private static void p43 () {
        stackTokens.push(new Token(TypeToken.OR, "or"));
    }
}
