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
public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

	private final Random random;
	
	public Reinsertion(Random random) {

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
			int selectIndex = random.nextInt(landmark);
			int targetIndex = random.nextInt(landmark);
			while (selectIndex == targetIndex) {
				targetIndex = random.nextInt(landmark);
			}			
			//System.out.print("    select: " + selectIndex +"    target: " + targetIndex);
			
			performReinsertion(solution, selectIndex, targetIndex);
			
			// print result
			/*System.out.print("    result: ");
			int rep[] = solution.getSolutionRepresentation().getSolutionRepresentation();
			for(int j = 0; j<rep.length; j++) {
				System.out.print(rep[j] + "  ");
			}
			System.out.print("    value" + solution.getObjectiveFunctionValue() + "\n");*/
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
