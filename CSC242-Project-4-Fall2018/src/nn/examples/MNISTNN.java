package nn.examples;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import nn.core.Example;
import nn.core.MultiLayerFeedForwardNeuralNetwork;
import nn.core.NeuralNetwork;
import nn.core.NeuralNetworkListener;

/**
 * Implements a fully-connected NeuralNetwork for recognizing MNIST digits.
 * The network has 28*28 input units (one per pixel), 300 hidden units (or whatever),
 * and 10 output units, one for each digit (label).
 * On my MBP Mar 2018, this took about 9 minutes for one round (epoch) of training, which yielded
 * an accuracy of 41.77%.
 */
public class MNISTNN extends MultiLayerFeedForwardNeuralNetwork {
	
	protected static final int NUM_INPUTS = 28*28;
	protected static final int NUM_HIDDENS = 300;
	protected static final int NUM_OUTPUTS = 10;
	
	public MNISTNN() {
		// Single hidden layer of this size
		super(NUM_INPUTS, NUM_HIDDENS, NUM_OUTPUTS);
	}
	
	public static void main(String[] argv) throws IOException {
		int epochs = 100;
		double alpha = 0.10;
		MNISTNN network = new MNISTNN();
		System.out.println("MNIST: reading training data...");
		List<Example> trainingSet = MNIST.read("src/nn/example/train-images-idx3-ubyte", "src/nn/example/train-labels-idx1-ubyte");
		System.out.println("MNIST: reading testing data...");
		List<Example> testingSet = MNIST.read("src/nn/example/t10k-images-idx3-ubyte", "src/nn/example/t10k-labels-idx1-ubyte");
		System.out.println("MNIST: training on " + trainingSet.size() + " examples for " + epochs + " epochs, alpha=" + alpha);
		System.out.println("MNIST: testing on " + testingSet.size() + " examples");
		System.out.println("EPOCH\tACCURACY\tTIMEms");;
		network.addListener(new NeuralNetworkListener() {
			public void trainingEpochStarted(NeuralNetwork network, int epoch) {
				if (epoch == 0) {
					System.out.format("0\t--\t%d\t\n", new Date().getTime());
				}
			}
			public boolean trainingEpochCompleted(NeuralNetwork network, int epoch) {
				//network.dump();
				double accuracy = network.test(testingSet);
				System.out.format("%d\t%.3f\t%d\n", epoch, accuracy, new Date().getTime());
				return true;
			}
		});
		network.train(trainingSet, epochs, alpha);
	}
	
}
