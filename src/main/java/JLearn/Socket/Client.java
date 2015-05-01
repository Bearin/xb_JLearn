package JLearn.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * Created by bin on 2015/5/1.
 */
public class Client {

    public static void main(String[] args){
        try{
            Socket socket = new Socket(InetAddress.getLocalHost(),8888);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            socket.setSoTimeout(20000);
            out.println("hello, server!");
            out.flush();
            System.out.println(in.readLine());
            out.close();
            in.close();
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
