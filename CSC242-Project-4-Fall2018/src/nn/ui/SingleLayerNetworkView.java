package nn.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

import nn.core.InputUnit;
import nn.core.NeuronUnit;
import nn.core.SingleLayerFeedForwardNeuralNetwork;

public class SingleLayerNetworkView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected SingleLayerFeedForwardNeuralNetwork network;
	
	protected JPanel inputLayerPanel;
	protected JPanel outputLayerPanel;
	
	public SingleLayerNetworkView(SingleLayerFeedForwardNeuralNetwork network) {
		super();
		this.network = network;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		inputLayerPanel = new JPanel();
		inputLayerPanel.setLayout(new BoxLayout(inputLayerPanel, BoxLayout.Y_AXIS));
		InputUnit[] inputs = network.getInputUnits();
		for (int i=0; i < inputs.length; i++) {
			if (i > 0) {
				inputLayerPanel.add(Box.createRigidArea(new Dimension(0,10)));
			}
			InputUnitView view = new InputUnitView(inputs[i]);
			inputLayerPanel.add(view);
		}		
		add(inputLayerPanel);
		add(Box.createRigidArea(new Dimension(10,00)));
		outputLayerPanel = new JPanel();
		outputLayerPanel.setLayout(new BoxLayout(outputLayerPanel, BoxLayout.Y_AXIS));
		NeuronUnit[] outputs = network.getOutputUnits();
		for (int i=0; i < outputs.length; i++) {
			if (i > 0) {
				outputLayerPanel.add(Box.createRigidArea(new Dimension(0,10)));
			}
			NeuronUnitView view = new NeuronUnitView(outputs[i]);
			outputLayerPanel.add(view);
		}
		add(outputLayerPanel);
		update();
		// Border
		Border line = BorderFactory.createLineBorder(Color.black);
		Border space = BorderFactory.createEmptyBorder(5,5,5,5);
		this.setBorder(BorderFactory.createCompoundBorder(line, space));
	}
	
	/*
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(640, 480);
	}
	*/
	
	public void update() {
		for (Component view: inputLayerPanel.getComponents()) {
			if (view instanceof InputUnitView) {
				((InputUnitView)view).update();
			}
		}
		for (Component view: outputLayerPanel.getComponents()) {
			if (view instanceof NeuronUnitView) {
				((NeuronUnitView)view).update();
			}
		}
	}

}
