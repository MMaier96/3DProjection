package application.gui.controller;

import java.io.File;
import java.util.ArrayList;

import application.parser.WaveFrontParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ApplicationController {

	@FXML
	HBox root;
	
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
	
	private File opened3DFile;
	private File projections;

	@FXML
	public void initialize() {
		ChangeListener<Boolean> checkBoxChangeListener = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if(xyProj.isSelected() || xzProj.isSelected() || yzProj.isSelected()) {
		        	createProjectionsButton.setDisable(false);
		        }else {
		        	createProjectionsButton.setDisable(true);
		        }
		    }
		};
		xyProj.selectedProperty().addListener(checkBoxChangeListener);
		xzProj.selectedProperty().addListener(checkBoxChangeListener);
		yzProj.selectedProperty().addListener(checkBoxChangeListener);
	}
	
	@FXML
	public void openFileChooser() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Load a 3D object...");
		chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("WaveFront", "*.obj")
            );
		opened3DFile = chooser.showOpenDialog(new Stage());
		
		if (opened3DFile == null) {
			return;
		}
		
		WaveFrontParser parser = new WaveFrontParser(opened3DFile);
		parser.read();
		
		input.setText(opened3DFile.getName());
		xyProj.setDisable(false);
		xzProj.setDisable(false);
		yzProj.setDisable(false);
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
		}else {
			projections = new File(saveFolder.getPath() + "/Projections");
			projections.mkdirs();
		}
	}
}
