package br.uff.mh.mestrado.gui.listener;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.utils.ParseUtils;
import br.uff.mh.mestrado.vo.Results;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ListenerHCMC implements EventListener<Results> {
	protected Config c;
	protected TextField txtMaxCost;
	protected TextField txtTimeMax;
	protected TextField txtLoopTime;
	protected Label lblProgress;
	protected Results results;

	public ListenerHCMC(Config c, Label lblProgress, TextField txtMaxCost, TextField txtTimeMax, TextField txtLoopTime) {
		this.c = c;
		this.lblProgress = lblProgress;
		this.txtMaxCost = txtMaxCost;
		this.txtTimeMax = txtTimeMax;
		this.txtLoopTime = txtLoopTime;
	}

	public void update(Results results) {
		this.results = results;
		Platform.runLater(this);
	}

	public void run() {
		StringBuilder sb = new StringBuilder();
		sb.append("Processing: ");
		sb.append(results.getTaskName());
		sb.append(", Loop: ");
		sb.append(results.getCurrentLoop());

		// lblProgress.setText(sb.toString());
		lblProgress.textProperty().set(sb.toString());

		if (results.getMaxCost() != 0)
			txtMaxCost.setText(ParseUtils.toString(results.getMaxCost()));

		txtTimeMax.setText(results.getMaxJobTime());
		txtLoopTime.setText(results.getLoopTotalTime());
	}

}
