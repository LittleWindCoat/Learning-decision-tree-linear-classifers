package nn.core;

import java.util.Arrays;

/**
 * An example is a vector of double values for the inputs and a
 * vector of double values for the outputs.
 */
public class Example {
	
	public double[] inputs;
	public double[] outputs;
	
	public Example(double[] inputs, double[] outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
	}
	
	public Example(int ninputs, int noutputs) {
		this(new double[ninputs], new double[noutputs]);
	}
	
	public String toString() {
		return Arrays.toString(inputs) + " -> " + Arrays.toString(outputs);
	}

}
