package application.wavefront;

public class VertexTexture {

	private float u;
	private float v;

	public VertexTexture(float u, float v) {
		this.u = u;
		this.v = v;
	}
	

	public float getU() {
		return u;
	}

	public void setU(float u) {
		this.u = u;
	}

	public float getV() {
		return v;
	}

	public void setV(float v) {
		this.v = v;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append("vt ")
				.append(u)
				.append(" ")
				.append(v)
				.append("\n");
		return builder.toString();
	}
}
