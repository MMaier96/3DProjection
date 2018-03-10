package application.wavefront;

public class Vertex {

	private float x;
	private float y;
	private float z;

	public Vertex(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append("v ")
				.append(x)
				.append(" ")
				.append(y)
				.append(" ")
				.append(z)
				.append("\n");
		return builder.toString();
	}
}
