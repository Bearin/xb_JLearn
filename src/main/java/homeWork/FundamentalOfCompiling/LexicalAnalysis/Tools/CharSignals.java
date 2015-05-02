package homeWork.FundamentalOfCompiling.LexicalAnalysis.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bin on 2015/5/2.
 */
public class CharSignals {

    private Map<String, BinaryFomula> signalsMap;

    private static CharSignals charSignals;

    public static CharSignals getInstance(){
        if(charSignals==null) {
            charSignals = new CharSignals();
            charSignals.initSignalsMap();
        }
        return charSignals;
    }

    public Map<String, BinaryFomula> getSignalsMap() {
        return signalsMap;
    }

    public String getBinaryFomula(String key){
        BinaryFomula bf = signalsMap.get(key);
        if(bf!=null){
            System.out.println(bf.toString());
            return bf.toString();
        }
        System.out.println("没找到..."+key);
        return null;
    }

    private CharSignals(){}

    public int isReserve(String key){
        BinaryFomula bf = signalsMap.get(key);
        if(bf!=null){
            return bf.getNum();
        }else{
            return 0;
        }
    }

    private void initSignalsMap(){
        signalsMap = new HashMap<String, BinaryFomula>();
        signalsMap.put("begin", new BinaryFomula("begin", 1));
        signalsMap.put("end", new BinaryFomula("end", 2));
        signalsMap.put("integer", new BinaryFomula("integer", 3));
        signalsMap.put("if", new BinaryFomula("if", 4));
        signalsMap.put("then", new BinaryFomula("then", 5));
        signalsMap.put("else", new BinaryFomula("else", 6));
        signalsMap.put("function", new BinaryFomula("function", 7));
        signalsMap.put("read", new BinaryFomula("read", 8));
        signalsMap.put("write", new BinaryFomula("write", 9));
        signalsMap.put("标识符", new BinaryFomula("标识符", 10));
        signalsMap.put("常数", new BinaryFomula("常数", 11));
        signalsMap.put("=", new BinaryFomula("=", 12));
        signalsMap.put("<>", new BinaryFomula("<>", 13));
        signalsMap.put("<=", new BinaryFomula("<=", 14));
        signalsMap.put("<", new BinaryFomula("<", 15));
        signalsMap.put(">=", new BinaryFomula(">=", 16));
        signalsMap.put(">", new BinaryFomula(">", 17));
        signalsMap.put("-", new BinaryFomula("-", 18));
        signalsMap.put("*", new BinaryFomula("*", 19));
        signalsMap.put(":=", new BinaryFomula(":=", 20));
        signalsMap.put("(", new BinaryFomula("(", 21));
        signalsMap.put(")", new BinaryFomula(")", 22));
        signalsMap.put(";", new BinaryFomula(";", 23));
    }

}
