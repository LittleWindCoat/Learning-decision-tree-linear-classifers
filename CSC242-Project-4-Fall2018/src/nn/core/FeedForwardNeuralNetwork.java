package nn.core;

/**
 * Base class for feed-forward NeuralNetworks, where the connections
 * are only in one direction (that is, it forms a directed acyclic
 * graph). ``Feed-forward networks are arranged in layers, such that
 * each unit receives input only from units in the immediately preceding
 * layer.'' (AIMA p729).
 */
abstract public class FeedForwardNeuralNetwork extends NeuralNetwork {
	
	/**
	 * The Units of this FeedForwardNeuralNetwork arranged in
	 * layers. The first layer should consist of InputUnits and
	 * the rest of the layers should be NeuronUnits (the last layer
	 * is the output layer).
	 */
	protected Unit[][] layers;
	
	public FeedForwardNeuralNetwork(Unit[][] layers) {
		this.layers = layers;
	}
	
	public int getNumLayers() {
		return this.layers.length;
	}
	
	/**
	 * Return the InputUnits in the input (first) layer of this FeedForwardNeuralNetwork.
	 */
	@Override
	public InputUnit[] getInputUnits() {
		return (InputUnit[])this.layers[0];
	}

	/**
	 * Return the NeuronUnits in the output (final) layer of this FeedForwardNeuralNetwork.
	 */
	@Override
	public NeuronUnit[] getOutputUnits() {
		return (NeuronUnit[])this.layers[this.layers.length-1];
	}

	/**
	 * Return the NeuronUnits in layer i of this FeedForwardNeuralNetwork.
	 */
	public NeuronUnit[] getLayerUnits(int i) {
		return (NeuronUnit[])this.layers[i];
	}
	
	/**
	 * Return the vector of output (activation) values of the output units of this FeedForwardNeuralNetwork.
	 */
	public double[] getOutputValues() {
		NeuronUnit[] units = getOutputUnits();
		double[] values = new double[units.length];
		for (int i=0; i < units.length; i++) {
			values[i] = units[i].getOutput();
		}
		return values;
	}	
	
}
