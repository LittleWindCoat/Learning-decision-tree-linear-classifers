package nn.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for computational elements (``units'') in a NeuralNetwork.
 * Units have to keep track of both outgoing and incoming Connections
 * (the latter are used for backprop).
 */
abstract public class Unit {
	
	/**
	 * Return the output value of this Unit.
	 */
	abstract public double getOutput();
	
	/**
	 * ``Run'' this Unit by recomputing its output
	 * value given the values of its inputs.
	 */
	abstract public void run();
	
	/**
	 * Update this Unit's weights based on the given input vector,
	 * output value, and learning rate.
	 */
	abstract public void update(double[] inputs, double output, double alpha);

	//
	// Connections
	//
	
	public List<Connection> outgoingConnections = new ArrayList<Connection>();
	
	public List<Connection> incomingConnections = new ArrayList<Connection>();
	
}
