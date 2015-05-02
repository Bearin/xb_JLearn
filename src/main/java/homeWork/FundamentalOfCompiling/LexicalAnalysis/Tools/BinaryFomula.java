package homeWork.FundamentalOfCompiling.LexicalAnalysis.Tools;

import java.util.BitSet;

/**
 * Created by bin on 2015/5/2.
 */
public class BinaryFomula {

    private String signal;

    private int num;

    public BinaryFomula(String signal, int num) {
        this.signal = signal;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public String getSignal() {
        return signal;
    }

    @Override
    public String toString() {
        return  String.format("%-16s", signal)+" "+num;
    }

}
