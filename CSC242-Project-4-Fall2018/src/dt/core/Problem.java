package dt.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import dt.util.ArraySet;

/**
 * A Problem (specification) is a list of input Variables
 * with their Domains and a single output Variable with its
 * Domain.
 * The inputs are stored as a list rather than a set because
 * the values are usually listed in that order in datasets. 
 */
abstract public class Problem {
	
	protected List<Variable> inputs;
	protected Variable output;
	
	public Problem(List<Variable> inputs, Variable output) {
		this.inputs = inputs;
		this.output = output;
	}
	
	public Problem() {
		this(new ArrayList<Variable>(), null);
	}
	
	public List<Variable> getInputs() {
		return inputs;
	}
	
	public Variable getOutput() {
		return output;
	}
	
	public void dump() {
		System.out.println(inputs + " -> " + output);
	}
	
	/**
	 * Read a Set of Examples from the given File and return it.
	 * This function assumes the data is one line per example,
	 * comma-separated values, one value for each input variable
	 * in order, followed by a value for the output variable.
	 * This format is common in UCI datasets.
	 * It is easy to do override this method and do something
	 * else if necessary for some problems.
	 */
	public Set<Example> readExamplesFromCSVFile(File file) throws IOException {
		Set<Example> examples = new ArraySet<Example>();
		Scanner in = new Scanner(file);
		in.useDelimiter("[,\n]");
		while (in.hasNext()) {
			Example example = new Example();
			for (int i=0; i<this.inputs.size(); i++) {
				String token = in.next();
				example.setInputValue(this.inputs.get(i), token);
			}
			String token = in.next();
			example.setOutputValue(token);
			examples.add(example);
		}
		in.close();
		return examples;
	}
}
