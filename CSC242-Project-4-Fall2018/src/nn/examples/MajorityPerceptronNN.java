package nn.examples;

import java.util.List;

import nn.core.*;

/**
 * N-input, one output single-layer feed-forward (perceptron) network
 * suitable for learning the majority function of N Boolean inputs.
 * See AIMA p. 731 and AIMA Fig 18.22(a) for a training/testing graph.
 * 
 * AIMA says that after training, each w_i should be 1 for i>0, and
 * w_0=-n/2=-5.5. However other combinations are possible. For example,
 * for 3 inputs, if w_0=-1.9, then the w_i can be +0.95 since 2*0.95=+1.9
 * and the PerceptronUnit fires at threshold 0.
 * 
 * For 11 inputs (as in the AIMA example), I found that after several hundred
 * training examples, the weights converged to w_0=-25.65 and the w_i had eight
 * weights=+4.75 and three weights=+3.8. This works because 3*3.8+3*4.75=25.65.
 * YMMV on different runs.
 */
public class MajorityPerceptronNN extends SingleLayerFeedForwardNeuralNetwork {
	
	/**
	 * Construct and return a new MajorityPerceptronNN with the given number of
	 * input units and a single PerceptronUnit output connected to all the inputs.
	 */
	public MajorityPerceptronNN(int ninputs) {
		// Eleven input units and one output unit
		super(new InputUnit[ninputs], new NeuronUnit[1]);
		// Create the units
		InputUnit[] inputs = this.getInputUnits();
		for (int i=0; i < ninputs; i++) {
			inputs[i] = new InputUnit();
		}
		// Could use LogisticUnit also; either way the network is a ``perceptron'' (AIMA p730)
		NeuronUnit[] outputs = this.getOutputUnits();
		outputs[0] = new PerceptronUnit();
		// Connect each of the inputs to the output
		for (int i=0; i < ninputs; i++) {
			new Connection(inputs[i], outputs[0]);
		}
	}

	/**
	 * Output of this NN is the 0-1 output of the only output unit.
	 */
	public double getOutputValue() {
		return (int)this.getOutputUnits()[0].getOutput();
	}
	
	/**
	 * Return true if this MajorityPerceptronNN gets the right answer on the given Example.
	 * For this perceptron network, the single output node will have output (activation)
	 * 0 or 1, which must match the output value of the Example.
	 */
	@Override
	public boolean test(Example example) {
		InputUnit[] inputs = this.getInputUnits();
		for (int i=0; i < inputs.length; i++) {
			inputs[i].setOutput(example.inputs[i]);
		}
		NeuronUnit[] outputs = this.getOutputUnits();
		outputs[0].run();
		boolean result = getOutputValue() == example.outputs[0];
		return result;
	}
	
	public static void main(String[] argv) {
		int ninputs = 11;
		double alpha = 0.95;
		// AIMA Fig 18.22(a) goes to 100 samples; I found ~3000 needed
		int nmax = 100;
		int nstep = 5;
		if (argv.length > 0) {
			nmax = Integer.parseInt(argv[0]);
		}
		if (argv.length > 1) {
			nstep = Integer.parseInt(argv[1]);
		}
		MajorityExampleGenerator generator = new MajorityExampleGenerator(ninputs);
		System.out.println("Learning Curve testing on all training data (see AIMA Fig 18.22(a)");
		System.out.println("N\tACCURACY");
		for (int n=nstep; n <= nmax; n+=nstep) {
			MajorityPerceptronNN network = new MajorityPerceptronNN(ninputs);
			List<Example> trainingSet = generator.examples(n);
			network.train(trainingSet, alpha);
			List<Example> testingSet = generator.examples(n);
			double accuracy = network.test(testingSet);
			System.out.print(n + "\t" + accuracy + "\t");
			network.dump();
		}
		System.out.println();
		int n = 100;
		int k = 10;
		System.out.println("k-Fold Cross-Validation: n=" + n + ", k=" + k);
		List<Example> examples = generator.examples(n);
		MajorityPerceptronNN network = new MajorityPerceptronNN(ninputs);
		double acc = network.kFoldCrossValidate(examples, k, alpha);
		System.out.format("average accuracy: %.3f\n", acc);
	}

}

