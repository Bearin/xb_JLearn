package homeWork.FundamentalOfCompiling.LexicalAnalysis;


import homeWork.FundamentalOfCompiling.LexicalAnalysis.Tools.BinaryFomula;
import homeWork.FundamentalOfCompiling.LexicalAnalysis.Tools.CharSignals;
import homeWork.FundamentalOfCompiling.LexicalAnalysis.Tools.FileUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * 词法分析器
 * Created by bin on 2015/5/2.
 * @author xbearin@gmail.com
 */
public class LexicalAnalysis {

    public static void main(String[] args){
        String path = LexicalAnalysis.class.getResource("").getPath()+"input.pas";
        StringBuilder input = FileUtils.read(path);
        LexicalAnalysis lexicalAnalysis = new LexicalAnalysis(input);
        while(lexicalAnalysis.currentIndex<lexicalAnalysis.inputStr.length()){
            lexicalAnalysis.lexAnalyze();
        }
        FileUtils.outToFile(lexicalAnalysis.bfs, "out.dyd");
        FileUtils.outErrorToFile(lexicalAnalysis.errorList, "error.err");
    }

    //符号表
    private CharSignals charSignals = CharSignals.getInstance();

    //常量表
    private List<String> constanList = new ArrayList<String>();

    private List<String> errorList = new ArrayList<String>();

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
        Character temp = inputStr.charAt(currentIndex++);
        if(temp=='\n'){
            currentLine++;
        }
        return temp;
    }

    public char getnbc(){
        char temp;
        while(true){
            temp = getChar();
            if(temp!=' '&&temp!='\0'){
                return temp;
            }
        }
    }

    public void lexAnalyze(){
        StringBuilder token = new StringBuilder();
        char currentChar;
        currentChar = getnbc();

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
                    bfs.add(charSignals.getBinaryFomula("<="));
                }else {
                    bfs.add(charSignals.getBinaryFomula("<"));
                }
                break;
            case ':':
                currentChar = getChar();
                if(currentChar=='=') {
                    bfs.add(charSignals.getBinaryFomula(":="));
                }else {
                    errorList.add(currentLine+"  符号匹配错误");
                }
                break;
            case '>':
                currentChar = getChar();
                if(currentChar=='=') {
                    bfs.add(charSignals.getBinaryFomula(">="));
                }else {
                    bfs.add(charSignals.getBinaryFomula(">"));
                }
                break;
            case '=':
                currentChar = getChar();
                if(currentChar=='=') {
                    bfs.add(charSignals.getBinaryFomula("=="));
                }else {
                    bfs.add(charSignals.getBinaryFomula("="));
                }
                break;
            case '!':
                currentChar = getChar();
                if (currentChar == '=') {
                    bfs.add(charSignals.getBinaryFomula("!="));
                }else {
                    errorList.add(currentLine+"  符号匹配错误");
                }
                break;
            default:
                if(currentChar=='\r'||currentChar=='\n'||currentChar=='\0'){
                    break;
                }
                String bfStr = charSignals.getBinaryFomula(String.valueOf(currentChar));
                if(bfStr==null) {
                    errorList.add(currentLine+"  错误zzz");
                }else{
                    bfs.add(bfStr);
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

        constanList.add(token.toString());
        bfs.add(new BinaryFomula(token.toString(), constanList.size()-1).toString());
    }

    private void dealLetterAndDigit(Character currentChar, StringBuilder token){
        while(true) {
            token.append(currentChar);
            currentChar = getChar();
            if(currentChar==null){
                break;
            }
            if(!Character.isLetter(currentChar)&&!Character.isDigit(currentChar)) {
                currentIndex--;
                break;
            }
        }

        int num = charSignals.isReserve(token.toString());
        if(num!=0){
            bfs.add(charSignals.getBinaryFomula(token.toString()));
        }else{
            constanList.add(token.toString());
            bfs.add(new BinaryFomula(token.toString(), constanList.size()-1).toString());
        }
    }

}
