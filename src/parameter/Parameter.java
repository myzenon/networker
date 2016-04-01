package parameter;

import java.util.HashMap;

/**
 * Created by Zenon on 3/27/2016 AD.
 */
public class Parameter {
    public static HashMap<String, String> mapParameter(String args[]) throws ParameterMisFormatException {
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
                throw new ParameterMisFormatException("The input " + (param.equals("") ? arg : "-" + param) + " is incorrect format.");

            }

        }

        return params;
    }

    public static void checkNullParameter(HashMap<String, String> params) throws ParameterNotFoundException {

        // Check Null Parameters

        if(params.get("x") == null) {
            throw new ParameterNotFoundException("Please input x number.", "Parameter -x not found.");
        }
        if(params.get("t") == null) {
            throw new ParameterNotFoundException("Please input Protocol Type" ,"Parameter -t not found.");
        }
        if(params.get("s") == null) {
            throw new ParameterNotFoundException("Please input IP Address", "Parameter -s not found.");
        }
        if(params.get("p") == null) {
            throw new ParameterNotFoundException("Please input Port Number", "Parameter -p not found.");
        }


    }


    public static void checkParameter(HashMap<String, String> params) throws ParameterMisMatchException {

        // Check Type Parameters
        try {
            if(params.get("x") != null) {
                int x = Integer.parseInt(params.get("x"));
                if(x < 0) {
                    throw new ParameterMisMatchException("The number x value must in range between 0 - " + Integer.MAX_VALUE,  "Incorrect in -x parameter");
                }
            }
            if(params.get("t") != null) {
                params.put("t", params.get("t").toLowerCase());
                if(!(params.get("t").equals("tcp") || params.get("t").equals("udp"))) {
                    throw new ParameterMisMatchException("Protocol Type can input only 'tcp' or 'udp'", "Incorrect in -t parameter");
                }
            }
            if(params.get("p") != null) {
                int p = Integer.parseInt(params.get("p"));
                if((p < 1) || (p > 65535)) {
                    throw new ParameterMisMatchException("Port Number can input only in range between [1 - 65535]", "Incorrect in -p parameter");
                }
            }
        }
        catch (NumberFormatException ex) {
            throw new ParameterMisMatchException(ex.getMessage() + " is not integer", "Incorrect in -x or -p parameter");
        }


    }
}
