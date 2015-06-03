package homeWork.FundamentalOfCompiling.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bin on 2015/5/2.
 */
public class CharStore {

    //保留符号表
    private static Map<String, BinaryFomula> reserveCharMap;

    //符号表
    private List<BinaryFomula> symbolList = new ArrayList<BinaryFomula>();

    //常量表
    private List<String> constantList = new ArrayList<String>();

    //错误表
    private List<String> errorList = new ArrayList<String>();

    private static CharStore charStore;

    public CharStore(){
        if(reserveCharMap==null){
            initSignalsMap();
        }
    }

    public String getBinaryFomula(String key){
        BinaryFomula bf = reserveCharMap.get(key);
        if(bf!=null){
            return bf.toString();
        }
        return null;
    }


    public int isReserve(String key){
        if(key==null){
            return 0;
        }
        BinaryFomula bf = reserveCharMap.get(key);
        if(bf!=null){
            return bf.getNum();
        }else{
            return 0;
        }
    }

    public int addToConstantList(String str){
        this.constantList.add(str);
        return this.constantList.size();
    }

    public void addToErrorList(String msg){
        this.errorList.add(msg);
    }

    public String addToSymbolList(String str){
        BinaryFomula bf  = new BinaryFomula(str, symbolList.size()+1);
        this.symbolList.add(bf);
        return bf.toString();
    }

    public boolean isInSymbolList(String str){
        for(BinaryFomula temp: symbolList){
            if(temp.getSignal().trim().equals(str)){
                return true;
            }
        }
        return false;
    }

    public boolean isInConstantList(String str){
        for(String temp: constantList){
            if(temp.trim().equals(str)){
                return true;
            }
        }
        return false;
    }

    private static void initSignalsMap(){
        reserveCharMap = new HashMap<String, BinaryFomula>();
        reserveCharMap.put("begin", new BinaryFomula("begin", 1));
        reserveCharMap.put("end", new BinaryFomula("end", 2));
        reserveCharMap.put("integer", new BinaryFomula("integer", 3));
        reserveCharMap.put("if", new BinaryFomula("if", 4));
        reserveCharMap.put("then", new BinaryFomula("then", 5));
        reserveCharMap.put("else", new BinaryFomula("else", 6));
        reserveCharMap.put("function", new BinaryFomula("function", 7));
        reserveCharMap.put("read", new BinaryFomula("read", 8));
        reserveCharMap.put("write", new BinaryFomula("write", 9));
        reserveCharMap.put("标识符", new BinaryFomula("标识符", 10));
        reserveCharMap.put("常数", new BinaryFomula("常数", 11));
        reserveCharMap.put("=", new BinaryFomula("=", 12));
        reserveCharMap.put("<>", new BinaryFomula("<>", 13));
        reserveCharMap.put("<=", new BinaryFomula("<=", 14));
        reserveCharMap.put("<", new BinaryFomula("<", 15));
        reserveCharMap.put(">=", new BinaryFomula(">=", 16));
        reserveCharMap.put(">", new BinaryFomula(">", 17));
        reserveCharMap.put("-", new BinaryFomula("-", 18));
        reserveCharMap.put("*", new BinaryFomula("*", 19));
        reserveCharMap.put(":=", new BinaryFomula(":=", 20));
        reserveCharMap.put("(", new BinaryFomula("(", 21));
        reserveCharMap.put(")", new BinaryFomula(")", 22));
        reserveCharMap.put(";", new BinaryFomula(";", 23));
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public List<BinaryFomula> getSymbolList() {
        return symbolList;
    }

    public List<String> getConstantList() {
        return constantList;
    }

    public static Map<String, BinaryFomula> getReserveCharMap() {
        return reserveCharMap;
    }
}
