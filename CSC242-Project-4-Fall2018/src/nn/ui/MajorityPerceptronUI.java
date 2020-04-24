package nn.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nn.core.Example;
import nn.example.MajorityExampleGenerator;
import nn.example.MajorityPerceptronNN;

public class MajorityPerceptronUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected MajorityPerceptronNN network; 
	protected SingleLayerNetworkView networkView;
	protected JTextField alphaTextField;
	protected JTextField nTextField;
	protected JLabel resultLabel;
	
	protected MajorityExampleGenerator generator;
	
	public MajorityPerceptronUI(MajorityPerceptronNN network) {
		super();
		this.network = network;
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		JPanel topbar = new JPanel();
		contentPane.add(topbar, BorderLayout.NORTH);
		JLabel label = new JLabel("alpha:");
		topbar.add(label);
		alphaTextField = new JTextField("0.95");
		topbar.add(alphaTextField);
		JButton button = new JButton("Train 1");
		topbar.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double alpha = Double.parseDouble(alphaTextField.getText());
				train1(alpha);
			}
		});
		label = new JLabel("n:");
		topbar.add(label);
		nTextField = new JTextField("100");
		topbar.add(nTextField);
		button = new JButton("Train N");
		topbar.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = Integer.parseInt(nTextField.getText());
				double alpha = Double.parseDouble(alphaTextField.getText());
				trainN(n, alpha);
			}
		});
		button = new JButton("Test 1");
		topbar.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test1();
			}
		});
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		networkView = new SingleLayerNetworkView(network);
		center.add(networkView);
		resultLabel = new JLabel();
		resultLabel.setFont(new Font("Dialog", Font.PLAIN, 96));
		center.add(resultLabel);
		contentPane.add(center, BorderLayout.CENTER);
		pack();
		// Example generator
		generator = new MajorityExampleGenerator(network.getInputUnits().length);
	}
	
	protected void train1(double alpha) {
		Example example = generator.nextExample();
		//System.out.println(example);
		network.train(example, alpha);
		test(example); // to update display
		networkView.update();
	}
	
	protected void trainN(int n, double alpha) {
		for (int i=0; i < n; i++) {
			train1(alpha);
		}	
	}
	
	protected void test1() {
		Example example = generator.nextExample();
		test(example);
	}
	
	protected void test(Example example) {
		if (network.test(example)) {
			resultLabel.setText("\u2714"); // CHECKMARK, HEAVY
			resultLabel.setForeground(Color.green);
		} else {
			resultLabel.setText("\u2718"); // BALLOT X, HEAVY
			resultLabel.setForeground(Color.red);
		}
		networkView.update();
	}
	
	public static void main(String[] argv) {
		int ninputs = 11;
		MajorityPerceptronNN network = new MajorityPerceptronNN(ninputs);
		JFrame frame = new MajorityPerceptronUI(network);
		frame.setVisible(true);
	}

}
