import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HttpRequester extends JPanel {



    public static StringBuilder Http(String ip, String method) throws IOException {
        URL obj = new URL(ip);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);
        con.setDoOutput(true);
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = null;
            while ((inputLine = in.readLine()) != null) {
                if (response != null)
                    response.append(inputLine);
                else
                    response = new StringBuilder(inputLine);
            }
            in.close();
            assert response != null;
            return response;
        }
        return new StringBuilder("ERROR");
    }

    public static JTextField ip = new JTextField("");
    public static String ipEntered;
    public static String methodEntered;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static JComboBox<String> httpMethodComboBox;
    private static JTextArea resultTextArea;

    @Override
    public void paintComponent(Graphics g) {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RequestApplication");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton request = new JButton("Отправить запрос");
        frame.setSize(WIDTH,HEIGHT);
        frame.add(request);
        request.setBounds(WIDTH - 200,100,150,20);

        JLabel ipLabel = new JLabel("IP адрес:");
        ipLabel.setBounds(600,10,150,20);
        frame.add(ipLabel);

        ip.setBounds(600,30,150,20);
        ip.setText("http://194.67.104.197:8080/sample");

        JLabel httpMethodLabel = new JLabel("HTTP метод:");
        httpMethodLabel.setBounds(600,50,150,20);
        String[] httpMethods = {"GET", "POST","PUT","DELETE"};
        httpMethodComboBox = new JComboBox<>(httpMethods);
        httpMethodComboBox.setBounds(600,70,150,20);

        resultTextArea = new JTextArea(20, 60);
        resultTextArea.setEditable(false);
        resultTextArea.setBounds(10,10,WIDTH - 300,HEIGHT);
        frame.add(resultTextArea);
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);
        frame.add(resultScrollPane);
        frame.add(httpMethodLabel);
        frame.add(httpMethodComboBox);

        frame.add(ip);
        HttpRequester panel = new HttpRequester();

        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.getContentPane().add(panel);

        request.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    methodEntered = httpMethodComboBox.getSelectedItem().toString();
                    ipEntered = ip.getText();
                    String response = String.valueOf(Http(ipEntered,methodEntered));
                    resultTextArea.setText(response);
                    frame.add(resultTextArea);
                    frame.repaint();
                    frame.revalidate();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        frame.setVisible(true);
    }

}