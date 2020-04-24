package lc.core;

public interface LearningRateSchedule {
	
	/**
	 * Return the learning rate alpha for the given iteration t.
	 * (AIMA p. 725).
	 */
	public double alpha(int t);

}
