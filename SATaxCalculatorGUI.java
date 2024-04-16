import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SATaxCalculatorGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel incomeLabel;
    private JTextField incomeField;
    private JLabel ageLabel;
    private JComboBox<String> ageComboBox;
    private JButton calculateButton;
    private JLabel resultLabel;

    public SATaxCalculatorGUI() {
        frame = new JFrame("South African Tax Calculator");
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        incomeLabel = new JLabel("Enter your annual income:");
        incomeField = new JTextField();
        ageLabel = new JLabel("Select your age bracket:");
        ageComboBox = new JComboBox<>(new String[]{"Under 65 years", "65 to 75 years", "Over 75 years"});
        calculateButton = new JButton("Calculate");
        resultLabel = new JLabel();

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTax();
            }
        });

        panel.add(incomeLabel);
        panel.add(incomeField);
        panel.add(ageLabel);
        panel.add(ageComboBox);
        panel.add(calculateButton);
        panel.add(resultLabel);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    private void calculateTax() {
        try {
            double income = Double.parseDouble(incomeField.getText());
            int ageBracketIndex = ageComboBox.getSelectedIndex();
            double tax = calculateTaxAmount(income, ageBracketIndex);
            resultLabel.setText(String.format("Your tax payable is: %.2f", tax));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter a valid number for income.");
        }
    }

    private double calculateTaxAmount(double income, int ageBracketIndex) {
        double tax = 0.0;
        double[] brackets = { 0, 205900, 321600, 445100, 584200 };
        double[] rates = { 0.18, 0.26, 0.31, 0.36, 0.39 };
        double primaryDeduction = 14958;

        double secondaryDeduction;
        switch (ageBracketIndex) {
            case 0: // Under 65 years
                secondaryDeduction = 0;
                break;
            case 1: // 65 to 75 years
                secondaryDeduction = 14958; // Secondary rebate for individuals aged 65 to 75 years
                break;
            case 2: // Over 75 years
                secondaryDeduction = 22994; // Secondary rebate for individuals over 75 years
                break;
            default:
                throw new IllegalArgumentException("Invalid age bracket index.");
        }

        for (int i = 0; i < brackets.length - 1; i++) {
            if (income > brackets[i]) {
                double taxableAmount = Math.min(brackets[i + 1] - brackets[i], income - brackets[i]);
                tax += taxableAmount * rates[i];
            } else {
                break;
            }
        }

        tax -= primaryDeduction;
        tax -= secondaryDeduction;

        return Math.max(tax, 0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SATaxCalculatorGUI();
            }
        });
    }
}
