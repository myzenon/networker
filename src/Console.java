import parameter.Parameter;
import parameter.ParameterConsoleException;
import parameter.ParameterMisMatchException;
import parameter.ParameterNotFoundException;

import java.util.*;

public class Console implements ViewInterface {

    private HashMap<String, String> params;

    public Console(HashMap<String, String> params) {
        this.params = params;
    }

    @Override
    public void showMessage(String message, String messageType) {
        if(messageType.equals("info")) {
            System.out.println("Info : " + message);
        }
        if(messageType.equals("error")) {
            System.err.println("Error : " + message);
        }
        if(messageType.equals("success")) {
            System.out.println("Success : " + message + " **");
        }
    }

    @Override
    public void create() {
        try {
            Parameter.checkNullParameter(this.params);
            Parameter.checkParameter(this.params);
            new Thread(new Client(params, this)).start();
        }
        catch (ParameterConsoleException ex) {
            showMenuWithError(ex);
        }
    }

    public static void showMenu() {
        System.out.println();
        System.out.println(" [ This program require all of following argument in the same format. ]");
        System.out.println();
        System.out.println("\t-x <Number> : Number is a 32-bit unsigned integer");
        System.out.println("\t-t <UDP|TCP> : UDP and TCP are type of protocol to connect server");
        System.out.println("\t-s <IP Address> : IP Address or Host Name of the server [ex: dmx.cs.colostate.edu, 127.0.0.1]");
        System.out.println("\t-p <Port Number> : the port being used by the server [1 - 65535]");
        System.out.println();
    }

    public static void showMenuWithError(ParameterConsoleException ex) {
        System.out.println();
        if(ex.getConsoleMessage() != null) {
            System.err.println(" *** " + ex.getConsoleMessage());
        }
        System.err.println(" **** " + ex.getMessage());
        showMenu();
    }



}
