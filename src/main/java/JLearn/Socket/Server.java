package JLearn.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * Created by bin on 2015/5/1.
 */
public class Server {

    public static void main(String[] args) {

        int port = 8888;
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(6000);
            Socket socket = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            while (true) {
                String str = in.readLine();
                System.out.println(str);
                out.println("hello, client! You said: "+str);
                out.flush();
                if("end".equals(str))
                    break;
            }
            in.close();
            out.close();
            server.close();
            System.out.println("server closed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
