import parameter.Parameter;
import java.util.*;

/**
 * Created by Zenon on 3/27/2016 AD.
 */
public class Main {
    public static void main(String[] args) {
        try {
            HashMap<String, String> params = Parameter.mapParameter(args);
            for (Map.Entry<String, String> param : params.entrySet()) {
                System.out.println(param.getKey() + " > " + param.getValue());
            }
            if(params.get("gui").equals("on")) {
                GUI.createWindow(params);
            }
            else {
                new Thread(new Client(params));
            }
        }
        catch (Exception ex) {
            System.out.println();
            System.err.println(" ***** " + ex.toString());
            System.out.println(" [ This program require all of following argument in the same format. ]");
            System.out.println();
            System.out.println("\t-x <Number> : Number is a 32-bit unsigned integer");
            System.out.println("\t-t <UDP|TCP> : UDP and TCP are type of protocol to connect server");
            System.out.println("\t-s <IP Address> : IP Address or Host Name of the server [ex: dmx.cs.colostate.edu, 127.0.0.1]");
            System.out.println("\t-p <Port Number> : the port being used by the server [1 - 65535]");
            System.out.println();
        }

    }
}
