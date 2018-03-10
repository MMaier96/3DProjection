package application.wavefront.convert;

import application.wavefront.Group3D;
import application.wavefront.Object3D;
import application.wavefront.Polygon;
import application.wavefront.Vertex;
import application.wavefront.VertexTexture;

public class WaveFrontToStringBuilderConverter extends WaveFrontConverter {

	private StringBuilder result = new StringBuilder();

	public WaveFrontToStringBuilderConverter(Group3D group) {
		super(group);
	}

	@Override
	public void convert() {

		for (Object3D _object : group.getObjects()) {
			result.append("g ").append(_object.getName()).append("\n");
			for (Vertex _vertex : _object.getVertices()) {
				result.append(_vertex.toString());
			}

			result.append("\n");
			for (VertexTexture _vertexTexture : _object.getVerticesTextures()) {
				result.append(_vertexTexture.toString());
			}
			result.append("\n");
			for (Polygon _polygon : _object.getPolygons()) {
				result.append(_polygon.toString());
			}
			result.append("\n");
		}
	}

	@Override
	public StringBuilder getResult() {
		return result;
	}

}
