package nn.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import nn.core.Connection;
import nn.core.NeuronUnit;

public class NeuronUnitView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected NeuronUnit unit;
	
	protected JPanel weightsPanel;
	protected JLabel weightLabels[];
	protected JLabel outputLabel;
	
	public NeuronUnitView(NeuronUnit unit) {
		super();
		this.unit = unit;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		weightsPanel = new JPanel();
		weightsPanel.setLayout(new BoxLayout(weightsPanel, BoxLayout.Y_AXIS));
		int nweights = unit.incomingConnections.size();
		weightLabels = new JLabel[nweights];
		for (int i=0; i < nweights; i++) {
			weightLabels[i] = new JLabel();
			weightsPanel.add(weightLabels[i]);
		}
		add(weightsPanel);
		add(Box.createRigidArea(new Dimension(5,00)));
		outputLabel = new JLabel();
		add(outputLabel);
		update();
		// Border
		Border line = BorderFactory.createLineBorder(Color.black);
		Border space = BorderFactory.createEmptyBorder(5,5,5,5);
		this.setBorder(BorderFactory.createCompoundBorder(line, space));
	}
	
	public void update() {
		int nweights = unit.incomingConnections.size();
		for (int i=0; i < nweights; i++) {
			Connection conn = unit.incomingConnections.get(i);
			weightLabels[i].setText(String.format("%.3f", conn.weight));
		}
		outputLabel.setText(String.format("%.3f",  unit.getOutput()));
	}

}
