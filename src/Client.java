import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;

public class Client implements Runnable {


    private String ip, protocol, gui;
    private int port, x;
    private JDialog dialog;

    public Client(HashMap<String, String> params) {
        this.ip = params.get("s");
        this.port = Integer.parseInt(params.get("p"));
        this.x = Integer.parseInt(params.get("x"));
        this.protocol = params.get("t");
        this.gui = params.get("gui");
    }

    public void connectViaTCP() throws Exception {

        try {
            Socket clientSocket = new Socket(this.ip, this.port);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(x + "");


            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("FROM SERVER: " + inFromServer.readLine());

            clientSocket.close();

        }
        catch (ConnectException ex) {
            ex.printStackTrace();

        }

    }

    public void connectViaUDP() throws Exception {

        DatagramSocket clientSocket = new DatagramSocket();

        String dataToSend = x + "";
        byte[] sendData = dataToSend.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(this.ip), this.port);
        clientSocket.send(sendPacket);


        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);


        System.out.println("FROM SERVER:" + receivePacket.getData());
        clientSocket.close();

    }

    @Override
    public void run() {
        try {
            System.out.println("come");

            if(this.gui.equals("on")) {
                JOptionPane optionPane = new JOptionPane("Waiting For Reply ...", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                dialog = optionPane.createDialog("Status");
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                dialog.setVisible(true);
            }

            connectViaTCP();



        }
        catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
}
