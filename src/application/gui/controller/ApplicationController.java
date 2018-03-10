package application.gui.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import application.parser.WaveFrontParser;
import application.wavefront.Group3D;
import application.wavefront.convert.WaveFrontToStringBuilderConverter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ApplicationController {

	@FXML
	VBox root;

	@FXML
	CheckBox xyProj;
	@FXML
	CheckBox xzProj;
	@FXML
	CheckBox yzProj;

	@FXML
	TextField input;

	@FXML
	Button createProjectionsButton;

	@FXML
	Button selectAllButton;
	

	private File opened3DFile;
	private File projections;

	@FXML
	public void initialize() {
		ChangeListener<Boolean> checkBoxChangeListener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (xyProj.isSelected() || xzProj.isSelected() || yzProj.isSelected()) {
					createProjectionsButton.setDisable(false);
				} else {
					createProjectionsButton.setDisable(true);
				}
			}
		};
		xyProj.selectedProperty().addListener(checkBoxChangeListener);
		xzProj.selectedProperty().addListener(checkBoxChangeListener);
		yzProj.selectedProperty().addListener(checkBoxChangeListener);
		
	}

	
	@FXML 
	public void selectAllButtonClick() {
		xyProj.setSelected(true);
		xzProj.setSelected(true);
		yzProj.setSelected(true);
	}
	
	@FXML
	public void openFileChooser() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File("."));

		chooser.setTitle("Load a 3D object...");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("WaveFront", "*.obj"));
		opened3DFile = chooser.showOpenDialog(new Stage());

		if (opened3DFile == null) {
			return;
		}

		input.setText(opened3DFile.getName());
		xyProj.setDisable(false);
		xzProj.setDisable(false);
		yzProj.setDisable(false);
		selectAllButton.setDisable(false);
	}

	@FXML
	public void saveFileChooser() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setInitialDirectory(opened3DFile.getParentFile());
		chooser.setTitle("Save the exported projections ...");
		File saveFolder = chooser.showDialog(new Stage());

		if (saveFolder == null) {
			return;
		}
		if (saveFolder.getName().equals("Projections")) {
			projections = saveFolder;
		} else {
			projections = new File(saveFolder.getPath() + "/Projections");
			projections.mkdirs();
		}

		WaveFrontParser parser = new WaveFrontParser(opened3DFile);
		Group3D parse;
		WaveFrontToStringBuilderConverter stringConverter;
		

		if (xyProj.isSelected()) {
			parse = parser.parse();
			parse.projectionZ();
			
			stringConverter = new WaveFrontToStringBuilderConverter(parse);
			stringConverter.convert();
		    StringBuilder result = stringConverter.getResult();
			
			//export
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(projections.getPath() + "/XY_Projection.obj", "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			writer.println(result.toString());
			writer.close();
		}

		if (xzProj.isSelected()) {
			parse = parser.parse();
			parse.projectionY();
			
			stringConverter = new WaveFrontToStringBuilderConverter(parse);
			stringConverter.convert();
		    StringBuilder result = stringConverter.getResult();
			
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(projections.getPath() + "/XZ_Projection.obj", "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			writer.println(result.toString());
			writer.close();
		}

		if (yzProj.isSelected()) {
			parse = parser.parse();
			parse.projectionX();
			
			stringConverter = new WaveFrontToStringBuilderConverter(parse);
			stringConverter.convert();
		    StringBuilder result = stringConverter.getResult();
			
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(projections.getPath() + "/YZ_Projection.obj", "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			writer.println(result.toString());
			writer.close();
		}
		
		Alert alert = new Alert(AlertType.INFORMATION, "The projections were saved!");
		alert.setTitle("Saved!");
		alert.setHeaderText("Saved!");
		alert.showAndWait();
		try {
			Desktop.getDesktop().open(projections);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
