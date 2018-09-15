package br.uff.mh.mestrado.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import br.uff.mh.mestrado.ProcessHeuristics;
import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.config.LsItem;
import br.uff.mh.mestrado.excel.Excel;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.gui.listener.ListenerHCMC;
import br.uff.mh.mestrado.gui.listener.ListenerHCMCR;
import br.uff.mh.mestrado.gui.listener.ListenerLabelStatus;
import br.uff.mh.mestrado.heuristic.GRASP;
import br.uff.mh.mestrado.heuristic.GRASPTarget;
import br.uff.mh.mestrado.heuristic.Genetic;
import br.uff.mh.mestrado.heuristic.HCMC;
import br.uff.mh.mestrado.heuristic.HCMCR;
import br.uff.mh.mestrado.heuristic.HCMCRAlfa;
import br.uff.mh.mestrado.heuristic.HCTNV;
import br.uff.mh.mestrado.heuristic.Heuristic;
import br.uff.mh.mestrado.heuristic.HeuristicType;
import br.uff.mh.mestrado.heuristic.LocalSearch;
import br.uff.mh.mestrado.heuristic.PathRelinking;
import br.uff.mh.mestrado.utils.ParseUtils;
import br.uff.mh.mestrado.utils.SoundUtils;
import br.uff.mh.mestrado.utils.SystemUtils;
import br.uff.mh.mestrado.utils.XMLUtils;
import br.uff.mh.mestrado.vo.Results;
import br.uff.mh.mestrado.vo.ResultsVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
	private Config c;
	private String configFilename;
	private String configPath;
	private Excel excel;
	private Thread run;

	@FXML
	private Label lblProgress;
	@FXML
	private Label lblProgressMain;
	@FXML
	private Label lblInput;
	@FXML
	private Label lblHidden;

	@FXML
	private MenuBar menuBar;

	@FXML
	private CheckBox chbHCMC;
	@FXML
	private CheckBox chbHCMCR;
	@FXML
	private CheckBox chbHCTNV;
	@FXML
	private CheckBox chbHCMCRAlfa;
	@FXML
	private CheckBox chbLS;
	@FXML
	private CheckBox chbGRASP;
	@FXML
	private CheckBox chbGRASPTarget;
	@FXML
	private CheckBox chbPRForward;
	@FXML
	private CheckBox chbPRBackward;
	@FXML
	private CheckBox chbPRForAndBack;
	@FXML
	private CheckBox chbGenetic;

	@FXML
	private TextField txtMaxCollectionSize;
	@FXML
	private TextField txtMaxSubsetSize;
	@FXML
	private TextField txtMaxCost;
	@FXML
	private TextField txtDistribution;
	@FXML
	private TextField txtAlfa;
	@FXML
	private TextField txtSeed;
	@FXML
	private TextField txtGreedySeed;
	@FXML
	private TextField txtGreedyLoops;
	@FXML
	private TextField txtLSLoops;
	@FXML
	private TextField txtGRASPLoops;
	@FXML
	private TextField txtGeneticLoops;
	@FXML
	private TextField txtMaxSizeRCLPR;
	@FXML
	private TextField txtHCMCMaxCost;
	@FXML
	private TextField txtHCMCLS;
	@FXML
	private TextField txtHCMCPR;
	@FXML
	private TextField txtHCMCMinGrasp;
	@FXML
	private TextField txtHCMCMaxGrasp;
	@FXML
	private TextField txtHCMCMaxTime;
	@FXML
	private TextField txtHCMCTotalTime;
	@FXML
	private TextField txtHCMCRMinCost;
	@FXML
	private TextField txtHCMCRMaxCost;
	@FXML
	private TextField txtHCMCRLS;
	@FXML
	private TextField txtHCMCRPR;
	@FXML
	private TextField txtHCMCRMinGrasp;
	@FXML
	private TextField txtHCMCRMaxGrasp;
	@FXML
	private TextField txtHCMCRMaxTime;
	@FXML
	private TextField txtHCMCRTotalTime;
	@FXML
	private TextField txtHCMCRAlfaMinCost;
	@FXML
	private TextField txtHCMCRAlfaMaxCost;
	@FXML
	private TextField txtHCMCRAlfaLS;
	@FXML
	private TextField txtHCMCRAlfaPR;
	@FXML
	private TextField txtHCMCRAlfaMinGrasp;
	@FXML
	private TextField txtHCMCRAlfaMaxGrasp;
	@FXML
	private TextField txtHCMCRAlfaMaxTime;
	@FXML
	private TextField txtHCMCRAlfaTotalTime;
	@FXML
	private TextField txtHCTNVMaxCost;
	@FXML
	private TextField txtHCTNVLS;
	@FXML
	private TextField txtHCTNVPR;
	@FXML
	private TextField txtHCTNVMinGrasp;
	@FXML
	private TextField txtHCTNVMaxGrasp;
	@FXML
	private TextField txtHCTNVMaxTime;
	@FXML
	private TextField txtHCTNVTotalTime;
	@FXML
	private TextField txtGeneticMinCost;
	@FXML
	private TextField txtGeneticMaxCost;
	@FXML
	private TextField txtGeneticMaxTime;
	@FXML
	private TextField txtGeneticTotalTime;

	@FXML
	private TextField txtExcelFilename;

	@FXML
	private Button btnRun;
	@FXML
	private Button btnClose;
	@FXML
	private Button btnOpen;
	@FXML
	private Button btnCreate;
	@FXML
	private Button btnEditConfig;

	public void stopPlay(ActionEvent event) {
		SoundUtils.getInstance().stop();
	}

	private Heuristic createLS(Heuristic h, EventListener<Results> listenerLS) {
		Heuristic heuristic = h;
		Config newConfig = createConfigFromWindow();

		for (LsItem item : newConfig.getLs().getItem()) {
			heuristic = new LocalSearch(item.getDel(), item.getAdd(), newConfig, heuristic, listenerLS);

		}

		return heuristic;
	}

	private Heuristic createPR(Heuristic h, HeuristicType greedyType) {
		Heuristic heuristic = h;

		if (chbPRForward.isSelected()) {
			heuristic = new PathRelinking(h, HeuristicType.PRFor, createConfigFromWindow(), createListenerPR(greedyType));
		}

		if (chbPRBackward.isSelected()) {
			heuristic = new PathRelinking(h, HeuristicType.PRBack, createConfigFromWindow(), createListenerPR(greedyType));
		}

		if (chbPRForAndBack.isSelected()) {
			heuristic = new PathRelinking(h, HeuristicType.PRForAndBack, createConfigFromWindow(), createListenerPR(greedyType));
		}

		return heuristic;
	}

	private EventListener<Results> createListener(HeuristicType t) {
		Label lbl = lblProgress;
		if (chbGenetic.isSelected())
			lbl = lblHidden;

		switch (t) {
		case HCMC:
			return new ListenerHCMC(createConfigFromWindow(), lbl, txtHCMCMaxCost, txtHCMCMaxTime, txtHCMCTotalTime);
		case HCMCR:
			return new ListenerHCMCR(createConfigFromWindow(), lbl, txtHCMCRMaxCost, txtHCMCRMinCost, txtHCMCRMaxTime, txtHCMCRTotalTime);
		case HCMCRAlfa:
			return new ListenerHCMCR(createConfigFromWindow(), lbl, txtHCMCRAlfaMaxCost, txtHCMCRAlfaMinCost, txtHCMCRAlfaMaxTime, txtHCMCRAlfaTotalTime);
		case HCTNV:
			return new ListenerHCMC(createConfigFromWindow(), lbl, txtHCTNVMaxCost, txtHCTNVMaxTime, txtHCTNVTotalTime);
		default:
			return null;
		}
	}

	private EventListener<Results> createListenerLS(HeuristicType t) {
		switch (t) {
		case HCMC:
			return new ListenerHCMC(createConfigFromWindow(), lblProgress, txtHCMCLS, txtHCMCMaxTime, txtHCMCTotalTime);
		case HCMCR:
			return new ListenerHCMC(createConfigFromWindow(), lblProgress, txtHCMCRLS, txtHCMCRMaxTime, txtHCMCRTotalTime);
		case HCMCRAlfa:
			return new ListenerHCMC(createConfigFromWindow(), lblProgress, txtHCMCRAlfaLS, txtHCMCRAlfaMaxTime, txtHCMCRAlfaTotalTime);
		case HCTNV:
			return new ListenerHCMC(createConfigFromWindow(), lblProgress, txtHCTNVLS, txtHCTNVMaxTime, txtHCTNVTotalTime);
		default:
			return null;
		}
	}

	private EventListener<Results> createListenerPR(HeuristicType t) {
		switch (t) {
		case HCMC:
			return new ListenerHCMC(createConfigFromWindow(), lblProgress, txtHCMCPR, txtHCMCMaxTime, txtHCMCTotalTime);
		case HCMCR:
			return new ListenerHCMC(createConfigFromWindow(), lblProgress, txtHCMCRPR, txtHCMCRMaxTime, txtHCMCRTotalTime);
		case HCMCRAlfa:
			return new ListenerHCMC(createConfigFromWindow(), lblProgress, txtHCMCRAlfaPR, txtHCMCRAlfaMaxTime, txtHCMCRAlfaTotalTime);
		case HCTNV:
			return new ListenerHCMC(createConfigFromWindow(), lblProgress, txtHCTNVPR, txtHCTNVMaxTime, txtHCTNVTotalTime);
		default:
			return null;
		}
	}

	private EventListener<Results> createListenerGRASP(HeuristicType t) {
		switch (t) {
		case HCMC:
			return new ListenerHCMCR(createConfigFromWindow(), lblProgressMain, txtHCMCMaxGrasp, txtHCMCMinGrasp, txtHCMCMaxTime, txtHCMCTotalTime);
		case HCMCR:
			return new ListenerHCMCR(createConfigFromWindow(), lblProgressMain, txtHCMCRMaxGrasp, txtHCMCRMinGrasp, txtHCMCRMaxTime, txtHCMCRTotalTime);
		case HCMCRAlfa:
			return new ListenerHCMCR(createConfigFromWindow(), lblProgressMain, txtHCMCRAlfaMaxGrasp, txtHCMCRAlfaMinGrasp, txtHCMCRAlfaMaxTime,
					txtHCMCRAlfaTotalTime);
		case HCTNV:
			return new ListenerHCMCR(createConfigFromWindow(), lblProgressMain, txtHCTNVMaxGrasp, txtHCTNVMinGrasp, txtHCTNVMaxTime, txtHCTNVTotalTime);
		default:
			return null;
		}
	}

	private EventListener<Results> createListenerGeneticMaster() {
		return new ListenerHCMCR(createConfigFromWindow(), lblProgressMain, txtGeneticMaxCost, txtGeneticMinCost, txtGeneticMaxTime, txtGeneticTotalTime);
	}

	private EventListener<String> createListenerGeneticSlave() {
		return new ListenerLabelStatus(lblProgress);
	}

	public void run(ActionEvent event) throws InterruptedException {
		try {
			setDisableButtons(true);
			excel = new Excel(createConfigFromWindow());
			clearTextBox();

			ProcessHeuristics pt = new ProcessHeuristics(new ListenerLabelStatus(lblProgress), this);

			if (chbHCMC.isSelected()) {
				Heuristic h = new HCMC(createConfigFromWindow(), createListener(HeuristicType.HCMC));

				if (chbLS.isSelected()) {
					h = createLS(h, createListenerLS(HeuristicType.HCMC));
				}

				h = createPR(h, HeuristicType.HCMC);

				if (chbGRASP.isSelected()) {
					h = new GRASP(createConfigFromWindow(), h, createListenerGRASP(HeuristicType.HCMC));
				}

				if (chbGRASPTarget.isSelected()) {
					h = new GRASPTarget(createConfigFromWindow(), h, createListenerGRASP(HeuristicType.HCMC));
				}

				if (chbGenetic.isSelected()) {
					h = new Genetic(createConfigFromWindow(), h, createListenerGeneticMaster(), createListenerGeneticSlave());
				}

				pt.add(h);
				excel.add(h);
			}

			if (chbHCMCR.isSelected()) {
				Heuristic h = new HCMCR(createConfigFromWindow(), createListener(HeuristicType.HCMCR));

				if (chbLS.isSelected()) {
					h = createLS(h, createListenerLS(HeuristicType.HCMCR));
				}

				h = createPR(h, HeuristicType.HCMCR);

				if (chbGRASP.isSelected()) {
					h = new GRASP(createConfigFromWindow(), h, createListenerGRASP(HeuristicType.HCMCR));
				}

				if (chbGRASPTarget.isSelected()) {
					h = new GRASPTarget(createConfigFromWindow(), h, createListenerGRASP(HeuristicType.HCMCR));
				}

				if (chbGenetic.isSelected()) {
					h = new Genetic(createConfigFromWindow(), h, createListenerGeneticMaster(), createListenerGeneticSlave());
				}

				pt.add(h);
				excel.add(h);
			}

			if (chbHCTNV.isSelected()) {
				Heuristic h = new HCTNV(createConfigFromWindow(), createListener(HeuristicType.HCTNV));

				if (chbLS.isSelected()) {
					h = createLS(h, createListenerLS(HeuristicType.HCTNV));
				}

				h = createPR(h, HeuristicType.HCTNV);

				if (chbGRASP.isSelected()) {
					h = new GRASP(createConfigFromWindow(), h, createListenerGRASP(HeuristicType.HCTNV));
				}

				if (chbGRASPTarget.isSelected()) {
					h = new GRASPTarget(createConfigFromWindow(), h, createListenerGRASP(HeuristicType.HCTNV));
				}

				if (chbGenetic.isSelected()) {
					h = new Genetic(createConfigFromWindow(), h, createListenerGeneticMaster(), createListenerGeneticSlave());
				}

				pt.add(h);
				excel.add(h);
			}

			if (chbHCMCRAlfa.isSelected()) {
				Heuristic h = new HCMCRAlfa(createConfigFromWindow(), createListener(HeuristicType.HCMCRAlfa));

				if (chbLS.isSelected()) {
					h = createLS(h, createListenerLS(HeuristicType.HCMCRAlfa));
				}

				h = createPR(h, HeuristicType.HCMCRAlfa);

				if (chbGRASP.isSelected()) {
					h = new GRASP(createConfigFromWindow(), h, createListenerGRASP(HeuristicType.HCMCRAlfa));
				}

				if (chbGRASPTarget.isSelected()) {
					h = new GRASPTarget(createConfigFromWindow(), h, createListenerGRASP(HeuristicType.HCMCRAlfa));
				}

				if (chbGenetic.isSelected()) {
					h = new Genetic(createConfigFromWindow(), h, createListenerGeneticMaster(), createListenerGeneticSlave());
				}

				pt.add(h);
				excel.add(h);
			}

			run = new Thread(pt);
			run.start();
		} catch (Exception e) {
			showWarningAlert(e.getMessage());
			setDisableButtons(false);
			e.printStackTrace();
		} finally {
		}
	}

	public void editConfig(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("EditConfig.fxml"));

		// create a new scene with root and set the stage
		Scene scene = new Scene(root);

		Stage newStage = new Stage();
		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.centerOnScreen();

		newStage.showAndWait();

		this.c = XMLUtils.readConfigFile(configPath + configFilename);
		loadFields(this.c);
	}

	public void createExcel(ActionEvent event) {
		try {
			if (excel == null) {
				showWarningAlert("No data to export. Execute run button.");
				return;
			}

			ResultsVo rHCMC = new ResultsVo(HeuristicType.HCMC.getName(), "", txtHCMCMaxCost.getText(), txtHCMCLS.getText(), txtHCMCPR.getText(),
					txtHCMCMinGrasp.getText(), txtHCMCMaxGrasp.getText(), txtHCMCMaxTime.getText(), txtHCMCTotalTime.getText());
			ResultsVo rHCMCR = new ResultsVo(HeuristicType.HCMCR.getName(), txtHCMCRMinCost.getText(), txtHCMCRMaxCost.getText(), txtHCMCRLS.getText(),
					txtHCMCRPR.getText(), txtHCMCRMinGrasp.getText(), txtHCMCRMaxGrasp.getText(), txtHCMCRMaxTime.getText(), txtHCMCRTotalTime.getText());
			ResultsVo rHCTNV = new ResultsVo(HeuristicType.HCTNV.getName(), "", txtHCTNVMaxCost.getText(), txtHCTNVLS.getText(), txtHCTNVPR.getText(),
					txtHCTNVMinGrasp.getText(), txtHCTNVMaxGrasp.getText(), txtHCTNVMaxTime.getText(), txtHCTNVTotalTime.getText());
			ResultsVo rHCMCRAlfa = new ResultsVo(HeuristicType.HCMCRAlfa.getName(), txtHCMCRAlfaMinCost.getText(), txtHCMCRAlfaMaxCost.getText(),
					txtHCMCRAlfaLS.getText(), txtHCMCRAlfaPR.getText(), txtHCMCRAlfaMinGrasp.getText(), txtHCMCRAlfaMaxGrasp.getText(),
					txtHCMCRAlfaMaxTime.getText(), txtHCMCRAlfaTotalTime.getText());

			excel.addResult(rHCMC);
			excel.addResult(rHCMCR);
			excel.addResult(rHCMCRAlfa);
			excel.addResult(rHCTNV);

			setDisableButtons(true);
			excel.create(txtExcelFilename.getText());
			excel = null;
		} catch (Exception e) {
			showWarningAlert(e.getMessage() + " " + e.getClass());
			setDisableButtons(false);
			e.printStackTrace();
		} finally {
			setDisableButtons(false);
		}
	}

	public void openExcel(ActionEvent event) {
		try {
			setDisableButtons(true);
			if (SystemUtils.isMac()) {
				Runtime.getRuntime().exec("open " + txtExcelFilename.getText());
			} else if (SystemUtils.isWindows()) {
				Desktop.getDesktop().open(new File(txtExcelFilename.getText()));
			}
		} catch (Exception e) {
			showWarningAlert(e.getMessage() + " " + e.getClass());
			setDisableButtons(false);
			e.printStackTrace();
		} finally {
			setDisableButtons(false);
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
		loadFields(this.c);
	}

	public void close(ActionEvent event) {
		lblProgress.setText("Closing...");
		if (run != null) {
			run.interrupt();
			lblProgress.setText("Closing...");
			while (run.isAlive())
				;

		}

		((Stage) btnClose.getScene().getWindow()).close();
	}

	public void load(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open configuration file");
		fileChooser.setInitialDirectory(new File(this.configPath));

		Stage stage = (Stage) btnClose.getScene().getWindow();

		File openFile = fileChooser.showOpenDialog(stage);

		if (openFile != null && openFile.getName() != null) {
			this.configPath = StringUtils.substringBeforeLast(openFile.getPath(), "/") + "/";
			this.configFilename = openFile.getName();
			this.c = XMLUtils.readConfigFile(openFile);
			loadFields(this.c);
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
			XMLUtils.saveConfigFile(saveFile, createConfigFromWindow());
		}
	}

	private Config createConfigFromWindow() {
		Config c = XMLUtils.readConfigFile(configPath + configFilename);

		c.setMaxCollectionSize(ParseUtils.toInteger(txtMaxCollectionSize.getText()));
		c.setMaxSubsetSize(ParseUtils.toInteger(txtMaxSubsetSize.getText()));
		c.setMaxCost(ParseUtils.toInteger(txtMaxCost.getText()));
		c.setGreedyRandomLoops(ParseUtils.toInteger(txtGreedyLoops.getText()));
		c.setLocalSearchRandomLoops(ParseUtils.toInteger(txtLSLoops.getText()));
		c.setGraspRandomLoops(ParseUtils.toInteger(txtGRASPLoops.getText()));

		br.uff.mh.mestrado.config.Genetic g = c.getGenetic();
		g.setLoop(ParseUtils.toInteger(txtGeneticLoops.getText()));
		c.setGenetic(g);

		c.setPathRelinkingCandidateListMaxSize(ParseUtils.toInteger(txtMaxSizeRCLPR.getText()));
		c.setDistribution(ParseUtils.toDouble(txtDistribution.getText()));
		c.setAlfa(ParseUtils.toDouble(txtAlfa.getText()));
		c.setSeed(ParseUtils.toInteger(txtSeed.getText()));
		c.setGreedySeed(ParseUtils.toInteger(txtGreedySeed.getText()));
		c.setStrategyHCMC(chbHCMC.isSelected());
		c.setStrategyHCMCR(chbHCMCR.isSelected());
		c.setStrategyHCMCRAlfa(chbHCMCRAlfa.isSelected());
		c.setStrategyHCTNV(chbHCTNV.isSelected());
		c.setStrategyLS(chbLS.isSelected());
		c.setStrategyGRASP(chbGRASP.isSelected());
		c.setStrategyGRASPTarget(chbGRASPTarget.isSelected());
		c.setStrategyPRFor(chbPRForward.isSelected());
		c.setStrategyPRBack(chbPRBackward.isSelected());
		c.setStrategyPRForAndBack(chbPRForAndBack.isSelected());
		c.setStrategyGenetic(chbGenetic.isSelected());

		return c;
	}

	private void clearTextBox() {
		txtHCMCMaxCost.clear();
		txtHCMCLS.clear();
		txtHCMCMaxTime.clear();
		txtHCMCTotalTime.clear();
		txtHCMCRMinCost.clear();
		txtHCMCRMaxCost.clear();
		txtHCMCRLS.clear();
		txtHCMCRMaxTime.clear();
		txtHCMCRTotalTime.clear();

		txtHCMCRAlfaMinCost.clear();
		txtHCMCRAlfaMaxCost.clear();
		txtHCMCRAlfaLS.clear();
		txtHCMCRAlfaMaxTime.clear();
		txtHCMCRAlfaTotalTime.clear();

		txtHCTNVMaxCost.clear();
		txtHCTNVLS.clear();
		txtHCTNVMaxTime.clear();
		txtHCTNVTotalTime.clear();

		txtHCMCMaxGrasp.clear();
		txtHCMCMinGrasp.clear();
		txtHCMCRAlfaMaxGrasp.clear();
		txtHCMCRAlfaMinGrasp.clear();
		txtHCMCRMaxGrasp.clear();
		txtHCMCRMinGrasp.clear();
		txtHCTNVMaxGrasp.clear();
		txtHCTNVMinGrasp.clear();

		txtHCMCPR.clear();
		txtHCMCRPR.clear();
		txtHCMCRAlfaPR.clear();
		txtHCTNVPR.clear();

		txtGeneticMaxCost.clear();
		txtGeneticMinCost.clear();
		txtGeneticMaxTime.clear();
		txtGeneticTotalTime.clear();
	}

	public void setDisableButtons(boolean value) {
		// menuBar.setDisable(value);

		txtMaxCollectionSize.setDisable(value);
		txtMaxCost.setDisable(value);
		txtMaxSubsetSize.setDisable(value);
		txtDistribution.setDisable(value);
		txtAlfa.setDisable(value);
		txtSeed.setDisable(value);
		txtGreedySeed.setDisable(value);
		txtGreedyLoops.setDisable(value);
		txtLSLoops.setDisable(value);
		txtGRASPLoops.setDisable(value);
		txtGeneticLoops.setDisable(value);
		txtMaxSizeRCLPR.setDisable(value);
		chbHCMC.setDisable(value);
		chbHCMCR.setDisable(value);
		chbHCMCRAlfa.setDisable(value);
		chbHCTNV.setDisable(value);
		chbLS.setDisable(value);
		chbGRASP.setDisable(value);
		chbGRASPTarget.setDisable(value);
		chbPRForward.setDisable(value);
		chbPRBackward.setDisable(value);
		chbPRForAndBack.setDisable(value);
		chbGenetic.setDisable(value);
		btnRun.setDisable(value);
		btnOpen.setDisable(value);
		btnCreate.setDisable(value);
		btnEditConfig.setDisable(value);

		if (!value) {
			onClickCheckboxGRASP(null);
			onClickCheckboxLS(null);
			onClickCheckboxGreedy(null);
			onClickCheckboxGenetic(null);
		}
	}

	private void loadFields(Config c) {
		txtMaxCollectionSize.setText(ParseUtils.toString(c.getMaxCollectionSize()));
		txtMaxSubsetSize.setText(ParseUtils.toString(c.getMaxSubsetSize()));
		txtMaxCost.setText(ParseUtils.toString(c.getMaxCost()));
		txtGreedyLoops.setText(ParseUtils.toString(c.getGreedyRandomLoops()));
		txtLSLoops.setText(ParseUtils.toString(c.getLocalSearchRandomLoops()));
		txtGRASPLoops.setText(ParseUtils.toString(c.getGraspRandomLoops()));
		txtGeneticLoops.setText(ParseUtils.toString(c.getGenetic().getLoop()));
		txtMaxSizeRCLPR.setText(ParseUtils.toString(c.getPathRelinkingCandidateListMaxSize()));
		txtDistribution.setText(ParseUtils.toString(c.getDistribution()));
		txtAlfa.setText(ParseUtils.toString(c.getAlfa()));
		txtGreedySeed.setText(ParseUtils.toString(c.getGreedySeed()));
		txtSeed.setText(ParseUtils.toString(c.getSeed()));
		chbHCMC.setSelected(c.isStrategyHCMC());
		chbHCMCR.setSelected(c.isStrategyHCMCR());
		chbHCMCRAlfa.setSelected(c.isStrategyHCMCRAlfa());
		chbHCTNV.setSelected(c.isStrategyHCTNV());
		chbLS.setSelected(c.isStrategyLS());
		chbGRASP.setSelected(c.isStrategyGRASP());
		chbGRASPTarget.setSelected(c.isStrategyGRASPTarget());
		chbPRForward.setSelected(c.isStrategyPRFor());
		chbPRBackward.setSelected(c.isStrategyPRBack());
		chbPRForAndBack.setSelected(c.isStrategyPRForAndBack());
		chbGenetic.setSelected(c.isStrategyGenetic());

		if (Main.parameters.length > 0) {
			txtExcelFilename.setText(new File(Main.parameters[2]).getAbsolutePath());
		} else {
			txtExcelFilename.setText(new File("./results.xlsx").getAbsolutePath());
		}

		lblInput.setText("Input from " + this.configPath + this.configFilename);

		onClickCheckboxGRASP(null);
		onClickCheckboxLS(null);
		onClickCheckboxGreedy(null);
		onClickCheckboxPR(null);
		onClickCheckboxGenetic(null);
	}

	private void showWarningAlert(String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning!");
		alert.setContentText(text);

		alert.showAndWait();
	}

	public void onClickCheckboxGenetic(ActionEvent event) {
		if (chbGenetic.isSelected()) {
			txtGeneticLoops.setDisable(false);
		} else {
			txtGeneticLoops.setDisable(true);
		}
	}

	public void onClickCheckboxGRASP(ActionEvent event) {
		if (chbGRASP.isSelected() || chbGRASPTarget.isSelected()) {
			txtGRASPLoops.setDisable(false);
		} else {
			txtGRASPLoops.setDisable(true);
		}

		if (chbGRASP.isSelected() && chbGRASPTarget.isSelected()) {
			showWarningAlert("Must select only one GRASP strategy!");
			btnRun.setDisable(true);
		} else {
			btnRun.setDisable(false);
		}
	}

	public void onClickCheckboxPR(ActionEvent event) {
		boolean forw = chbPRForward.isSelected();
		boolean back = chbPRBackward.isSelected();
		boolean forAndBack = chbPRForAndBack.isSelected();

		int count = 0;
		if (forw)
			count++;

		if (back)
			count++;

		if (forAndBack)
			count++;

		if (count > 0) {
			txtMaxSizeRCLPR.setDisable(false);
		} else {
			txtMaxSizeRCLPR.setDisable(true);
		}

		if (count > 1) {
			showWarningAlert("You have selected redundant path relinking strategies!");
			btnRun.setDisable(true);
		} else {
			btnRun.setDisable(false);
		}

	}

	public void onClickCheckboxLS(ActionEvent event) {
		if (chbLS.isSelected()) {
			txtLSLoops.setDisable(false);
		} else {
			txtLSLoops.setDisable(true);
		}
	}

	public void onClickCheckboxGreedy(ActionEvent event) {
		boolean hcmc = chbHCMC.isSelected();
		boolean hcmcr = chbHCMCR.isSelected();
		boolean hcmcralfa = chbHCMCRAlfa.isSelected();
		boolean hctnv = chbHCTNV.isSelected();

		if (!hcmc && !hcmcr && !hcmcralfa && !hctnv) {
			txtGreedyLoops.setDisable(true);
			showWarningAlert("At least one greedy strategy should be selected!");
			btnRun.setDisable(true);
		} else {
			txtGreedyLoops.setDisable(false);
			btnRun.setDisable(false);
		}

	}
}
