package application.wavefront;

import java.util.ArrayList;

public class Group3D {
	ArrayList<Object3D> objects = new ArrayList<>();

	public Group3D() {

	}

	public ArrayList<Object3D> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<Object3D> objects) {
		this.objects = objects;
	}

	public void addObject(Object3D object) {
		this.objects.add(object);
	}

	public void projectionX() {
		for (Object3D _object : objects) {
			for (Vertex _vertex : _object.getVertices()) {
				_vertex.setX(0);
			}
		}
	}

	public void projectionY() {
		for (Object3D _object : objects) {
			for (Vertex _vertex : _object.getVertices()) {
				_vertex.setY(0);
			}
		}
	}
	public void projectionZ() {
		for (Object3D _object : objects) {
			for (Vertex _vertex : _object.getVertices()) {
				_vertex.setZ(0);
			}
		}
	}
}
