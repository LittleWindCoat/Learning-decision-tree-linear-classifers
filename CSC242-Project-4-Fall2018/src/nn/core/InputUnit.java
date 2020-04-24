package nn.core;

/**
 * An InputUnit is a Unit whose output value can be changed
 * after it is constructed, but which doesn't change during
 * training.
 */
public class InputUnit extends Unit {
	
	protected double output;
	
	public void setOutput(double value) {
		this.output = value;
	}
	
	/**
	 * Return the output value of this Unit.
	 */
	@Override
	public double getOutput() {
		return output;
	}
	
	/**
	 * An InputUnit's output value does not depend on the values
	 * of its inputs, so this is a no-op.
	 * That's a great sentence...
	 */
	@Override
	public void run() {
		// Nothing to do
	}

	/**
	 * An InputUnit has no weights to update, so this is a no-op.
	 */
	@Override
	public void update(double[] inputs, double output, double alpha) {
		// Nothing to do
	}
}
