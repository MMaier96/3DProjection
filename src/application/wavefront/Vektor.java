package application.wavefront;

public class Vektor {
	
	private long x;
	private long y;
	private long z;
	
	public Vektor(long x,long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "v " + x + " " + y + " " + z;
	}
}
