package nn.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nn.core.Example;
import nn.examples.IrisExampleGenerator;
import nn.examples.IrisNN;

public class IrisNNUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected IrisNN network; 
	protected MultiLayerNetworkView networkView;
	protected JTextField alphaTextField;
	protected JTextField nTextField;
	protected JLabel resultLabel;
	protected JLabel counterLabel;
	
	protected List<Example> examples;
	
	public IrisNNUI(IrisNN network) throws IOException {
		super("Iris NN");
		this.network = network;
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		JPanel topbar = new JPanel();
		contentPane.add(topbar, BorderLayout.NORTH);
		JLabel label = new JLabel("alpha:");
		topbar.add(label);
		alphaTextField = new JTextField("0.10");
		topbar.add(alphaTextField);
		JButton button = new JButton("Train 1");
		topbar.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double alpha = Double.parseDouble(alphaTextField.getText());
				train1(alpha);
			}
		});
		button = new JButton("Train Rest");
		topbar.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double alpha = Double.parseDouble(alphaTextField.getText());
				trainRest(alpha);
			}
		});
		label = new JLabel("n:");
		topbar.add(label);
		nTextField = new JTextField("100");
		topbar.add(nTextField);
		button = new JButton("Train n Epochs");
		topbar.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = Integer.parseInt(nTextField.getText());
				double alpha = Double.parseDouble(alphaTextField.getText());
				trainNEpochs(n, alpha);
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
		networkView = new MultiLayerNetworkView(network);
		center.add(networkView);
		resultLabel = new JLabel();
		resultLabel.setFont(new Font("Dialog", Font.PLAIN, 96));
		center.add(resultLabel);
		contentPane.add(center, BorderLayout.CENTER);
		counterLabel = new JLabel("Next example: 0");
		counterLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPane.add(counterLabel, BorderLayout.SOUTH);
		pack();
		// Examples
		try {
			this.examples = new IrisExampleGenerator("src/nn/examples/iris.data.txt").examples();
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	int nextExampleIndex = 0;
	
	protected Example nextExample() {
		Example result = examples.get(nextExampleIndex);
		nextExampleIndex += 1;
		if (nextExampleIndex >= examples.size()) {
			nextExampleIndex = 0;
		}
		counterLabel.setText("Next example: " + nextExampleIndex);
		return result;
	}
	
	protected void train1(double alpha) {
		Example example = nextExample();
		network.train(example, alpha);
		test(example);
		networkView.update();
	}
	
	protected void trainRest(double alpha) {
		while (nextExampleIndex != 0) {
			train1(alpha);
		}	
		resultLabel.setText("");
	}
	
	protected void trainNEpochs(int n, double alpha) {
		network.train(examples, n, alpha);
		networkView.update();
		resultLabel.setText("");
	}
	
	protected void test1() {
		Example example = nextExample();
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
	
	public static void main(String[] argv) throws IOException {
		IrisNN network = new IrisNN();
		JFrame frame = new IrisNNUI(network);
		frame.setVisible(true);
	}

}
