package homeWork.FundamentalOfCompiling.SyntaxAnalysis;

/**
 * Created by bin on 2015/5/24.
 *
 * @author xbearin@gmail.com
 */
public class Variable {

    private String vname;

    private String vproc;

    private int vkind;

    private String vtype;

    private int vlev;

    private int vadr;

    public Variable(String vname, String vproc, int vkind, String vtype, int vlev, int vadr) {
        this.vname = vname;
        this.vproc = vproc;
        this.vkind = vkind;
        this.vtype = vtype;
        this.vlev = vlev;
        this.vadr = vadr;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public void setVproc(String vproc) {
        this.vproc = vproc;
    }

    public void setVkind(int vkind) {
        this.vkind = vkind;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    public void setVlev(int vlev) {
        this.vlev = vlev;
    }

    public void setVadr(int vadr) {
        this.vadr = vadr;
    }

    public String getVname() {
        return vname;

    }

    public String getVproc() {
        return vproc;
    }

    public int getVkind() {
        return vkind;
    }

    public String getVtype() {
        return vtype;
    }

    public int getVlev() {
        return vlev;
    }

    public int getVadr() {
        return vadr;
    }


    @Override
    public String toString() {
        String str = "Vname: "+ vname + "; "+
                "Vproc: "+ vproc + "; " +
                "Vkind: "+ vkind + "; " +
                "Vtype: "+ vtype + "; " +
                "Vlev: "+ vlev + "; " +
                "Vadr: "+ vadr + "; ";
        return str;

    }
}
