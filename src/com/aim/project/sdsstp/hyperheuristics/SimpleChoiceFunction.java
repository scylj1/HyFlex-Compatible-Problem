package com.aim.project.sdsstp.hyperheuristics;


import java.util.HashSet;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

/**
 * This hyper-heuristic is based on choice function. 
 * f1 is simplified as delta value of solution after heuristic applied, divide the time in millisecond it taken
 * f2 is simplified as last time when these 2 heuristics are applied, the change of value divide the the time in millisecond it taken
 * f3 is simplified as the time in microseconds since last time the heuristic is applied
 * accept any changes of solutions
 * update phi after heuristic applied. Change to 0.99 when improved, decrease 0.01 with the minimum of 0.01 otherwise
 * @author Lekang Jiang
 *
 */
public class SimpleChoiceFunction extends HyperHeuristic {

		double[] score = new double[7];
		HeuristicData[] data = new HeuristicData[7];
		double[][] F2 = new double[7][7];
		
		double phi;
		int last_h;
		
		public SimpleChoiceFunction(long seed) {
			super(seed);
			for(int i = 0; i<7; i++) {
				data[i] = new HeuristicData();
			}
			phi = 0.99;
			last_h = 0;
		}

		@Override
		protected void solve(ProblemDomain problem) {

			problem.setMemorySize(3);
			
			problem.initialiseSolution(0);
			double current = problem.getBestSolutionValue();
			
			problem.setIntensityOfMutation(0.5);
			problem.setDepthOfSearch(0.2);
			
			int h = 0;
			int counter = 1;
			int[] xos = problem.getHeuristicsOfType(HeuristicType.CROSSOVER);
			HashSet<Integer> set = new HashSet<Integer>();
			for(int i : xos) {
				set.add(i);
			}
			
			System.out.println("Start");

			while(!hasTimeExpired() ) {
				long currentTime = System.nanoTime();
				for(int i = 0; i < 7; i++) {
					score[i] = calculateScore(last_h, i, currentTime);
					//System.out.println("  score" + i + " : " + score[i]);
				}
				
				last_h = h;
				h = rng.nextInt(7);			
				h =  selectHeuristic(h);
				
				//System.out.println("select: " + h);
				
				double candidate;
				long duration;
				
				if(set.contains(h)) {
					
					problem.initialiseSolution(2);
					current = problem.getBestSolutionValue();
					long beforeTime = System.nanoTime();
					candidate = problem.applyHeuristic(h, 0, 2, 1);
					long afterTime = System.nanoTime();
					duration = afterTime-beforeTime;
					
				} else {
					long beforeTime = System.nanoTime();
					candidate = problem.applyHeuristic(h, 0, 1);
					long afterTime = System.nanoTime();
					duration = afterTime-beforeTime;
				
				}
				
				//System.out.println("duration: " + duration);
				
				//System.out.println(counter + "\t" + current + "\t" + candidate + "\t");
				updateHeuristicData(h, System.nanoTime(), duration, current, candidate);
				if(counter > 1) {
					updataF2(last_h, h, duration, current, candidate);
				}
				//System.out.println("delta: " + data[h].getF_delta() + "    duration: " + data[h].getPreviousApplicationDuration() + "    lasttime: " + data[h].getTimeLastApplied());
				
				if(candidate < current) {
					updatephi(1);
				}		
				if (candidate >= current){
					updatephi(0);

				}
				
				problem.copySolution(1, 0);
				current = candidate;	
				counter++;
				
			}
		}

	/**
	 * The method is used to update value for f2
	 * @param last heuristic
	 * @param current heuristic
	 * @param time taken
	 * @param current value
	 * @param candidate value
	 */
	private void updataF2(int last_h2, int h, long duration, double current, double candidate) {
			F2[last_h2][h] = (current-candidate)/(duration/1e6);
		}

	/**
	 * This method is used to update heuristic data for f1 and f3
	 * @param current heuristic
	 * @param currentTime
	 * @param takenTime
	 * @param current value
	 * @param candidate value
	 */
	private void updateHeuristicData(int h, long currentTime, long takenTime, double current, double candidate) {
			HeuristicData d = data[h];
			d.setF_delta(current-candidate);
			d.setTimeLastApplied(currentTime);
			d.setPreviousApplicationDuration(takenTime);
		}

	/**
	 * This method is used to calculate the score for heuristic selection
	 * @param last heuristic
	 * @param current heuristic
	 * @param currentTime
	 * @return score
	 */
	private double calculateScore(int last_h, int h, long currentTime) {
			HeuristicData d = data[h];
			double f1 = phi * d.getF_delta()/(d.getPreviousApplicationDuration()/1e6);
			double f2 = phi * F2[last_h][h];
			double f3 = (1-phi) * (currentTime-d.getTimeLastApplied())/1e3;
			double score = f1+f2+f3;
			//System.out.println("f1: " + f1 + "  f2: " + f2 + "  f3: " + f3);
			return score;
		}

	/**
	 * This method is used to update phi after a heuristic is applied
	 * @param if the sulotion is improved
	 */
	private void updatephi(int i) {
			if(i == 1) {
				phi = 0.99;
			}
			else {
				if(phi-0.01>0.01) {
					phi = phi - 0.01;
				}
				else {
					phi = 0.01;
				}
			}
			//System.out.println("phi: " + phi);
		}

	/**
	 * Select the heuristic with the highest score
	 * 
	 */
	private int selectHeuristic(int h) {
		
			for(int i = 0; i<score.length; i++) {
				if (score[i]>score[h]) {
					h = i;
				}
			}
			
			return h;
		}

	
	@Override
	public String toString() {
		return "Simple Choice Function";
	};
		

}
