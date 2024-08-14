package com.meuCompilador.analisadores;

import java.util.ArrayList;
import java.util.List;

import com.meuCompilador.exceptions.LexicoException;
import com.meuCompilador.model.entities.Position;
import com.meuCompilador.model.entities.Token;
import com.meuCompilador.model.enums.TypeToken;

public class Lexico {

    private static Position position;
    private static int head;
    private static int length;
    private static String sourceCode;
    private static List<Token> list; 

    private static final char []charIgnore = { '\t', '\r', '\f' , ' ', '\b', '\s'};
    private static final char []charArithmetics = {  '/', '+' , '-', '*'};
    private static final char []charLogics = {'>' , '=', '<'};
    
    public static List<Token> checkTokens (String code) throws LexicoException {
        list = new ArrayList<>();
        sourceCode = code;
        position = new Position();
        head = 0;
        q0();
        return list;
    }

    private static void q0 () throws LexicoException{
        while (head < sourceCode.length()){
            length = 0;
            char currentChar = sourceCode.charAt(head);
            forward();
            switch (currentChar) {
                case 'P':
                    q1();
                    break;

                case 'v':
                    q9();
                    break;

                case 'e':
                    q12();
                    break;

                case 'l':
                    q26();
                    break;

                case 's':
                    q30();
                    break;

                case 'p':
                    q35();
                    break;
                case '\'':
                    q39();
                    break;

                case '<':
                    q40();
                    break;

                case '&':
                    q41();
                    break;

                case '|':
                    q42();
                    break;

                case '(':
                    q43();
                    break;

                case ')':
                    q44();
                    break;

                case '{':
                    q45();
                    break;
                
                case '}':
                    q46();
                    break;
                
                case ',':
                    q47();
                    break;

                case ';':
                    q48();
                    break;

                case '\n':
                    breakLine();
                    break;
                
                default:
                    if (Character.isLetter(currentChar)){
                        qId();
                    }else if (Character.isDigit(currentChar)){
                        qNum();
                    }else if (isLogic(currentChar)){
                        qLogic();
                    }else if (isArithmetic(currentChar)){
                        qArit();
                    }else if (!isIgnore(currentChar)){
                        throw new LexicoException("Caracter não identificado na linha " + position.getLine() + ", coluna " + position.getColumn());
                    }
            }
        }
    }


    private static void q1 (){
        if (sourceCode.charAt(head) == 'r'){
            forward();
            q2();
        }else {
            qId();
        }
    }

    private static void q2 (){
        if (sourceCode.charAt(head) == 'o'){
            forward();
            q3();
        }else {
            qId();
        }
    }

    private static void q3 (){
        if (sourceCode.charAt(head) == 'g'){
            forward();
            q4();
        }else {
            qId();
        }
    }

    private static void q4 (){
        if (sourceCode.charAt(head) == 'r'){
            forward();
            q5();
        }else {
            qId();
        }
    }

    private static void q5 (){
        if (sourceCode.charAt(head) == 'a'){
            forward();
            q6();
        }else {
            qId();
        }
    }

    private static void q6 (){
        if (sourceCode.charAt(head) == 'm'){
            forward();
            q7();
        }else {
            qId();
        }
    }

    private static void q7 (){
        if (sourceCode.charAt(head) == 'a'){
            forward();
            q8();
        }else {
            qId();
        }
    }

    private static void q8 (){
        if (!Character.isLetter(sourceCode.charAt(head)) && !Character.isDigit(sourceCode.charAt(head))){
            insereToken(TypeToken.LABEL_PROGRAM, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            qId();
        }
    }

    private static void q9 (){
        if (sourceCode.charAt(head) == 'a'){
            forward();
            q10();
        }else {
            qId();
        }
    }

    private static void q10 (){
        if (sourceCode.charAt(head) == 'r'){
            forward();
            q11();
        }else {
            qId();
        }
    }

    private static void q11 (){
        if (!Character.isLetter(sourceCode.charAt(head)) && !Character.isDigit(sourceCode.charAt(head))){
            insereToken(TypeToken.LABEL_VAR, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            qId();
        }
    }

    private static void q12 (){
        if (sourceCode.charAt(head) == 's'){
            forward();
            q13();
        }else if (sourceCode.charAt(head) == 'n'){
            forward();
            q19();
        }else {
            qId();
        }
    }

    private static void q13 (){
        if (sourceCode.charAt(head) == 'c'){
            forward();
            q14();
        }else {
            qId();
        }
    }

    private static void q14 (){
        if (sourceCode.charAt(head) == 'r'){
            forward();
            q15();
        }else {
            qId();
        }
    }

    private static void q15 (){
        if (sourceCode.charAt(head) == 'e'){
            forward();
            q16();
        }else {
            qId();
        }
    }

    private static void q16 (){
        if (sourceCode.charAt(head) == 'v'){
            forward();
            q17();
        }else {
            qId();
        }
    }

    private static void q17 (){
        if (sourceCode.charAt(head) == 'a'){
            forward();
            q18();
        }else {
            qId();
        }
    }

    private static void q18 (){
        if (!Character.isLetter(sourceCode.charAt(head)) && !Character.isDigit(sourceCode.charAt(head))){
            insereToken(TypeToken.WRITE, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            qId();
        }
    }

    private static void q19 (){
        if (sourceCode.charAt(head) == 'q'){
            forward();
            q20();
        }else {
            qId();
        }
    }

    private static void q20 (){
        if (sourceCode.charAt(head) == 'u'){
            forward();
            q21();
        }else {
            qId();
        }
    }

    private static void q21 (){
        if (sourceCode.charAt(head) == 'a'){
            forward();
            q22();
        }else {
            qId();
        }
    }

    private static void q22 (){
        if (sourceCode.charAt(head) == 'n'){
            forward();
            q23();
        }else {
            qId();
        }
    }

    private static void q23 (){
        if (sourceCode.charAt(head) == 't'){
            forward();
            q24();
        }else {
            qId();
        }
    }

    private static void q24 (){
        if (sourceCode.charAt(head) == 'o'){
            forward();
            q25();
        }else {
            qId();
        }
    }

    private static void q25 (){
        if (!Character.isLetter(sourceCode.charAt(head)) && !Character.isDigit(sourceCode.charAt(head))){
            insereToken(TypeToken.WHILE, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            qId();
        }
    }

    private static void q26 (){
        if (sourceCode.charAt(head) == 'e'){
            forward();
            q27();
        }else {
            qId();
        }
    }

    private static void q27 (){
        if (sourceCode.charAt(head) == 'i'){
            forward();
            q28();
        }else {
            qId();
        }
    }

    private static void q28 (){
        if (sourceCode.charAt(head) == 'a'){
            forward();
            q29();
        }else {
            qId();
        }
    }

    private static void q29 (){
        if (!Character.isLetter(sourceCode.charAt(head)) && !Character.isDigit(sourceCode.charAt(head))){
            insereToken(TypeToken.READ, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            qId();
        }
    }

    private static void q30 (){
        if (sourceCode.charAt(head) == 'e'){
            forward();
            q31();
        }else {
            qId();
        }
    }

    private static void q31 (){
        if (!Character.isLetter(sourceCode.charAt(head)) && !Character.isDigit(sourceCode.charAt(head))){
            insereToken(TypeToken.IF, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else if (sourceCode.charAt(head) == 'n') {
            q32();
        }else {
            qId();
        }
    }

    private static void q32 (){
        if (sourceCode.charAt(head) == 'a'){
            forward();
            q33();
        }else {
            qId();
        }
    }

    private static void q33 (){
        if (sourceCode.charAt(head) == 'o'){
            forward();
            q34();
        }else {
            qId();
        }
    }

    private static void q34 (){
        if (!Character.isLetter(sourceCode.charAt(head)) && !Character.isDigit(sourceCode.charAt(head))){
            insereToken(TypeToken.ELSE, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            qId();
        }
    }

    private static void q35 (){
        if (sourceCode.charAt(head) == 'a'){
            forward();
            q36();
        }else {
            qId();
        }
    }

    private static void q36 (){
        if (sourceCode.charAt(head) == 'r'){
            forward();
            q37();
        }else {
            qId();
        }
    }

    private static void q37 (){
        if (sourceCode.charAt(head) == 'a'){
            forward();
            q38();
        }else {
            qId();
        }
    }

    private static void q38 (){
        if (!Character.isLetter(sourceCode.charAt(head)) && !Character.isDigit(sourceCode.charAt(head))){
            insereToken(TypeToken.FOR, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            qId();
        }
    }

    private static void q39() {
        if (sourceCode.charAt(head) == '\''){
            forward();
            insereToken(TypeToken.STRING, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            forward();
            q39();
        }
    }

    private static void q40(){
        if (sourceCode.charAt(head) == '-'){
            forward();
            insereToken(TypeToken.ASSIGN, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            qLogic();
        }
    }

    private static void q41() throws LexicoException{
        if (sourceCode.charAt(head) == '&'){
            forward();
            insereToken(TypeToken.AND, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            throw new LexicoException("Caracter não identificado na linha " + position.getLine() + ", coluna " + position.getColumn());
        }
    }

    private static void q42() throws LexicoException{
        if (sourceCode.charAt(head) == '|'){
            forward();
            insereToken(TypeToken.OR, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn()- length));
        }else {
            throw new LexicoException("Caracter não identificado na linha " + position.getLine() + ", coluna " + position.getColumn());
        }
    }

    private static void q43(){
        insereToken(TypeToken.OPEN_PAR, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
    }

    private static void q44(){
        insereToken(TypeToken.CLOSE_PAR, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
    }

    private static void q45(){
        insereToken(TypeToken.OPEN_BRACE, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
    }

    private static void q46(){
        insereToken(TypeToken.CLOSE_BRACE, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
    }

    private static void q47(){
        insereToken(TypeToken.COMMA, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
    }

    private static void q48(){
        insereToken(TypeToken.SEMICOLON, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
    }

    private static void qId () {
        if (Character.isLetter(sourceCode.charAt(head)) || Character.isDigit(sourceCode.charAt(head))){
            forward();
            qId();
        }else {
            insereToken(TypeToken.ID, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));     
        }
    }

    private static void qNum () {
        if ( Character.isDigit(sourceCode.charAt(head))){
            forward();
            qNum();
        }else if (sourceCode.charAt(head) == '.'){
            forward();
            qNumDouble();
        }else {
            insereToken(TypeToken.NUMBER, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));     
        }
    }

    private static void qNumDouble () {
        if ( Character.isDigit(sourceCode.charAt(head))){
            forward();
            qNumDouble();
        }else {
            insereToken(TypeToken.NUMBER, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));     
        }
    }

    private static void qArit () {
        insereToken(TypeToken.ARITHMETIC, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
    }

    private static void qLogic () {
        if (isLogic(sourceCode.charAt(head))){
            forward();
            insereToken(TypeToken.LOGIC, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }else {
            insereToken(TypeToken.LOGIC, sourceCode.substring(head - length, head), new Position(position.getLine(), position.getColumn() - length));
        }
    }

    private static void insereToken (TypeToken type, String lexema, Position position){
        list.add(new Token(type, lexema, position));
    }
    private static void incrementHead () { head++;}

    private static void incrementLength () { length++;}

    private static void forward () {
        incrementHead();
        position.incrementColumn();
        incrementLength();
    }

    private static void breakLine() {
        position.incrementLine();
        position.setColumn(1);
    }

    private static boolean isLogic (char ch) {
        for (char logic : charLogics){
            if (logic == ch) return true;
        }
        return false;
    }

    private static boolean isArithmetic (char ch) {
        for (char arithmetic : charArithmetics){
            if (arithmetic == ch) return true;
        }
        return false;
    }

    private static boolean isIgnore (char ch) {
        for (char ignore : charIgnore){
            if (ignore == ch) return true;
        }
        return false;
    }
}
