package com.aim.project.sdsstp.runners;


import com.aim.project.sdsstp.hyperheuristics.SimpleChoiceFunction;
import AbstractClasses.HyperHeuristic;

/**
 * Runner for SimpleChoiceFunction 
 * @author Lekang Jiang
 *
 */
public class SimpleChoiceFunction_VisualRunner extends HH_Runner_Visual {

	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {

		return new SimpleChoiceFunction(seed);
	}
	
	public static void main(String [] args) {
		
		HH_Runner_Visual runner = new SimpleChoiceFunction_VisualRunner();
		runner.run();
	}

}
