package com.aim.project.sdsstp;

import com.aim.project.sdsstp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.sdsstp.interfaces.SolutionRepresentationInterface;

public class SDSSTPObjectiveFunction implements ObjectiveFunctionInterface {

	private final int[][] aiTimeDistanceMatrix;

	private final int[] aiTimeDistancesFromTourOffice;

	private final int[] aiTimeDistancesToTourOffice;

	private final int[] aiVisitingDurations;

	public SDSSTPObjectiveFunction(int[][] aiTimeDistanceMatrix, int[] aiTimeDistancesFromTourOffice,
			int[] aiTimeDistancesToTourOffice, int[] aiVisitingDurations) {

		this.aiTimeDistanceMatrix = aiTimeDistanceMatrix;
		this.aiTimeDistancesFromTourOffice = aiTimeDistancesFromTourOffice;
		this.aiTimeDistancesToTourOffice = aiTimeDistancesToTourOffice;
		this.aiVisitingDurations = aiVisitingDurations;
	}

	@Override
	public int getObjectiveFunctionValue(SolutionRepresentationInterface solution) {

		// TODO
		int[] rep = solution.getSolutionRepresentation();
		
		int totalVisit = 0;
		for (int i = 0; i<rep.length; i++) {
			totalVisit += getVisitingTimeAt(rep[i]);
		}
		
		int landmarkTime = 0;
		for (int i = 0; i<rep.length-1; i++) {
			landmarkTime += getTravelTime(rep[i], rep[i+1]);
		}
		return getTravelTimeFromTourOfficeToLandmark(rep[0]) + getTravelTimeFromLandmarkToTourOffice(rep[rep.length-1]) + totalVisit + landmarkTime;
	}

	@Override
	public int getTravelTime(int location_a, int location_b) {

		// TODO
		return aiTimeDistanceMatrix[location_a][location_b];
		
	}

	@Override
	public int getVisitingTimeAt(int landmarkId) {

		// TODO
		return aiVisitingDurations[landmarkId];
	}

	@Override
	public int getTravelTimeFromTourOfficeToLandmark(int toLandmarkId) {
		
		// TODO
		return aiTimeDistancesFromTourOffice[toLandmarkId];
		
	}

	@Override
	public int getTravelTimeFromLandmarkToTourOffice(int fromLandmarkId) {
		
		// TODO
		return aiTimeDistancesToTourOffice[fromLandmarkId];
	}
	
}
