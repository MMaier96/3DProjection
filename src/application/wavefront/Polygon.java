package application.wavefront;

import java.util.ArrayList;

public class Polygon {
	ArrayList<Integer> vertexIndices;
	ArrayList<Integer> textureIndices;
	ArrayList<Integer> normalsIndices;

	public Polygon(ArrayList<Integer> vertexIndices, ArrayList<Integer> textureIndices,
			ArrayList<Integer> normalsIndices) {
		this.vertexIndices = new ArrayList<>();
		this.textureIndices = new ArrayList<>();
		this.normalsIndices = new ArrayList<>();

		for (int i = 0; i < vertexIndices.size(); i++)
			this.vertexIndices.add(vertexIndices.get(i));

		for (int i = 0; i < textureIndices.size(); i++)
			this.textureIndices.add(textureIndices.get(i));

		for (int i = 0; i < normalsIndices.size(); i++)
			this.normalsIndices.add(textureIndices.get(i));
	}

	public ArrayList<Integer> getVertexIndices() {
		return vertexIndices;
	}

	public void setVertexIndices(ArrayList<Integer> vertexIndices) {
		this.vertexIndices = vertexIndices;
	}

	public ArrayList<Integer> getTextureIndices() {
		return textureIndices;
	}

	public void setTextureIndices(ArrayList<Integer> textureIndices) {
		this.textureIndices = textureIndices;
	}

	public ArrayList<Integer> getNormalsIndices() {
		return normalsIndices;
	}

	public void setNormalsIndices(ArrayList<Integer> normalsIndices) {
		this.normalsIndices = normalsIndices;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("f ");
		for (int i = 0; i < vertexIndices.size(); i++) {
			if (vertexIndices.size() > 0 && normalsIndices.size() > 0 && textureIndices.size() > 0) { //v, vt, vn
				builder.append(vertexIndices.get(i))
				.append("/")
				.append(textureIndices.get(i)==0?"":textureIndices.get(i))
				.append("/")
				.append(normalsIndices.get(i))
				.append(" ");
			} else if (vertexIndices.size() > 0 && normalsIndices.size() == 0 && textureIndices.size() > 0) { //v, vt
				builder.append(vertexIndices.get(i))
				.append("/")
				.append(textureIndices.get(i))
				.append(" ");
			} else if (vertexIndices.size() > 0 && normalsIndices.size() == 0 && textureIndices.size() == 0) { //v
				builder.append(vertexIndices.get(i))
				.append(" ");
			}
		}
		builder.append("\n");
		return builder.toString();
	}

}
