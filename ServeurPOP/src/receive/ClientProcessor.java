package receive;

import Utils.Utils;

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


        while (!sock.isClosed()) {

            try {

                writer = new PrintWriter(sock.getOutputStream());
                reader = new BufferedInputStream(sock.getInputStream());

                String toSend = "";

                toSend = Constante.SERVER_READY.value();

                //On envoie l'état du server au client
                writer.write(toSend);
                writer.flush();

                //on attend la réponse
                String response = read();
                InetSocketAddress remote = (InetSocketAddress) sock.getRemoteSocketAddress();

                String debug = "";
                debug = "Thread : " + Thread.currentThread().getName() + ". ";
                debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() + ".";
                debug += " Sur le port : " + remote.getPort() + ".\n";
                debug += "\t -> Commande reçue : " + response + "\n";
                System.err.println("\n" + debug);
                toSend = traitement(response);


                //On envoie la réponse au client
                writer.write(toSend);
                //Il FAUT IMPERATIVEMENT UTILISER flush()
                //Sinon les données ne seront pas transmises au client
                //et il attendra indéfiniment
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
        // COMMANDE APOP
        if (response.toUpperCase().contains("APOP")) {
            this.user = Utils.getArgumentOfString(1, response.toUpperCase());
            String password = Utils.getArgumentOfString(1, response.toUpperCase());
            if (this.user != null && password != null) {
                //TODO check le user et le password
                return Constante.ACCES_MAIL_GRANTED.value();
                //return Constante.PERMISSION_DENIED;
            }
            //COMMMANDE USER
        } else if (response.toUpperCase().contains("USER")) {
            this.user = Utils.getArgumentOfString(1, response.toUpperCase());
            if (this.user != null) {
                waitingPass = true;
            }
            //COMMANDE PASS
        } else if (response.toUpperCase().contains("PASS") && waitingPass) {
            //TODO check le user et le password - ERRs a gérer
            return Constante.ACCES_MAIL_GRANTED.value();
            //return Constante.PERMISSION_DENIED;
        }
        return Constante.COMMANDE_INCORRECT.value();
    }
}