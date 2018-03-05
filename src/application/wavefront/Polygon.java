package application.wavefront;

import java.util.ArrayList;

public class Polygon {
	ArrayList<Integer> vertexIndices;
	ArrayList<Integer> textureIndices;
	
	public Polygon(int[] vertexIndices, int[] textureIndices) {
		this.vertexIndices = new ArrayList<>();
		this.textureIndices = new ArrayList<>();
		
		for (int i = 0; i < vertexIndices.length; i++) {
			this.vertexIndices.add(vertexIndices[i]);
		}
		for (int i = 0; i < textureIndices.length; i++) {
			this.textureIndices.add(textureIndices[i]);
		}
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
	
	
	
}
