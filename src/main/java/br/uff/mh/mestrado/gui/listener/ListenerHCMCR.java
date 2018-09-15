package br.uff.mh.mestrado.gui.listener;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.utils.ParseUtils;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ListenerHCMCR extends ListenerHCMC {
	protected TextField txtMinCost;

	public ListenerHCMCR(Config c, Label lblProgress, TextField txtMaxCost, TextField txtMinCost, TextField txtTimeMax,
			TextField txtLoopTime) {
		super(c, lblProgress, txtMaxCost, txtTimeMax, txtLoopTime);
		this.txtMinCost = txtMinCost;
	}

	public void run() {
		super.run();

		if (results.getMinCost() != Integer.MAX_VALUE)
			txtMinCost.setText(ParseUtils.toString(results.getMinCost()));
	}

}
