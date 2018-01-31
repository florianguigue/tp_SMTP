package receive;

import Utils.Utils;
import hibernate.Operation;
import model.Authentication;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class ClientProcessor implements Runnable {

    private Socket sock;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private boolean closeConnexion = false;
    private boolean isConnected = false;
    private boolean waitingPass = false;
    private String user = "";


    public ClientProcessor(Socket pSock) {
        sock = pSock;
    }

    public void run() {
        System.err.println("Lancement du traitement des mails");

        try {
            writer = new PrintWriter(sock.getOutputStream());
            reader = new BufferedInputStream(sock.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String toSend = "";

        toSend = Constante.SERVER_READY.value() + "\n\r";
        //On envoie l'état du server au client
        writer.write(toSend);
        writer.flush();

        while (!sock.isClosed()) {

            try {

                //on attend la réponse
                String response = read();
                InetSocketAddress remote = (InetSocketAddress) sock.getRemoteSocketAddress();

                String debug = "";
                debug = "Thread : " + Thread.currentThread().getName() + ". ";
                debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() + ".";
                debug += " Sur le port : " + remote.getPort() + ".\n";
                debug += "\t -> Commande reçue : " + response + "\n";
                System.err.println("\n" + debug);
                toSend = traitement(response) + "\r\n";


                //On envoie la réponse au client
                writer.write(toSend);
                writer.flush();

                if (closeConnexion) {
                    System.err.println("COMMANDE CLOSE DETECTEE ! ");
                    writer = null;
                    reader = null;
                    sock.close();
                    break;
                }
            } catch (SocketException e) {
                System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lecture de la réponse
     *
     * @return
     * @throws IOException
     */
    private String read() throws IOException {
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        response = response.replace("\n", "").replace("\r", "");
        return response;
    }

    /**
     * Traitement des messages reçus
     *
     * @param response
     * @return
     */
    private String traitement(String response) {
        String toSend = "";
        //NON CONNECTE
        if (isConnected == false) {
            if (response.toUpperCase().contains("APOP")
                    || response.toUpperCase().contains("USER")
                    || response.toUpperCase().contains("PASS")) {
                toSend = connect(response);
            } else {
                toSend = Constante.COMMANDE_INCORRECT.value();
            }
            //CONNECTE
        } else {
            if (response.toUpperCase().contains("QUIT")) {
                toSend = Constante.QUIT.value();
                closeConnexion = true;
            }
        }
        return toSend;
    }


    /**
     * Gestion de la connection utilisateur
     *
     * @param response
     * @return
     */
    private String connect(String response) {
        String commande = Utils.getArgumentOfString(0, response.toUpperCase());
        String arg1 = Utils.getArgumentOfString(1, response.toUpperCase());
        String arg2 = Utils.getArgumentOfString(2, response.toUpperCase());
        // COMMANDE APOP
        if (commande.toUpperCase().equals("APOP")) {
            this.user = arg1;
            String password = arg2;
            if (this.user != null && password != null) {
                if (connectUser(user, password))
                    return Constante.ACCES_MAIL_GRANTED.value();
                return Constante.PERMISSION_DENIED.value();
            }
            //COMMMANDE USER
        } else if (commande.toUpperCase().equals("USER")) {
            this.user = arg1;
            if (this.user != null && isUserExist(this.user)) {
                waitingPass = true;
                return Constante.USER_VALID.value().replace("::userName", user).replace("\n", "").replace("\r", "");
            } else {
                return Constante.USER_INVALID.value().replace("::userName", user).replace("\n", "").replace("\r", "");
            }
            //COMMANDE PASS
        } else if (commande.toUpperCase().equals("PASS") && waitingPass) {
            String password = arg1;
            if (password != null && connectUser(null, password)) {
                this.isConnected = true;
                return Constante.ACCES_MAIL_GRANTED.value();
            } else {
                return Constante.INVALID_PASSWORD.value();
            }
        }
        return Constante.COMMANDE_INCORRECT.value();
    }

    /**
     * @param user
     * @return
     */
    public boolean isUserExist(String user) {
        try {
            Operation operation = new Operation();
            Authentication authentication = operation.getAuthentication(user);
            if (authentication != null) {
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
            return false;
        }
        return false;
    }

    /**
     * @param user
     * @param password
     * @return
     */
    public boolean connectUser(String user, String password) {
        if (user == null)
            user = this.user;
        try {
            Operation operation = new Operation();
            Authentication authentication = operation.getAuthentication(user);
            if (authentication != null) {
                if (authentication.getPassword().equalsIgnoreCase(password)) {
                    isConnected = true;
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
            return false;
        }
        return false;
    }
}