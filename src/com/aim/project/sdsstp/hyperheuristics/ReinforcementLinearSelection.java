package com.aim.project.sdsstp.hyperheuristics;

import java.util.HashSet;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

/**
 * This hyper-heuristic is based on reinforcement learning, linear selection and Roulette Wheel Selection. 
 * There are 7 low-level heuristics, and will be given a score start from 0. 
 * The heuristics will be ranking based on the scores, so that the probabilities of being chosen is 
 * 1/28, 2/28, ..., 7/28 respectively. 
 * Roulette Wheel Selection is used to select which heuristic is applied. 
 * If the selected heuristic is improved or equal, then add 1 score; otherwise reduce 1 score. 
 * The method should accept any improved or equal solutions. 
 * 
 * @author Lekang Jiang
 *
 */
public class ReinforcementLinearSelection extends HyperHeuristic {

	int[] score = new int[7];
	int[] heuristic_rank = new int[] {0,1,2,3,4,5,6};
	double[] probability = new double[]{1,2,3,4,5,6,7};
	
	public ReinforcementLinearSelection(long seed) {
		super(seed);
	}

	@Override
	protected void solve(ProblemDomain problem) {

		problem.setMemorySize(3);
		
		problem.initialiseSolution(0);

		double current = problem.getFunctionValue(0);
		
		problem.setIntensityOfMutation(0.2);
		problem.setDepthOfSearch(0.2);
		
		int h = 0;
		boolean accept;
		
		int[] xos = problem.getHeuristicsOfType(HeuristicType.CROSSOVER);
		HashSet<Integer> set = new HashSet<Integer>();
		for(int i : xos) {
			set.add(i);
		}
		
		System.out.println("Start");

		while(!hasTimeExpired() ) {
			/*for(int i = 0; i < 7; i++) {
				System.out.println("score:" + score[i]);
			}*/
			
			rankHeuristic();
			/*for(int i = 0; i < 7; i++) {
				System.out.println("rank:" + heuristic_rank[i]);
			}*/
			
			int h1 = rng.nextInt(28);		
			h =  heuristic_rank[LinearSelection(h1)];
			//System.out.println("number" + h1 + "select:" + h);
			
			double candidate;	
			if(set.contains(h)) {
				
				problem.initialiseSolution(2);
				candidate = problem.applyHeuristic(h, 0, 2, 1);
				
			} else {
				
				candidate = problem.applyHeuristic(h, 0, 1);
			
			}
			
			//System.out.println(counter + "\t" + current + "\t" + candidate + "\t");
			accept = candidate <= current;
			if(accept) {
				score[h]++;
				problem.copySolution(1, 0);
				current = candidate;
			}
			else {
				score[h]--;
			}
			
		}
	}

	/**
	 * This method is used to rank heuristic based on scores. 
	 * The heuristics will be stored in 'heuristic_rank' array. 
	 * The probability of choosing the first one is 1/28, and the last one is 7/28. 
	 */
	private void rankHeuristic() {	
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6-i; j++) {
				if(score[heuristic_rank[j]]>score[heuristic_rank[j+1]]) {
					int a = heuristic_rank[j]; 
					heuristic_rank[j] = heuristic_rank[j+1];
					heuristic_rank[j + 1] = a;
					//System.out.print(a);
				}
			}
		}
		
	}

	/**
	 * 
	 * @param a random integer for Roulette Wheel Selection
	 * @return the index of selected heuristic in 'heuristic_rank' array
	 */
	private int LinearSelection(int h1) {
		int result = 1;
		double total = 1;
		while(total <= h1) {
			total += probability[result];
			result++;
		}
		return result-1;
	}
	
	@Override
	public String toString() {

		return "Reinforcement Tournament";
	}
}
