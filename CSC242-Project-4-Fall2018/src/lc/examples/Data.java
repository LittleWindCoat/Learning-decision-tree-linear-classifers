package lc.examples;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import lc.core.Example;

public class Data {
	
	/**
	 * AIMA earthquake datasets are space-separated triples: x1, x2,
	 * then classification (output) 0 or 1.
	 * Remember that we need to ensure that x_0=1.0 (AIMA p. 721).
	 */
	public static List<Example> readEarthquakeDataFromFile(String filename) throws IOException {
		List<Example> examples = new ArrayList<Example>();
		Scanner in = new Scanner(new File(filename));
		while (in.hasNext()) {
			Example example = new Example(3);
			example.inputs[0] = 1.0;				// x_0
			example.inputs[1] = in.nextDouble();	// x_1
			example.inputs[2] = in.nextDouble();	// x_2
			example.output = in.nextDouble();		// y
			examples.add(example);
		}
		in.close();
		return examples;
	}

	/**
	 * Read examples from a file: one example per line, values
	 * separated by commas, last value is output value. 
	 * Remember that we need to ensure that x_0=1.0 (AIMA p. 721).
	 */
	public static List<Example> readFromFile(String filename) throws IOException {
		//System.err.println("Data.readFromFile: " + filename);
		List<Example> examples = new ArrayList<Example>();
		FileReader reader = new FileReader(new File(filename));
		StreamTokenizer tokenizer = new StreamTokenizer(reader);
		tokenizer.eolIsSignificant(true);
		Vector<Double> values = new Vector<Double>();
		boolean done = false;
		while (!done) {
			int token = tokenizer.nextToken();
			//System.err.println("Data.readFromFile: token=" + tokenizer.toString());
			switch (token) {
			case StreamTokenizer.TT_EOF:
				done = true;
				break;
			case StreamTokenizer.TT_EOL:
				int nvalues = values.size();
				// Sanity check
				if (!examples.isEmpty() && nvalues != examples.get(0).inputs.length) {
					throw new IOException("example length mismatch: " + values.toString());
				}
				Example example = new Example(nvalues);
				example.inputs[0] = 1.0;
				for (int i=1; i < values.size(); i++) {
					example.inputs[i] = values.get(i-1);
				}
				example.output = values.lastElement();
				examples.add(example);
				values.clear();
				break;
			case StreamTokenizer.TT_NUMBER:
				values.add(tokenizer.nval);
				break;
			case StreamTokenizer.TT_WORD:
				if (!tokenizer.sval.equals(",")) {
					throw new IOException("bad word token: " + tokenizer.sval);
				}
				break;
			}
		}
		reader.close();
		//System.err.println("Data.readFromFile: returning " + examples.size() + " examples");
		return examples;
	}
	
}
