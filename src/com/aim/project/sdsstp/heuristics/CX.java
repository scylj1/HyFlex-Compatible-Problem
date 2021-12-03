package com.aim.project.sdsstp.heuristics;

import java.util.Random;

import com.aim.project.sdsstp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.sdsstp.interfaces.SDSSTPSolutionInterface;
import com.aim.project.sdsstp.interfaces.XOHeuristicInterface;

/**
 *
 * @author Warren G. Jackson
 * @since 26/03/2021
 *
 * Methods needing to be implemented:
 * - public double apply(SDSSTPSolutionInterface solution, double depthOfSearch, double intensityOfMutation)
 * - public double apply(SDSSTPSolutionInterface p1, SDSSTPSolutionInterface p2, SDSSTPSolutionInterface c, double depthOfSearch, double intensityOfMutation)
 * - public boolean isCrossover()
 * - public boolean usesIntensityOfMutation()
 * - public boolean usesDepthOfSearch()
 */
public class CX implements XOHeuristicInterface {

	private final Random random;

	private ObjectiveFunctionInterface f;

	public CX(Random random) {

		this.random = random;
	}

	@Override
	public double apply(SDSSTPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

		// TODO
		return solution.getObjectiveFunctionValue();
	}

	@Override
	public double apply(SDSSTPSolutionInterface p1, SDSSTPSolutionInterface p2,
			SDSSTPSolutionInterface c, double depthOfSearch, double intensityOfMutation) {

		// TODO
		int times = (int)(Math.floor(intensityOfMutation/0.2)+1);
		// get 2 parent route
		int[] p1Rep = p1.getSolutionRepresentation().clone().getSolutionRepresentation();
		int[] p2Rep = p2.getSolutionRepresentation().clone().getSolutionRepresentation();
		int landmark = p1Rep.length;
		int[] c1Rep = new int[landmark];
		int[] c2Rep = new int[landmark];
		
		for(int i = 0; i < times; i++) {
			//System.out.print("times: " + i);
			
			// initial with -1
			initialArray(c1Rep);
			initialArray(c2Rep);
			// get a random start
			int start = random.nextInt(landmark);
			//System.out.print("    start: " + start);
			// get children
			int index = start;
			for (int j = 0; j<landmark; j++) {
				c1Rep[index] = p1Rep[index];
				//System.out.println("c1repindex" + c1Rep[index]);
				for(int p = 0; p<landmark;p++) {
					//System.out.println(p1Rep[p]+"   "+p);
					if(p2Rep[index] == p1Rep[p]) {
						index = p;
						break;
						
					}
				}			
				if(index == start) {
					break;
				}
			}
			
			for (int j = 0; j<landmark; j++) {
				if(c1Rep[j]==-1) {
					c1Rep[j] = p2Rep[j];
					c2Rep[j] = p1Rep[j];
				}
			}
			for (int j = 0; j<landmark; j++) {
				if(c2Rep[j]==-1) {
					c2Rep[j] = p2Rep[j];
				}
			}
			
			// set the parents to the children we just obtained, for future crossover
			for(int p = 0; p<landmark; p++) {
				p1Rep[p] = c1Rep[p];
				p2Rep[p] = c2Rep[p];
			}
			
			// print current child
			/*System.out.print("    child1: ");
			for(int p = 0; p<p1Rep.length; p++) {
				System.out.print(p1Rep[p] + "  ");
			}
			System.out.print("    child2: ");
			for(int p = 0; p<p1Rep.length; p++) {
				System.out.print(p2Rep[p] + "  ");
			}
			System.out.println("");*/
		}
		
		// get 1 children randomly
		int[] finalRep;
		if (random.nextInt(2)==1) {
			finalRep = p1Rep;
		}
		else {
			finalRep = p2Rep;
		}
		
		// print final result
		/*System.out.print("result: ");
		for(int p = 0; p<p1Rep.length; p++) {
			System.out.print(finalRep[p] + "  ");
		}
		System.out.println();*/
		
		c.getSolutionRepresentation().setSolutionRepresentation(finalRep);
		double currentValue = f.getObjectiveFunctionValue(c.getSolutionRepresentation());
		c.setObjectiveFunctionValue(currentValue);
		return c.getObjectiveFunctionValue();
	}

	@Override
	public boolean isCrossover() {

		// TODO
		return true;
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

	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {

		this.f = f;
	}
	
	// initialize an array with -1
		private void initialArray(int[] array) {
			for(int i = 0; i < array.length; i++) {
				array[i] = -1;
			}
		}
}
