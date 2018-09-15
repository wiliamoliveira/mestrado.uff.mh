package br.uff.mh.mestrado.gui;

import org.apache.log4j.BasicConfigurator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static String[] parameters;

	public static void main(String[] args) {
		if (args.length == 0) {
			BasicConfigurator.configure();
		}
		parameters = args;
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent loader = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
		
		Scene scene = new Scene(loader);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
