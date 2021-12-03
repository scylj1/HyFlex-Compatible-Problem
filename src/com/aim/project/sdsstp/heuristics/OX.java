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
public class OX implements XOHeuristicInterface {
	
	private final Random random;
	
	private ObjectiveFunctionInterface f;

	public OX(Random random) {
		
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
		int cut1, cut2;
		for(int i = 0; i < times; i++) {
			//System.out.print("times: " + i);
			
			// get 2 different cut points, and there not the first and last location at the same time
			int diff;
			do {
				cut1 = random.nextInt(landmark);
				cut2 = random.nextInt(landmark);
				if(cut1 > cut2) {
					int temp = cut1;
					cut1 = cut2;
					cut2 = temp;
				}
				diff = cut2-cut1;
			}while(diff == 0 || diff == landmark - 1 || diff == landmark - 2);
			
			
			//System.out.println("    cut1: " + cut1+"    cut2: " + cut2);
			
			// get the remain part between 2 cut points
			int[] remain1 = new int[cut2-cut1+1];
			int[] remain2 = new int[cut2-cut1+1];		
			for(int j = 0; j < cut2-cut1+1; j++) {
				remain1[j] = p1Rep[j+cut1];
				remain2[j] = p2Rep[j+cut1];
				//System.out.print(remain1[j]);
			}
		
			int[] others1 = new int[landmark-cut2+cut1-1];
			int[] others2 = new int[landmark-cut2+cut1-1];
			int index = 0;
			for(int j = 0; j < landmark; j++) {
				int flag = 0;
				for(int p = 0; p<cut2-cut1+1;p++) {
					if(p2Rep[(j+cut2+1)%landmark] == remain1[p]) {
						flag = 1;
						break;
					}
				}
				if(flag == 0) {
					others1[index] = p2Rep[(j+cut2+1)%landmark];
					index++;
				}
			}
			index = 0;
			for(int j = 0; j < landmark; j++) {
				int flag = 0;
				for(int p = 0; p<cut2-cut1+1;p++) {
					if(p1Rep[(j+cut2+1)%landmark] == remain2[p]) {
						flag = 1;
						break;
					}
				}
				if(flag == 0) {
					others2[index] = p1Rep[(j+cut2+1)%landmark];
					index++;
				}
			}
			// get 2 children
			int[] c1Rep = new int[landmark];
			int[] c2Rep = new int[landmark];
						
			for (int j = 0; j < landmark; j++) {
				if (j>=cut1 && j<=cut2) {
					c1Rep[j] = p1Rep[j];
					c2Rep[j] = p2Rep[j];
				}
				else if(j>cut2) {
					c1Rep[j] = others1[j-cut2-1];
					c2Rep[j] = others2[j-cut2-1];
				}
				else {
					c1Rep[j] = others1[j-cut2+landmark-1];
					c2Rep[j] = others2[j-cut2+landmark-1];
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
		/*System.out.println("result:");
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
}
