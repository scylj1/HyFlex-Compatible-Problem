package com.aim.project.sdsstp.solution;

import com.aim.project.sdsstp.interfaces.SolutionRepresentationInterface;

/**
 * 
 * @author Warren G. Jackson
 * 
 *
 */
public class SolutionRepresentation implements SolutionRepresentationInterface {

	private int[] representation;
	
	public SolutionRepresentation(int[] representation) {
		
		this.representation = representation;
	}
	
	@Override
	public int[] getSolutionRepresentation() {

		// TODO
		return representation;
	}

	@Override
	public void setSolutionRepresentation(int[] solution) {
		
		// TODO
		this.representation = solution;
	}

	@Override
	public int getNumberOfLandmarks() {

		// TODO
		return representation.length;
	}

	@Override
	public SolutionRepresentationInterface clone() {
		
		// TODO
		int[] rep = new int[representation.length];
		for (int i=0; i<representation.length; i++) {
			rep[i] = representation[i];
		}
		return new SolutionRepresentation(rep);
	}

}
