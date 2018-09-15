package br.uff.mh.mestrado.gui;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.utils.XMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditConfigController {
	private Config c;
	private String configFilename;
	private String configPath;

	@FXML
	private TextField txtConfigFilename;
	@FXML
	private TextArea txtConfig;

	@FXML
	private Button btnClose;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnLoad;

	public void close(ActionEvent event) {
		if (event.getSource() == btnClose) {
			Stage stage = (Stage) btnClose.getScene().getWindow();
			stage.close();
		}
	}

	public void initialize() {
		if (Main.parameters.length > 0) {
			configPath = Main.parameters[0];
			configFilename = Main.parameters[1];
		} else {
			configPath = "./src/main/resources/";
			configFilename = "configuration.xml";
		}

		this.c = XMLUtils.readConfigFile(configPath + configFilename);
		this.txtConfigFilename.setText(configPath + configFilename);
		txtConfig.setText(XMLUtils.format(readFile(new File(configPath + configFilename))));
	}

	private String readFile(File file) {
		try {
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void load(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open configuration file");
		fileChooser.setInitialFileName(this.configFilename);
		fileChooser.setInitialDirectory(new File(this.configPath));

		Stage stage = (Stage) btnClose.getScene().getWindow();

		File openFile = fileChooser.showOpenDialog(stage);
		
		if (openFile != null && openFile.getName() != null) {
			this.c = XMLUtils.readConfigFile(openFile);
			txtConfig.setText(XMLUtils.format(readFile(openFile)));
		}
	}

	public void save(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save configuration file");
		fileChooser.setInitialFileName(this.configFilename);
		fileChooser.setInitialDirectory(new File(this.configPath));

		Stage stage = (Stage) btnClose.getScene().getWindow();

		File saveFile = fileChooser.showSaveDialog(stage);

		if (saveFile != null && saveFile.getName() != null) {
			XMLUtils.saveConfigFile(saveFile, txtConfig.getText());
		}
	}
}
