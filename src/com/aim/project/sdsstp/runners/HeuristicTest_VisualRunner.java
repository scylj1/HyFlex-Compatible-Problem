package com.aim.project.sdsstp.runners;

import com.aim.project.sdsstp.hyperheuristics.HeuristicTest;

import AbstractClasses.HyperHeuristic;

/**
 * Runner for HeuristicTest 
 * @author Lekang Jiang
 *
 */
public class HeuristicTest_VisualRunner extends HH_Runner_Visual {

	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {

		return new HeuristicTest(seed);
	}
	
	public static void main(String [] args) {
		
		HH_Runner_Visual runner = new HeuristicTest_VisualRunner();
		runner.run();
	}

}
