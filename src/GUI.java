import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GUI {
    private JPanel panel1;
    private JTextField parameters;
    private JButton connectButton;
    private JTextField ipAddress;
    private JTextField port;
    private JTextField x;
    private JRadioButton TCPRadioButton;
    private JRadioButton UDPRadioButton;

    private HashMap<String, String> params;

    public GUI(HashMap<String, String> params) {
        this.params = params;
        setParametersText();
        setParametersEachField();
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
        TCPRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                params.put("t", "tcp");
                setParametersText();
                super.mouseClicked(e);
            }
        });
        UDPRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                params.put("t", "udp");
                setParametersText();
                super.mouseClicked(e);
            }
        });
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Client(params)).run();
            }
        });
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
        String paramsText = "";
        for(Map.Entry<String, String> param : params.entrySet()) {
            if(param.getKey().equals("gui")) {
                continue;
            }
            paramsText += "-" + param.getKey() + " " + param.getValue() + " ";
        }
        parameters.setText(paramsText);
    }

    public static void createWindow(HashMap<String, String> params) {
        JFrame frame = new JFrame("Client Program");
        frame.setContentPane(new GUI(params).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
}
