package receive;

public enum Constante {
    SERVER_READY("+OK POP3 server ready"),
    ACCES_MAIL_GRANTED("+OK accès à la boite aux lettres vérouillées"),
    COMMANDE_INCORRECT("commande incorrect\n\r"),
    QUIT("+OK dewey POP3 server signing off"),
    MAIL_ALREADY_LOCKED("-ERR maildrop already locked"),
    INVALID_PASSWORD("-ERR invalid password"),
    USER_VALID("+OK ::userName logged in"),
    USER_INVALID("-ERR ::userName does not exist"),
    SERVER_ERROR("-ERR server error"),
    STAT_MESSAGE("+OK ::nbMessages ::tailleDepot octets"),
    RETRIEVE_MESSAGE("+OK ::taille octets \r\n ::message"),
    LIST_MESSAGE("+OK ::nbMessage messages \r\n ::message"),
    INVALID_MESSAGE("-ERR numéro de message invalid"),
    PERMISSION_DENIED("-ERR permission denied");

    private String constante;

    Constante(String constante){
        this.constante = constante;
    }

    public String value() {
        return constante;
    }
}
