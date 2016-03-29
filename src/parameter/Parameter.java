package parameter;

import java.util.HashMap;
import java.util.InputMismatchException;

/**
 * Created by Zenon on 3/27/2016 AD.
 */
public class Parameter {
    public static HashMap<String, String> mapParameter(String args[]) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        String param = "";
        params.put("gui", "off");
        for(String arg : args) {

            // Special Case
            if(arg.equals("-gui")) {
                params.put("gui", "on");
                continue;
            }

            if((arg.charAt(0) == '-') && (param.equals(""))) {
                param = arg.substring(1);
            }
            else if((arg.charAt(0) != '-') && (!param.equals(""))) {
                params.put(param, arg);
                param = "";
            }
            else {
                throw new ParameterMisFormat("The input " + (param.equals("") ? arg : "-" + param) + " is incorrect format.");

            }

        }
        checkParameter(params);
        return params;
    }
    public static void checkParameter(HashMap<String, String> params) throws Exception {

        // Check Null Parameters
        if(params.get("gui").equals("off")) {
            if(params.get("x") == null) {
                throw new ParameterMisFormat("Parameter -x not found.");
            }
            if(params.get("t") == null) {
                throw new ParameterMisFormat("Parameter -t not found.");
            }
            if(params.get("s") == null) {
                throw new ParameterMisFormat("Parameter -s not found.");
            }
            if(params.get("p") == null) {
                throw new ParameterMisFormat("Parameter -p not found.");
            }
        }


        // Check Type Parameters
        try {
            if(params.get("x") != null) {
                int x = Integer.parseInt(params.get("x"));
                if(x < 0) {
                    throw new ParameterMisMatch("x value must in range 0 - " + Integer.MAX_VALUE);
                }
            }
            if(params.get("t") != null) {
                params.put("t", params.get("t").toLowerCase());
                if(!(params.get("t").equals("tcp") || params.get("t").equals("udp"))) {
                    throw new ParameterMisMatch("t value can input only 'tcp' or 'udp'.");
                }
            }
            if(params.get("p") != null) {
                int p = Integer.parseInt(params.get("p"));
                if((p < 1) || (p > 65535)) {
                    throw new ParameterMisMatch("p value can input only [1 - 65535]");
                }
            }
        }
        catch (NumberFormatException ex) {
            throw new ParameterMisMatch(ex.getMessage());
        }


    }
}
