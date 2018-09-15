package br.uff.mh.mestrado.heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.log4j.Logger;

import br.uff.mh.mestrado.config.Config;
import br.uff.mh.mestrado.config.LsItem;
import br.uff.mh.mestrado.gui.listener.DummyListener;
import br.uff.mh.mestrado.gui.listener.EventListener;
import br.uff.mh.mestrado.strategy.GeneticCrossingUniform;
import br.uff.mh.mestrado.strategy.LocalSearchAddXRemoveYRandom;
import br.uff.mh.mestrado.strategy.LocalSearchStrategy;
import br.uff.mh.mestrado.strategy.SortPackingByCost;
import br.uff.mh.mestrado.utils.HeuristicUtils;
import br.uff.mh.mestrado.vo.Packing;
import br.uff.mh.mestrado.vo.Results;

public class Genetic extends HeuristicDecorator {
	private static Logger logger = Logger.getLogger(Genetic.class);

	protected EventListener<Results> masterListener;
	protected EventListener<String> slaveListener;
	protected EventListener<Results> dummyListener;
	protected MersenneTwister random;
	protected Config config;
	protected Results r;
	protected List<LocalSearchStrategy> lsList;
	protected Comparator<Packing> comparator;
	protected GeneticCrossingUniform crossingStrategy;

	public Genetic(Config config, Heuristic heuristic, EventListener<Results> masterListener, EventListener<String> slaveListener) {
		super(heuristic);
		this.random = new MersenneTwister(config.getSeed());
		this.masterListener = masterListener;
		this.slaveListener = slaveListener;
		this.dummyListener = new DummyListener();
		this.config = config;
		this.r = new Results(taskName());
		this.comparator = new SortPackingByCost();
		this.crossingStrategy = new GeneticCrossingUniform(this);

		this.lsList = new ArrayList<LocalSearchStrategy>();
		for (LsItem ls : this.config.getLs().getItem()) {
			this.lsList.add(new LocalSearchAddXRemoveYRandom(this, ls.getDel(), ls.getAdd()));
		}
	}

	private List<Packing> createGreedyPopulation(int lenght) throws Exception {
		List<Packing> population = new ArrayList<Packing>(lenght);

		int i = 0;
		while (i != lenght) {
			this.getCollection().getPacking().clear();
			super.doJob();
			if (!population.contains(this.getCollection().getPacking())) {
				population.add(this.getCollection().getPacking().copy());
				i++;
			}
		}

		return population;
	}

	private Packing localSearch(Packing packing) {
		Results results = new Results(HeuristicType.LS.getName());
		results.startAll();

		Packing best = packing;
		Packing curPacking = packing;

		for (LocalSearchStrategy localSearchStrategy : lsList) {

//			Packing neighbor = localSearchStrategy.findFeasibleNeighbor(best, results);
//			while (neighbor.getCost() > best.getCost()) {
//				this.getCollection().setPacking(neighbor);
//				best = neighbor;
//				neighbor = localSearchStrategy.findFeasibleNeighbor(best, results);
//			}

			Packing neighbor = localSearchStrategy.findFeasibleNeighbor(curPacking, results);
			for (int i = 0; i < 10; i++) {
				while (neighbor.getCost() > curPacking.getCost()) {
					if (neighbor.getCost() > best.getCost())
						best = neighbor;

					curPacking = neighbor;
					neighbor = localSearchStrategy.findFeasibleNeighbor(curPacking, r);
				}

				curPacking = best.copy();
				HeuristicUtils.randomMix(this.getCollection(), this.random, curPacking, localSearchStrategy.getDel() * 2);
				neighbor = localSearchStrategy.findFeasibleNeighbor(curPacking, r);
			}
		}

		return best;
	}

	private Packing crossing(List<Packing> population) {
		int indexFather1 = this.random.nextInt(this.config.getGenetic().getClassASize());
		int indexFather2 = this.random.nextInt(this.config.getGenetic().getPopulationSize());

		if (indexFather2 < this.config.getGenetic().getClassASize())
			indexFather2 += this.config.getGenetic().getClassASize();

		return crossingStrategy.crossing(population.get(indexFather1), population.get(indexFather2));
	}

	private List<Packing> newPopulation(List<Packing> curPopulation) throws Exception {
		List<Packing> population = new ArrayList<Packing>(this.config.getGenetic().getPopulationSize());

		// add Class A
		slaveListener.update("New Population: preserving " + this.config.getGenetic().getClassASize() + " individuals Class A");
		for (int i = 0; i < this.config.getGenetic().getClassASize(); i++) {
			population.add(curPopulation.get(i));
		}

		// generate Class B
		int i = 0;
		int max = this.config.getGenetic().getPopulationSize() * 100;
		while (i != this.config.getGenetic().getClassBSize() && max-- != 0) {
			Packing p = crossing(curPopulation);
			p = localSearch(p);

			if (!population.contains(p)) {
				population.add(p);
				slaveListener.update("New Population: crossing individuals Class A with Class B/C: " + i + " cost: " + p.getCost());
				i++;
			}
		}

		// generate Class C
		slaveListener.update("New Population: creating " + this.config.getGenetic().getClassCSize() + " new individuals using " + this.getName());
		population.addAll(createGreedyPopulation(this.config.getGenetic().getClassCSize()));
		Collections.sort(population, this.comparator);

		return population;
	}

	public void doJob() throws Exception {
		r.startAll();

		slaveListener.update("Creating new population using: " + this.getName());
		List<Packing> population = createGreedyPopulation(this.config.getGenetic().getPopulationSize());
		Collections.sort(population, this.comparator);
		logger.debug("First population: " + population);

		for (int i = 1; i <= this.config.getGenetic().getLoop(); i++) {
			r.setCurrentLoop(i);
			r.startJob();
			masterListener.update(r);

			population = newPopulation(population);

			if (logger.isDebugEnabled())
				logger.debug("Population: " + population);

			r.endJob();
			r.updatePackingCost(population.get(0).getCost());
		}

		this.getCollection().setPacking(population.get(0).copy());

		r.updatePackingCost(this.getCollection().getPacking().getCost());
		r.endAll();
		masterListener.update(r);
	}

	public void setNewSeed(int seed) {
		logger.debug(this.getClass() + " new seed: " + seed);
		this.random = new MersenneTwister(seed);
		heuristic.setNewSeed(seed);
	}

	public String taskName() {
		return super.taskName() + "." + HeuristicType.Genetic;
	}

	public Config getConfig() {
		return this.config;
	}

	public EventListener<Results> getListener() {
		return dummyListener;
	}

	public MersenneTwister getRandom() {
		return this.random;
	}

}
