package homeWork.FundamentalOfCompiling.Tools;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 用于文件读取
 * Created by bin on 2015/5/2.
 */
public class FileUtils {

    public static StringBuilder read(String path){
        StringBuilder inputStr = new StringBuilder();
        BufferedReader reader;
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(path));
            reader = new BufferedReader(new InputStreamReader(inputStream));
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

    public static List<BinaryFomula> readToList(String path, Queue lines){
        List<BinaryFomula> list = new ArrayList<BinaryFomula>();
        InputStream inputStream;
        BufferedReader reader;
        String temp;
        try {
            inputStream = new FileInputStream(new File(path));
            reader = new BufferedReader(new InputStreamReader(inputStream));
            int linesIndex = 0;
            while((temp = reader.readLine())!=null){
                linesIndex++;
                temp = temp.trim();
                if(temp.contains("EOF  25")){
                    break;
                }else if(temp.contains("EOLN  24")){
                    lines.add(linesIndex);
                    if(!temp.equals("EOLN  24")&&!temp.equals("EOF  25")) {
                        list.add(new BinaryFomula(temp.substring(0, 16).trim(), Integer.valueOf(temp.substring(17, 19).trim())));
                    }
                }else {
                    list.add(new BinaryFomula(temp.substring(0, 16).trim(), Integer.valueOf(temp.substring(17,19).trim())));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void outToFile(List<?> list, String filePath){
        File file = new File(filePath);
        try {
            file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for(Object o: list){
                out.write(o==null?"":o.toString());
                out.write("\n");
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outDydToFile(List<String> strList, String filePath){
        File file = new File(filePath);
        try {
            file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for(int i=0;i<strList.size();i++){
                if(i!=0){
                    out.write("\n");
                }
                out.write(strList.get(i)==null?"":strList.get(i));
                if(i+1<strList.size()){
                    if(strList.get(i+1).contains("EOLN  24")||strList.get(i+1).contains("EOF  25")) {
                        out.write(strList.get(++i)==null?"":strList.get(i));
                    }
                }
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
