package nn.core;

/**
 * A ConstantUnit's output is always a fixed value.
 */
public class ConstantUnit extends Unit {
	
	protected double value;
	
	public ConstantUnit(double value) {
		this.value = value;
	}
	
	/**
	 * Return the output value of this Unit.
	 */
	@Override
	public double getOutput() {
		return value;
	}
	
	/**
	 * A ContantUnit's output value does not depend on the values
	 * of its inputs, so this is a no-op.
	 */
	@Override
	public void run() {
		// Nothing to do
	}
	
	/**
	 * A ConstantUnit has no weights to update, so this is a no-op.
	 */
	@Override
	public void update(double[] inputs, double output, double alpha) {
		// Nothing to do
	}

}
