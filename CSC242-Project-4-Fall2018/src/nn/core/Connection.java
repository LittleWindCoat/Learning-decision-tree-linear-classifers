package nn.core;

/**
 * A directed, weighted connection between two Units.
 */
public class Connection {
	
	public Unit src;
	public Unit dst;
	public double weight;
	
	public Connection(Unit src, Unit dst, double weight) {
		this.src = src;
		this.dst = dst;
		this.weight = weight;
		src.outgoingConnections.add(this);
		dst.incomingConnections.add(this);
	}

	public Connection(Unit src, Unit dst) {
		this(src, dst, 0.0);
	}
	
}
