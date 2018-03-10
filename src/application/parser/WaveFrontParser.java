package application.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import application.wavefront.Group3D;
import application.wavefront.Object3D;
import application.wavefront.Polygon;
import application.wavefront.Vertex;
import application.wavefront.VertexNormal;
import application.wavefront.VertexTexture;

public class WaveFrontParser {

	private Path path;
	private ArrayList<String> lines;
	private Group3D group;

	public WaveFrontParser(File file) {
		path = Paths.get(file.getPath());
		lines = new ArrayList<>();
		read();
	}

	private void read() {
		try {
			lines.addAll(Files.readAllLines(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Group3D parse() {
		group = new Group3D(); // reset the object for every parse process

		for (int i = 0; i < lines.size(); i++) {
			parseLine(lines.get(i));
		}
		return group;
	}

	private void parseLine(String currentLine) {
		if (currentLine.length() == 0) {
			return;
		}

		trimCurrentLine(currentLine);
		
		switch (currentLine.charAt(0)) {
		case 'g': // new object in group
		case 'o': // new object in group
			parseNewObject(currentLine);
			break;

		case 'v': // new vertex(+texture) in object
			if (currentLine.charAt(1) == 't') {
				parseNewVertexTexture(currentLine);
				break;
			} else if (currentLine.charAt(1) == 'n') {
				parseNewVertexNormal(currentLine);
				break;
			}
			parseNewVertex(currentLine);
			break;

		case 'f': // new polygon in object
			parseNewPolygon(currentLine);
			break;

		default:
			// not necessary
			break;
		}
	}

	private void trimCurrentLine(String currentLine) {
		while (currentLine.charAt(0) == ' ') {
			currentLine = currentLine.substring(1);
		}
	}

	private void parseNewVertexNormal(String currentLine) {
		if (latestObject() == null) { // no object was created, use default name
			group.addObject(new Object3D("object"));
		}

		String[] arguments = currentLine.split(" ");

		if (!(arguments.length <= 5 && arguments.length >= 4)) {
			throw new RuntimeException("Error by parsing new vertex. The length was not 4 or 5! " + currentLine);
		}

		float x = 0;
		float y = 0;
		float z = 0;

		try {
			x = Float.parseFloat(arguments[1]);
			y = Float.parseFloat(arguments[2]);
			z = Float.parseFloat(arguments[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		latestObject().addVertexNormal(new VertexNormal(x, y, z));
	}

	private void parseNewObject(String currentLine) {
		if (currentLine.split(" ").length != 2) {
			throw new RuntimeException("Error by parsing new object. The length was not 2! " + currentLine);
		}
		String name = currentLine.split(" ")[1];
		group.addObject(new Object3D(name));
	}

	private void parseNewVertex(String currentLine) {
		if (latestObject() == null) { // no object was created, use default name
			group.addObject(new Object3D("object"));
		}

		String[] arguments = currentLine.split(" ");

		if (!(arguments.length <= 5 && arguments.length >= 4)) {
			throw new RuntimeException("Error by parsing new vertex. The length was not 4 or 5! " + currentLine);
		}
		float x = 0;
		float y = 0;
		float z = 0;
		try {
			x = Float.parseFloat(arguments[1]);
			y = Float.parseFloat(arguments[2]);
			z = Float.parseFloat(arguments[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		latestObject().addVertex(new Vertex(x, y, z));
	}

	private void parseNewVertexTexture(String currentLine) {
		if (latestObject() == null) { // no object was created, use default name
			group.addObject(new Object3D("object"));
		}
		
		String[] arguments = currentLine.split(" ");

		if (!(arguments.length <= 4 && arguments.length >= 3)) {
			throw new RuntimeException("Error by parsing new vertex. The length was not 3 or 4! " + currentLine);
		}

		float u = 0;
		float v = 0;
		try {
			u = Float.parseFloat(arguments[1]);
			v = Float.parseFloat(arguments[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		latestObject().addVertexTexture(new VertexTexture(u, v));
	}

	private void parseNewPolygon(String currentLine) {
		if (latestObject() == null) { // no object was created, use default name
			group.addObject(new Object3D("object"));
		}
		
		String[] arguments = currentLine.split(" ");

		ArrayList<Integer> vertexIndices = new ArrayList<>();
		ArrayList<Integer> normalsIndices = new ArrayList<>();
		ArrayList<Integer> textureIndices = new ArrayList<>();

		for (String _argument : arguments) {
			if (_argument.equals("f")) {
				continue;
			}
			
			String[] split = _argument.split("/");
			
			int vertex = 0;
			int texture = 0;
			int normal = 0;			
			
			if (split.length == 1) { //only v
				try {
					if (split[0].length() > 0) {
						vertex = Integer.parseInt(split[0]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				vertexIndices.add(vertex);
			}else if (split.length == 2) { // v, vt
				try {
					if (split[0].length() > 0) {
						vertex = Integer.parseInt(split[0]);
					}
					if (split[1].length() > 0) {
						texture = Integer.parseInt(split[1]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				vertexIndices.add(vertex);
				textureIndices.add(texture);
			}else if (split.length == 3) { // v, vn, vt
				try {
					if (split[0].length() > 0) {
						vertex = Integer.parseInt(split[0]);
					}
					if (split[1].length() > 0) {
						texture = Integer.parseInt(split[1]);
					}
					if (split[2].length() > 0) {
						normal = Integer.parseInt(split[2]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				vertexIndices.add(vertex);
				textureIndices.add(texture);
				normalsIndices.add(normal);
			}
		}

		latestObject().addPolygon(new Polygon(vertexIndices, textureIndices, normalsIndices));
	}

	private Object3D latestObject() {
		if (group.getObjects().size() == 0) {
			return null;
		}
		return group.getObjects().get(group.getObjects().size() - 1);
	}
}
