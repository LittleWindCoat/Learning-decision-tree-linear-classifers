package nn.core;

/**
 * Interface implemented by objects that want to be notified of changes to a NeuralNetwork during training.
 * This API is provisional, to be extended as needed.
 */
public interface NeuralNetworkListener {
	
	/**
	 * Called before each training epoch.
	 */
	public void trainingEpochStarted(NeuralNetwork network, int epoch);

	/**
	 * Called after each training epoch, return false to stop training.
	 */
	public boolean trainingEpochCompleted(NeuralNetwork network, int epoch);

}
