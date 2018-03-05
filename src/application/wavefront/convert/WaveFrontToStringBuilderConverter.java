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
				result.append("v ")
				.append(_vertex.getX() + " ")
				.append(_vertex.getY() + " ")
				.append(_vertex.getZ() + " ")
				.append("\n");
			}

			result.append("\n");
			for (VertexTexture _vertexTexture : _object.getVerticesTextures()) {
				result.append("vt ")
				.append(_vertexTexture.getU() + " ")
				.append(_vertexTexture.getV() + " ")
				.append("\n");
			}
			result.append("\n");
			for (Polygon _polygon : _object.getPolygons()) {
				if (_polygon.getTextureIndices().size() > 0) {
					result.append("f ");
					for (int i = 0; i < _polygon.getVertexIndices().size(); i++) {
						result.append(_polygon.getVertexIndices().get(i) + "/")
						.append(_polygon.getTextureIndices().get(i) + " ");
					}
					result.append("\n");
				} else {
					result.append("f ");
					for (int i = 0; i < _polygon.getVertexIndices().size(); i++) {
						result.append(_polygon.getVertexIndices().get(i) + " ");
					}
					result.append("\n");
				}
			}
		}
	}

	@Override
	public StringBuilder getResult() {
		return result;
	}

}
