package nn.examples;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nn.core.Example;

/**
 * An IrisExampleGenerator generates Examples taken from the
 * UCI Iris database (which it reads from a file).
 * ``This is perhaps the best known database to be found in the pattern recognition literature.
 * Fisher's paper is a classic in the field and is referenced frequently to this day. (See Duda & Hart, for example.)
 * The data set contains 3 classes of 50 instances each, where each class refers to a type of iris plant.
 * One class is linearly separable from the other 2; the latter are NOT linearly separable from each other.''
 * Each example has four real-valued inputs and one of three class labels, which are mapped onto three Boolean outputs.
 * @see http://archive.ics.uci.edu/ml/datasets/Iris
 * @see IrisNN
 */
public class IrisExampleGenerator {
	
	Scanner scanner;
	
	public IrisExampleGenerator(String filename) throws IOException {
		scanner = new Scanner(new FileInputStream(filename));
		scanner.useDelimiter("[,\\n]");
	}
		
	public Example nextExample() throws IOException {
		double sepalLength = scanner.nextDouble();
		double sepalWidth = scanner.nextDouble();
		double petalLength = scanner.nextDouble();
		double petalWidth = scanner.nextDouble();
		String label = scanner.next();
		double[] inputs = { sepalLength, sepalWidth, petalLength, petalWidth };
		double[] outputs = { 0.0, 0.0, 0.0 };
		if (label.equals("Iris-setosa")) {
			outputs[0] = 1.0;
		} else if (label.equals("Iris-versicolor")) {
			outputs[1] = 1.0;
		} else if (label.equals("Iris-virginica")) {
			outputs[2] = 1.0;
		} else {
			throw new IOException("bad class label " + label);
		}
		return new Example(inputs, outputs);
	}
	
	public List<Example> examples(int len) throws IOException {
		List<Example> result = new ArrayList<Example>(len);
		for (int i=0; i < len; i++) {
			result.add(nextExample());
		}
		return result;
	}
	
	public List<Example> examples() throws IOException {
		return examples(150);
	}
}
