package homeWork.FundamentalOfCompiling.SyntaxAnalysis;

/**
 * Created by bin on 2015/5/24.
 *
 * @author xbearin@gmail.com
 */
public class Procedure {

    private String pname;

    private String ptype;

    private int plev;

    private int fadr;

    public Procedure(String pname, String ptype, int plev, int fadr, int ladr) {
        this.pname = pname;
        this.ptype = ptype;
        this.plev = plev;
        this.fadr = fadr;
        this.ladr = ladr;
    }

    public String getPtype() {
        return ptype;
    }

    public int getPlev() {
        return plev;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public void setPlev(int plev) {
        this.plev = plev;
    }

    public void setFadr(int fadr) {
        this.fadr = fadr;
    }

    public void setLadr(int ladr) {
        this.ladr = ladr;
    }

    public int getFadr() {
        return fadr;

    }

    public int getLadr() {
        return ladr;
    }

    private int ladr;

    public String getPname() {
        return pname;
    }

    public String toString(){
        String str = "pname: "+ pname + "; "+
                "ptype: "+ ptype + "; "+
                "plev: "+ plev + "; "+
                "fadr: "+ fadr + "; "+
                "ladr: "+ ladr + "; ";
        return str;
    }
}
