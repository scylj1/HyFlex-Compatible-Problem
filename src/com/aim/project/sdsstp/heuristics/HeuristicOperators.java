package com.aim.project.sdsstp.heuristics;


import com.aim.project.sdsstp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.sdsstp.interfaces.SDSSTPSolutionInterface;

/**
 * 
 * @author Warren G. Jackson
 * @since 26/03/2021
 * 
 */
public class HeuristicOperators {

	/*
	 *  PLEASE NOTE THAT USE OF THIS CLASS IS OPTIONAL BUT WE
	 *  STRONGLY RECOMMEND THAT YOU IMPLEMENT ANY COMMON FUNCTIONALITY
	 *  IN HERE TO HELP YOU WITH IMPLEMENTING THE HEURISTICS.
	 *
	 *  (HINT: It might be worthwhile to have a method that performs adjacent swaps in here :)) 
	 */

	protected static final boolean ENABLE_CHECKING = false;  // private originally

	private ObjectiveFunctionInterface obj;

	public HeuristicOperators() {

	}

	public void setObjectiveFunction(ObjectiveFunctionInterface f) {

		this.obj = f;
	}

	// private original
	protected void performAdjacentSwap(SDSSTPSolutionInterface solution, int var_a) {

		// OPTIONAL: this might be useful to implement and reuse in your heuristics!

		double currentValue = obj.getObjectiveFunctionValue(solution.getSolutionRepresentation());
		
		// perform adjacent swap with delta evaluation
		int[] rep = solution.getSolutionRepresentation().getSolutionRepresentation();
		if(var_a == rep.length - 1) {
			int temp = rep[0];
			rep[0] = rep[var_a];
			rep[var_a] = temp;
			currentValue += obj.getTravelTimeFromLandmarkToTourOffice(rep[var_a]) + obj.getTravelTimeFromTourOfficeToLandmark(rep[0]) 
							+ obj.getTravelTime(rep[0], rep[1]) + obj.getTravelTime(rep[var_a-1], rep[var_a])
							- obj.getTravelTimeFromTourOfficeToLandmark(rep[var_a]) - obj.getTravelTimeFromLandmarkToTourOffice(rep[0])
							- obj.getTravelTime(rep[var_a], rep[1]) - obj.getTravelTime(rep[var_a-1], rep[0]);
		}
		else {
			int temp = rep[var_a];
			rep[var_a] = rep[var_a+1];
			rep[var_a+1] = temp;
			if(var_a == 0) {
				currentValue += obj.getTravelTimeFromTourOfficeToLandmark(rep[0]) 
				+ obj.getTravelTime(rep[0], rep[1]) + obj.getTravelTime(rep[1], rep[2]) 
				- obj.getTravelTimeFromTourOfficeToLandmark(rep[1])
				- obj.getTravelTime(rep[1], rep[0]) - obj.getTravelTime(rep[0], rep[2]) ;
			}
			else if(var_a == rep.length - 2) {
				currentValue += obj.getTravelTimeFromLandmarkToTourOffice(rep[var_a+1])
				+ obj.getTravelTime(rep[var_a-1], rep[var_a]) + obj.getTravelTime(rep[var_a], rep[var_a+1])
				- obj.getTravelTimeFromLandmarkToTourOffice(rep[var_a])
				- obj.getTravelTime(rep[var_a+1], rep[var_a]) - obj.getTravelTime(rep[var_a-1], rep[var_a+1]);
			}
			else {
				currentValue += obj.getTravelTime(rep[var_a-1], rep[var_a]) + obj.getTravelTime(rep[var_a], rep[var_a+1]) + obj.getTravelTime(rep[var_a+1], rep[var_a+2])
						- obj.getTravelTime(rep[var_a-1], rep[var_a+1]) - obj.getTravelTime(rep[var_a+1], rep[var_a])- obj.getTravelTime(rep[var_a], rep[var_a+2]);
			}
		}
		
		// standard evaluation
		//double currentValue1 = obj.getObjectiveFunctionValue(solution.getSolutionRepresentation());
		//System.out.println("    standard value: " + currentValue1);
		
		solution.setObjectiveFunctionValue(currentValue);
		
	}
	
	protected void performInversionMutaion(SDSSTPSolutionInterface solution, int firstIndex, int secondIndex) {

		double currentValue = obj.getObjectiveFunctionValue(solution.getSolutionRepresentation());
		int[] rep = solution.getSolutionRepresentation().getSolutionRepresentation();
		int[] newRep = new int[rep.length];
		
		for(int i = 0; i < rep.length; i++) {
			if(i >= firstIndex && i <= secondIndex) {
				newRep[i] = rep[secondIndex + firstIndex - i];
			}
			else {
				newRep[i] = rep[i];
			}
		}
		
		//delta evaluation
		for(int i = firstIndex; i<secondIndex; i++) {
			currentValue += obj.getTravelTime(newRep[i], newRep[i+1]) - obj.getTravelTime(rep[i], rep[i+1]);
		}
		if(firstIndex == 0) {
			currentValue += obj.getTravelTimeFromTourOfficeToLandmark(newRep[0]) 
						    - obj.getTravelTimeFromTourOfficeToLandmark(rep[0]);
		}
		if(firstIndex > 0) {
			currentValue += obj.getTravelTime(newRep[firstIndex-1], newRep[firstIndex]) - obj.getTravelTime(rep[firstIndex-1], rep[firstIndex]);
		}
		if(secondIndex == rep.length - 1) {
			currentValue += obj.getTravelTimeFromLandmarkToTourOffice(newRep[secondIndex]) 
						    - obj.getTravelTimeFromLandmarkToTourOffice(rep[secondIndex]);
			
		}
		if(secondIndex < rep.length -1) {
			currentValue += obj.getTravelTime(newRep[secondIndex], newRep[secondIndex+1]) - obj.getTravelTime(rep[secondIndex], rep[secondIndex+1]);
		}
		
		solution.getSolutionRepresentation().setSolutionRepresentation(newRep);
		
		// standard evaluation
		//double currentValue1 = obj.getObjectiveFunctionValue(solution.getSolutionRepresentation());
		//System.out.println("    standard value: " + currentValue1);
		
		solution.setObjectiveFunctionValue(currentValue);
		
	}
	
	protected void performReinsertion(SDSSTPSolutionInterface solution, int selectIndex, int targetIndex) {

		double currentValue = obj.getObjectiveFunctionValue(solution.getSolutionRepresentation());
		int[] rep = solution.getSolutionRepresentation().getSolutionRepresentation();
		int landmark = rep.length;
		int[] newRep = new int[landmark];

		if(selectIndex > targetIndex) {
			for(int i = 0; i < landmark; i++) {
				if(i > targetIndex && i <= selectIndex) {
					newRep[i] = rep[i - 1];
				}				
				else if (i == targetIndex) {
					newRep[i] = rep[selectIndex];
				}
				else{
					newRep[i] = rep[i];
				}
			}
			
			// delta evaluation
			if(targetIndex == 0) {
				currentValue += obj.getTravelTimeFromTourOfficeToLandmark(newRep[0]) 
					    - obj.getTravelTimeFromTourOfficeToLandmark(rep[0]);
				if (selectIndex == landmark -1) {
					currentValue += obj.getTravelTimeFromLandmarkToTourOffice(newRep[landmark-1]) 
						    - obj.getTravelTimeFromLandmarkToTourOffice(rep[landmark-1]);
					currentValue += obj.getTravelTime(newRep[targetIndex], newRep[targetIndex+1]) 
							- obj.getTravelTime(rep[selectIndex-1], rep[selectIndex]);
				}
				else {
					currentValue += obj.getTravelTime(newRep[targetIndex], newRep[targetIndex + 1]) 
							+ obj.getTravelTime(newRep[selectIndex], newRep[selectIndex + 1])
							- obj.getTravelTime(rep[selectIndex-1], rep[selectIndex])
							- obj.getTravelTime(rep[selectIndex], rep[selectIndex+1]);
				}
			}
			else {
				if (selectIndex == landmark - 1) {
					currentValue += obj.getTravelTimeFromLandmarkToTourOffice(newRep[landmark-1]) 
						    - obj.getTravelTimeFromLandmarkToTourOffice(rep[landmark-1]);
					currentValue += obj.getTravelTime(newRep[targetIndex-1], newRep[targetIndex]) 
							+ obj.getTravelTime(newRep[targetIndex], newRep[targetIndex + 1])
							- obj.getTravelTime(rep[selectIndex-1], rep[selectIndex])
							- obj.getTravelTime(rep[targetIndex-1], rep[targetIndex]);
				}
				else if (selectIndex == targetIndex + 1) {
					currentValue += obj.getTravelTime(newRep[targetIndex-1], newRep[targetIndex])  
							+ obj.getTravelTime(newRep[selectIndex-1], newRep[selectIndex]) 
							+ obj.getTravelTime(newRep[selectIndex], newRep[selectIndex + 1]) 
							- obj.getTravelTime(rep[selectIndex], rep[selectIndex+1])
							- obj.getTravelTime(rep[selectIndex-1], rep[selectIndex])
							- obj.getTravelTime(rep[targetIndex-1], rep[targetIndex]);
				}
				else {
					currentValue += obj.getTravelTime(newRep[targetIndex-1], newRep[targetIndex]) 
							+ obj.getTravelTime(newRep[targetIndex], newRep[targetIndex + 1]) 
							+ obj.getTravelTime(newRep[selectIndex], newRep[selectIndex + 1]) 
							- obj.getTravelTime(rep[selectIndex], rep[selectIndex+1])
							- obj.getTravelTime(rep[selectIndex-1], rep[selectIndex])
							- obj.getTravelTime(rep[targetIndex-1], rep[targetIndex]);
				}
			}
		}
		else {
			for(int i = 0; i < landmark; i++) {
				if(i < targetIndex && i >= selectIndex) {
					newRep[i] = rep[i+1];
				}				
				else if (i == targetIndex) {
					newRep[i] = rep[selectIndex];
				}
				else{
					newRep[i] = rep[i];
				}
			}
			
			// delta evaluation
			if(selectIndex == 0) {
				currentValue += obj.getTravelTimeFromTourOfficeToLandmark(newRep[0]) 
					    - obj.getTravelTimeFromTourOfficeToLandmark(rep[0]);
				if (targetIndex == landmark - 1) {
					currentValue += obj.getTravelTimeFromLandmarkToTourOffice(newRep[landmark-1]) 
						    - obj.getTravelTimeFromLandmarkToTourOffice(rep[landmark-1]);
					currentValue += obj.getTravelTime(newRep[targetIndex-1], newRep[targetIndex]) 
							- obj.getTravelTime(rep[selectIndex], rep[selectIndex+1]);
				}
				else {
					currentValue += obj.getTravelTime(newRep[targetIndex-1], newRep[targetIndex]) 
							+ obj.getTravelTime(newRep[targetIndex], newRep[targetIndex + 1]) 
							- obj.getTravelTime(rep[selectIndex], rep[selectIndex+1])
							- obj.getTravelTime(rep[targetIndex], rep[targetIndex+1]);
				}
			}
			else {
				if (targetIndex == landmark - 1) {
					currentValue += obj.getTravelTimeFromLandmarkToTourOffice(newRep[landmark-1]) 
						    - obj.getTravelTimeFromLandmarkToTourOffice(rep[landmark-1]);
					currentValue += obj.getTravelTime(newRep[targetIndex-1], newRep[targetIndex]) 
							+ obj.getTravelTime(newRep[selectIndex-1], newRep[selectIndex]) 
							- obj.getTravelTime(rep[selectIndex], rep[selectIndex+1])
							- obj.getTravelTime(rep[selectIndex-1], rep[selectIndex]);
				}
				else if(targetIndex == selectIndex + 1) {
					currentValue += obj.getTravelTime(newRep[targetIndex-1], newRep[targetIndex]) 
							+ obj.getTravelTime(newRep[targetIndex], newRep[targetIndex + 1]) 
							+ obj.getTravelTime(newRep[selectIndex-1], newRep[selectIndex]) 
							- obj.getTravelTime(rep[selectIndex], rep[selectIndex+1])
							- obj.getTravelTime(rep[selectIndex-1], rep[selectIndex])
							- obj.getTravelTime(rep[targetIndex], rep[targetIndex+1]);
				}
				else {
					currentValue += obj.getTravelTime(newRep[targetIndex-1], newRep[targetIndex]) 
							+ obj.getTravelTime(newRep[targetIndex], newRep[targetIndex + 1]) 
							+ obj.getTravelTime(newRep[selectIndex-1], newRep[selectIndex]) 
							- obj.getTravelTime(rep[selectIndex], rep[selectIndex+1])
							- obj.getTravelTime(rep[selectIndex-1], rep[selectIndex])
							- obj.getTravelTime(rep[targetIndex], rep[targetIndex+1]);
				}
			}
		}
		
		
		
		
		solution.getSolutionRepresentation().setSolutionRepresentation(newRep);
		
		// standard evaluation
		//double currentValue1 = obj.getObjectiveFunctionValue(solution.getSolutionRepresentation());
		//System.out.println("    standard value: " + currentValue1);
		
		solution.setObjectiveFunctionValue(currentValue);
		
	}
	
	
}
