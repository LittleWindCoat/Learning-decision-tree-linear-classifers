package nn.examples;

import java.io.IOException;
import java.util.List;

import nn.core.Connection;
import nn.core.Example;
import nn.core.InputUnit;
import nn.core.LogisticUnit;
import nn.core.MultiLayerFeedForwardNeuralNetwork;
import nn.core.Unit;

/**
 * Implements a fully-connected NeuralNetwork with four inputs, seven hidden units, and 3 output units, for use
 * with the UCI Iris dataset (see IrisExampleGenerator).
 * <p>
 * @see https://visualstudiomagazine.com/Articles/2014/09/01/Azure-Machine-Learning-Studio.aspx
 * With 100 epochs and alpha=0.10, the article shows a confusion matrix and says the following:
 * ``The model correctly predicted all instances of species setosa [class 0] and virginica [class 2]
 * (the 100 percent values). For the versicolor [class 1] species in the training data, 87.5 percent
 * were correctly predicted, but 12.5 percent were incorrectly classified as virginica, although none
 * were incorrectly classified as setosa.''
 * <p>
 * My results (in one run anyway):
 *   Classes 0 and 2: 100%, class 1 62% with 38% incorrectly classified as class 2 (virginica).
 */
public class IrisNN extends MultiLayerFeedForwardNeuralNetwork {
	
	protected static final int NUM_LAYERS = 3;
	protected static final int INPUT = 0;
	protected static final int HIDDEN = 1;
	protected static final int OUTPUT = 2;
	
	protected static final int NUM_INPUTS = 4;
	protected static final int NUM_HIDDENS = 7;
	protected static final int NUM_OUTPUTS = 3;
	
	/**
	 * Construct and return a new 4-3-3 neural network for the Iris classification problem.
	 */
	// Note: I could have used the superclass shortcut constructor, but I left this code as is
	// as an example of how to do it manually.
	public IrisNN() {
		super(new Unit[NUM_LAYERS][]);
		// Input units
		this.layers[INPUT] = new InputUnit[NUM_INPUTS];
		for (int i=0; i < NUM_INPUTS; i++) {
			this.layers[INPUT][i] = new InputUnit();
		}
		// Hidden units: each connected to all input units
		this.layers[HIDDEN] = new LogisticUnit[NUM_HIDDENS];
		for (int j=0; j < NUM_HIDDENS; j++) {
			this.layers[HIDDEN][j] = new LogisticUnit();
			for (int i=0; i < NUM_INPUTS; i++) {
				new Connection(this.layers[INPUT][i], this.layers[HIDDEN][j]);
			}
		}
		// Output units: each connected all hidden units
		this.layers[OUTPUT] = new LogisticUnit[NUM_OUTPUTS];
		for (int j=0; j < NUM_OUTPUTS; j++) {
			this.layers[OUTPUT][j] = new LogisticUnit();
			for (int i=0; i < NUM_HIDDENS; i++) {
				new Connection(this.layers[HIDDEN][i], this.layers[OUTPUT][j]);
			}
		}
	}
	
	/**
	 * Output of this NN is the index of output unit (i.e., class label) with the maximum activation.
	 */
	public int getOutputValue() {
		double max = Double.NEGATIVE_INFINITY;
		int answer = -1;
		for (int i=0; i < this.getOutputUnits().length; i++) {
			Unit unit = this.getOutputUnits()[i];
			if (unit.getOutput() > max) {
				answer = i;
				max = unit.getOutput();
			}
		}
		return answer;
	}
	
	/**
	 * Return a confusion matrix with the accuracy of each class of Iris.
	 * First dimension: actual class in example
	 * Second dimension: class predicted by network for example
	 */
	public double[][] confusionMatrix(List<Example> examples) {
		int nclasses = this.getOutputUnits().length;
		int[] n = new int[nclasses];					// Total count of examples of each class
		int[][] count = new int[nclasses][nclasses];	// Predicted class x actual class counts 
		for (Example example : examples) {
			propagate(example);
			int predicted = getOutputValue();
			int actual = -1;
			for (int i=0; i < example.outputs.length; i++) {
				if (example.outputs[i] == 1.0) {
					actual = i;
					break;
				}
			}
			n[actual] += 1;
			count[actual][predicted] += 1;
		}
		double[][] result = new double[nclasses][nclasses];
		for (int i=0; i < 3; i++) {
			for (int j=0; j < 3; j++) {
				result[i][j] = (double)count[i][j] / n[i];
			}
		}
		return result;
	}

	public static void main(String[] argv) throws IOException {
		int epochs = 1000;
		double alpha = 0.10;
		if (argv.length > 0) {
			epochs = Integer.parseInt(argv[0]);
		}
		if (argv.length > 1) {
			alpha = Double.parseDouble(argv[1]);
		}
		List<Example> examples = new IrisExampleGenerator("src/nn/examples/iris.data.txt").examples();
		IrisNN network = new IrisNN();
		System.out.println("Training for " + epochs + " epochs with alpha=" + alpha);
		network.train(examples, epochs, alpha);
		network.dump();
		double accuracy = network.test(examples);
		System.out.println("Overall accuracy=" + accuracy);
		System.out.println();
		System.out.println("Confusion matrix:");
		double[][] matrix = network.confusionMatrix(examples);
		System.out.println("\tPredicted");
		System.out.print("Actual");
		for (int i=0; i < matrix.length; i++) {
			System.out.format("\t%d", i);
		}
		System.out.println();
		for (int i=0; i < matrix.length; i++) {
			System.out.format("%d", i);
			for (int j=0; j < matrix[i].length; j++) {
				System.out.format("\t%.3f", matrix[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		int n = examples.size();
		int k = 10;
		System.out.println("k-Fold Cross-Validation: n=" + n + ", k=" + k);
		double acc = network.kFoldCrossValidate(examples, k, epochs, alpha);
		System.out.format("average accuracy: %.3f\n", acc);
		System.out.println();
		System.out.println("Learning Curve testing on all training data");
		System.out.println("EPOCHS\tACCURACY");
		for (epochs=100; epochs <= 3000; epochs+=100) {
			network.train(examples, epochs, alpha);
			accuracy = network.test(examples);
			System.out.format("%d\t%.3f\n",  epochs, accuracy);
		}
	}
	
}
