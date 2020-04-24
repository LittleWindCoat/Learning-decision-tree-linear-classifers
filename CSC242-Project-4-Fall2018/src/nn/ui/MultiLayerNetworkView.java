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
import nn.core.MultiLayerFeedForwardNeuralNetwork;
import nn.core.NeuronUnit;

public class MultiLayerNetworkView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected MultiLayerFeedForwardNeuralNetwork network;
	
	protected JPanel[] layerPanels;
	
	public MultiLayerNetworkView(MultiLayerFeedForwardNeuralNetwork network) {
		super();
		this.network = network;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		layerPanels = new JPanel[network.getNumLayers()];
		// Input layer
		layerPanels[0] = new JPanel();
		layerPanels[0].setLayout(new BoxLayout(layerPanels[0], BoxLayout.Y_AXIS));
		InputUnit[] inputs = network.getInputUnits();
		for (int i=0; i < inputs.length; i++) {
			if (i > 0) {
				layerPanels[0].add(Box.createRigidArea(new Dimension(0,10)));
			}
			InputUnitView view = new InputUnitView(inputs[i]);
			layerPanels[0].add(view);
		}
		this.add(layerPanels[0]);
		// Rest of layers
		for (int l=1; l < network.getNumLayers(); l++) {
			add(Box.createRigidArea(new Dimension(10,00)));
			layerPanels[l] = new JPanel();
			layerPanels[l].setLayout(new BoxLayout(layerPanels[l], BoxLayout.Y_AXIS));
			NeuronUnit[] units = network.getLayerUnits(l);
			for (int i=0; i < units.length; i++) {
				if (i > 0) {
					layerPanels[l].add(Box.createRigidArea(new Dimension(0,10)));
				}
				NeuronUnitView view = new NeuronUnitView(units[i]);
				layerPanels[l].add(view);
			}
			this.add(layerPanels[l]);
		}
		// Set all labels
		update();
		// Border
		Border line = BorderFactory.createLineBorder(Color.black);
		Border space = BorderFactory.createEmptyBorder(5,5,5,5);
		this.setBorder(BorderFactory.createCompoundBorder(line, space));
	}
	
	public void update() {
		for (int l=0; l < layerPanels.length; l++) {	
			for (Component view: layerPanels[l].getComponents()) {
				if (view instanceof InputUnitView) {
					((InputUnitView)view).update();
				} else if (view instanceof NeuronUnitView) {
						((NeuronUnitView)view).update();
				}
			}
		}
	}

}
