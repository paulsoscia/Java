import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
/**
 * A Java Swing program that shows how much water you should drink a day.
 * @author www.codejava.net
*/

public class TakeInput extends JFrame implements ActionListener {
    private JLabel labelQuestion;
    private JLabel labelWeight;
    private JTextField fieldWeight;
    private JButton buttonTellMe;
 
    public TakeInput() {
        super("LBS->KG Calculator");
 
        initComponents();
 
        setSize(240, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
 
    private void initComponents() {
        labelQuestion = new JLabel("Input weight (lbs)?");
        labelWeight = new JLabel("My weight (lbs):");
        fieldWeight = new JTextField(5);
        buttonTellMe = new JButton("LBS-> KG");
 
        setLayout(new FlowLayout());
 
        add(labelQuestion);
        add(labelWeight);
        add(fieldWeight);
        add(buttonTellMe);
 
        buttonTellMe.addActionListener(this);
    }
 
    public void actionPerformed(ActionEvent event) {
        String message = "Your weight in KGs %.1f ";
 
        float weight = Float.parseFloat(fieldWeight.getText());
        float waterAmount = convertLb2KG(weight);
 
        message = String.format(message, waterAmount);
 
        JOptionPane.showMessageDialog(this, message);
    }
 
    private float convertLb2KG(float weight) {
        return (weight /  2.205f) * 1.0f;
    }
 
    public static void main(String[] args) {
        new TakeInput().setVisible(true);
    }
}