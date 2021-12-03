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
public class InversionMutation extends HeuristicOperators implements HeuristicInterface {
	
	private final Random random;
	
	public InversionMutation(Random random) {
	
		super();
		
		this.random = random;
	}

	@Override
	public double apply(SDSSTPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

		// TODO
		int times = (int)(Math.floor(intensityOfMutation/0.2) + 1);
		int landmark = solution.getNumberOfLandmarks();
		for(int i = 0; i < times; i++) {
			//System.out.print("times: " + i);
			
			// obtain 2 different random index
			int index1 = random.nextInt(landmark);
			int index2 = random.nextInt(landmark);
			while (index1 == index2) {
				index2 = random.nextInt(landmark);
			}
			// let index1 smaller than index2
			if (index1 > index2) {
				int temp = index2;
				index2 = index1;
				index1 = temp;
			}
			//System.out.print("    index1: " + index1 +"    index2: " + index2);
			
			performInversionMutaion(solution, index1, index2);
			
			// print result
			/*System.out.print("    result: ");
			int rep[] = solution.getSolutionRepresentation().getSolutionRepresentation();
			for(int j = 0; j<rep.length; j++) {
				System.out.print(rep[j] + "  ");
			}
			System.out.print("value: " + solution.getObjectiveFunctionValue()+ "\n");*/
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
		return true;
	}

	@Override
	public boolean usesDepthOfSearch() {

		// TODO
		return false;
	}

}
