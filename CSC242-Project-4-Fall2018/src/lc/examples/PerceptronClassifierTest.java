package lc.examples;

import java.io.IOException;
import java.util.List;

import lc.core.Example;
import lc.core.LearningRateSchedule;
import lc.core.PerceptronClassifier;

public class PerceptronClassifierTest {

	/**
	 * Train a PerceptronClassifier on a file of examples and
	 * print its accuracy after each training step.
	 */
	public static void main(String[] argv) throws IOException {
		if (argv.length < 3) {
			System.out.println("usage: java PerceptronClassifierTest data-filename nsteps alpha");
			System.out.println("       specify alpha=0 to use decaying learning rate schedule (AIMA p725)");
			System.exit(-1);
		}
		String filename = argv[0];
		int nsteps = Integer.parseInt(argv[1]);
		double alpha = Double.parseDouble(argv[2]);
		System.out.println("filename: " + filename);
		System.out.println("nsteps: " + nsteps);
		System.out.println("alpha: " + alpha);
		
		List<Example> examples = Data.readFromFile(filename);
		int ninputs = examples.get(0).inputs.length; 
		PerceptronClassifier classifier = new PerceptronClassifier(ninputs) {
			public void trainingReport(List<Example> examples, int stepnum, int nsteps) {
				double accuracy = accuracy(examples);
				System.out.println(stepnum + "\t" + accuracy);
			}
		};
		if (alpha > 0) {
			classifier.train(examples, nsteps, alpha);
		} else {
			classifier.train(examples, 100000, new LearningRateSchedule() {
				public double alpha(int t) { return 1000.0/(1000.0+t); }
			});
		}
	}

}
