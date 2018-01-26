package receive;

public enum Constante {
    SERVER_READY("+OK POP3 server ready"),
    ACCES_MAIL_GRANTED("+OK accès à la boite aux lettres vérouillées"),
    COMMANDE_INCORRECT("commande incorrect"),
    QUIT("+OK dewey POP3 server signing off"),
    MAIL_ALREADY_LOCKED("-ERR maildrop already locked"),
    INVALID_PASSWORD("-ERR invalid password"),
    PERMISSION_DENIED("-ERR permission denied");

    private String constante;

    Constante(String constante){
        this.constante = constante;
    }

    public String value() {
        return constante;
    }
}
