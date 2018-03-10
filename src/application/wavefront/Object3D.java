package application.wavefront;

import java.util.ArrayList;

public class Object3D {

	private String name;
	private ArrayList<Vertex> vertices;
	private ArrayList<VertexNormal> verticesNormals;
	private ArrayList<VertexTexture> verticesTextures;
	private ArrayList<Polygon> polygons;

	public Object3D(String name) {
		this.name = name;
		vertices = new ArrayList<>();
		verticesNormals = new ArrayList<>();
		verticesTextures = new ArrayList<>();
		polygons = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}
	
	public void addVertex(Vertex vertex) {
		this.vertices.add(vertex);
	}

	public ArrayList<VertexTexture> getVerticesTextures() {
		return verticesTextures;
	}

	public void setVerticesTextures(ArrayList<VertexTexture> verticesTextures) {
		this.verticesTextures = verticesTextures;
	}
	
	public void addVertexTexture(VertexTexture verticesTexture) {
		this.verticesTextures.add(verticesTexture);
	}

	public ArrayList<Polygon> getPolygons() {
		return polygons;
	}

	public void setPolygons(ArrayList<Polygon> polygons) {
		this.polygons = polygons;
	}
	
	public void addPolygon(Polygon polygon) {
		this.polygons.add(polygon);
	}
	
	public ArrayList<VertexNormal> getVerticesNormals() {
		return verticesNormals;
	}

	public void setVerticesNormals(ArrayList<VertexNormal> verticesNormals) {
		this.verticesNormals = verticesNormals;
	}

	public void addVertexNormal(VertexNormal vertexNormal) {
		verticesNormals.add(vertexNormal);
	}
}
