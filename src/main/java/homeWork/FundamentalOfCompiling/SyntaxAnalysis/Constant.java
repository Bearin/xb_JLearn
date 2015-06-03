package homeWork.FundamentalOfCompiling.SyntaxAnalysis;

/**
 * Created by bin on 2015/5/16.
 *
 * @author xbearin@gmail.com
 */
public interface Constant {

    public final String SYMBOL_LACK_ERROR = "缺少符号错误";

    public final String SYMBOL_MATCH_ERROR = "符号匹配错误";

    public final String SYMBOL_UN_DEFINE_ERROR = "符号未定义或重复定义";

    public final int VARIABLE_CALL = -1;

    public final int PARAMETER_TYPE = 0;

    public final int VARIABLE_TYPE = 1;

    public final int FUNCTION_TYPE = 2;

    public final int INTEGER_TYPE = 0;
}
