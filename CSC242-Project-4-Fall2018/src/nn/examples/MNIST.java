package nn.examples;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nn.core.Example;

/**
 * Classes to deal with the MNIST database of handwritten digits.
 * @see http://yann.lecun.com/exdb/mnist/
 */
public class MNIST {

	static class InputStream {

		FileInputStream fis;
		int count;

		protected InputStream(String filename, int magic) throws IOException {
			this.fis = new FileInputStream(filename);
			int n = readInt32();
			if (n != magic) {
				throw new IOException("bad magic number: " + n);
			}
			this.count = readInt32();
		}

		// Java bytes are signed
		protected int readByte() throws IOException {
			int n = fis.read();
			if (n == -1) {
				throw new EOFException();
			}
			return n;
		}

		protected int readInt32() throws IOException {
			long n = 0; // ``unsigned''
			for (int i=0; i < 4; i++) {
				n = n*256 + readByte();
			}
			return (int)n;
		}
	}

	static class ImageInputStream extends InputStream {
		private static final int MAGIC = 2051;
		protected int nrows;
		protected int ncols;
		public ImageInputStream(String filename) throws IOException {
			super(filename, MAGIC);
			this.nrows = readInt32();
			this.ncols = readInt32();
		}

		public int[] nextImage() throws IOException {
			int[] data = new int[nrows*ncols]; // Java bytes are signed
			// could just do fis.read(data) ...
			for (int i=0; i < nrows * ncols; i++) {
				data[i] = readByte();
			}
			return data;
		}

	}

	static class LabelInputStream extends InputStream {
		private static final int MAGIC = 2049;
		public LabelInputStream(String filename) throws IOException {
			super(filename, MAGIC);
		}
		public int nextLabel() throws IOException {
			return readByte();
		}
	}

	/**
	 * Read and return a List of Examples from the given pair of MNIST files.
	 * There are 28*28=784 input values (each 0 or 1), one for each pixel.
	 * There are 10 output values, exactly one being 1 and the rest 0, for the label.
	 */
	static public List<Example> read(String imageFilename, String labelFilename) throws IOException {
		ImageInputStream istream = new ImageInputStream(imageFilename);
		LabelInputStream lstream = new LabelInputStream(labelFilename);
		if (istream.count != lstream.count) {
			throw new IOException("unequal counts: " + istream.count + " " + lstream.count);
		}
		int n = istream.count;
		List<Example> examples = new ArrayList<Example>(n);
		for (int i=0; i < n; i++) {
			int[] imageData = istream.nextImage();
			int label = lstream.nextLabel();
			double[] inputs = new double[imageData.length];
			for (int j=0; j < imageData.length; j++) {
				inputs[j] = (double)imageData[j];
			}
			double[] outputs = new double[10];
			outputs[label] = 1.0;
			examples.add(new Example(inputs, outputs));
		}
		return examples;
	}

	public static void main(String[] args) throws IOException {
		List<Example> examples = MNIST.read("src/nn/example/t10k-images-idx3-ubyte", "src/nn/example/t10k-labels-idx1-ubyte");
		System.out.println(examples.size() + " examples");
		System.out.println(examples.get(0));
	}

}
