package com.schaeidt.contract.maker.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.schaeidt.contract.maker.model.DocumentModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {
	private DocumentModel model = new DocumentModel();
	private File openFile = null;

	private enum FileDialogMode {
		OPEN, SAVE;
	}

	@FXML
	private ScrollPane content;

	@FXML
	private MenuItem open;

	@FXML
	private MenuItem save_as;

	@FXML
	private MenuItem quit;

	@FXML
	private MenuItem about;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		open.setOnAction(e -> {
			if (openFile == null) {
				openFile = OpenFileDialog("Open File", FileDialogMode.OPEN);
				model.initialize(openFile);
				VBox commands = new VBox();
				for (String variable : model.getVariables()) {
					HBox line = new HBox();
					Label l = new Label();
					l.setText(variable);
					Separator s = new Separator();
					s.setMaxWidth(0);
					TextArea t = new TextArea();
					line.getChildren().addAll(l, s, t);
					commands.getChildren().add(line);
				}
				content.setContent(commands);
			}
		});
		save_as.setOnAction(e -> {
			// TODO: set values in DocumentModel and save
			File file = OpenFileDialog("Save File As...", FileDialogMode.SAVE);
			model.save(file);
		});
		quit.setOnAction(e -> {
			//TODO: warn about saving?
			Stage stage = (Stage) content.getScene().getWindow();
		    stage.close();
		});
	}

	private File OpenFileDialog(String title, FileDialogMode mode) {
		File f = null;
		FileChooser chooser = new FileChooser();
		chooser.setTitle(title);
		chooser.getExtensionFilters().add(new ExtensionFilter("OpenDocument Text (.odt)", "*.odt"));
		switch (mode) {
		case SAVE:
			f = chooser.showSaveDialog(new Stage());
			break;
		case OPEN:
			f = chooser.showOpenDialog(new Stage());
			break;
		default:
			break;
		}
		return f;
	}
}
