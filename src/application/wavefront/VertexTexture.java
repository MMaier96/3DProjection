package application.wavefront;

public class VertexTexture {

	private float u;
	private float v;
	private float w; // is optional, default: 0.0f

	public VertexTexture(float u, float v) {
		this.u = u;
		this.v = v;
		this.w = 0.0f;
	}
	
	public VertexTexture(float u, float v, float w) {
		this.u = u;
		this.v = v;
		this.w = w;
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

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append("vt ")
				.append(u)
				.append(" ")
				.append(v)
				.append(" ")
				.append(w)
				.append("\n");
				
		return builder.toString();
	}
}
