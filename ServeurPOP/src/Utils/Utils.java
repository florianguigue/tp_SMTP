package Utils;

public class Utils {

    /**
     * arg[0] -> commande
     *
     * @param nbArg
     * @param string
     * @return
     */
    public static String getArgumentOfString(Integer nbArg, String string) {
        String[] args = string.split(" ");
        if (args.length > nbArg && args.length > 0) {
            return args[nbArg];
        }
        return null;
    }
}
