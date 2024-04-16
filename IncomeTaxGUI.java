import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IncomeTaxGUI {
	/* elements used in our GUI app */
	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JTextField input, output;
	private JButton button;

	/* public constructor to configure app */
	public IncomeTaxGUI() {
		System.out.println("starting app...\n\n");
		frame = new JFrame("Income Tax Calculator Java"); // create JFrame
		panel = new JPanel(); // create JPanel
		panel.setLayout(new FlowLayout()); // set panel layout

		label = new JLabel("Enter Total Income"); // create a label with proper message
		input = new JTextField(); // text field for user input
		input.setPreferredSize(new Dimension(200, 24)); // input text field dimensions
		output = new JTextField(); // text field for output (total tax)
		output.setPreferredSize(new Dimension(200, 24)); // output text field dimensions
		output.setEditable(false); // don't let user edit the output field
		button = new JButton(); // create button to calculate tax
		button.addActionListener(new ActionListener() { // on button click this action will be performed

			@Override
			public void actionPerformed(ActionEvent e) { // call calculate tax method and show total tax in output
															// textfield
				try {
					double ti = Double.parseDouble(input.getText()); // total income
					double tax = calculateTax(ti); // total tax
					output.setText("" + tax);
				} catch (NumberFormatException e1) { // handle non-numeric inputs
					String errMsg = "total income must be numeric\n\n";
					output.setText(errMsg);
					System.err.println(errMsg);
				}
			}
		});
		button.setText("Calculate Tax"); // set message to be displayed on button

		/* add all elements to the panel */
		panel.add(label);
		panel.add(input);
		panel.add(button);
		panel.add(output);

		/* set frame properties */
		frame.add(panel);
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public double calculateTax(double ti) { // ti -> total income
		double total_tax = 0;
		double total_cess = 0;
		double tax1=0, tax2=0, tax3=0;
		if (ti > 300000) { // slab 1
			double amt = 0;
			if ((ti - 300000) > 200000) // if ti > slab 1
				amt = 200000;
			else
				amt = ti - 300000;
			tax1 = (0.1 * amt); // total tax in slab 1
			total_tax += tax1;
			System.out.println("Tax Payable for slab 300,000-500,000: " + tax1);
		}
		if (ti > 500000) { // slab 2
			double amt = 0;
			if ((ti - 500000) > 500000) // if ti > slab 2
				amt = 500000;
			else
				amt = ti - 500000;
			tax2 = 0.2 * amt; // total tax in slab 2
			total_tax += tax2;
			System.out.println("Tax Payable for slab 500,000-1,000,000: " + tax2);
		}
		if (ti > 1000000) { // slab 3
			tax3 = 0.3 * (ti - 1000000); // total tax in slab 3
			total_tax += tax3;
			System.out.println("Tax Payable for slab 1,000,000-above: " + tax3);
		}
		total_cess = 0.03 * total_tax; // all slabs have same 3% cess
		System.out.println("Total tax = "+tax1+" + "+tax2+" + "+tax3+" = "+total_tax);
		System.out.println("Total cess = 3% of income tax = " + total_cess);
		System.out.println("total tax: " + (total_tax + total_cess));
		System.out.println("\n");
		return total_tax + total_cess; // return total tax + total cess
	}

	public static void main(String s[]) throws IOException {
		IncomeTaxGUI app = new IncomeTaxGUI();
	}
}
