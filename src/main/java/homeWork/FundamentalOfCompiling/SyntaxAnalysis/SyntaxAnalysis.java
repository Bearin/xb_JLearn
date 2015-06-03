package homeWork.FundamentalOfCompiling.SyntaxAnalysis;

import homeWork.FundamentalOfCompiling.LexicalAnalysis.LexicalAnalysis;
import homeWork.FundamentalOfCompiling.Tools.BinaryFomula;
import homeWork.FundamentalOfCompiling.Tools.CharStore;
import homeWork.FundamentalOfCompiling.Tools.FileUtils;

import java.util.*;

import static homeWork.FundamentalOfCompiling.SyntaxAnalysis.Constant.*;

/**
 * Created by bin on 2015/5/8.
 *
 * @author xbearin@gmail.com
 */
public class SyntaxAnalysis {

    public static void main(String[] args) {
        LexicalAnalysis lexicalAnalysis = LexicalAnalysis.start();
        start(lexicalAnalysis.charStore);
    }

    public static void start(CharStore charStore){
        SyntaxAnalysis syntaxAnalysis = new SyntaxAnalysis();
        if(syntaxAnalysis.judgeErrorFile("lex.err")){
            System.out.println("源代码词法有误");
        }else{
            syntaxAnalysis.charStore = charStore;
            //read from .dyd file
            syntaxAnalysis.inputStrList = FileUtils.readToList("out.dyd", syntaxAnalysis.lines);
            while(syntaxAnalysis.currentIndex <syntaxAnalysis.inputStrList.size()-1){
                syntaxAnalysis.program();
            }
            syntaxAnalysis.outResults();
        }
    }

    private Queue<Integer> lines = new LinkedList<Integer>();

    private CharStore charStore;

    private List<Variable>  vTable = new ArrayList<Variable>();

    private List<Procedure>  pTable = new ArrayList<Procedure>();

    private List<String> errorList = new ArrayList<String>();

    private int currentIndex = 0;

    private int currentLine = 1;

    private int currentLevel = 1;

    private int curVarKind = VARIABLE_TYPE;

    private int curVarType = -1;

    private Stack<String> curProQueue = new Stack<String>();

    private List<BinaryFomula> inputStrList;

    private void outResults(){
        System.out.println("错误信息: ");
        for(String t: errorList){
            System.out.println(t.toString());
        }
        FileUtils.outToFile(errorList, "syn.error");

        System.out.println("变量表: ");
        for(Variable t: vTable){
            System.out.println(t.toString());
        }
        FileUtils.outToFile(vTable, "syn.var");

        System.out.println("过程表: ");
        for(Procedure t: pTable){
            System.out.println(t.toString());
        }
        FileUtils.outToFile(pTable, "syn.pro");

        System.exit(-1);
    }

    private boolean judgeErrorFile(String fileName) {
        StringBuilder err  = FileUtils.read(fileName);
        if(err.length()>0){
            return true;
        }
        return false;
    }

    private void addToVTable(){
        Variable var = new Variable(getCurrentSym(), curProQueue.peek(),
                curVarKind, curVarType==INTEGER_TYPE?"integer":null, currentLevel, vTable.size());
        //如果为形参
        if(curVarKind==PARAMETER_TYPE){
            if(vTableContains(var)){
                outError(SYMBOL_UN_DEFINE_ERROR, getCurrentSym());
                return;
            }
        }else if(curVarKind==VARIABLE_TYPE){ //变量类型
            if(curVarType==INTEGER_TYPE){
                if(vTableContains(var)){
                    outError(SYMBOL_UN_DEFINE_ERROR, getCurrentSym());
                    return;
                }
            }
        }else{
            return;
        }

        if(vTable.size()>0){
            if(!vTable.get(vTable.size()-1).getVproc().equals(curProQueue.peek())){
                pTable.get(pTable.size()-1).setFadr(vTable.size());
            }
        }
        for(Procedure t:pTable){
            if(t.getPname().equals(curProQueue.peek())){
                t.setLadr(vTable.size());
            }
        }
        vTable.add(var);
    }

    private void addToPTable(){
        pTable.add(new Procedure(getCurrentSym(),
                curVarType==INTEGER_TYPE?"integer":null,currentLevel, 0, 0));
        curProQueue.add(getCurrentSym());
    }

    private boolean pTableContains(String name, int level){
        for(Procedure temp: pTable){
            if(temp.getPname().equals(name) && temp.getPlev()==level){
                return true;
            }
        }
        return false;
    }

    private boolean vTableContains(String name){
        for(Variable temp: vTable){
            if(temp.getVname().equals(name)  && temp.getVlev()<= currentLevel && temp.getVkind()==VARIABLE_TYPE){
                    return true;
            }
        }
        return false;
    }

    private boolean vTableContains(Variable t){
        for(Variable temp: vTable){
            if(temp.getVname().equals(t.getVname()) && t.getVkind()==temp.getVkind() && temp.getVproc().equals(curProQueue.peek())
                    && temp.getVlev()==currentLevel ){
                if(t.getVtype() != null&&temp.getVtype().equals(t.getVtype())){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isVariable(String str){
        if(charStore.isReserve(str)==0 && isLetter(str)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * program
     */
    private void program(){
        if(!"begin".equals(getCurrentSym())){
            outError(SYMBOL_LACK_ERROR, "begin");
        }else{
            advance();
        }
            pTable.add(new Procedure("main", null, currentLevel, 0, 0));
            curProQueue.add("main");
            declareTable();
            advance();

        if(!";".equals(getCurrentSym())){
            outError(SYMBOL_LACK_ERROR, ";");
        }
            advance();
            excuteTable();

            if(!"end".equals(getCurrentSym())){
                outError(SYMBOL_LACK_ERROR,"end");
            }
            advance();
            curProQueue.pop();
    }

    /**
     * <说明语句表>→<说明语句>│<说明语句表> ；<说明语句>
     */
    private void declareTable(){
        declare();
        declareTable_();
    }

    private void declareTable_(){
        if(!";".equals(getCurrentSym())){
            outError(SYMBOL_LACK_ERROR,";");
        }else{
            advance();
        }
            if("integer".equals(getCurrentSym())){
                declare();
                declareTable_();
            }else{
                goBack(2);
            }
    }

    /**
     * <说明语句>→<变量说明>│<函数说明>
     */
    private void declare(){
        if(!"integer".equals(getCurrentSym())){
            outError(SYMBOL_LACK_ERROR,"integer");
        }else{
            advance();
        }
            this.curVarType = INTEGER_TYPE;
            declare_();
            this.curVarType = -1;
    }

    private void declare_(){
        if("function".equals(getCurrentSym())){
            currentLevel++;
            advance();
            curVarKind = FUNCTION_TYPE;
            functionDeclare();
            curVarKind = -1;
            curProQueue.pop();
        }else{
            variableDeclare();
        }
    }


    /**
     * 变量说明
     */
    private void variableDeclare(){
        if(charStore.isReserve(getCurrentSym())==0) {
            this.curVarKind = VARIABLE_TYPE;
            symbol();
            curVarKind = -1;
        }
    }

    /**
     * 标识符
     */
    private void symbol(){
        if(isVariable(getCurrentSym())){
            if(curVarKind==FUNCTION_TYPE){
                addToPTable();
            }else if(curVarKind==VARIABLE_CALL){    //变量调用
                if(!vTableContains(getCurrentSym())){
                    outError(SYMBOL_UN_DEFINE_ERROR, getCurrentSym());
                }
            }else{
                addToVTable();
            }
            advance();
        }
    }

    /**
     * 是否为字母
     * @param str
     * @return
     */
    private boolean isLetter(String str){
        if(str==null){
            return false;
        }
        for(int i=0;i<str.length();i++){
            if(!Character.isLetter(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为数字
     * @param str
     * @return
     */
    private boolean isDigit(String str){
        for(int i=0;i<str.length();i++){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 方法说明
     */
    private void functionDeclare(){
        symbol();

        if(!"(".equals(getCurrentSym())){
            outError(SYMBOL_LACK_ERROR, "(");
        }else{
            advance();
        }
        this.curVarKind = PARAMETER_TYPE;
        parameter();
        this.curVarKind = -1;
        if(!")".equals(getCurrentSym())) {
            outError(SYMBOL_MATCH_ERROR, ")");
        }else{
            advance();
        }
        if(!";".equals(getCurrentSym())) {
            outError(SYMBOL_LACK_ERROR, ";");
        }else{
            advance();
        }
        functionBody();
    }

    /**
     * 方法体
     */
    private void functionBody(){
        if(!"begin".equals(getCurrentSym())) {
            outError(SYMBOL_LACK_ERROR, "begin");
        }else{
            advance();
        }

            declareTable();
            advance();
            if(!";".equals(getCurrentSym())) {
                outError(SYMBOL_LACK_ERROR, ";");
            }else{
                advance();
            }
                excuteTable();
                if(!"end".equals(getCurrentSym())){
                    outError(SYMBOL_MATCH_ERROR,"end");
                }else{
                    advance();
                }
                if(!";".equals(getCurrentSym())){
                    outError(SYMBOL_LACK_ERROR,";");
                }

    }

    /**
     * 参数
     */
    private void parameter(){
        symbol();
    }


    /**
     * 执行语句表
     */
    private void excuteTable(){
        excute();
        excuteTable_();
    }

    private void excuteTable_(){
        if(";".equals(getCurrentSym())){
            advance();
            excute();
            excuteTable_();
        }
    }

    /**
     * 执行语句
     */
    private void excute(){
        read();
        write();
        condition();
        evaluate();
    }

    /**
     * 读语句
     */
    private void read(){
        if("read".equals(getCurrentSym())){
            advance();
            if(!"(".equals(getCurrentSym())) {
                outError(SYMBOL_LACK_ERROR,"(");
            }else {
                advance();
            }
                curVarKind = VARIABLE_CALL;
                symbol();
                curVarKind = -1;
                if(!")".equals(getCurrentSym())){
                    outError(SYMBOL_MATCH_ERROR, ")");
                }else {
                    advance();
                }
            }
        }

    /**
     * 写语句
     */
    private void write() {
        if ("write".equals(getCurrentSym())) {
            advance();
            if (!"(".equals(getCurrentSym())) {
                outError(SYMBOL_LACK_ERROR, "(");
            } else {
                advance();
            }
            curVarKind = VARIABLE_CALL;
            symbol();
            curVarKind = -1;
            if (!")".equals(getCurrentSym())) {
                outError(SYMBOL_MATCH_ERROR, ")");
            } else {
                advance();
            }
        }
    }

    /**
     * 赋值语句
     */
    private void evaluate(){
        if(isVariable(getCurrentSym())){
            advance();
            if(!":=".equals(getCurrentSym())) {
                outError(SYMBOL_LACK_ERROR, ":=");
            }else{
                advance();
            }

                arithmeticExpression();
        }
    }

    /**
     * 算数表达式
     */
    private void arithmeticExpression(){
        term();
        arithmeticExpression_();
    }

    private void arithmeticExpression_(){
        if("-".equals(getCurrentSym())){
            advance();
            term();
            arithmeticExpression_();
        }
    }

    /**
     * 项
     */
    private void term(){
        factor();
        term_();
    }

    private void term_(){
        if("*".equals(getCurrentSym())){
            advance();
            factor();
            term_();
        }
    }

    /**
     * 因子
     */
    private void factor(){
        if(pTableContains(getCurrentSym(), currentLevel)){
            advance();
            callFunction();
        }else{
            symbol();
            constant();
        }
    }

    /**
     * 常数
     */
    private void constant(){
        unsignInteger();
    }

    private void unsignInteger(){
        if(isDigit(getCurrentSym())){
            advance();
            unsignInteger_();
        }
    }

    private void unsignInteger_(){
        if(isDigit(getCurrentSym())){
            unsignInteger_();
        }
    }

    /**
     * 条件语句
     */
    private void condition(){
        if("if".equals(getCurrentSym())){
            advance();
            curVarKind = VARIABLE_CALL;
            conditionExpression();
            if(!"then".equals(getCurrentSym())) {
                outError(SYMBOL_MATCH_ERROR, "then");
            }else{
                advance();
            }
                curVarKind = VARIABLE_CALL;
                excute();
                if("else".equals(getCurrentSym())) {
                    advance();
                    curVarKind = VARIABLE_CALL;
                    excute();
                }
        }
    }

    /**
     * 条件表达式
     */
    private void conditionExpression(){
        arithmeticExpression();
        relationSymbol();
        arithmeticExpression();
    }

    /**
     * 关系符号
     */
    private void relationSymbol(){
        String temp = getCurrentSym();
        if(temp.equals("<")||temp.equals("<=")||temp.equals(">")||temp.equals(">=")
                ||temp.equals("=")||temp.equals("<>")){
            advance();
        }else{
            outError(SYMBOL_MATCH_ERROR, " ");
        }
    }

    /**
     * 函数调用
     */
    private void callFunction(){
        if(!"(".equals(getCurrentSym())) {
            outError(SYMBOL_LACK_ERROR, "(");
        }else {
            advance();
        }
        curVarKind = VARIABLE_CALL;
        arithmeticExpression();
        curVarKind = -1;
        if(")".equals(getCurrentSym())){
                advance();
        }else{
            outError(SYMBOL_LACK_ERROR, ")");
        }
    }

    private String getCurrentSym(){
        if(currentIndex <inputStrList.size()){
            return inputStrList.get(currentIndex).getSignal();
        }else{
            return null;
        }
    }

    private boolean advance(){
        this.currentIndex++;
        if(currentIndex >=inputStrList.size()){
            return false;
        }
        if(lines.peek().equals(currentIndex)){
            currentLine++;
            lines.poll();
        }
        return true;
    }

    private boolean goBack(int i){
        this.currentIndex -= i;
        if(this.currentIndex<0){
            return false;
        }
        return true;
    }

    private void outError(String msg, String key){
        errorList.add(currentLine + "  LINE  " + msg + "   " + key);
    }

}
