package application.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WaveFrontParser {

	private Path path;
	private ArrayList<String> lines;

	public WaveFrontParser(File file) {
		this.path = Paths.get(file.getPath());
		lines = new ArrayList<>();

	}

	public void read() {
			try {
				Files.readAllLines(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void parse() {
		//parse here
	}
	
	public ArrayList<String> getLines(){
		return lines;
	}
}
