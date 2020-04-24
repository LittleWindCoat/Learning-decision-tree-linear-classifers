package nn.core;

/**
 * Base class for the non-input units of a NeuralNetwork.
 * Do not include an input for the constant a_0=1.0 (AIMA p. 728)
 * since that will be added automatically.
 */
abstract public class NeuronUnit extends Unit {
	
	public NeuronUnit() {
		new Connection(new ConstantUnit(1.0), this);
	}
	
	/**
	 * Return the weight for this NeuronUnit's i'th input
	 * (where input 0 is the constant bias).
	 */
	public double getWeight(int i) {
		return this.incomingConnections.get(i).weight;
	}
	
	/**
	 * Set the weight for this NeuronUnit's i'th input
	 * (where input 0 is the constant bias).
	 */
	public void setWeight(int i, double w) {
		this.incomingConnections.get(i).weight = w;
	}
	
	/**
	 * This NeuronUnit's output value.
	 */
	protected double output = 0.0;
	
	/**
	 * Return the output value of this NeuronUnit.
	 */
	@Override
	public double getOutput() {
		return output;
	}

	/**
	 * Return the weighted sum of this NeuronUnit's inputs.
	 * This is also used in the backpropagation algorithm which is
	 * why I broke it out from the run() method.
	 */
	public double getInputSum() {
		double sum = 0;
		for (Connection conn : this.incomingConnections) {
			sum += conn.weight * conn.src.getOutput();
		}
		return sum;
	}
	
	/**
	 * Return h_w(x) = Threshold(w \cdot [1,x])
	 */
	public double h_w(double[] x) {
		double wdotx = 0;
		for (int i=0; i < this.incomingConnections.size(); i++) {
			double wi = this.incomingConnections.get(i).weight;
			double xi = (i == 0) ? 1.0 : x[i-1];
			wdotx += wi * xi;
		}
		return activation(wdotx);
	}
	
	/**
	 * ``Each unit j first computes a weighted sum of its inputs.
	 * Then it applies an activation function g to this sum to
	 * derive the output.'' (AIMA p728).
	 */
	@Override
	public void run() {
		double in_j = this.getInputSum();
		//System.out.println("NeuronUnit.run: in_j=" + in_j);
		double a_j = this.activation(in_j);
		//System.out.println("NeuronUnit.run: a_j=" + a_j);
		this.output = a_j;
	}
	
	/**
	 * The activation (threshold) function used by this NeuronUnit.
	 * This method must be specified by subclassses.
	 */
	abstract public double activation(double in);
	
	/**
	 * Update the weights of this NeuronUnit using the given
	 * input values, the given output value, and learning rate (alpha).
	 * This method must be specified by subclassses.
	 */
	@Override
	abstract public void update(double[] inputs, double output, double alpha);

	/**
	 * Error term for this NeuronUnit, used during backprop.
	 */
	public double delta;
	
}
