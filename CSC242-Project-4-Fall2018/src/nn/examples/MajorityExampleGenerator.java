package nn.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nn.core.Example;

/**
 * A MajorityExampleGenerator generates Examples consisting
 * of some number of inputs bits, each randomly set to 0 or 1,
 * and the output value set to 1 iff the majority of the input
 * bits are 1. A set of these Examples is suitable for training
 * a perceptron network on the majority function (see AIMA p 731).
 * @see MajorityPerceptronNN
 */
public class MajorityExampleGenerator {
	
	protected int ninputs;
	
	public MajorityExampleGenerator(int ninputs) {
		this.ninputs = ninputs;
	}
	
	protected Random random = new Random();
	
	public Example nextExample() {
		Example example = new Example(ninputs, 1);
		int count = 0;
		for (int i=0; i < ninputs; i++) {
			if (random.nextBoolean()) {
				example.inputs[i] = 1.0;
				count += 1;
			} else {
				example.inputs[i] = 0;
			}
		}
		if (count > ninputs/2) {
			example.outputs[0] = 1.0;
		} else {
			example.outputs[0] = 0;
		}
		return example;
	}
	
	public List<Example> examples(int len) {
		List<Example> result = new ArrayList<Example>(len);
		for (int i=0; i < len; i++) {
			result.add(nextExample());
		}
		return result;
	}
}
