package math.util;

public class VectorOps {
	
	/**
	 * Return dot-product of vectors x and y:
	 * x \cdot y = \sum_i x[i]*y[i]
	 */
	static public double dot(double[] x, double[] y) {
		double sum = 0.0;
		for (int i=0; i < x.length; i++) {
			sum += x[i] * y[i];
		}
		return sum;
	}
	
	/**
	 * For vector w of length n and vector x of length n-1,
	 * return w \cdot [1,x]. It's easier to do this in code
	 * than to constantly allocate and copy new vectors for
	 * the ``extended'' x vector.
	 */
	static public double dot1(double[] w, double[] x) {
		double sum = w[0];
		for (int i=1; i < w.length; i++) {
			sum += w[i] * x[i-1];
		}
		return sum;
	}

}
