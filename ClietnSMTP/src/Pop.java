import java.io.*;
import java.net.*;
import java.util.*;

class Pop {

    String server;
    String user;
    String pass;

    Pop(String zserver,
            String zuser, String zpass) {
        server = zserver;
        user = zuser;
        pass = zpass;
    }

    void lit() {
        PrintWr iter to;
        BufferedReader from;
        String str, title, msg;
        Vector v = new Vector();
        try {
            Socket socket = n
            ew Socket
            (In etAddress
            .g etByName(server
            )
,110)
; 
        to = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())), tru
e
            ); 
        from = new BufferedReader(
                    new InputStreamReader(
                            socket.getInpu
            tStream()
            ))
; 
        while (!(from.readLine())
                    .startsWith("+OK"
                    ));
            to.println("USER "
                    + user + "\r");
            while (!(from.readLine())
                    .startsWith("+OK"
                    ));
            to.println("PASS " + pass + "\r");
            while (!(from.readLine())
                    .startsWith("+OK"
                    ));
            http://www.nicola
            sjean.
c

            om
Nicolas JEA
            N to
            .println("LIST\r");
            while (!(from.readLine())
                    .startsWith("+OK"
                    ));
            do {
                str = from.readLine();
                if (str.compareTo("."
                ) != 0) {
                    v.add(str);
                }
            } while (str.compareTo("."
            ) != 0);
            for (int i = 0; i < v.s ize();
            i++ 
                )
 { 
          title = (String) v.e
                lementAt(i);
                to.println("RETR " + (new StringTokenizer(title))
                        .nextToken()
                        + "\r");
                while (!(from.readLine()).star

                    tsWith("+OK"
                    ) ); 
          msg = "";
                do {
                    msg += from.readLine()
                            + "\n";
                } while (!msg.endsWith("\n.\n"
                ));
                System.out.println(msg);
            }
            socket.
                    close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String args[]) {
        Pop p = new Pop("serveur", "user", "pass");
        p.lit();
    }
}
