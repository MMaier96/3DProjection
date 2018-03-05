package application.gui.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;


import application.parser.WaveFrontParser;
import application.wavefront.Face;
import application.wavefront.Object3D;
import application.wavefront.Vector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
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

		if (xyProj.isSelected()) {
			Object3D object3D = parser.parse().projectXY();
			
			ArrayList<Float> pointsList = new ArrayList<>();
			ArrayList<Integer> facesList = new ArrayList<>();
			
			TriangleMesh triangleMesh = new TriangleMesh();
			
			for (Vector vector: object3D.getVectors()) {
				pointsList.add((float) vector.getX());
				pointsList.add((float) vector.getY());
				pointsList.add((float) vector.getZ());
			}
			int maxId = 0;
			for (Face _face: object3D.getFaces()) {
				if (_face.getIndex1() > maxId) {
					maxId = _face.getIndex1();
				}
				if (_face.getIndex2() > maxId) {
					maxId = _face.getIndex2();
				}
				if (_face.getIndex3() > maxId) {
					maxId = _face.getIndex3();
				}
				
				facesList.add(_face.getIndex1());
				facesList.add(0);
				facesList.add(_face.getIndex2());
				facesList.add(0);
				facesList.add(_face.getIndex3());
				facesList.add(0);
			}
			float[] pointsArray = new float[pointsList.size()];
			for (int i = 0; i < pointsArray.length; i++) {
				pointsArray[i] = pointsList.get(i);
			}
			
			int[] facesArray = new int[facesList.size()];
			for (int i = 0; i < facesArray.length; i++) {
				facesArray[i] = facesList.get(i);
			}
			System.out.println("max: " + maxId);
			System.out.println("lengt: " + pointsArray.length);
			
			triangleMesh.getPoints().addAll(pointsArray);
			triangleMesh.getTexCoords().addAll(0f,0f);
			triangleMesh.getFaces().addAll(facesArray);
			
			MeshView meshView = new MeshView(triangleMesh);
			meshView.setCullFace(CullFace.FRONT);
			meshView.setScaleX(4);
		    meshView.setScaleY(4);
		    meshView.setScaleZ(4);
			root.getChildren().add(meshView);
			root.getChildren().add(new Button("tttt"));
			
			//export
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(projections.getPath() + "/XY_Projection.obj", "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			writer.println(object3D.toString());
			writer.close();
		}

		if (xzProj.isSelected()) {
			Object3D object = parser.parse().projectXZ();
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(projections.getPath() + "/XZ_Projection.obj", "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			writer.println(object.toString());
			writer.close();
		}

		if (yzProj.isSelected()) {
			Object3D object = parser.parse().projectYZ();
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(projections.getPath() + "/YZ_Projection.obj", "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			writer.println(object.toString());
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
