package nn.core;

import java.util.ArrayList;
import java.util.List;

import nn.core.Example;

/**
 * Base class for NeuralNetworks made up of Units.
 */
abstract public class NeuralNetwork {
	
	abstract public InputUnit[] getInputUnits();
	abstract public NeuronUnit[] getOutputUnits();
	
	//
	// Listener
	//
	
	ArrayList<NeuralNetworkListener> listeners = new ArrayList<NeuralNetworkListener>();
	
	public void addListener(NeuralNetworkListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(NeuralNetworkListener listener) {
		listeners.remove(listener);
	}
	
	protected void notifyTrainingEpochStarted(int epoch) {
		for (NeuralNetworkListener listener : listeners) {
			listener.trainingEpochStarted(this, epoch);
		}
	}
	
	protected void notifyTrainingEpochCompleted(int epoch) {
		for (NeuralNetworkListener listener : listeners) {
			listener.trainingEpochCompleted(this, epoch);
		}
	}
	
	
	//
	// Generic testing methods, including k-fold cross-validation
	//
	
	/**
	 * Return true if this NeuralNetwork gets the right answer on the given Example.
	 * ``Getting the right answer'' depends on how the problem is represented in the
	 * network, so this method must be implemented by subclasses.
	 */
	abstract public boolean test(Example example);
	
	/**
	 * Return the accuracy (proportion correct) for this NeuralNetwork
	 * on the given list of Examples.
	 */
	public double test(List<Example> examples) {
		int ncorrect = 0;
		for (Example example : examples) {
			if (test(example)) {
				ncorrect += 1;
			}
		}
		return ncorrect/(double)examples.size();
	}
	
	/**
	 * Run a k-fold cross-validation experiment on this NeuralNetwork using
	 * the given Examples and return the average accuracy over the k trials.
	 */
	public double kFoldCrossValidate(List<Example> examples, int k, Trainer trainer, Tester tester) {
		int n = examples.size();		// Number of examples total
		int nk = n / k;				// Number of examples per subset (truncated to int)
		double accsum = 0.0;			// Running total accuracy
		// For each subset i...
		System.out.println("SET\tACCURACY");
		for (int i = 0; i < k; i++) {
			List<Example> training = new ArrayList<Example>(n-nk);
			List<Example> testing = new ArrayList<Example>(nk);
			// Divide examples into training and testing, with ith subset being testing
			int testingStart = (int)(i * ((double)n/k));
			for (int j=0; j < n; j++) {
				if (j >= testingStart && j < testingStart+nk) {
					testing.add(examples.get(j));
				} else {
					training.add(examples.get(j));
				}
			}
			// Train on training data
			trainer.train(this, training);
			// Test on testing data
			double acc = tester.test(this, testing);
			System.out.format("%d\t%.3f\n", i, acc);
			// Running total for average
			accsum += acc;
		}
		// Return average accuracy over the k trials
		return accsum / k;
	}
	
	/**
	 * Interface implemented by objects that can train a NeuralNetwork.
	 * This will typically involve a closure that specifies additional
	 * arguments for the training, such as the learning rate alpha.
	 */
	public interface Trainer {
		/**
		 * Train the given NeuralNetwork on the given List of Examples.
		 */
		public void train(NeuralNetwork network, List<Example> examples);
	}
	
	/**
	 * Interface implemented by objects that can test a NeuralNetwork.
	 */
	public interface Tester {
		/**
		 * Return the accuracy of the given NeuralNetwork on the give List of Examples.
		 */
		public double test(NeuralNetwork network, List<Example> examples);
	}
	
}
