package com.aim.project.sdsstp.hyperheuristics;


import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

/**
 * This class is used for testing each low-level heuristic
 * @author Lekang Jiang
 *
 */
public class HeuristicTest extends HyperHeuristic {

	
	public HeuristicTest(long seed) {
		
		super(seed);
	}

	@Override
	protected void solve(ProblemDomain problem) {

		problem.setMemorySize(3);
		
		problem.initialiseSolution(0);
		//problem.initialiseSolution(1);
		//problem.initialiseSolution(2);
		double current = problem.getFunctionValue(0);
		
		problem.setIntensityOfMutation(0.5);
		problem.setDepthOfSearch(0.5);
		
		int h = 5;
		
		boolean accept;
		int iteration = 1;
		System.out.println("Iteration\tf(s)\tf(s')\tAccept");
		
		while(!hasTimeExpired() ) {
			
			double candidate;

			//candidate = problem.applyHeuristic(h, 0, 1);
			problem.initialiseSolution(2);
			candidate = problem.applyHeuristic(h, 0, 2 ,1);
			
			System.out.print("iteration: " + iteration+"\tcurrentvalue:" + current + "\tcandidatevalue:" + candidate + "\t");
			
			accept = candidate <= current;
			if(accept) {
				
				problem.copySolution(1, 0);
				current = candidate;
				System.out.print("accept");
			}
			System.out.println("");
			iteration++;
			
		}
	}

	@Override
	public String toString() {

		return "HeuristicTest";
	}

}
