package com.aim.project.sdsstp.instance;

import java.util.ArrayList;
import java.util.Random;

import com.aim.project.sdsstp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.sdsstp.interfaces.SDSSTPInstanceInterface;
import com.aim.project.sdsstp.interfaces.SDSSTPSolutionInterface;
import com.aim.project.sdsstp.solution.SDSSTPSolution;
import com.aim.project.sdsstp.solution.SolutionRepresentation;

/**
 * 
 * @author Warren G. Jackson
 * @since 26/03/2021
 * 
 * Methods needing to be implemented:
 * - public SDSSTPSolution createSolution(InitialisationMode mode)
 */
public class SDSSTPInstance implements SDSSTPInstanceInterface {

	private final String strInstanceName;

	private final int iNumberOfLandmarks;

	private final SDSSTPLocation oTourOffice;

	private final SDSSTPLocation[] aoLandmarks;

	private final Random oRandom;

	private final ObjectiveFunctionInterface oObjectiveFunction;

	public SDSSTPInstance(String strInstanceName, int iNumberOfLandmarks,
			com.aim.project.sdsstp.instance.SDSSTPLocation oTourOffice,
			com.aim.project.sdsstp.instance.SDSSTPLocation[] aoLandmarks, Random oRandom,
			ObjectiveFunctionInterface f) {

		this.strInstanceName = strInstanceName;
		this.iNumberOfLandmarks = iNumberOfLandmarks;
		this.oTourOffice = oTourOffice;
		this.aoLandmarks = aoLandmarks;
		this.oRandom = oRandom;
		this.oObjectiveFunction = f;
	}

	@Override
	public SDSSTPSolution createSolution(InitialisationMode mode) {

		// TODO
		if (mode == InitialisationMode.RANDOM) {
			int[] array = new int[iNumberOfLandmarks];
			for (int i = 0; i < iNumberOfLandmarks; i++) {
				array[i] = i;
			}
			
			int[] representation = new int[iNumberOfLandmarks];			
			for (int i = 0; i < iNumberOfLandmarks-1; i++) {
				int landmark = oRandom.nextInt(iNumberOfLandmarks-i);
				representation[i] = array[landmark];
				for(int j = 0; j < array.length - 1; j++) {
					if (j >= landmark) {
						array[j] = array[j+1];
					}
				}
			}
			representation[iNumberOfLandmarks-1] = array[0];
			
			/*System.out.print("Initialise solution: ");
			for (int j = 0; j < iNumberOfLandmarks; j++) {
				System.out.print(representation[j]+"    ");
			}
			System.out.println();*/
			
			SolutionRepresentation solution = new SolutionRepresentation(representation);
			return new SDSSTPSolution(solution, oObjectiveFunction.getObjectiveFunctionValue(solution), iNumberOfLandmarks);
		}
		else if (mode == InitialisationMode.CONSTRUCTIVE) {
			int[] representation = new int[iNumberOfLandmarks];
			int minTravelFromOffice = Integer.MAX_VALUE;
			representation[0] = 0;
			for (int i = 0; i < iNumberOfLandmarks; i++) {
				if (oObjectiveFunction.getTravelTimeFromTourOfficeToLandmark(i) < minTravelFromOffice) {
					minTravelFromOffice = oObjectiveFunction.getTravelTimeFromTourOfficeToLandmark(i);
					representation[0] = i;
				}
				else if (oObjectiveFunction.getTravelTimeFromTourOfficeToLandmark(i) == minTravelFromOffice) {
					if (Math.random() < 0.5) {
						minTravelFromOffice = oObjectiveFunction.getTravelTimeFromTourOfficeToLandmark(i);
						representation[0] = i;
					}
				}
				//System.out.println(oObjectiveFunction.getTravelTimeFromTourOfficeToLandmark(i));
			}
			
			for (int i = 1; i < iNumberOfLandmarks; i++) {
				int minTravelTime = Integer.MAX_VALUE;
				representation[i] = 0;
				for (int j = 0; j < iNumberOfLandmarks; j++) {
					int isSolution = 0;
					for (int p = 0; p < i; p++) {
						if (j == representation[p]) {
							isSolution = 1;
						}
					}
					if (isSolution == 1) {
						continue;
					}
					else {
						if (oObjectiveFunction.getTravelTime(representation[i-1], j) < minTravelTime) {
							minTravelTime = oObjectiveFunction.getTravelTime(representation[i - 1], j);
							representation[i] = j;
						}
						else if (oObjectiveFunction.getTravelTime(representation[i-1], j) == minTravelTime) {
							if (Math.random() < 0.5) {
								minTravelTime = oObjectiveFunction.getTravelTime(representation[i - 1], j);
								representation[i] = j;
							}
						}
					}
				}			
			}
			//System.out.println(iNumberOfLandmarks);
			/*for (int i = 0; i < iNumberOfLandmarks; i++) {
				System.out.println(representation[i]);
			}*/
			SolutionRepresentation solution = new SolutionRepresentation(representation);
			return new SDSSTPSolution(solution, oObjectiveFunction.getObjectiveFunctionValue(solution), iNumberOfLandmarks);
			
		}
		
		else {
			return null;
		}
	}

	@Override
	public ObjectiveFunctionInterface getSDSSTPObjectiveFunction() {

		return oObjectiveFunction;
	}

	@Override
	public int getNumberOfLandmarks() {

		return iNumberOfLandmarks;
	}

	@Override
	public SDSSTPLocation getLocationForLandmark(int deliveryId) {

		return aoLandmarks[deliveryId];
	}

	@Override
	public SDSSTPLocation getTourOffice() {

		return this.oTourOffice;
	}

	@Override
	public ArrayList<SDSSTPLocation> getSolutionAsListOfLocations(SDSSTPSolutionInterface oSolution) {

		ArrayList<SDSSTPLocation> locs = new ArrayList<>();
		locs.add(oTourOffice);
		int[] aiDeliveries = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		for (int i = 0; i < aiDeliveries.length; i++) {
			locs.add(getLocationForLandmark(aiDeliveries[i]));
		}
		locs.add(oTourOffice);
		return locs;
	}

	@Override
	public String getInstanceName() {
		
		return strInstanceName;
	}
	

}
