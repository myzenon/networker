import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;

public class Client implements Runnable {


    private String ip, protocol, gui;
    private int port, x;
    private ViewInterface view;

    public Client(HashMap<String, String> params, ViewInterface view) {
        this.ip = params.get("s");
        this.port = Integer.parseInt(params.get("p"));
        this.x = Integer.parseInt(params.get("x"));
        this.protocol = params.get("t");
        this.gui = params.get("gui");
        this.view = view;
    }

    public void connectViaTCP() {
            try {
                view.showMessage("Waiting for connection ...", "info");
                Socket clientSocket = new Socket(this.ip, this.port);
                clientSocket.setSoTimeout(3000);

                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                outToServer.writeBytes(x + "\n");
                view.showMessage("Sent number to server " + this.ip + ":" + this.port + " via TCP", "info");

                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                try {
                    inFromServer.readLine();
                    view.showMessage("Sent Message Complete", "success");
                }
                catch (SocketTimeoutException ex) {
                    view.showMessage("Connection Timeout. Server isn't reply in 3 seconds.", "error");
                }
                finally {
                    clientSocket.shutdownOutput();
                    clientSocket.shutdownInput();
                    clientSocket.close();
                }
            }
            catch (SocketException ex) {
                view.showMessage("Can't Connect to Server [" + this.ip + ":" + this.port + "]", "error");
            }
            catch (Exception ex) {
                ex.printStackTrace();
                view.showMessage(ex.getMessage(), "error");
            }
    }

    public void connectViaUDP() {

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            clientSocket.setSoTimeout(3000);

            byte[] sendData = (this.x + "").getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(this.ip), this.port);
            clientSocket.send(sendPacket);

            view.showMessage("Sent number to server " + this.ip + ":" + this.port + " via UDP", "info");

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try {
                clientSocket.receive(receivePacket);
                receivePacket.getData();
                view.showMessage("Sent Message Complete", "success");
            }
            catch (SocketTimeoutException ex) {
                view.showMessage("Connection Timeout. Server isn't reply in 3 seconds.", "error");
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
                clientSocket.close();
            }

        }
        catch (BindException ex) {
            view.showMessage("Cannot Connect to Server", "error");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
        if(protocol.equals("tcp")) {
            connectViaTCP();
        }
        else if(protocol.equals("udp")) {
            connectViaUDP();
        }
    }
}
