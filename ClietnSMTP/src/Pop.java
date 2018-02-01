
import java.io.*;
import java.net.*;
import java.util.*;

class Pop {

    String server;
    String user;
    String pass;

    Pop(String zserver,String zuser, String zpass) {
        server = zserver;
        user = zuser;
        pass = zpass;
    }

    void lit() {
        PrintWriter to;
        BufferedReader from;
        String str, title, msg;
        Vector v = new Vector();
        try {
            Socket socket = new Socket(InetAddress.getByName(server), 110);
            to = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            from = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!(from.readLine()).startsWith("+OK"));
            to.println("USER " + user + "\r");
            while (!(from.readLine()).startsWith("+OK"));
            to.println("PASS " + pass + "\r");
            while (!(from.readLine()).startsWith("+OK"));
            to.println("LIST\r");
            while (!(from.readLine()).startsWith("+OK"));
            do {
                str = from.readLine();
                if (str.compareTo(".") != 0) {
                    v.add(str);
                }
            } while (str.compareTo(".") != 0);
            for (int i = 0; i < v.size(); i++) {
                title = (String) v.elementAt(i);
                to.println("RETR " + (new StringTokenizer(title)).nextToken() + "\r");
                while (!(from.readLine()).startsWith("+OK"));
                msg = "";
                do {
                    msg += from.readLine() + "\r\n";
                } while (!msg.endsWith("\r\n.\r\n"));
                System.out.println(msg);
            }
            socket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String args[]) {
        Pop p = new Pop("127.0.0.1", "test1", "password");
        p.lit();
    }
}
