package homeWork.FundamentalOfCompiling.LexicalAnalysis;


import homeWork.FundamentalOfCompiling.Tools.BinaryFomula;
import homeWork.FundamentalOfCompiling.Tools.CharStore;
import homeWork.FundamentalOfCompiling.Tools.FileUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * 词法分析器
 * Created by bin on 2015/5/2.
 * @author xbearin@gmail.com
 */
public class LexicalAnalysis {

    public static void main(String[] args){
        start();
    }

    public static LexicalAnalysis start(){
        String path = "input.pas";
        StringBuilder input = FileUtils.read(path);
        lexicalAnalysis = new LexicalAnalysis(input);
        while(lexicalAnalysis.currentIndex<lexicalAnalysis.inputStr.length()){
            lexicalAnalysis.lexAnalyze();
        }
        out();
        return lexicalAnalysis;
    }

    private static void out(){
        lexicalAnalysis.bfs.add(String.format("%16s", "EOLN  24"));
        lexicalAnalysis.bfs.add(String.format("%16s", "EOF  25"));
        FileUtils.outDydToFile(lexicalAnalysis.bfs, "out.dyd");
        FileUtils.outToFile(lexicalAnalysis.charStore.getErrorList(), "lex.err");
    }

    public  CharStore charStore = new CharStore();

    private static LexicalAnalysis lexicalAnalysis;

    //二元式数组
    private List<String> bfs = new ArrayList<String>();

    //输入字符串
    private StringBuilder inputStr;

    private int currentIndex = 0;

    private int currentLine = 1;

    public LexicalAnalysis(StringBuilder str){
        this.inputStr = str;
    }

    private Character getChar(){
        if(currentIndex+1>inputStr.length()){
            return null;
        }
        char temp = inputStr.charAt(currentIndex++);
        if(temp=='\n'){
            currentLine++;
            bfs.add(String.format("%16s", "EOLN  24"));
        }
        return temp;
    }

    public Character getnbc(){
        Character temp;
        while(true){
            temp = getChar();
            if(temp==null){
                return null;
            }
            if(temp!=' '&&temp!='\0'&&temp!='\r'&&temp!='\n'){
                return temp;
            }
        }
    }

    public void lexAnalyze(){
        StringBuilder token = new StringBuilder();
        Character currentChar;
        currentChar = getnbc();
        if(currentChar == null){
            return;
        }else{
            if(Character.isLetter(currentChar)) {
                dealLetterAndDigit(currentChar, token);
            return;
            }else if(Character.isDigit(currentChar)) {
                dealNum(currentChar, token);
            return;
            }
            switch (currentChar) {
                case '<':
                    currentChar = getChar();
                    if(currentChar=='=') {
                        bfs.add(charStore.getBinaryFomula("<="));
                    }else {
                        bfs.add(charStore.getBinaryFomula("<"));
                    }
                    break;
                case ':':
                    currentChar = getChar();
                    if(currentChar=='=') {
                        bfs.add(charStore.getBinaryFomula(":="));
                    }else {
                        charStore.addToErrorList(currentLine + "  LINE  非法字符 "+ inputStr.charAt(currentIndex-2));
                    }
                    break;
                case '>':
                    currentChar = getChar();
                    if(currentChar=='=') {
                        bfs.add(charStore.getBinaryFomula(">="));
                    }else {
                        bfs.add(charStore.getBinaryFomula(">"));
                    }
                    break;
                case '=':
                    currentChar = getChar();
                    if(currentChar=='=') {
                        bfs.add(charStore.getBinaryFomula("=="));
                    }else {
                        bfs.add(charStore.getBinaryFomula("="));
                    }
                    break;
                case '!':
                    currentChar = getChar();
                    if (currentChar == '=') {
                        bfs.add(charStore.getBinaryFomula("!="));
                    }else {
                        charStore.addToErrorList(currentLine+" LINE  非法字符  "+ inputStr.charAt(currentIndex-2));
                    }
                    break;
                default:
                    if(currentChar=='\r'||currentChar=='\n'||currentChar=='\0'){
                        break;
                    }
                    String bfStr = charStore.getBinaryFomula(String.valueOf(currentChar));
                    if(bfStr==null) {
                        charStore.addToErrorList(currentLine + " LINE  符号无定义 "+inputStr.charAt(currentIndex-2));
                    }else{
                        bfs.add(bfStr);
                    }
            }
        }

    }

    private void dealNum(char currentChar, StringBuilder token) {
        while(true) {
            token.append(currentChar);
            currentChar = getChar();
            if(!Character.isDigit(currentChar)) {
                currentIndex--;
                break;
            }
        }
        charStore.addToConstantList(token.toString());

        bfs.add(new BinaryFomula(token.toString(), charStore.getConstantList().size()).toString());
    }

    private void dealLetterAndDigit(Character currentChar, StringBuilder token){
        while(true) {
            token.append(currentChar);
            currentChar = getChar();
            if(currentChar==null){
               return;
            }
            if(!Character.isLetter(currentChar)&&!Character.isDigit(currentChar)) {
                currentIndex--;
                break;
            }
        }
        int num = charStore.isReserve(token.toString());
        if(num!=0){
            bfs.add(charStore.getBinaryFomula(token.toString()));
        }else{
            bfs.add(charStore.addToSymbolList(token.toString()));
        }
    }

}
