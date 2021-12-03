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
public class DavissHillClimbing extends HeuristicOperators implements HeuristicInterface {
	
	private final Random random;
	
	public DavissHillClimbing(Random random) {
	
		this.random = random;
	}

	@Override
	public double apply(SDSSTPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

		// TODO
		int times = (int)(Math.floor(depthOfSearch/0.2)+1);
		int landmarks = solution.getNumberOfLandmarks();
		for(int i = 0; i < times; i++) {
			//System.out.print("times: " + i);
			
			// obtain a random sequence
			
			int[] orderedArray = new int[landmarks];
			int[] randomArray = new int[landmarks];			
			for(int j = 0; j < landmarks; j++) {
				orderedArray[j] = j;
			}		
			for(int j = 0; j < landmarks-1; j++) {
				int index = random.nextInt(landmarks-j);
				randomArray[j] = orderedArray[index];
				for(int p = 0; p < landmarks - 1; p++) {
					if (p >= index) {
						orderedArray[p] = orderedArray[p+1];
					}
				}
			}
			randomArray[landmarks-1] = orderedArray[0];
			
			// print random array
			/*System.out.println("    random array: ");
			for(int p = 0; p<randomArray.length; p++) {
				System.out.print(randomArray[p] + "  ");
			}
			System.out.println();*/
			
			double bestValue = solution.getObjectiveFunctionValue();
			for (int j = 0; j < landmarks; j++) {
				
				performAdjacentSwap(solution, randomArray[j]);
				
				double tempValue = solution.getObjectiveFunctionValue();
				
				// improve or equal then keep the swap
				if (tempValue <= bestValue) {
					bestValue = tempValue;
					//System.out.println("improve on: " + randomArray[j]);
				}
				else {
					performAdjacentSwap(solution, randomArray[j]);
				}
				
				// print each perturbation
				/*System.out.print("each time perturbation: ");
				int rep[] = solution.getSolutionRepresentation().getSolutionRepresentation();
				for(int p = 0; p<rep.length; p++) {
					System.out.print(rep[p] + "  ");
				}
				System.out.print("    value: " + solution.getObjectiveFunctionValue() + "\n");*/
			}
			
			// print final result
			/*System.out.print("    result: ");
			int rep[] = solution.getSolutionRepresentation().getSolutionRepresentation();
			for(int j = 0; j<rep.length; j++) {
				System.out.print(rep[j] + "  ");
			}
			System.out.print("    value: " + solution.getObjectiveFunctionValue() + "\n");*/
			
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
