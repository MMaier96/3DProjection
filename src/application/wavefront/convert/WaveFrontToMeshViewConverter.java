package application.wavefront.convert;

import java.util.ArrayList;

import application.wavefront.Group3D;
import application.wavefront.Object3D;
import application.wavefront.Polygon;
import javafx.scene.shape.TriangleMesh;

public class WaveFrontToMeshViewConverter extends WaveFrontConverter {

	private TriangleMesh result;

	public WaveFrontToMeshViewConverter(Group3D group) {
		super(group);
	}
	
	@Override
	public void convert() {
		TriangleMesh triangleMesh = new TriangleMesh();
		/*
		 * BUGFIX: 
		 * JavaFx's first vertex index is 0. 
		 * The most programs start with vertex 1.
		 * To fix that an redundant vertex 0 -> 0.0 0.0 0.0 is needed
		*/
		triangleMesh.getPoints().addAll(0.0f, 0.0f, 0.0f);
		triangleMesh.getTexCoords().addAll(0.0f, 0.0f);
		
		
		float[] vertices;
		float[] verticesTextures;
		int[] polygons;

		ArrayList<Integer> polygonsList = new ArrayList<>();
 		
		ArrayList<Object3D> objects = group.getObjects();
		for (Object3D _object : objects) {
			vertices = new float[_object.getVertices().size()*3];
			for (int i = 0; i < vertices.length; i+=3) {
				vertices[i+0] = _object.getVertices().get(i/3).getX();
				vertices[i+1] = _object.getVertices().get(i/3).getY();
				vertices[i+2] = _object.getVertices().get(i/3).getZ();
			}
			
			verticesTextures = new float[_object.getVerticesTextures().size()*2];
			for (int i = 0; i < verticesTextures.length; i+=2) {
				verticesTextures[i+0] = _object.getVerticesTextures().get(i/2).getU();
				verticesTextures[i+1] = _object.getVerticesTextures().get(i/2).getV();
			}
			
			for (Polygon _polygon : _object.getPolygons()) {
				if(_polygon.getTextureIndices().size() > 0) {
					for (int i = 0; i < _polygon.getVertexIndices().size(); i++) {
						polygonsList.add(_polygon.getVertexIndices().get(i));
						polygonsList.add(_polygon.getTextureIndices().get(i));
					}
				}else {
					for (int i = 0; i < _polygon.getVertexIndices().size(); i++) {
						polygonsList.add(_polygon.getVertexIndices().get(i));
					}
				}
			}
			polygons = new int[polygonsList.size()];
			for (int i = 0; i < polygons.length; i++) {
				polygons[i] = polygonsList.get(i);
			}
			
			
			triangleMesh.getPoints().addAll(vertices);
			triangleMesh.getTexCoords().addAll(verticesTextures);
			triangleMesh.getFaces().addAll(polygons);
			System.out.println(triangleMesh.getPointElementSize());
		}
		
		result = triangleMesh;

	}

	@Override
	public TriangleMesh getResult() {
		return result;
	}
	
	

}
