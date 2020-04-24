package nn.core;

import java.util.List;

import nn.core.Example;

/**
 * A SingleLayerFeedForwardNeuralNetwork is a single-layer feed-forward network
 * where all the inputs are directly connected to the outputs
 * (AIMA Section 18.7.2).
 */
abstract public class SingleLayerFeedForwardNeuralNetwork extends FeedForwardNeuralNetwork {
	
	/**
	 * Construct and return a new SingleLayerFeedForwardNeuralNetwork with the given
	 * InputUnits for inputs and NeuronUnits for outputs. It's up
	 * to you to arrange the feed-forward connections between the Units
	 * properly.
	 */
	public SingleLayerFeedForwardNeuralNetwork(InputUnit[] inputs, NeuronUnit[] outputs) {
		super(new Unit[2][]);
		this.layers[0] = inputs;
		this.layers[1] = outputs;
	}
	
	/**
	 * Print this SingleLayerFeedForwardNeuralNetwork to stdout.
	 * We output the weights on the output units in tab-delimited format:
	 * UNITNUM w_0 w_1 ... w_n. 
	 */
	public void dump() {
		NeuronUnit[] outputs = this.getOutputUnits();
		for (int i=0; i < outputs.length; i++) {
			NeuronUnit unit = outputs[i];
			System.out.print(i);
			for (Connection conn : unit.incomingConnections) {
				System.out.format("\t%.2f", conn.weight);
			}
			System.out.println();
		}
	}
	
	/**
	 * Train this SingleLayerFeedForwardNeuralNetwork on the given Examples,
	 * using given learning rate alpha.
	 * This means updating the weights on the output units for
	 * each example on each step.
	 */
	public void train(List<Example> examples, double alpha) {
		for (int i=0; i < examples.size(); i++) {
			Example example = examples.get(i);
			train(example, alpha);
		}
	}
	
	/**
	 * Train this SingleLayerFeedForwardNeuralNetwork on the given Example,
	 * using given learning rate alpha.
	 * This means updating the weights on the output units based on the example.
	 */
	public void train(Example example, double alpha) {
	    // Must be implemented by you
	}
	
	/**
	 * Run a k-fold cross-validation experiment on this SingleLayerFeedForwardNeuralNetwork using
	 * the given Examples and return the average accuracy over the k trials.
	 */
	public double kFoldCrossValidate(List<Example> examples, int k, double alpha) {
		NeuralNetwork.Trainer trainer = new NeuralNetwork.Trainer() {
			public void train(NeuralNetwork network, List<Example> examples) {
				((SingleLayerFeedForwardNeuralNetwork)network).train(examples, alpha);
			}
		};
		NeuralNetwork.Tester tester = new NeuralNetwork.Tester() {
			public double test(NeuralNetwork network, List<Example> examples) {
				return ((SingleLayerFeedForwardNeuralNetwork)network).test(examples);
			}
		};
		return super.kFoldCrossValidate(examples, k, trainer, tester);
	}

}
