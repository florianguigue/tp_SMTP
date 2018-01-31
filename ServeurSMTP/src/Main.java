import hibernate.Operation;
import model.Authentication;
import model.Mail;
import server.Server;

import java.util.List;

public class Main {
    public static void main(String args[]) {
        Server server = new Server();
        server.open();
        /*try {
            Operation operation = new Operation();
            List<Mail> mails = operation.getUserMails(operation.getAuthentication("test1"));
            if (mails != null) {
                for (Mail mail : mails) {
                    System.out.println(mail.getId() + " " + mail.getBody().getBytes().length + "\r\n");
                }
            } else {
                System.out.println("ko");
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
            System.out.println("no ko");
         }*/
    }
}


