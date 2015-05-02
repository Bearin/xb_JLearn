package homeWork.FundamentalOfCompiling.LexicalAnalysis.Tools;

import java.io.*;
import java.util.List;

/**
 * 用于文件读取
 * Created by bin on 2015/5/2.
 */
public class FileUtils {

    public static StringBuilder read(String path){
        StringBuilder inputStr = new StringBuilder();
        Reader reader;
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(path));
            reader = new InputStreamReader(inputStream);
            int temp ;
            while((temp=reader.read())!=-1){
                inputStr.append((char)temp);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStr;
    }

    public static void outToFile(String outStr, String filePath){
        File file = new File(filePath);
        try {
            file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(outStr);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outErrorToFile(List<String> strList, String filePath){
        File file = new File(filePath);
        try {
            file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for(String str: strList){
                out.write(str==null?"":str);
                out.write("\n");
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outToFile(List<String> strList, String filePath){
        File file = new File(filePath);
        try {
            file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for(String str: strList){
                out.write(str==null?"":str);
                out.write("\n");
                out.write("                 EOLN 24");
                out.write("\n");
            }
            out.write("                 EOF 25");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
