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
		
		switch (currentLine.charAt(0)) {
		case 'g': // new object in group
		case 'o': // new object in group
			parseNewObject(currentLine);
			break;

		case 'v': // new vertex(+texture) in object
			if (currentLine.charAt(1) == 't' ) {
				parseNewVertexTexture(currentLine);
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

	private void parseNewObject(String currentLine) {
		if (currentLine.split(" ").length != 2) {
			throw new RuntimeException("Error by parsing new object. The length was not 2! ");
		}
		String name = currentLine.split(" ")[1];
		group.addObject(new Object3D(name));
	}

	private void parseNewVertex(String currentLine) {
		String[] arguments = currentLine.split(" ");
		
		if (!(arguments.length <= 5 && arguments.length >= 4)) {
			throw new RuntimeException("Error by parsing new vertex. The length was not 4 or 5! ");
		}
		float x ;
		float y ;
		float z ;
		try {
		x = Float.parseFloat(arguments[1]);
		y = Float.parseFloat(arguments[2]);
		z = Float.parseFloat(arguments[3]);
		}catch (Exception e) {
			System.out.println("");
			x = Float.parseFloat(arguments[1]);
			y = Float.parseFloat(arguments[2]);
			z = Float.parseFloat(arguments[3]);
		}
		
		if (arguments.length == 4) {
			latestObject().addVertex(new Vertex(x, y, z));
		} else if (arguments.length == 5) {
			float w = Float.parseFloat(arguments[4]);
			latestObject().addVertex(new Vertex(x, y, z, w));
		}
	}
	
	private void parseNewVertexTexture(String currentLine) {
		String[] arguments = currentLine.split(" ");
		
		if (!(arguments.length <= 4 && arguments.length >= 3)) {
			throw new RuntimeException("Error by parsing new vertex. The length was not 3 or 4! ");
		}

		float u = Float.parseFloat(arguments[1]);
		float v = Float.parseFloat(arguments[2]);

		if (arguments.length == 3) {
			latestObject().addVertexTexture(new VertexTexture(u, v));
		} else if (arguments.length == 4) {
			float w = Float.parseFloat(arguments[3]);
			latestObject().addVertexTexture(new VertexTexture(u, v, w));
		}
	}

	private void parseNewPolygon(String currentLine) {
		String[] arguments = currentLine.split(" ");
		ArrayList<Integer> vertexIndices = new ArrayList<>();
		ArrayList<Integer> textureIndices = new ArrayList<>();

		for (String _argument : arguments) {
			if (_argument.equals("f")) {
				continue;
			}
			if (_argument.contains("/")) {
				String[] split = _argument.split("/");
				vertexIndices.add(Integer.parseInt(split[0]));
				textureIndices.add(Integer.parseInt(split[1]));
			}else {
				vertexIndices.add(Integer.parseInt(_argument));
			}
		}
		
		int[] vertexIndicesArray = new int[vertexIndices.size()];
		int[] textureIndicesArray = new int[textureIndices.size()];
		
		for (int i = 0; i < vertexIndicesArray.length; i++) 
			vertexIndicesArray[i] = vertexIndices.get(i);
		
		for (int i = 0; i < textureIndicesArray.length; i++) 
			textureIndicesArray[i] = textureIndices.get(i);
		
		
		latestObject().addPolygon(new Polygon(vertexIndicesArray, textureIndicesArray));
	}
	
	private Object3D latestObject() {
		return group.getObjects().get(group.getObjects().size()-1);
	}
}
