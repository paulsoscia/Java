import javax.swing.*;
import java.awt.*;

public class GetTextFieldInput extends JFrame {

    private JTextField inputField;
    private JButton submitButton;
    private JLabel displayLabel;

    public GetTextFieldInput() {
        setTitle("Get Text Field Input");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        inputField = new JTextField(20); // 20 columns wide
        add(inputField);

        submitButton = new JButton("Submit");
        add(submitButton);

        displayLabel = new JLabel("Input will appear here.");
        add(displayLabel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText(); // Get the text from the JTextField
                displayLabel.setText("You entered: " + inputText); // Display it in a JLabel
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GetTextFieldInput::new);
    }
}
