package com.aim.project.sdsstp.runners;

import com.aim.project.sdsstp.hyperheuristics.ReinforcementLinearSelection;
import AbstractClasses.HyperHeuristic;

/**
 * Runner for ReinforcementLinearSelection hyper-heuristics
 * @author Lekang Jiang
 *
 */
public class ReinforcementLinearSelection_VisualRunner extends HH_Runner_Visual {

	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {

		return new ReinforcementLinearSelection(seed);
	}
	
	public static void main(String [] args) {
		
		HH_Runner_Visual runner = new ReinforcementLinearSelection_VisualRunner();
		runner.run();
	}

}
