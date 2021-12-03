package com.aim.project.sdsstp.heuristics;


import java.util.Random;

import com.aim.project.sdsstp.interfaces.HeuristicInterface;
import com.aim.project.sdsstp.interfaces.SDSSTPSolutionInterface;


/**
 * 
 * @author Warren G. Jackson
 * @since 26/03/2021
 * 
 * Methods needing to be implemented:
 * - public double apply(SDSSTPSolutionInterface solution, double depthOfSearch, double intensityOfMutation)
 * - public boolean isCrossover()
 * - public boolean usesIntensityOfMutation()
 * - public boolean usesDepthOfSearch()
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {
	
	private final Random random;
	
	public NextDescent(Random random) {
	
		this.random = random;
	}

	@Override
	public double apply(SDSSTPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

		// TODO
		int times = (int)(Math.floor(depthOfSearch/0.2)+1);
		int landmark = solution.getNumberOfLandmarks();
		// get random start
		int start = random.nextInt(landmark);
		
		for(int i = 0; i < times; i++) {
			//System.out.print("times: " + i);		
			//System.out.print("    start: " + start);
			
			double bestValue = solution.getObjectiveFunctionValue();
			boolean isImproved = false;
			
			
			for (int j = 0; j < landmark; j++) {
				
				performAdjacentSwap(solution, (start + j)%landmark);
				
				double tempValue = solution.getObjectiveFunctionValue();
				// if it strictly improved, accept the swap, reset the start point
				if (tempValue < bestValue) {
					bestValue = tempValue;
					isImproved = true;
					//System.out.print("    improve on index" + (start + j)%landmark);
					start = (start + j +1)%landmark;
					break;
				}
				else {
					performAdjacentSwap(solution, (start + j)%landmark);
				}
			}
			
			// print result
			/*System.out.print("    result: ");
			int rep[] = solution.getSolutionRepresentation().getSolutionRepresentation();
			for(int j = 0; j<rep.length; j++) {
				System.out.print(rep[j] + "  ");
			}
			System.out.print("    value: " + solution.getObjectiveFunctionValue() + "\n");*/
			
			// if there is no improvement, end loop
			if (isImproved == false) {
				//System.out.println("No improvement");
				break;
			}
			
		}
		return solution.getObjectiveFunctionValue();
	}

	@Override
	public boolean isCrossover() {

		// TODO
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {

		// TODO
		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {

		// TODO
		return true;
	}
}
