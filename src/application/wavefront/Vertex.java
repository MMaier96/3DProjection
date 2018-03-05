package application.wavefront;

public class Vertex {

	private float x;
	private float y;
	private float z;
	private float w; //optional, default: 1.0f

	public Vertex(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1.0f;
	}
	
	public Vertex(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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
	
	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
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
				.append(" ")
				.append(w)
				.append("\n");
				
		return builder.toString();
	}
}
