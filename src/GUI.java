import parameter.Parameter;
import parameter.ParameterConsoleException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GUI implements ViewInterface {
    private JPanel panel;
    private JTextField parameters;
    private JButton connectButton;
    private JTextField ipAddress;
    private JTextField port;
    private JTextField x;
    private JRadioButton TCPRadioButton;
    private JRadioButton UDPRadioButton;

    private HashMap<String, String> params;
    private GUI viewGUI;

    public GUI(HashMap<String, String> params) {
        this.params = params;
        this.viewGUI = this;
        setParametersText();
        setParametersEachField();
        parameters.setHorizontalAlignment(JTextField.CENTER);
        ipAddress.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                params.put("s", ipAddress.getText());
                setParametersText();
                super.keyReleased(e);
            }
        });
        port.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                params.put("p", port.getText());
                setParametersText();
                super.keyReleased(e);
            }
        });
        x.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                params.put("x", x.getText());
                setParametersText();
                super.keyReleased(e);
            }
        });
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableField();
                showMessage("Checking input ...", "info");
                try {
                    Parameter.checkNullParameter(params);
                    Parameter.checkParameter(params);
                    showMessage("Waiting for connection ...", "info");
                    new Thread(new Client(params, viewGUI)).start();
                }
                catch (ParameterConsoleException ex) {
                    showMessage(ex.getMessage(), "error");
                }
            }
        });
        TCPRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                params.put("t", "tcp");
                setParametersText();
            }
        });
        UDPRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                params.put("t", "udp");
                setParametersText();
            }
        });
    }

    @Override
    public void showMessage(String message, String messageType) {
        parameters.setBackground(Color.WHITE);
        if(messageType.equals("info")) {
            parameters.setForeground(Color.BLUE);
        }
        if(messageType.equals("error")) {
            parameters.setForeground(Color.RED);
            enableField();
        }
        if(messageType.equals("success")) {
            parameters.setForeground(new Color(0, 150, 0));
            enableField();
        }
        parameters.setText(message);
    }

    public void enableField() {
        connectButton.setEnabled(true);
        ipAddress.setEnabled(true);
        port.setEnabled(true);
        x.setEnabled(true);
        TCPRadioButton.setEnabled(true);
        UDPRadioButton.setEnabled(true);
    }

    public void disableField() {
        connectButton.setEnabled(false);
        ipAddress.setEnabled(false);
        port.setEnabled(false);
        x.setEnabled(false);
        TCPRadioButton.setEnabled(false);
        UDPRadioButton.setEnabled(false);
    }

    public void setParametersEachField() {
        if(params.get("x") != null) {
            x.setText(params.get("x"));
        }
        if(params.get("t") != null) {
            if(params.get("t").equals("tcp")) {
                TCPRadioButton.setSelected(true);
            }
            else if(params.get("t").equals("udp")) {
                UDPRadioButton.setSelected(true);
            }
        }
        if(params.get("s") != null) {
            ipAddress.setText(params.get("s"));
        }
        if(params.get("p") != null) {
            port.setText(params.get("p"));
        }
    }

    public void setParametersText() {
        parameters.setForeground(panel.getForeground());
        parameters.setBackground(panel.getBackground());
        String paramsText = "";
        for(Map.Entry<String, String> param : params.entrySet()) {
            if(param.getKey().equals("gui")) {
                continue;
            }
            paramsText += "-" + param.getKey() + " " + param.getValue() + " ";
        }
        parameters.setText(paramsText);
    }



    @Override
    public void create() {
        JFrame frame = new JFrame("Client Program");
        frame.setContentPane(new GUI(params).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
}
