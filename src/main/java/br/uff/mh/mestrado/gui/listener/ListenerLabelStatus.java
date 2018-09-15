package br.uff.mh.mestrado.gui.listener;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class ListenerLabelStatus implements EventListener<String> {

	private Label lblProgress;
	private String message;

	public ListenerLabelStatus(Label lblProgress) {
		this.lblProgress = lblProgress;
	}

	public void update(String message) {
		this.message = message;
		Platform.runLater(this);

	}

	public void run() {
		lblProgress.setText(message);

	}

}
