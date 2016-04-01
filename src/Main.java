import parameter.Parameter;
import parameter.ParameterMisFormatException;

import java.io.InputStreamReader;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            Console.showMenu();
        }
        else {
            try {
                HashMap<String, String> params = Parameter.mapParameter(args);
//                for (Map.Entry<String, String> param : params.entrySet()) {
//                    System.out.println(param.getKey() + " > " + param.getValue());
//                }
                if(params.get("gui").equals("on")) {
                    new GUI(params).create();
                }
                else {
                    new Console(params).create();
                }
                new InputStreamReader(System.in).read();
            }
            catch (ParameterMisFormatException ex) {
                Console.showMenuWithError(ex);
            }
            catch (Exception ex) {

            }
        }

    }
}
